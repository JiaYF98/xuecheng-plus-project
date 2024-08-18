package com.xuecheng.content.service;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.AddCourseDTO;
import com.xuecheng.content.model.dto.CourseBaseInfoDTO;
import com.xuecheng.content.model.dto.QueryCourseParamsDTO;
import com.xuecheng.content.model.dto.UpdateCourseDTO;
import com.xuecheng.content.model.po.CourseBase;

public interface CourseBaseInfoService {
    PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDTO queryCourseParamsDTO);

    CourseBaseInfoDTO addCourseBase(AddCourseDTO addCourseDTO);

    CourseBaseInfoDTO getCourseBaseInfo(Long id);

    CourseBaseInfoDTO updateCourseBaseInfo(UpdateCourseDTO updateCourseDTO);
}
