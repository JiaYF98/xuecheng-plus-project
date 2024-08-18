package com.xuecheng.content.api.controller;

import com.xuecheng.content.model.dto.TeachPlanTreeDTO;
import com.xuecheng.content.service.TeachPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TeachPlanController {
    @Autowired
    private TeachPlanService teachPlanService;

    @GetMapping("/teachplan/{courseId}/tree-nodes")
    public List<TeachPlanTreeDTO> getTeachPlanTreeNodes(@PathVariable("courseId") Long courseId) {
        return teachPlanService.getTeachPlanTreeNodes(courseId);
    }
}
