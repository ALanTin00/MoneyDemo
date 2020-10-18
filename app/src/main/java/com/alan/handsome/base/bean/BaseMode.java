package com.alan.handsome.base.bean;

import android.text.TextUtils;

/**
 * Created by liufe on 2018/6/26
 * {"name":123,"age":1}
 * 基础接口返回数据基类
 */
public class BaseMode<T> {

    //是否授权
    private boolean unAuthorizedRequest;
    //是否请求成功
    private boolean success;
    //结果
    private T result;
    //错误消息
    private ErrorBean error;


    public boolean isUnAuthorizedRequest() {
        return unAuthorizedRequest;
    }

    public void setUnAuthorizedRequest(boolean unAuthorizedRequest) {
        this.unAuthorizedRequest = unAuthorizedRequest;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }

    public static class ErrorBean {
        private int code;
        private String message;
        private String details;
        private String validationErrors;

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

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public String getValidationErrors() {
            return validationErrors;
        }

        public void setValidationErrors(String validationErrors) {
            this.validationErrors = validationErrors;
        }

        public String getShowMessage() {
            if (TextUtils.isEmpty(details)) {
                return message;
            }else {
                return message +" "+ details;
            }
        }
    }

}
