package com.wangke.wkcore.http;

/**
 * Created by wk37 on 2017/3/24.
 */

public abstract class HttpCallBack<T> {

    public abstract void onSuccess(int code, T data);

    public abstract void onFail(String msg);
}
