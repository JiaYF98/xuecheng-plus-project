package com.xuecheng.media.api;

import com.xuecheng.base.model.RestResponse;
import com.xuecheng.media.service.BigFileService;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "大文件上传接口", description = "大文件上传接口")
@RestController
public class BigFilesController {

    @Autowired
    private BigFileService bigFileService;

    @Schema(description = "文件上传前检查文件")
    @PostMapping("/upload/checkfile")
    public RestResponse<Boolean> checkFile(@RequestParam("fileMd5") String fileMd5) throws Exception {
        return RestResponse.success(bigFileService.checkFile(fileMd5));
    }


    @Schema(description = "分块文件上传前的检测")
    @PostMapping("/upload/checkchunk")
    public RestResponse<Boolean> checkChunk(@RequestParam("fileMd5") String fileMd5,
                                            @RequestParam("chunk") int chunk) throws Exception {
        return RestResponse.success(bigFileService.checkChunk(fileMd5, chunk));
    }

    @Schema(description = "上传分块文件")
    @PostMapping("/upload/uploadchunk")
    public RestResponse<Boolean> uploadChunk(@RequestParam("file") MultipartFile file,
                                             @RequestParam("fileMd5") String fileMd5,
                                             @RequestParam("chunk") int chunk) throws Exception {
        return RestResponse.success(bigFileService.uploadChunk(file, fileMd5, chunk));
    }

    @Schema(description = "合并文件")
    @PostMapping("/upload/mergechunks")
    public RestResponse<Boolean> mergeChunks(@RequestParam("fileMd5") String fileMd5,
                                             @RequestParam("fileName") String fileName,
                                             @RequestParam("chunkTotal") int chunkTotal) throws Exception {
        return RestResponse.success(bigFileService.mergeChunks(fileMd5, fileName, chunkTotal));
    }
}
