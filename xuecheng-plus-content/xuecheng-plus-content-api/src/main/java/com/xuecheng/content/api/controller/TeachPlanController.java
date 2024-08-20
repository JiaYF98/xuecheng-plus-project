package com.xuecheng.content.api.controller;

import com.xuecheng.content.model.dto.SaveTeachplanDTO;
import com.xuecheng.content.model.dto.TeachPlanTreeDTO;
import com.xuecheng.content.service.TeachplanService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TeachPlanController {
    @Autowired
    private TeachplanService teachPlanService;

    @Operation(summary = "查询课程计划")
    @GetMapping("/teachplan/{courseId}/tree-nodes")
    public List<TeachPlanTreeDTO> getTeachPlanTreeNodes(@PathVariable("courseId") Long courseId) {
        return teachPlanService.getTeachPlanTreeNodes(courseId);
    }

    @Operation(summary = "课程计划创建或修改")
    @PostMapping("/teachplan")
    public void saveTeachplan(@RequestBody @Validated SaveTeachplanDTO saveTeachplanDTO) {
        teachPlanService.saveTeachplan(saveTeachplanDTO);
    }

    @Operation(summary = "删除课程计划")
    @DeleteMapping("/teachplan/{id}")
    public void deleteTeachplan(@PathVariable("id") Long id) {
        teachPlanService.deleteTeachplan(id);
    }
}
