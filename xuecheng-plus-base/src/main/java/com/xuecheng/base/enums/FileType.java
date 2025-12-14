package com.xuecheng.base.enums;

import lombok.Getter;

@Getter
public enum FileType {
    DOCUMENT("001001"),
    AUDIO("001002"),
    VIDEO("001003"),
    ;
    private final String code;

    FileType(String code) {
        this.code = code;
    }
}
