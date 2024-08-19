package com.xuecheng.mapper;

import com.xuecheng.base.constant.TeachPlanConstant;
import com.xuecheng.base.enums.MediaType;
import com.xuecheng.content.mapper.TeachplanMapper;
import com.xuecheng.content.model.po.Teachplan;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TeachplanMapperTest {
    @Autowired
    private TeachplanMapper teachplanMapper;

    @Test
    public void testInsertTeachplan() {
        Teachplan teachplan = new Teachplan();
        teachplan.setParentid(TeachPlanConstant.CHAPTER_PARENT_ID);
        teachplan.setPname("test");
        teachplan.setGrade(1);
        teachplan.setMediaType(MediaType.VIDEO.getCode());
        teachplan.setOrderby(0);
        teachplan.setCourseId(125L);
        teachplan.setStatus(1);
        teachplanMapper.insertTeachplan(teachplan);
    }

    @Test
    public void testUpdateTechplan() {
        Teachplan teachplan = new Teachplan();
        teachplan.setId(290L);
        teachplan.setOrderby(1);
        teachplanMapper.updateTeachplanById(teachplan);
    }
}
