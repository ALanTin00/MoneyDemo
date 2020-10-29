package com.alan.handsome.net;

import java.io.IOException;

public class ApiException extends IOException {

    public static final int TYPE_SHOW_MESSAGE = 0;
    public static final int TYPE_DEAL_EVENT = 1;
    private int code;

    public ApiException() {
    }

    public ApiException(String message) {
        this(TYPE_SHOW_MESSAGE, message);
    }

    public ApiException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
