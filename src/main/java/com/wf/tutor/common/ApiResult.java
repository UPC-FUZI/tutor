package com.wf.tutor.common;

public class ApiResult<T> {
    //是否成功
    private boolean success;

    //错误码
    private int code;

    //包含的消息
    private String message;

    //成功调用的返回值
    private T data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <K> ApiResult<K> createSuccess(K data) {
        ApiResult<K> result = new ApiResult<>();
        result.data = data;
        result.code = ErrorCodeEnum.SUCCESS.getCode();
        result.success = true;
        result.message = ErrorCodeEnum.SUCCESS.getMessage();
        return result;
    }

    public static <K> ApiResult<K> createError(ErrorCodeEnum errorCodeEnum) {
        ApiResult<K> result = new ApiResult<>();
        result.code = errorCodeEnum.getCode();
        result.success = false;
        result.message = errorCodeEnum.getMessage();
        return result;
    }

    public static <K> ApiResult<K> createError(String message) {
        ApiResult<K> result = new ApiResult<>();
        result.code = 999;
        result.success = false;
        result.message = message;
        return result;
    }

}
