package com.xuecheng.base.enums;

import lombok.Getter;

@Getter
public enum TechplanStatus {
    NORMAL(1),
    DELETED(0),
    ;
    private final Integer code;

    TechplanStatus(Integer code) {
        this.code = code;
    }
}
