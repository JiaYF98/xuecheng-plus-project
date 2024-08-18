package com.xuecheng.base.enums;

import lombok.Getter;

@Getter
public enum TeachMode {
    UNKNOWN("未知"),
    COMMON("普通"),
    record("录播"),
    live("直播"),
    ;

    private final String desc;

    TeachMode(String desc) {
        this.desc = desc;
    }

    public static TeachMode of(String desc) {
        for (TeachMode teachMode : TeachMode.values()) {
            if (teachMode.desc.equals(desc)) {
                return teachMode;
            }
        }
        return UNKNOWN;
    }
}
