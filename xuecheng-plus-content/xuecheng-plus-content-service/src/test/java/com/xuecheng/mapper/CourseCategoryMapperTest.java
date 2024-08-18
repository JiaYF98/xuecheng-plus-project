package com.xuecheng.mapper;

import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.model.po.CourseCategory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CourseCategoryMapperTest {
    @Autowired
    private CourseCategoryMapper courseCategoryMapper;

    @Test
    public void testSelectTreeNodes() {
        List<CourseCategory> courseCategories = courseCategoryMapper.selectTreeNodes("1");
        Assertions.assertNotNull(courseCategories);
    }

}
