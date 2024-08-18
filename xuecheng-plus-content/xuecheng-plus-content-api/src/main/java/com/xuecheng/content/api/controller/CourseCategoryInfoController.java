package com.xuecheng.content.api.controller;

import com.xuecheng.content.model.dto.CourseCategoryTreeDTO;
import com.xuecheng.content.service.CourseCategoryInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("course-category")
public class CourseCategoryInfoController {
    @Autowired
    private CourseCategoryInfoService courseCategoryInfoService;

    @GetMapping("/tree-nodes")
    public List<CourseCategoryTreeDTO> getCourseCategoryTreeNodes() {
        return courseCategoryInfoService.selectTreeNodes("1");
    }
}
