package com.example.demo.dto.response;

public class ResultResponse {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    // 只返回状态
    public static ResponseBody success() {
        return new ResponseBody().setResult(ResultCode.SUCCESS);
    }

    // 成功返回数据
    public static ResponseBody success(Object data) {
        return new ResponseBody().setResult(ResultCode.SUCCESS, data);


    }

    // 失败
    public static ResponseBody failure(ResultCode resultCode) {
        return new ResponseBody().setResult(resultCode);
    }

    // 失败
    public static ResponseBody failure(ResultCode resultCode, Object data) {
        return new ResponseBody().setResult(resultCode, data);
    }



}
