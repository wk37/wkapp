package com.wangke.wkcore.http;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Map;

import okhttp3.Call;

/**
 * Created by wk37 on 2016/7/22.
 */
public class OkHttpUtil {


    public  static void get(String url, Map<String, String> map) {

        OkHttpUtils
                .get()
                .url(url)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                    }
                });
    }

    public static void post(String url, Map<String, String> map) {
        OkHttpUtils
                .post()
                .url(url)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                    }
                });
    }

    public static void delete(String url, Map<String, Object> map) {

        String json = "{\"channel\":1,\"eqmsn\":\"1469174209\",\"sign\":\"c449eabb1f0bcf8796a720ae74141b8c\",\"tempTime\":1469175007,\"userid\":37}";
        OkHttpUtils
                .delete()
                .url(url+"?json="+json)
//                .requestBody( json)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                    }
                });
    }

/*    public static void put(String url, Map<String, Object> map, final Databack databack) {

        OkHttpUtils
                .put()
                .url(url)
                .requestBody("json="+ HttpHelper.newInstance().getJsonStr(map))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        databack.onStringResponse(response, id);
                    }
                });
    }

    public static void upload(String url, Map<String, Object> map, final Databack databack) {
        OkHttpUtils
                .get()
                .url(url)
                .addParams("json", HttpHelper.newInstance().getJsonStr(map))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                    }
                });
    }

    // 点击回调
    public interface Databack {
        void onStringResponse(String response, int cancalTag);


    }*/



}
