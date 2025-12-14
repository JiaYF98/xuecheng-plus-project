package com.xuecheng.media.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tika.Tika;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;

import java.io.File;
import java.io.FileInputStream;

@Slf4j
public class FileUtil {
    private static final Tika tika = new Tika();

    //根据扩展名获取mimeType
    public static String getMimeType(String fileName) {
        return tika.detect(fileName);
    }

    public static String getFileSuffix(String mimeType) {
        MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
        try {
            return allTypes.forName(mimeType).getExtension();
        } catch (MimeTypeException e) {
            log.info("获取文件后缀失败，错误信息{}", e.toString());
            return ".dat";
        }
    }

    //获取文件的md5
    public static String getFileMd5(File file) {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            return DigestUtils.md5Hex(fileInputStream);
        } catch (Exception e) {
            log.error("获取文件md5失败，错误信息{}", e.toString());
            return null;
        }
    }
}
