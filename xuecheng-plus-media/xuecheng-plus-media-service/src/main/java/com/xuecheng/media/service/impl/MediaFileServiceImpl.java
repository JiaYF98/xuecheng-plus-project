package com.xuecheng.media.service.impl;

import com.xuecheng.base.enums.FileType;
import com.xuecheng.base.execption.XueChengPlusException;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.media.mapper.MediaFilesMapper;
import com.xuecheng.media.model.dto.QueryMediaParamsDTO;
import com.xuecheng.media.model.dto.UploadFileParamsDTO;
import com.xuecheng.media.model.dto.UploadFileResultDTO;
import com.xuecheng.media.model.po.MediaFiles;
import com.xuecheng.media.service.MediaFileService;
import com.xuecheng.media.utils.FileUtil;
import com.xuecheng.media.utils.MinioUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author Mr.M
 * @version 1.0
 * @description TODO
 * @date 2022/9/10 8:58
 */
@Slf4j
@Service
public class MediaFileServiceImpl implements MediaFileService {

    @Autowired
    private MediaFilesMapper mediaFilesMapper;

    @Autowired
    private MinioUtil minioUtil;

    //存储普通文件
    @Value("${minio.bucket.files}")
    private String mediaFilesBucket;

    @Override
    public PageResult<MediaFiles> queryMediaFiels(Long companyId, PageParams pageParams, QueryMediaParamsDTO queryMediaParamsDTO) {
        List<MediaFiles> mediaFiles = mediaFilesMapper.selectByPage(pageParams);
        return new PageResult<>(mediaFiles, mediaFiles.size(), pageParams.getPageNo(), pageParams.getPageSize());
    }


    //获取文件默认存储目录路径 年/月/日
    private String getDefaultFolderPath() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date()).replace("-", "/") + "/";
    }


    /**
     * @param fileMd5             文件md5值
     * @param uploadFileParamsDTO 上传文件的信息
     * @param bucket              桶
     * @param objectName          对象名称
     * @return com.xuecheng.media.model.po.MediaFiles
     * @description 将文件信息添加到文件表
     * @author Mr.M
     * @date 2022/10/12 21:22
     */
    public MediaFiles addMediaFilesToDB(String fileMd5, UploadFileParamsDTO uploadFileParamsDTO, String bucket, String objectName) {
        //将文件信息保存到数据库
        MediaFiles mediaFiles = mediaFilesMapper.selectById(fileMd5);
        if (mediaFiles == null) {
            mediaFiles = MediaFiles.builder()
                    .id(fileMd5)
                    .bucket(bucket)
                    .filePath(objectName)
                    .fileId(fileMd5)
                    .url("/" + bucket + "/" + objectName)
                    .createDate(LocalDateTime.now())
                    .status("1")
                    .auditStatus("002003")
                    .build();
            BeanUtils.copyProperties(uploadFileParamsDTO, mediaFiles);
            //插入数据库
            int insert = mediaFilesMapper.insert(mediaFiles);
            if (insert <= 0) {
                log.debug("向数据库保存文件失败,bucket:{},objectName:{}", bucket, objectName);
                return null;
            }
            return mediaFiles;
        }
        return mediaFiles;
    }

    @Override
    public UploadFileResultDTO uploadFile(MultipartFile fileData) throws IOException {
        //文件名
        String filename = fileData.getOriginalFilename();
        //得到mimeType
        String mimeType = FileUtil.getMimeType(filename);
        //将文件从内存中暂存到磁盘
        File tempFile = File.createTempFile("xuecheng-plus-file", ".temp");
        fileData.transferTo(tempFile);
        //文件路径
        String localFilePath = tempFile.getAbsolutePath();
        //子目录
        String defaultFolderPath = getDefaultFolderPath();
        //文件的md5值
        String fileMd5 = FileUtil.getFileMd5(new File(localFilePath));
        String objectName = defaultFolderPath + fileMd5 + FileUtil.getFileSuffix(mimeType);

        boolean result = minioUtil.uploadFile(mediaFilesBucket, objectName, localFilePath, mimeType);
        if (!result) {
            XueChengPlusException.cast("上传文件失败");
        }

        UploadFileParamsDTO uploadFileParamsDTO = UploadFileParamsDTO.builder()
                .filename(filename)
                .fileSize(fileData.getSize())
                .fileType(FileType.DOCUMENT.getCode())
                .companyId(1232141425L)
                .build();
        //入库文件信息
        MediaFiles mediaFiles = this.addMediaFilesToDB(fileMd5, uploadFileParamsDTO, mediaFilesBucket, objectName);
        if (mediaFiles == null) {
            XueChengPlusException.cast("文件上传后保存信息失败");
        }
        //准备返回的对象
        UploadFileResultDTO uploadFileResultDto = new UploadFileResultDTO();
        BeanUtils.copyProperties(mediaFiles, uploadFileResultDto);

        return uploadFileResultDto;
    }
}
