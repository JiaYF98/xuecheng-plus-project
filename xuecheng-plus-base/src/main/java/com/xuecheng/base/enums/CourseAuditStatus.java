package com.xuecheng.base.enums;

import lombok.Getter;

@Getter
public enum CourseAuditStatus {
    UNKNOWN("202000"),
    FAILED("202001"),
    NOT_SUBMITTED("202002"),
    SUBMITTED("202003"),
    PASS("202004"),
    ;
    private final String code;

    CourseAuditStatus(String code) {
        this.code = code;
    }

    public static CourseAuditStatus of(String code) {
        for (CourseAuditStatus courseAuditStatus : CourseAuditStatus.values()) {
            if (courseAuditStatus.code.equals(code)) {
                return courseAuditStatus;
            }
        }
        return UNKNOWN;
    }
}
