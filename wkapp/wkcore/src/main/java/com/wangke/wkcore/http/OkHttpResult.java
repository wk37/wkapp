package com.wangke.wkcore.http;

/**
 * Created by wk37 on 2017/3/24.
 */

public class OkHttpResult {

    private Object result;
    private int errCode;
    private String message;

    public Object getData() {
        return result;
    }

    public void setData(Object result) {
        this.result = result;
    }

    public int getCode() {
        return errCode;
    }

    public void setCode(int errCode) {
        this.errCode = errCode;
    }

    public String getMsg() {
        return message;
    }

    public void setMsg(String msg) {
        this.message = msg;
    }
}