package com.alan.handsome.base.bean;

/**
 * 类说明：
 * 作者：qiujialiu
 * 时间：2018/10/22
 */

public class BaseResultInsurance<T> {
    private String state;
    private String msg;
    private T data;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return "0".equals(state);
    }
}
