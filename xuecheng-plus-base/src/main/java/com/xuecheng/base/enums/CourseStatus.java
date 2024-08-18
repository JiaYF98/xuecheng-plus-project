package com.xuecheng.base.enums;

import lombok.Getter;

@Getter
public enum CourseStatus {
    UNKNOWN("203000"),
    UNPUBLISHED("203001"),
    PUBLISHED("203002"),
    OFFLINE("203003"),
    ;

    private final String code;

    CourseStatus(String code) {
        this.code = code;
    }

    public static CourseStatus of(String code) {
        for (CourseStatus courseStatus : CourseStatus.values()) {
            if (courseStatus.code.equals(code)) {
                return courseStatus;
            }
        }
        return UNKNOWN;
    }
}
