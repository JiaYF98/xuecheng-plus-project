package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.TeachPlanTreeDTO;

import java.util.List;

public interface TeachPlanService {
    List<TeachPlanTreeDTO> getTeachPlanTreeNodes(Long courseId);
}
