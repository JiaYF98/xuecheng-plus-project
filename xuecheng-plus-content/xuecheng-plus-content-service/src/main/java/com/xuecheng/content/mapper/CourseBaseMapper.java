package com.xuecheng.content.mapper;

import com.xuecheng.base.annotation.DBAutoFill;
import com.xuecheng.base.enums.DBOperationType;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.content.model.po.CourseBase;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseBaseMapper {
    Long selectCount();

    CourseBase selectById(Long id);

    List<CourseBase> selectByCondition(PageParams pageParams, String courseName, String auditStatus, String publishStatus);

    @DBAutoFill(DBOperationType.INSERT)
    void insertCourseBase(CourseBase courseBase);

    @DBAutoFill(DBOperationType.UPDATE)
    Integer updateCourseBase(CourseBase courseBase);
}
