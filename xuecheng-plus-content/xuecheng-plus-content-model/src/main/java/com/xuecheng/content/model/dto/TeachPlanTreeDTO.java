package com.xuecheng.content.model.dto;

import com.xuecheng.content.model.po.Teachplan;
import com.xuecheng.content.model.po.TeachplanMedia;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TeachPlanTreeDTO extends Teachplan {
    private List<TeachPlanTreeDTO> teachPlanTreeNodes;
    private TeachplanMedia teachplanMedia;
}
