package com.wangke.wkcore.utils;

import android.content.Context;

/**
 * Created by wk37 on 2017/3/16.
 */

public class WkAppUtil {


    private static Context context;

    public static void init(Context contexts) {
        context = contexts;
    }

    public static Context getContext() {
        return context;
    }


}
