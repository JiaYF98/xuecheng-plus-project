package com.xuecheng.content.model.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CourseBase {
    private Long id;
    private Long companyId;
    private String companyName;
    private String name;
    private String users;
    private String tags;
    private String mt;
    private String st;
    private String grade;
    private String teachmode;
    private String description;
    private String pic;
    private LocalDateTime createDate;
    private LocalDateTime changeDate;
    private String createPeople;
    private String changePeople;
    private String auditStatus;
    private String status;
}
