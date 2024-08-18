package com.xuecheng.content.service.impl;

import com.xuecheng.base.constant.TeachPlanConstant;
import com.xuecheng.content.mapper.TeachplanMapper;
import com.xuecheng.content.mapper.TeachplanMediaMapper;
import com.xuecheng.content.model.dto.TeachPlanTreeDTO;
import com.xuecheng.content.model.po.Teachplan;
import com.xuecheng.content.service.TeachPlanService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TeachPlanServiceImpl implements TeachPlanService {
    @Autowired
    private TeachplanMapper teachPlanMapper;
    @Autowired
    private TeachplanMediaMapper teachplanMediaMapper;

    @Override
    public List<TeachPlanTreeDTO> getTeachPlanTreeNodes(Long courseId) {
        List<Teachplan> teachplans = teachPlanMapper.selectByCourseId(courseId);

        // 创建章节点
        Map<Long, TeachPlanTreeDTO> chaptersMap = teachplans.stream()
                .filter(teachplan -> Objects.equals(teachplan.getParentid(), TeachPlanConstant.chapterParentId))
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
}
