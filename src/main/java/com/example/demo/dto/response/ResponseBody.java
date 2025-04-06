package com.example.demo.dto.response;

import java.io.Serializable;

public class ResponseBody<T> implements Serializable {
    private Integer code;
    private String message;
    private T data;
    public ResponseBody<T> setResult(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        return this;
    }

    public ResponseBody<T> setResult(ResultCode resultCode,T data) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.setData(data);
        return this;
    }

    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
