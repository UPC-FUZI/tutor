package com.wf.tutor.common;

public enum FilePathEnum {
    CARD("card"),
    SCORE("score"),
    AWARD("award");
    private String value;
    FilePathEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
