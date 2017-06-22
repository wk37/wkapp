package com.wangke.wkcore.http;

import java.io.File;

/**
 * Created by wk37 on 2017/3/24.
 */

public abstract class HttpFileCallBack extends HttpCallBack<File> {

    public abstract void onSuccess(Object tag, int code, File data);

    public abstract void onFail(Object tag, String msg);

    public void inProgress(float progress, long total, int id) {

    }
}
