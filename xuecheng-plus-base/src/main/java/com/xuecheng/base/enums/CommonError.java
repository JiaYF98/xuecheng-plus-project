package com.xuecheng.base.enums;

import lombok.Getter;

@Getter
public enum CommonError {
    UNKOWN_ERROR("执行过程异常，请重试。"),
    PARAMS_ERROR("非法参数"),
    OBJECT_NULL("对象为空"),
    QUERY_NULL("查询结果为空"),
    REQUEST_NULL("请求参数为空");

    private final String errMessage;

    CommonError(String errMessage) {
        this.errMessage = errMessage;
    }
}

