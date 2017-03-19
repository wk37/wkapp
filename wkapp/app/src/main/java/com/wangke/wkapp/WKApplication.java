package com.wangke.wkapp;

import android.support.multidex.MultiDexApplication;

import com.github.mzule.activityrouter.annotation.Modules;
import com.wangke.wkapp.utils.DataInitUtils;

/**
 * Created by wking on 2017/3/15.
 */

@Modules({"WKAppModule","SignModule","ViewsModule"})
public class WKApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        DataInitUtils.initApplication(this);
    }
}
