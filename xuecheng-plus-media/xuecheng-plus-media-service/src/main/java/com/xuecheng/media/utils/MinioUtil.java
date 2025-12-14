package com.xuecheng.media.utils;

import io.minio.*;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class MinioUtil {
    @Autowired
    private MinioClient minioClient;

    /**
     * 上传文件
     *
     * @param bucket     桶
     * @param objectName 待上传文件名
     * @param filePath   待上传文件路径
     * @param fileType   文件类型
     * @return 上传结果
     */
    public boolean uploadFile(String bucket, String objectName, String filePath, String fileType) {
        try {
            minioClient.uploadObject(UploadObjectArgs.builder()
                    .bucket(bucket)
                    .filename(filePath)
                    .object(objectName)
                    .contentType(Optional.ofNullable(fileType).orElse("application/octet-stream"))
                    .build());
            log.debug("上传文件到minio成功,bucket:{},objectName:{}", bucket, objectName);
            return true;
        } catch (Exception e) {
            log.error("上传文件出错,bucket:{},objectName:{},错误信息:{}", bucket, objectName, e.getMessage());
            return false;
        }
    }

    /**
     * 上传文件
     *
     * @param bucket     桶
     * @param objectName 待上传文件名
     * @param filePath   待上传文件路径
     * @return 上传结果
     */
    public boolean uploadFile(String bucket, String objectName, String filePath) {
        return uploadFile(bucket, objectName, filePath, null);
    }

    /**
     * 下载文件
     *
     * @param bucket     桶
     * @param objectName 待下载文件
     * @return 文件流
     */
    public InputStream downloadFile(String bucket, String objectName) {
        try {
            return minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucket)
                    .object(objectName)
                    .build());
        } catch (Exception e) {
            log.error("下载文件出错,bucket:{},objectName:{},错误信息:{}", bucket, objectName, e.getMessage());
            return null;
        }

    }

    /**
     * 合并文件
     *
     * @param bucket     桶
     * @param chunkNames 待合并文件名称列表
     * @param objectName 合并后的文件名称
     * @return 是否合并成功
     */
    public boolean composeFile(String bucket, @NotNull List<String> chunkNames, String objectName) {
        List<ComposeSource> sources = chunkNames.stream()
                .map(chunkName -> ComposeSource.builder()
                        .bucket(bucket)
                        .object(chunkName)
                        .build())
                .toList();

        try {
            minioClient.composeObject(ComposeObjectArgs.builder()
                    .bucket(bucket)
                    .object(objectName)
                    .sources(sources)
                    .build());
            return true;
        } catch (Exception e) {
            log.error("合并文件出错,bucket:{},objectName:{},错误信息:{}", bucket, objectName, e.getMessage());
            return false;
        }
    }

    /**
     * 删除文件
     *
     * @param bucket     桶
     * @param objectName 待删除文件
     * @return 是否删除成功
     */
    public boolean removeFile(String bucket, String objectName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucket)
                    .object(objectName)
                    .build());
            return true;
        } catch (Exception e) {
            log.error("删除文件出错,bucket:{},objectName:{},错误信息:{}", bucket, objectName, e.getMessage());
            return false;
        }
    }

    /**
     * 删除多个文件
     *
     * @param bucket      桶
     * @param objectNames 待删除文件名
     * @return 删除失败的文件名
     */
    public List<String> removeFiles(String bucket, List<String> objectNames) {
        List<DeleteObject> deleteObjects = objectNames.stream().map(DeleteObject::new).toList();
        Iterable<Result<DeleteError>> results = minioClient.removeObjects(RemoveObjectsArgs.builder()
                .bucket(bucket)
                .objects(deleteObjects)
                .build());
        List<String> errorsObjectName = new ArrayList<>();
        results.forEach(r -> {
            try {
                DeleteError deleteError = r.get();
                errorsObjectName.add(deleteError.objectName());
                log.error("删除文件出错,bucket:{},objectName:{}", bucket, deleteError.objectName());
            } catch (Exception e) {
                log.error("删除文件出错,bucket:{},错误信息:{}", bucket, e.getMessage());
            }
        });
        return errorsObjectName;
    }

    /**
     * 查询某个路径是否存在文件
     *
     * @param bucket 桶
     * @param prefix 待查询路径
     * @return 是否存在
     */
    public boolean checkPrefix(String bucket, String prefix) {
        return minioClient.listObjects(ListObjectsArgs.builder()
                        .bucket(bucket)
                        .prefix(prefix)
                        .maxKeys(1)
                        .build())
                .iterator()
                .hasNext();
    }

    /**
     * 查询文件是否存在
     *
     * @param bucket     桶
     * @param objectName 待查询对象名称
     * @return 是否存在
     */
    public boolean fileExists(String bucket, String objectName) {
        try {
            minioClient.statObject(StatObjectArgs.builder()
                    .bucket(bucket)
                    .object(objectName)
                    .build());
            return true;
        } catch (Exception e) {
            log.debug("文件不存在,bucket:{},objectName:{}", bucket, objectName);
            return false;
        }
    }
}
