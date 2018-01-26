package com.ruan.bankemployee.javabean;

/**
 * @author by ruan on 2018/1/26.
 * 个人资料bean
 */

public class UserInfo {
    private String userInfo;

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public UserInfo(String userInfo){
        this.userInfo = userInfo;

    }
}
