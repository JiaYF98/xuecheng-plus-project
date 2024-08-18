package com.xuecheng.content.service.impl;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.model.dto.CourseCategoryTreeDTO;
import com.xuecheng.content.model.po.CourseCategory;
import com.xuecheng.content.service.CourseCategoryInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseCategoryInfoServiceImpl implements CourseCategoryInfoService {
    @Autowired
    private CourseCategoryMapper courseCategoryMapper;

    @Override
    public CourseCategory selectById(String id) {
        return courseCategoryMapper.selectById(id);
    }

    @Override
    public PageResult<CourseCategory> selectByCondition(String name, String label, Integer isShow, Integer orderby, Integer isLeaf, PageParams pageParams) {
        List<CourseCategory> courseCategories = courseCategoryMapper.selectByCondition(name, label, isShow, orderby, isLeaf, pageParams);
        return new PageResult<>(courseCategories, courseCategories.size(), pageParams.getPageNo(), pageParams.getPageSize());
    }

    @Override
    public List<CourseCategoryTreeDTO> selectTreeNodes(String id) {
        List<CourseCategoryTreeDTO> result = new ArrayList<>();

        List<CourseCategory> courseCategories = courseCategoryMapper.selectTreeNodes(id);
        Map<String, CourseCategoryTreeDTO> treeDTOMap = courseCategories.stream()
                .filter(courseCategory -> !id.equals(courseCategory.getId()))
                .collect(Collectors.toMap(CourseCategory::getId, CourseCategoryTreeDTO::new, (v1, v2) -> v1));

        courseCategories.stream()
                .filter(courseCategory -> !id.equals(courseCategory.getId()))
                .forEach(courseCategory -> {
                    String parentid = courseCategory.getParentid();
                    String courseCategoryId = courseCategory.getId();
                    Optional.ofNullable(treeDTOMap.get(parentid)).ifPresentOrElse(
                            parentDTO -> {
                                if (parentDTO.getChildrenTreeNodes() == null) {
                                    parentDTO.setChildrenTreeNodes(new ArrayList<>());
                                }
                                parentDTO.getChildrenTreeNodes().add(treeDTOMap.get(courseCategoryId));
                            },
                            () -> result.add(treeDTOMap.get(courseCategoryId)));
                });
        return result;
    }
}
