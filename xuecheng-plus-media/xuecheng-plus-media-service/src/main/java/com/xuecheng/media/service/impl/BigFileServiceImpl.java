package com.xuecheng.media.service.impl;

import com.xuecheng.media.model.dto.UploadFileParamsDTO;
import com.xuecheng.media.service.BigFileService;
import com.xuecheng.media.service.MediaFileService;
import com.xuecheng.media.utils.FileUtil;
import com.xuecheng.media.utils.MinioUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class BigFileServiceImpl implements BigFileService {
    @Autowired
    private MediaFileService mediaFileService;

    @Autowired
    private MinioUtil minioUtil;

    @Value("${minio.bucket.videos}")
    private String videosBucket;

    @Override
    public Boolean checkFile(String fileMd5) {
        return minioUtil.fileExists(videosBucket,
                String.format("%s/%s/%s", fileMd5.charAt(0), fileMd5.charAt(1), fileMd5));
    }

    @Override
    public Boolean checkChunk(String fileMd5, int chunk) {
        return minioUtil.fileExists(videosBucket,
                String.format("%s/%s/%s/chunk/%s.part", fileMd5.charAt(0), fileMd5.charAt(1), fileMd5, chunk));
    }

    @Override
    public Boolean uploadChunk(MultipartFile file, String fileMd5, int chunk) throws IOException {
        String objectName = String.format("%s/%s/%s/chunk/%s.part", fileMd5.charAt(0), fileMd5.charAt(1), fileMd5, chunk);
        File tempFile = File.createTempFile("xuecheng-plus-file", ".temp");
        file.transferTo(tempFile);
        return minioUtil.uploadFile(videosBucket, objectName, tempFile.getAbsolutePath());
    }

    @Override
    public Boolean mergeChunks(String fileMd5, String fileName, int chunkTotal) {
        String mimeType = FileUtil.getMimeType(fileName);
        log.info("开始合并文件，fileMd5:{}, chunkTotal:{}", fileMd5, chunkTotal);
        String prefix = String.format("%s/%s/%s", fileMd5.charAt(0), fileMd5.charAt(1), fileMd5);
        List<String> chunkNames = new ArrayList<>();
        for (int i = 0; i < chunkTotal; i++) {
            String chunkFileName = String.format("%s/chunk/%d.part", prefix, i);
            chunkNames.add(chunkFileName);
        }

        String objectName = String.format("%s/%s%s", prefix, fileMd5, FileUtil.getFileSuffix(mimeType));
        log.info("开始合并文件，objectName:{}", objectName);
        if (!minioUtil.composeFile(videosBucket, chunkNames, objectName)) {
            return false;
        }

        try (InputStream inputStream = minioUtil.downloadFile(videosBucket, objectName)) {
            if (!fileMd5.equals(DigestUtils.md5Hex(inputStream))) {
                log.error("文件不完整，合并文件失败，bucket:{}, objectName{}", videosBucket, objectName);
                return false;
            }

            log.debug("合并文件成功，bucket:{}, objectName{}", videosBucket, objectName);
            UploadFileParamsDTO uploadFileParamsDTO = UploadFileParamsDTO.builder()
                    .companyId(1232141425L)
                    .fileType(mimeType)
                    .filename(fileName)
                    .build();
            mediaFileService.addMediaFilesToDB(fileMd5, uploadFileParamsDTO, videosBucket, objectName);
            return CollectionUtils.isEmpty(minioUtil.removeFiles(videosBucket, chunkNames));
        } catch (Exception e) {
            log.error("合并文件出错,bucket:{},objectName:{},错误信息:{}", videosBucket, objectName, e.getMessage());
            return false;
        }
    }
}
