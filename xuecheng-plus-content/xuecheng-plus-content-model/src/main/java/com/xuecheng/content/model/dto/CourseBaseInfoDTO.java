package com.xuecheng.content.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CourseBaseInfoDTO {
    private Long id;
    private Long companyId;
    private String companyName;
    private String name;
    private String users;
    private String tags;
    private String mt;
    private String mtName;
    private String st;
    private String stName;
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
    private Long coursePubId;
    private LocalDateTime coursePubDate;
    private String charge;
    private Double price;
    private Double originalPrice;
    private String qq;
    private String wechat;
    private String phone;
    private Integer validDays;
}
