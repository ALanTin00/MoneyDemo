package com.alan.handsome.base.bean;

import android.text.TextUtils;

/**
 * Created by liufe on 2018/6/26
 * {"name":123,"age":1}
 * 基础接口返回数据基类
 */
public class BaseMode<T> {

    private int status;
    //是否请求返回的码
    private String code;
    //放回信息
    private String message;
    //结果
    private T result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

}
