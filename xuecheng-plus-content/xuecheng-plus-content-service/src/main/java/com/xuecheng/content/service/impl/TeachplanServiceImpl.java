package com.xuecheng.content.service.impl;

import com.xuecheng.base.constant.TeachPlanConstant;
import com.xuecheng.base.enums.CommonError;
import com.xuecheng.base.enums.PreviewType;
import com.xuecheng.base.enums.TechplanStatus;
import com.xuecheng.base.execption.XueChengPlusException;
import com.xuecheng.content.mapper.TeachplanMapper;
import com.xuecheng.content.mapper.TeachplanMediaMapper;
import com.xuecheng.content.model.dto.SaveTeachplanDTO;
import com.xuecheng.content.model.dto.TeachPlanTreeDTO;
import com.xuecheng.content.model.po.Teachplan;
import com.xuecheng.content.service.TeachplanService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TeachplanServiceImpl implements TeachplanService {
    @Autowired
    private TeachplanMapper teachPlanMapper;
    @Autowired
    private TeachplanMediaMapper teachplanMediaMapper;

    @Override
    public List<TeachPlanTreeDTO> getTeachPlanTreeNodes(Long courseId) {
        List<Teachplan> teachplans = teachPlanMapper.selectByCourseId(courseId);

        // 创建章节点
        Map<Long, TeachPlanTreeDTO> chaptersMap = teachplans.stream()
                .filter(teachplan -> Objects.equals(teachplan.getParentid(), TeachPlanConstant.CHAPTER_PARENT_ID))
                .collect(Collectors.toMap(Teachplan::getId, teachplan -> {
                    TeachPlanTreeDTO teachPlanTreeDTO = new TeachPlanTreeDTO();
                    BeanUtils.copyProperties(teachplan, teachPlanTreeDTO);
                    return teachPlanTreeDTO;
                }, (v1, v2) -> v1));

        // 将节节点放入到章节点中
        teachplans.stream()
                .filter(teachplan -> chaptersMap.containsKey(teachplan.getParentid()))
                .forEach(teachplan -> {
                    // 获取章节点
                    TeachPlanTreeDTO chapter = chaptersMap.get(teachplan.getParentid());
                    // 如果章节点海没有节节点的列表，则创建一个新列表
                    if (chapter.getTeachPlanTreeNodes() == null) {
                        chapter.setTeachPlanTreeNodes(new ArrayList<>());
                    }

                    // 创建节节点
                    TeachPlanTreeDTO teachPlanTreeDTO = new TeachPlanTreeDTO();
                    BeanUtils.copyProperties(teachplan, teachPlanTreeDTO);
                    // 查询媒质信息
                    teachPlanTreeDTO.setTeachplanMedia(teachplanMediaMapper.selectTeachplanMediaByTeachplanId(teachplan.getId()));
                    // 向章节点中添加节节点
                    chapter.getTeachPlanTreeNodes().add(teachPlanTreeDTO);
                });

        return chaptersMap.values().stream().toList();
    }

    @Transactional
    @Override
    public void saveTeachplan(SaveTeachplanDTO saveTeachplanDTO) {
        Long id = saveTeachplanDTO.getId();
        Teachplan teachplan = teachPlanMapper.selectById(id);
        // 如果存在，则修改，不存在则插入
        if (teachplan != null) {
            BeanUtils.copyProperties(saveTeachplanDTO, teachplan);
            teachPlanMapper.updateTeachplanById(teachplan);
        } else {
            teachplan = new Teachplan();
            BeanUtils.copyProperties(saveTeachplanDTO, teachplan);
            teachplan.setStatus(TechplanStatus.NORMAL.getCode());
            teachplan.setIsPreview(PreviewType.TRUE.getCode());
            teachPlanMapper.insertTeachplan(teachplan);
        }
    }

    @Transactional
    @Override
    public void deleteTeachplan(Long id) {
        // todo 会有并发问题
        Teachplan teachplan = teachPlanMapper.selectById(id);
        if (teachplan == null) {
            XueChengPlusException.cast(CommonError.QUERY_NULL);
        }

        // 如果要删除的是章 要检查节是否存在 不存在时才可以删除
        if (TeachPlanConstant.CHAPTER_GRADE.equals(teachplan.getGrade())) {
            Long count = teachPlanMapper.selectSectionCount(id);
            if (count > 0) {
                XueChengPlusException.cast("章下面有节时不允许删除");
            }
        } else {
            teachplanMediaMapper.deleteTeachplanMediaByTeachplanId(id);
        }

        teachPlanMapper.deleteTeachplan(id);
    }
}
