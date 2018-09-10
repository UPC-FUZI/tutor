package com.wf.tutor.common;

public class TutorException extends Exception {
    private ErrorCodeEnum errorCodeEnum;

    public TutorException(String msg, ErrorCodeEnum codeEnum) {
        super(msg);
        errorCodeEnum = codeEnum;
    }

    public ErrorCodeEnum getErrorCodeEnum() {
        return errorCodeEnum;
    }
}
