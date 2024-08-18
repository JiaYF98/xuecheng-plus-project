package com.xuecheng.service;

import com.xuecheng.content.model.dto.TeachPlanTreeDTO;
import com.xuecheng.content.service.TeachPlanService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TeachPlanServiceTest {
    @Autowired
    private TeachPlanService teachPlanService;

    @Test
    public void testGetTeachPlanTreeNodes() {
        List<TeachPlanTreeDTO> teachPlanTreeNodes = teachPlanService.getTeachPlanTreeNodes(82L);
        Assertions.assertNotNull(teachPlanTreeNodes);
    }
}
