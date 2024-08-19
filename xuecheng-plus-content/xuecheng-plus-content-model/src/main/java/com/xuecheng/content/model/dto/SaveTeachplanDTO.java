package com.xuecheng.content.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveTeachplanDTO {
    // 教学计划id
    private Long id;
    // 课程计划名称
    @NotBlank(message = "课程计划名称不能为空")
    private String pname;
    // 课程计划父级id
    @NotNull(message = "parentid不能为null")
    private Long parentid;
    // 层级，分1、2、3级
    @NotNull(message = "grade不能为null")
    private Integer grade;
    // 课程类型：1 视频、2 文档
    private String mediaType;
    // 课程标识
    @NotNull(message = "courseId不能为null")
    private Long courseId;
    // 课程发布标识
    private Long coursePubId;
    // 是否支持试学或预览（试看）
    private Character isPreview;
}
