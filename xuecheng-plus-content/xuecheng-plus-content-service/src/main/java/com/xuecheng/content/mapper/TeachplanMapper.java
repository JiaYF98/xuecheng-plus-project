package com.xuecheng.content.mapper;

import com.xuecheng.content.model.po.Teachplan;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeachplanMapper {
    List<Teachplan> selectByCourseId(Long courseId);
}
