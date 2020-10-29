package com.alan.handsome.user;

/**
 * 类说明：登录用户信息
 */

public class UserInformation  {

    private String id; //用户id
    private String action;//"register" or "login"
    private String mobile; //	手机号
    private int authorized; //是否认证 0:已认证 1:第一步认证 2：第二步认证 3：第三步认证
    private String token;
    private String name;
    private boolean seePassType;//有没有看过审核通过界面

    public boolean isSeePassType() {
        return seePassType;
    }

    public void setSeePassType(boolean seePassType) {
        this.seePassType = seePassType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
