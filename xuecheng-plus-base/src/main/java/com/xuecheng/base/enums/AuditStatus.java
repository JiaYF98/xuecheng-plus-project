package com.xuecheng.base.enums;

import lombok.Getter;

@Getter
public enum AuditStatus {
    UNKNOWN("202000"),
    FAILED("202001"),
    UNAUDITED("202002"),
    PASS("202003"),
    ;
    private final String code;

    AuditStatus(String code) {
        this.code = code;
    }

    public static AuditStatus of(String code) {
        for (AuditStatus auditStatus : AuditStatus.values()) {
            if (auditStatus.code.equals(code)) {
                return auditStatus;
            }
        }
        return UNKNOWN;
    }
}
