package com.xuecheng.base.enums;

import lombok.Getter;

@Getter
public enum CourseGrade {
    UNKNOWN("204000"),
    PRIMARY("204001"),
    INTERMEDIATE("204002"),
    SENIOR("204003"),
    ;

    private final String code;

    CourseGrade(String code) {
        this.code = code;
    }

    public static CourseGrade of(String code) {
        for (CourseGrade courseGrade : CourseGrade.values()) {
            if (courseGrade.code.equals(code)) {
                return courseGrade;
            }
        }
        return UNKNOWN;
    }
}
