package com.wangke.wkapp.utils;

import android.content.Context;

import com.wangke.wkcore.utils.WkAppUtil;
import com.wangke.wkcore.utils.WkSpUtil;

/**
 * Created by wk37 on 2017/3/16.
 */

public class DataInitUtils {

    /**
     *  需要在  application 里面初始化的数据
     * @param context
     */
    public static void initApplication(Context context) {
        WkAppUtil.init(context);
        WkSpUtil.init(context);



    }


    /**
     * 在登录以后 初始化的数据
     * @param context
     */
    public static void initLogin(Context context) {

    }
}
