package com.xuecheng.mapper;

import com.xuecheng.base.enums.CourseAuditStatus;
import com.xuecheng.base.enums.CourseGrade;
import com.xuecheng.base.enums.CourseStatus;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.model.po.CourseBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CourseBaseMapperTest {
    @Autowired
    private CourseBaseMapper courseBaseMapper;

    @Test
    public void testSelectById() {
        CourseBase courseBase = courseBaseMapper.selectById(18L);
        Assertions.assertNotNull(courseBase);
    }

    @Test
    public void testSelectByCondition() {
        PageParams pageParams = new PageParams(1L, 10L);
        String courseName = "java";
        String auditStatus = "202004";
        String publishStatus = "203001";
        List<CourseBase> courseBases = courseBaseMapper.selectByCondition(pageParams, courseName, auditStatus, publishStatus);
        Assertions.assertNotNull(courseBases);
    }

    @Test
    public void testInsertCourseBase() {
        CourseBase courseBase = new CourseBase();
        courseBase.setCompanyId(123321L);
        courseBase.setCompanyName("test-companyName");
        courseBase.setName("test-name");
        courseBase.setUsers("test-users");
        courseBase.setTags("test-tags");
        courseBase.setMt("1-1");
        courseBase.setSt("1-1-1");
        courseBase.setGrade(CourseGrade.PRIMARY.getCode());
        courseBase.setTeachmode("test-teachmode");
        courseBase.setDescription("test-description");
        courseBase.setPic("test-pic");
        courseBase.setAuditStatus(CourseAuditStatus.FAILED.getCode());
        courseBase.setStatus(CourseStatus.UNPUBLISHED.getCode());
        courseBaseMapper.insertCourseBase(courseBase);
        Assertions.assertNotNull(courseBase.getId());
    }

    @Test
    public void testUpdateCourseBase() {
        CourseBase courseBase = new CourseBase();
        courseBase.setId(122L);
        courseBase.setCompanyId(123321L);
        courseBase.setCompanyName("test-companyName-update");
        courseBase.setName("test-name-update");
        courseBase.setUsers("test-users-update");
        courseBase.setTags("test-tags-update");
        courseBase.setMt("1-1");
        courseBase.setSt("1-1-1");
        courseBase.setGrade(CourseGrade.PRIMARY.getCode());
        courseBase.setTeachmode("test-teachmode-update");
        courseBase.setDescription("test-description-update");
        courseBase.setPic("test-pic");
        courseBase.setAuditStatus(CourseAuditStatus.FAILED.getCode());
        courseBase.setStatus(CourseStatus.UNPUBLISHED.getCode());
        courseBaseMapper.updateCourseBase(courseBase);
    }
}
