package com.xuecheng.content.model.dto;

import com.xuecheng.base.execption.ValidationGroups;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AddCourseDTO {
    // 机构id
    private Long companyId;
    // 机构名称
    private String companyName;
    // 课程名称
    @NotEmpty(message = "修改课程名称不能为空", groups = ValidationGroups.Update.class)
    @NotEmpty(message = "添加课程名称不能为空", groups = ValidationGroups.Update.class)
    private String name;
    // 适用人群
    @NotEmpty(message = "适用人群不能为空")
    private String users;
    // 课程标签
    private String tags;
    // 大分类
    @NotEmpty(message = "课程分类不能为空")
    private String mt;
    // 小分类
    @NotEmpty(message = "课程分类不能为空")
    private String st;
    // 课程等级
    @NotEmpty(message = "课程等级不能为空")
    private String grade;
    // 教学模式（普通，录播，直播等）
    private String teachmode;
    // 课程介绍（非必须）
    private String description;
    // 课程图片
    private String pic;
    // 收费规则，对应数据字典
    @NotEmpty(message = "收费规则不能为空")
    private String charge;
    // 价格
    private Double price;
    // 原价
    private Double originalPrice;
    // qq
    private String qq;
    // 微信
    private String wechat;
    // 联系电话
    private String phone;
    // 有效期
    private Integer validDays;
}
