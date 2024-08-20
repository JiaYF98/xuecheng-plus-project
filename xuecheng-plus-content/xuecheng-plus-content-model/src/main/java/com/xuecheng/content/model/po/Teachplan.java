package com.xuecheng.content.model.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Teachplan {
    private Long id;
    // 课程计划名称
    private String pname;
    // 课程计划父级id
    private Long parentid;
    // 层级
    private Integer grade;
    // 课程类型
    private String mediaType;
    // 开始直播时间
    private LocalDateTime startTime;
    // 直播结束时间
    private LocalDateTime endTime;
    // 章节及课时介绍
    private String description;
    // 时长 时:分:秒
    private String timelength;
    // 排序字段
    private Integer orderby;
    // 课程标识
    private Long courseId;
    // 课程发布标识
    private Long coursePubId;
    // 状态（1正常 0删除）
    private Integer status;
    // 是否支持试学或预览
    private Character isPreview;
    // 创建时间
    private LocalDateTime createDate;
    // 修改时间
    private LocalDateTime changeDate;
}
