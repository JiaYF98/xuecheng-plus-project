package com.xuecheng.service;

import com.xuecheng.base.enums.PreviewType;
import com.xuecheng.base.execption.XueChengPlusException;
import com.xuecheng.content.model.dto.SaveTeachplanDTO;
import com.xuecheng.content.model.dto.TeachPlanTreeDTO;
import com.xuecheng.content.service.TeachplanService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TeachplanServiceTest {
    @Autowired
    private TeachplanService teachplanService;

    @Test
    public void testGetTeachPlanTreeNodes() {
        List<TeachPlanTreeDTO> teachPlanTreeNodes = teachplanService.getTeachPlanTreeNodes(82L);
        Assertions.assertNotNull(teachPlanTreeNodes);
    }

    @Test
    public void testSaveTeachplan() {
        SaveTeachplanDTO saveTeachplanDTO = new SaveTeachplanDTO();
        saveTeachplanDTO.setId(290L);
        saveTeachplanDTO.setIsPreview(PreviewType.TRUE.getCode());
        teachplanService.saveTeachplan(saveTeachplanDTO);
    }

    @Test
    public void testDeleteTeachplanMediaWithSectionNotEmpty() {
        Assertions.assertThrows(XueChengPlusException.class, () -> teachplanService.deleteTeachplan(291L));
    }

    @Test
    public void testDeleteSectionTeachplan() {
        Assertions.assertDoesNotThrow(() -> teachplanService.deleteTeachplan(294L));
    }

    @Test
    public void testDeleteChapterTeachplan() {
        Assertions.assertDoesNotThrow(() -> teachplanService.deleteTeachplan(291L));
    }
}
