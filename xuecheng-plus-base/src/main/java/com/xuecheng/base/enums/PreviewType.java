package com.xuecheng.base.enums;

import lombok.Getter;

@Getter
public enum PreviewType {
    FALSE('0'),
    TRUE('1'),
    ;

    private final Character code;

    PreviewType(Character code) {
        this.code = code;
    }
}
