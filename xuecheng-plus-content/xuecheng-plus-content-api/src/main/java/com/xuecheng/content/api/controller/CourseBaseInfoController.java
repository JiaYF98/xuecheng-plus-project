package com.xuecheng.content.api.controller;

import com.xuecheng.base.execption.ValidationGroups;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.AddCourseDTO;
import com.xuecheng.content.model.dto.CourseBaseInfoDTO;
import com.xuecheng.content.model.dto.QueryCourseParamsDTO;
import com.xuecheng.content.model.dto.UpdateCourseDTO;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.service.CourseBaseInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "课程信息管理接口", description = "课程信息管理接口")
public class CourseBaseInfoController {
    @Autowired
    private CourseBaseInfoService courseBaseInfoService;

    @Operation(summary = "课程查询接口")
    @PostMapping("/course/list")
    public PageResult<CourseBase> list(PageParams pageParams, @RequestBody(required = false) QueryCourseParamsDTO queryCourseParamsDTO) {
        return courseBaseInfoService.queryCourseBaseList(pageParams, queryCourseParamsDTO);
    }

    @Operation(summary = "新增课程基础信息")
    @PostMapping("/course")
    public CourseBaseInfoDTO addCourseBase(@RequestBody @Validated(ValidationGroups.Insert.class) AddCourseDTO addCourseDTO) {
        // todo 机构id，由于认证系统没有上线暂时硬编码
        addCourseDTO.setCompanyId(1232141425L);
        return courseBaseInfoService.addCourseBase(addCourseDTO);
    }

    @Operation(summary = "查询课程基础信息")
    @GetMapping("/course/{id}")
    public CourseBaseInfoDTO getCourseBaseById(@PathVariable("id") Long id) {
        return courseBaseInfoService.getCourseBaseInfo(id);
    }

    @Operation(summary = "更新课程基础信息")
    @PutMapping("/course")
    public CourseBaseInfoDTO updateCourseBaseById(@RequestBody UpdateCourseDTO updateCourseDTO) {
        return courseBaseInfoService.updateCourseBaseInfo(updateCourseDTO);
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

}
