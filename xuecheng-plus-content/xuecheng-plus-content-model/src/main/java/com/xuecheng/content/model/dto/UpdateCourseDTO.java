package com.xuecheng.content.model.dto;

import com.xuecheng.base.execption.ValidationGroups;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCourseDTO {
    @NotEmpty(message = "课程id不能为空")
    private Long id;
    private Long companyId;
    private String companyName;
    @NotEmpty(message = "修改课程名称不能为空", groups = ValidationGroups.Update.class)
    private String name;
    private String tags;
    @NotEmpty(message = "课程分类不能为空")
    private String mt;
    @NotEmpty(message = "课程分类不能为空")
    private String st;
    @NotEmpty(message = "课程等级不能为空")
    private String grade;
    private String description;
    private String users;
    private String pic;
    private String teachmode;
    @NotEmpty(message = "收费规则不能为空")
    private String charge;
    private Double originalPrice;
    private Double price;
    private String qq;
    private String wechat;
    private String phone;
    private Integer validDays;
}
