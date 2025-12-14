package com.xuecheng.base.handler;

import com.xuecheng.base.enums.CommonError;
import com.xuecheng.base.execption.RestErrorResponse;
import com.xuecheng.base.execption.XueChengPlusException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(XueChengPlusException.class)
    public RestErrorResponse handlerXueChengPlusException(XueChengPlusException e) {
        log.error("【系统异常】{}", e.getErrMessage(), e);
        return new RestErrorResponse(e.getErrMessage());
    }

    @ExceptionHandler(Exception.class)
    public RestErrorResponse handlerException(Exception e) {
        log.error("【系统异常】{}", e.getMessage(), e);
        return new RestErrorResponse(CommonError.UNKOWN_ERROR.getErrMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RestErrorResponse handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<String> msgList = new ArrayList<>();
        bindingResult.getFieldErrors().forEach(msg -> msgList.add(msg.getDefaultMessage()));
        String msg = StringUtils.join(msgList, ",");
        log.error("【系统异常】{}", msg);
        return new RestErrorResponse(msg);
    }
}
