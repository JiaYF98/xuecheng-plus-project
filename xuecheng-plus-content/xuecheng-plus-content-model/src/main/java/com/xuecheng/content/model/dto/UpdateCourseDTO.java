package com.xuecheng.content.model.dto;

import com.xuecheng.base.execption.ValidationGroups;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCourseDTO {
    @NotNull(message = "课程id不能为空")
    private Long id;
    private Long companyId;
    private String companyName;
    @NotBlank(message = "修改课程名称不能为空", groups = ValidationGroups.Update.class)
    private String name;
    private String tags;
    @NotBlank(message = "课程分类不能为空")
    private String mt;
    @NotBlank(message = "课程分类不能为空")
    private String st;
    @NotBlank(message = "课程等级不能为空")
    private String grade;
    private String description;
    private String users;
    private String pic;
    private String teachmode;
    @NotBlank(message = "收费规则不能为空")
    private String charge;
    private Double originalPrice;
    private Double price;
    private String qq;
    private String wechat;
    private String phone;
    private Integer validDays;
}
