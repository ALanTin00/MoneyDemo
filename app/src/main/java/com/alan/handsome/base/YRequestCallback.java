package com.alan.handsome.base;

/***
 * 功能描述:请求的回调
 *
 ***/

public interface YRequestCallback<T> {
    void onSuccess(T var1);

    void onFailed(int var1, String message);

    void onException(Throwable var1);
}
