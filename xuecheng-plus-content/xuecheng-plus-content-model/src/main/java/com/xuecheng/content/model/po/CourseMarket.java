package com.xuecheng.content.model.po;

import lombok.Data;

@Data
public class CourseMarket {
    private Long id;
    private String charge;
    private Double price;
    private Double originalPrice;
    private String qq;
    private String wechat;
    private String phone;
    private Integer validDays;
}
