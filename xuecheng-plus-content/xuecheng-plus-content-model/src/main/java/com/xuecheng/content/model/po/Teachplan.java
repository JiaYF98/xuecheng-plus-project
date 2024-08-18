package com.xuecheng.content.model.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Teachplan {
    private Long id;
    private String parentid;
    private Short grade;
    private String mediaType;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String description;
    private String timelength;
    private Integer orderby;
    private Long courseId;
    private Long coursePubId;
    private Integer status;
    private Character isPreview;
    private LocalDateTime createDate;
    private LocalDateTime changeDate;
}
