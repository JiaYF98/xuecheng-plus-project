package com.xuecheng.base.model;

import lombok.Data;

@Data
public class RestResponse<T> {
    private int code;
    private String msg;
    private T result;

    public RestResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public RestResponse() {
        this(0, "success");
    }

    /**
     * 添加正常响应数据（包含响应内容）
     *
     * @return RestResponse Rest服务封装相应数据
     */
    public static <T> RestResponse<T> success(T result) {
        RestResponse<T> response = new RestResponse<T>();
        response.setResult(result);
        return response;
    }

    public static <T> RestResponse<T> success(T result, String msg) {
        RestResponse<T> response = new RestResponse<T>();
        response.setResult(result);
        response.setMsg(msg);
        return response;
    }

    /**
     * 添加正常响应数据（不包含响应内容）
     *
     * @return RestResponse Rest服务封装相应数据
     */
    public static <T> RestResponse<T> success() {
        return new RestResponse<T>();
    }

    public Boolean isSuccessful() {
        return this.code == 0;
    }

}
