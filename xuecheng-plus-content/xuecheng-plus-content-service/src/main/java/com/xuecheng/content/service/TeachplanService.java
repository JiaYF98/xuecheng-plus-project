package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.SaveTeachplanDTO;
import com.xuecheng.content.model.dto.TeachPlanTreeDTO;

import java.util.List;

public interface TeachplanService {
    List<TeachPlanTreeDTO> getTeachPlanTreeNodes(Long courseId);

    void saveTeachplan(SaveTeachplanDTO saveTeachplanDTO);

    void deleteTeachplan(Long id);
}
