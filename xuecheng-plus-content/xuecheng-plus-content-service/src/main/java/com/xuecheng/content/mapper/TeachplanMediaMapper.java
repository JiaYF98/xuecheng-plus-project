package com.xuecheng.content.mapper;

import com.xuecheng.content.model.po.TeachplanMedia;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeachplanMediaMapper {
    TeachplanMedia selectTeachplanMediaByTeachplanId(Long teachplanId);

    void insertTeachplanMedia(TeachplanMedia teachplanMedia);

    void deleteTeachplanMediaByTeachplanId(Long teachplanId);
}
