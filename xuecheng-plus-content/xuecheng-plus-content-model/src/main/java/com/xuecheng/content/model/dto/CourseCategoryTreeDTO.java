package com.xuecheng.content.model.dto;

import com.xuecheng.content.model.po.CourseCategory;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class CourseCategoryTreeDTO extends CourseCategory implements Serializable {
    private List<CourseCategoryTreeDTO> childrenTreeNodes;

    public CourseCategoryTreeDTO(CourseCategory courseCategory) {
        super(courseCategory.getId(), courseCategory.getName(), courseCategory.getLabel(), courseCategory.getParentid(), courseCategory.getIsShow(), courseCategory.getOrderby(), courseCategory.getIsLeaf());
    }
}
