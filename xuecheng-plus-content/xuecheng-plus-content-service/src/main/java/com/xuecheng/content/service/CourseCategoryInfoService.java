package com.xuecheng.content.service;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.CourseCategoryTreeDTO;
import com.xuecheng.content.model.po.CourseCategory;

import java.util.List;

public interface CourseCategoryInfoService {
    CourseCategory selectById(String id);

    PageResult<CourseCategory> selectByCondition(String name, String label, Integer isShow, Integer orderby, Integer isLeaf, PageParams pageParams);

    List<CourseCategoryTreeDTO> selectTreeNodes(String id);
}
