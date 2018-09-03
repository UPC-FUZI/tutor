package com.wf.tutor.common;

import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionHandler extends LoggerBase{
    @org.springframework.web.bind.annotation.ExceptionHandler
    public ApiResult<String> handleException(HttpServletRequest request,Exception e){
        getLogger().error("客户端IP:[{}],发生错误:",request.getRemoteHost(),e);
        return ApiResult.createError(e.getMessage());
    }
}
