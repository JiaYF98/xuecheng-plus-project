package com.xuecheng.media;

import io.minio.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tika.Tika;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class BigFileTest {
    MinioClient minioClient = MinioClient.builder()
            .endpoint("http://localhost:9000")
            .credentials("minioadmin", "minioadmin")
            .build();

    @Test
    public void testBigFile() throws IOException {
        File sourceFile = new File("D:\\embrace\\Music\\Jam - 七月上.flac");
        Path sourcePath = sourceFile.toPath();
        long sourceSize = Files.size(sourcePath);
        int chunkSize = 1024 * 1024 * 5;
        int chunkCount = (int) Math.ceil((double) sourceSize / chunkSize);

        String chunkDir = "D:\\tmp\\chunk\\";
        Path chunkPath = Paths.get(chunkDir);
        Files.createDirectories(chunkPath);

        byte[] buffer = new byte[1024];
        try (RandomAccessFile ra_read = new RandomAccessFile(sourceFile, "r")) {
            for (int i = 0; i < chunkCount; i++) {
                File chunkFile = new File(chunkDir + i + ".part");
                int len;
                try (RandomAccessFile ra_write = new RandomAccessFile(chunkFile, "rw")) {
                    while ((len = ra_read.read(buffer)) != -1) {
                        ra_write.write(buffer, 0, len);
                        if (Files.size(chunkFile.toPath()) >= chunkSize) {
                            break;
                        }
                    }
                }
            }
        }
    }

    @Test
    public void testMerge() throws IOException {
        File chunkDir = new File("D:\\tmp\\chunk\\");
        Stream<File> fileStream = Arrays.stream(Optional.ofNullable(chunkDir.listFiles()).orElse(new File[0]))
                .sorted(Comparator.comparingInt(f -> Integer.parseInt(f.getName().split("\\.")[0])));
        File originalFile = new File("D:\\embrace\\Music\\Jam - 七月上.flac");
        File mergeFile = new File("D:\\tmp\\merge\\Jam - 七月上.flac");
        if (mergeFile.exists()) {
            mergeFile.delete();
        }
        if (!mergeFile.createNewFile()) {
            System.out.println("文件创建失败");
            return;
        }
        try (RandomAccessFile raf_write = new RandomAccessFile(mergeFile, "rw")) {
            raf_write.seek(0);
            byte[] buffer = new byte[1024];
            fileStream.forEach(file -> {
                try (RandomAccessFile raf_read = new RandomAccessFile(file, "r")) {
                    int len;
                    while ((len = raf_read.read(buffer)) != -1) {
                        raf_write.write(buffer, 0, len);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        try (FileInputStream fileInputStream = new FileInputStream(originalFile);
             FileInputStream mergeFileStream = new FileInputStream(mergeFile)) {
            String originalMd5 = DigestUtils.md5Hex(fileInputStream);
            String mergeFileMd5 = DigestUtils.md5Hex(mergeFileStream);
            if (originalMd5.equals(mergeFileMd5)) {
                System.out.println("合并成功");
            } else {
                System.out.println("合并失败");
            }
        }
    }

    @Test
    public void testMinIOChunk() throws Exception {
        File sourceFile = new File("D:\\embrace\\Music\\Jam - 七月上.flac");
        String md5Hex = DigestUtils.md5Hex(new FileInputStream(sourceFile));
        File chunkDir = new File("D:\\tmp\\chunk");
        String path = md5Hex.charAt(0) + "/" + md5Hex.charAt(1) + "/" + md5Hex;
        Arrays.stream(Optional.ofNullable(chunkDir.listFiles()).orElse(new File[0]))
                .forEach(file -> {
                    try {
                        minioClient.uploadObject(UploadObjectArgs.builder()
                                .bucket("video")
                                .filename(file.getPath())
                                .object(path + "/" + "chunk" + "/" + file.getName())
                                .build()
                        );
                    } catch (Exception e) {
                        System.out.println("上传文件失败");
                    }
                });
    }

    @Test
    public void testMinIOMerge() throws Exception {
        String md5Hex = DigestUtils.md5Hex(new FileInputStream("D:\\embrace\\Music\\Jam - 七月上.flac"));
        String path = md5Hex.charAt(0) + "/" + md5Hex.charAt(1) + "/" + md5Hex;
        List<ComposeSource> sources = new ArrayList<>();

        minioClient.composeObject(ComposeObjectArgs.builder()
                .bucket("video")
                .sources(StreamSupport.stream(minioClient.listObjects(ListObjectsArgs.builder()
                                .bucket("video")
                                .prefix(path + "/" + "chunk" + "/")
                                .build()).spliterator(), false)
                        .sorted(Comparator.comparingInt(f -> {
                            try {
                                String fileName = f.get().objectName();
                                return Integer.parseInt(fileName
                                        .substring(fileName.lastIndexOf("/") + 1)
                                        .split("\\.")[0]);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }))
                        .map(result -> {
                                    try {
                                        return ComposeSource.builder()
                                                .bucket("video")
                                                .object(result.get().objectName())
                                                .build();
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                        ).toList())
                .object(path + "/" + md5Hex + ".flac")
                .build());
    }

    @Test
    public void testMimeType() {
        Tika tika = new Tika();
        System.out.println(tika.detect("test.mp4"));
    }
}
