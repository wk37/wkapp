package com.wangke.wkcore.http;

/**
 * Created by wk37 on 2017/3/24.
 */

public class VolleyHttpResult {

    private Object data;
    private int code;
    private String msg;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}