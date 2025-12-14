package com.xuecheng.media.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class Mp4VideoUtil extends VideoUtil {

    private final String ffmpeg_path;//ffmpeg的安装位置
    private final String video_path;
    private final String mp4_name;
    private final String mp4folder_path;

    public Mp4VideoUtil(String ffmpeg_path, String video_path, String mp4_name, String mp4folder_path) {
        super(ffmpeg_path);
        this.ffmpeg_path = ffmpeg_path;
        this.video_path = video_path;
        this.mp4_name = mp4_name;
        this.mp4folder_path = mp4folder_path;
    }

    //清除已生成的mp4
    private void clear_mp4(String mp4_path) {
        //删除原来已经生成的m3u8及ts文件
        File mp4File = new File(mp4_path);
        if (mp4File.exists() && mp4File.isFile()) {
            mp4File.delete();
        }
    }

    /**
     * 视频编码，生成mp4文件
     *
     * @return 成功返回success，失败返回控制台日志
     */
    public String generateMp4() {
        //清除已生成的mp4
        clear_mp4(mp4folder_path);
        /*
        ffmpeg.exe -i  lucene.avi -c:v libx264 -s 1280x720 -pix_fmt yuv420p -b:a 63k -b:v 753k -r 18 .\lucene.mp4
         */
        List<String> commend = Arrays.asList(ffmpeg_path, "-i", video_path, "-c:v", "libx264", "-y", "-s", "1280x720",
                "-pix_fmt", "yuv420p", "-b:a", "63k", "-b:v", "753k", "-r", "18", mp4folder_path);
        String outstring = null;
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commend);
            //将标准输入流和错误输入流合并，通过标准输入流程读取信息
            builder.redirectErrorStream(true);
            Process p = builder.start();
            outstring = waitFor(p);

        } catch (Exception ex) {
            log.error("Mp4VideoUtil>generateMp4>", ex);
        }
//        Boolean check_video_time = this.check_video_time(video_path, mp4folder_path + mp4_name);
        Boolean check_video_time = this.check_video_time(video_path, mp4folder_path);
        if (!check_video_time) {
            return outstring;
        } else {
            return "success";
        }
    }
}