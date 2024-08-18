package com.xuecheng.service;

import com.xuecheng.content.model.dto.CourseCategoryTreeDTO;
import com.xuecheng.content.service.CourseCategoryInfoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CourseCategoryInfoServiceTest {
    @Autowired
    private CourseCategoryInfoService courseCategoryInfoService;

    @Test
    public void testSelectTreeNodes() {
        List<CourseCategoryTreeDTO> courseCategoryTreeDTOS = courseCategoryInfoService.selectTreeNodes("1");
        Assertions.assertNotNull(courseCategoryTreeDTOS);
    }
}
