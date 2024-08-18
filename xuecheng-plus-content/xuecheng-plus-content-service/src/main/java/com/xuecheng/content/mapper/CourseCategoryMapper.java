package com.xuecheng.content.mapper;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.content.model.po.CourseCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseCategoryMapper {
    CourseCategory selectById(String id);

    List<CourseCategory> selectByCondition(String name, String label, Integer isShow, Integer orderby, Integer isLeaf, PageParams pageParams);

    List<CourseCategory> selectTreeNodes(String id);
}
