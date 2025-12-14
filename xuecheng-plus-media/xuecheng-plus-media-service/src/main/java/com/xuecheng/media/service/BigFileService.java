package com.xuecheng.media.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface BigFileService {
    Boolean checkFile(String fileMd5);

    Boolean checkChunk(String fileMd5, int chunk) throws Exception;

    Boolean uploadChunk(MultipartFile file, String fileMd5, int chunk) throws IOException;

    Boolean mergeChunks(String fileMd5, String fileName, int chunkTotal);
}
