package com.art.recruitment.artperformance.view;

import java.io.IOException;

public class DataResultException extends IOException {
    private String msg;
    private int code;

    public DataResultException(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }

    public DataResultException(String message, String msg, int code) {
        super(message);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
