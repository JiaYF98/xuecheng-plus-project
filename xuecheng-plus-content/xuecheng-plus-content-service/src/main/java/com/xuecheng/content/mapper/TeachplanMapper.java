package com.xuecheng.content.mapper;

import com.xuecheng.base.annotation.DBAutoFill;
import com.xuecheng.base.enums.DBOperationType;
import com.xuecheng.content.model.po.Teachplan;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeachplanMapper {
    List<Teachplan> selectByCourseId(Long courseId);

    Teachplan selectById(Long id);

    @DBAutoFill(DBOperationType.INSERT)
    void insertTeachplan(Teachplan teachplan);

    @DBAutoFill(DBOperationType.UPDATE)
    void updateTeachplanById(Teachplan teachplan);
}
