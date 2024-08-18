package com.xuecheng.base.enums;

import lombok.Getter;

@Getter
public enum ChargeType {
    UNKNOWN("200001"),
    PAID("201001"),
    FREE("202001"),
    ;
    private final String code;

    ChargeType(String code) {
        this.code = code;
    }

    public static ChargeType of(String code) {
        for (ChargeType chargeType : ChargeType.values()) {
            if (chargeType.code.equals(code)) {
                return chargeType;
            }
        }
        return UNKNOWN;
    }
}
