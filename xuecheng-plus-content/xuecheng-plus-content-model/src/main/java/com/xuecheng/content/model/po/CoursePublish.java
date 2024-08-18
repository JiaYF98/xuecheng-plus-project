package com.xuecheng.content.model.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CoursePublish {
    private Long id;
    private Long companyId;
    private String companyName;
    private String name;
    private String users;
    private String tags;
    private String username;
    private String mt;
    private String mtName;
    private String st;
    private String stName;
    private String grade;
    private String teachmode;
    private String pic;
    private String description;
    private String market;
    private String teachplan;
    private String teachers;
    private LocalDateTime createDate;
    private LocalDateTime onlineDate;
    private LocalDateTime offlineDate;
    private String status;
    private String remark;
    private String charge;
    private Double price;
    private Double originalPrice;
    private Integer validDays;
}
