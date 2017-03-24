package com.wangke.wkcore.http;

import com.android.volley.Request;
import com.blankj.utilcode.utils.LogUtils;
import com.google.gson.Gson;
import com.wangke.wkcore.http.temptest.MD5Util;
import com.wangke.wkcore.http.temptest.MapKeyComparator;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by wk37 on 2017/3/24.
 */

public class HttpTest {



    public static void  VolleyHttpUtilTest(){
        String url = "http://sz.wisdudu.com/api/Home/eqment/manage.html";

        Map<String, String> map = new TreeMap<>();

        map.put("json", getJsonStr());

        VolleyHttpUtil.getInstance().request(Request.Method.GET, url, map, new HttpCallBack<String>() {
            @Override
            public void onSuccess(int code, String data) {
                LogUtils.e("HttpUtilTest    Volley",code+"    "+data );
                EventBus.getDefault().post(code+data);
            }

            @Override
            public void onFail(String msg) {
                LogUtils.e("HttpUtilTest    Volley",  "  fail  "+msg );
                EventBus.getDefault().post("fail"+msg);

            }
        }
    );




    }


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

    public static Map<String, Object> sortMapByKey(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, Object> sortMap = new TreeMap<>(new MapKeyComparator());

        sortMap.putAll(map);

        return sortMap;
    }

}
