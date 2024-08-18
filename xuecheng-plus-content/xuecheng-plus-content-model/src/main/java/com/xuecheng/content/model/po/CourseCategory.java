package com.xuecheng.content.model.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseCategory {
    private String id;
    private String name;
    private String label;
    private String parentid;
    private String isShow;
    private Integer orderby;
    private Integer isLeaf;
}
