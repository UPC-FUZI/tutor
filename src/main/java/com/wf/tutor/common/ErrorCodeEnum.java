package com.wf.tutor.common;

public enum ErrorCodeEnum {
    SUCCESS(0, "成功"),
    ALREADY_REGISTERED(10,"已经注册，请直接登录"),
    REGISTERED_FAIL(11,"注册失败，稍后重试"),
    Login_FAIL(12,"请先注册，再登录"),
    FAIL(99, "失败");

    private int code;
    private String message;

    ErrorCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
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
}
