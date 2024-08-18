package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.TeachPlanTreeDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TeachPlanService {
    List<TeachPlanTreeDTO> getTeachPlanTreeNodes(Long courseId);
}
