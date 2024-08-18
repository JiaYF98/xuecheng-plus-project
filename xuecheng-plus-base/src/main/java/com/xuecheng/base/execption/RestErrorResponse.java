package com.xuecheng.base.execption;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RestErrorResponse implements Serializable {
    private String errMessage;

    public RestErrorResponse(String errMessage) {
        this.errMessage = errMessage;
    }
}
