package com.xuecheng.media;

import com.xuecheng.media.utils.Mp4VideoUtil;

import java.io.IOException;

public class Mp4VideoUtilTest {
    public void testFfmpeg() throws IOException {
//        ProcessBuilder builder = new ProcessBuilder();
//        builder.command("D:\\Program Files\\EditPlus\\EditPlus.exe");
//        //将标准输入流和错误输入流合并，通过标准输入流程读取信息
//        builder.redirectErrorStream(true);
//        Process p = builder.start();

        //ffmpeg的路径
        String ffmpeg_path = "D:\\soft\\ffmpeg\\ffmpeg.exe";//ffmpeg的安装位置
        //源avi视频的路径
        String video_path = "D:\\develop\\upload\\02-概述-分库分表是什么.avi";
        //转换后mp4文件的名称
        String mp4_name = "02-概述-分库分表是什么.mp4";
        //转换后mp4文件的路径
        String mp4_path = "D:\\develop\\upload\\02-概述-分库分表是什么.mp4";
        //创建工具类对象
        Mp4VideoUtil videoUtil = new Mp4VideoUtil(ffmpeg_path, video_path, mp4_name, mp4_path);
        //开始视频转换，成功将返回success
        String s = videoUtil.generateMp4();
        System.out.println(s);
    }
}
