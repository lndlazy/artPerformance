package com.art.recruitment.common.base;

import java.io.Serializable;


public class BaseBean<T> implements Serializable {

    //接口返回的业务码
    private int code;
    //接口返回信息
    private String message;
    //接口返回数据
    private T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
