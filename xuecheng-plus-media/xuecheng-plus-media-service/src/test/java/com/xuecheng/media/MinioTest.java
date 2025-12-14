package com.xuecheng.media;

import io.minio.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.tika.Tika;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterInputStream;

/**
 * @author Mr.M
 * @version 1.0
 * @description 测试minio的sdk
 * @date 2023/2/17 11:55
 */
public class MinioTest {

    MinioClient minioClient =
            MinioClient.builder()
                    .endpoint("http://localhost:9000")
                    .credentials("minioadmin", "minioadmin")
                    .build();

    @Test
    public void test_make_bucket() throws Exception {
        minioClient.makeBucket(MakeBucketArgs.builder()
                .bucket("test-bucket")
                .build()
        );
    }

    @Test
    public void test_upload_file() throws Exception {
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket("test-upload-file-bucket").build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket("test-upload-file-bucket").build());
        }
        minioClient.uploadObject(UploadObjectArgs.builder()
                .bucket("test-upload-file-bucket")
                .filename("D:\\embrace\\Pictures\\素材图片\\桂林漓江.jpg")
                .object("桂林漓江.jpg")
                .build()
        );
    }

    @Test
    public void test_upload() throws Exception {

        //通过扩展名得到媒体资源类型 mimeType
        //根据扩展名取出mimeType
        Tika tika = new Tika();
        String mimeType = tika.detect(".mp4");
        //上传文件的参数信息
        UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder()
                .bucket("testbucket")//桶
                .filename("D:\\develop\\upload\\1.mp4") //指定本地文件路径
//                .object("1.mp4")//对象名 在桶下存储该文件
                .object("test/01/1.mp4")//对象名 放在子目录下
                .contentType(mimeType)//设置媒体文件类型
                .build();

        //上传文件
        minioClient.uploadObject(uploadObjectArgs);


    }

    //删除文件
    @Test
    public void test_delete() throws Exception {

        //RemoveObjectArgs
        RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder().bucket("testbucket").object("1.mp4").build();

        //删除文件
        minioClient.removeObject(removeObjectArgs);


    }

    //查询文件 从minio中下载
    @Test
    public void test_getFile() throws Exception {

        GetObjectArgs getObjectArgs = GetObjectArgs.builder().bucket("testbucket").object("test/01/1.mp4").build();
        //查询远程服务获取到一个流对象
        FilterInputStream inputStream = minioClient.getObject(getObjectArgs);
        //指定输出流
        FileOutputStream outputStream = new FileOutputStream(new File("D:\\develop\\upload\\1a.mp4"));
        IOUtils.copy(inputStream, outputStream);

        //校验文件的完整性对文件的内容进行md5
        FileInputStream fileInputStream1 = new FileInputStream(new File("D:\\develop\\upload\\1.mp4"));
        String source_md5 = DigestUtils.md5Hex(fileInputStream1);
        FileInputStream fileInputStream = new FileInputStream(new File("D:\\develop\\upload\\1a.mp4"));
        String local_md5 = DigestUtils.md5Hex(fileInputStream);
        if (source_md5.equals(local_md5)) {
            System.out.println("下载成功");
        }

    }


}
