package com.xuecheng.service;

import com.xuecheng.base.enums.ChargeType;
import com.xuecheng.base.enums.CourseGrade;
import com.xuecheng.base.enums.TeachMode;
import com.xuecheng.content.model.dto.AddCourseDTO;
import com.xuecheng.content.model.dto.CourseBaseInfoDTO;
import com.xuecheng.content.model.dto.UpdateCourseDTO;
import com.xuecheng.content.service.CourseBaseInfoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CourseBaseInfoServiceTest {
    @Autowired
    private CourseBaseInfoService courseBaseInfoService;

    @Test
    public void testAddCourse() {
        AddCourseDTO addCourseDTO = new AddCourseDTO();
        addCourseDTO.setMt("1-1");
        addCourseDTO.setSt("1-1-1");
        addCourseDTO.setName("Java-Test");
        addCourseDTO.setGrade(CourseGrade.SENIOR.getCode());
        addCourseDTO.setTeachmode(TeachMode.COMMON.getDesc());
        addCourseDTO.setUsers("初学者");
        addCourseDTO.setCharge(ChargeType.FREE.getCode());
        addCourseDTO.setCompanyId(123321456L);

        CourseBaseInfoDTO courseBaseInfoDTO = courseBaseInfoService.addCourseBase(addCourseDTO);
        Assertions.assertNotNull(courseBaseInfoDTO);
    }

    @Test
    public void testUpdateCourse() {
        UpdateCourseDTO updateCourseDTO = new UpdateCourseDTO();
        updateCourseDTO.setId(122L);
        updateCourseDTO.setCompanyId(123321456L);
        updateCourseDTO.setMt("1-1");
        updateCourseDTO.setSt("1-1-1");
        updateCourseDTO.setName("Java-Test-update");
        updateCourseDTO.setGrade(CourseGrade.SENIOR.getCode());
        updateCourseDTO.setTeachmode(TeachMode.COMMON.getDesc());


        CourseBaseInfoDTO courseBaseInfoDTO = courseBaseInfoService.updateCourseBaseInfo(updateCourseDTO);
        Assertions.assertNotNull(courseBaseInfoDTO);
    }
}
