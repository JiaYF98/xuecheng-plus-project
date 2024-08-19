package com.xuecheng.base.enums;

import lombok.Getter;

@Getter
public enum MediaType {
    VIDEO("1"),
    DOCUMENT("2"),
    ;
    private final String code;

    MediaType(String code) {
        this.code = code;
    }
}
