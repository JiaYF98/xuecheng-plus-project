package com.xuecheng.mapper;

import com.xuecheng.content.mapper.TeachplanMediaMapper;
import com.xuecheng.content.model.po.TeachplanMedia;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class TeachplanMediaMapperTest {
    @Autowired
    private TeachplanMediaMapper teachplanMediaMapper;

    @Test
    public void testSelectTeachplanMediaByTeachplanId() {
        TeachplanMedia teachplanMedia = teachplanMediaMapper.selectTeachplanMediaByTeachplanId(149L);
        Assertions.assertNotNull(teachplanMedia);
    }

    @Test
    public void testInsertTeachplanMedia() {
        TeachplanMedia teachplanMedia = new TeachplanMedia();
        teachplanMedia.setTeachplanId(291L);
        teachplanMedia.setMediaId(UUID.randomUUID().toString().replace("-", "").substring(0, 32));
        teachplanMedia.setCourseId(125L);
        teachplanMedia.setMediaFileName("test");
        teachplanMediaMapper.insertTeachplanMedia(teachplanMedia);
    }

    @Test
    public void testDeleteTeachplanMediaByTeachplanId() {
        teachplanMediaMapper.deleteTeachplanMediaByTeachplanId(290L);
    }
}
