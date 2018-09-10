package com.wf.tutor.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AccountModifyRequest {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private String telephone;
    private String password;
    private LocalDateTime updateTime;

    public static DateTimeFormatter getFormatter() {
        return formatter;
    }

    public static void setFormatter(DateTimeFormatter formatter) {
        AccountModifyRequest.formatter = formatter;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUpdateTime() {
        if (updateTime == null) {
            return null;
        }
        return updateTime.format(formatter);
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "AccountModifyRequest{" +
                ", telephone='" + telephone + '\'' +
                ", password='" + password + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }
}
