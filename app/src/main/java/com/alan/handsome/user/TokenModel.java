package com.alan.handsome.user;

/**
 * 类说明：授权返回
 * 作者：qiujialiu
 * 时间：2018/7/3
 */

public class TokenModel {

    private long id; //用户id
    private String action;//"register" or "login"
    private String mobile; //	手机号
    private int authorized; //是否认证 0:已认证
    private String token;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getAuthorized() {
        return authorized;
    }

    public void setAuthorized(int authorized) {
        this.authorized = authorized;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
