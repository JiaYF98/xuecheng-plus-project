package com.xuecheng.content.mapper;

import com.xuecheng.content.model.po.CourseMarket;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CourseMarketMapper {
    CourseMarket selectById(Long id);
    void insertCourseMarket(CourseMarket courseMarket);
}
