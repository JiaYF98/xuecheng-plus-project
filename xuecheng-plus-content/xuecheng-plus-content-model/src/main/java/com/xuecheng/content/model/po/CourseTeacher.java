package com.xuecheng.content.model.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CourseTeacher {
    private Long id;
    private Long courseId;
    private String teacherName;
    private String position;
    private String introduction;
    private String photograph;
    private LocalDateTime createDate;
}
