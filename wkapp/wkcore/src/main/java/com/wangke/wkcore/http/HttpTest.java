package com.wangke.wkcore.http;

import com.google.gson.Gson;
import com.wangke.wkcore.http.temptest.MD5Util;
import com.wangke.wkcore.http.temptest.MapKeyComparator;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by wk37 on 2017/3/24.
 * 测试 网络请求 类
 */

public class HttpTest {
    // 测试接口
   public static String url = "http://sz.wisdudu.com/api/Home/eqment/manage.html";

    // 测试 Volley 请求
    public static void  VolleyHttpUtilTest(int method){
        Map<String, String> map = new TreeMap<>();
        map.put("json", getJsonStr());

        VolleyHttpUtil.getInstance().request("Volley", method, url, map, new HttpCallBack<String>() {
                    @Override
                    public void onSuccess(Object tag, int code, String data) {
                        EventBus.getDefault().post(new EventBusBean<String>(tag, code , data));
                    }

                    @Override
                    public void onFail(Object tag, String msg) {
                        EventBus.getDefault().post(new EventBusBean<String>(tag, -1 , msg));
                    }
        }
        );
    }

    // 测试 OkHttp 请求 ，已封装到 BaseActivity 的 request 方法
    public static void  OKHttpUtilTest(int method){
        Map<String, String> map = new TreeMap<>();
        map.put("json", getJsonStr());

        OkHttpUtil.getInstance().request("ok", method, url, map , new HttpCallBack<String>() {
            @Override
            public void onSuccess(Object tag, int code, String data) {
                EventBus.getDefault().post(new EventBusBean<String>(tag, code , data));
            }

            @Override
            public void onFail(Object tag, String msg) {
                EventBus.getDefault().post(new EventBusBean<String>(tag, -1 , msg));


            }


        });
    }

    //测试接口需要用的 数据转换，
    public static  String getJsonStr() {
        Map<String, Object> map = new TreeMap<>();
        map.put("phone", "18888888888");
        map.put("type", 1);
        int tempTime = (int) ((System.currentTimeMillis()) / 1000);
        map.put("tempTime", tempTime);
        String signStr = getMD5Key(map).toLowerCase();

        map.put("sign", signStr);
        String jsonStr = new Gson().toJson(map);

        return jsonStr;
    }
    // 测试接口需要用到的数据转换
    public static String getMD5Key(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        String tempStr = "";
        String code = "Fanghui2016";

        Map<String, Object> resultMap = sortMapByKey(map);
        for (Map.Entry<String, Object> entry : resultMap.entrySet()) {
            tempStr += entry.getValue();
        }
        tempStr = tempStr + code;
        return MD5Util.MD5(tempStr);
    }
    //测试接口需要用的 数据转换，
    public static Map<String, Object> sortMapByKey(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, Object> sortMap = new TreeMap<>(new MapKeyComparator());

        sortMap.putAll(map);

        return sortMap;
    }

}
