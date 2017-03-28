package com.wangke.wkcore.http;

import com.blankj.utilcode.utils.LogUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by wk37 on 2016/7/22.
 */
public class OkHttpUtil {

    private static OkHttpUtil okHttpUtil;
    private static Gson mGson;
    public final static int DEPRECATED_GET_OR_POST = -1;
    public final static int GET = 0;
    public final static int POST = 1;
    public final static int PUT = 2;
    public final static int DELETE = 3;
    public final static int HEAD = 4;
    public final static int OPTIONS = 5;
    public final static int TRACE = 6;
    public final static int PATCH = 7;


    private OkHttpUtil() {
        mGson = new Gson();
    }

    public static OkHttpUtil getInstance() {
        if (okHttpUtil == null) {
            synchronized (OkHttpUtil.class) {
                if (okHttpUtil == null) {
                    okHttpUtil = new OkHttpUtil();
                }
            }
        }
        return okHttpUtil;
    }


    public void request(Object tag, int method, String url, Map<String, String> map) {
        request(tag, method, url, map, new HttpCallBack<String>() {
            @Override
            public void onSuccess(Object tag, int code, String data) {
                EventBus.getDefault().post(new EventBusBean<String>(tag, code, data));
            }

            @Override
            public void onFail(Object tag, String msg) {
                EventBus.getDefault().post(new EventBusBean<String>(tag, -1, msg));
            }
        });

    }

    public <T> void request(int method, String url, Map<String, String> map, final HttpCallBack<T> httpCallBack) {
        request(-1, method, url, map, httpCallBack);

    }

    public <T> void request(Object tag, int method, String url, Map<String, String> map, final HttpCallBack<T> httpCallBack) {
        switch (method) {

            case GET:
                get(tag, url, map, httpCallBack);
                break;
            case POST:
                post(tag, url, map, httpCallBack);

                break;
            case PUT:

                break;
            case DELETE:

                break;
            case HEAD:

                break;
            case OPTIONS:

                break;
            case TRACE:

                break;
            case PATCH:

                break;
        }


    }


    public <T> void get(final Object tag, String url, Map<String, String> map, final HttpCallBack<T> httpCallBack) {

        OkHttpUtils
                .get()
                .url(url)
                .params(map)
                .tag(tag)
//                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        onFialBack(httpCallBack, tag, e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        onSuccessBack(httpCallBack, tag, response);

                    }
                });
    }

    public <T> void post(final Object tag, String url, Map<String, String> map, final HttpCallBack<T> httpCallBack) {
        OkHttpUtils
                .post()
                .url(url)
                .params(map)
                .tag(tag)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        onFialBack(httpCallBack, tag, e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        onSuccessBack(httpCallBack, tag, response);
                    }
                });
    }

    public void delete(String url, Map<String, Object> map) {

        String json = "{\"channel\":1,\"eqmsn\":\"1469174209\",\"sign\":\"c449eabb1f0bcf8796a720ae74141b8c\",\"tempTime\":1469175007,\"userid\":37}";
        OkHttpUtils
                .delete()
                .url(url + "?json=" + json)
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


    private <T> void onFialBack(final HttpCallBack<T> httpCallBack, Object tag, String msg) {
        if (httpCallBack == null) {
            return;
        }
        LogUtils.e("OK  fail ", msg);

        httpCallBack.onFail(tag, msg);
    }


    private <T> void onSuccessBack(HttpCallBack<T> httpCallBack, Object tag, String response) {
        if (httpCallBack == null) {
            return;
        }
        LogUtils.e("OK success", response);

        Type type = getTType(httpCallBack.getClass());

        OkHttpResult okHttpResult = mGson.fromJson(response, OkHttpResult.class);
        if (okHttpResult != null) {
                     /*       if (volleyHttpResult.getCode() != 200) {//失败
                                httpCallBack.onFail(volleyHttpResult.getMsg());
                            } else */
            {//成功
                //获取data对应的json字符串
                String json = mGson.toJson(okHttpResult.getData());
                if (type == String.class) {//泛型是String，返回结果json字符串
                    httpCallBack.onSuccess(tag, okHttpResult.getCode(), (T) json);
                } else {//泛型是实体或者List<>
                    T t = mGson.fromJson(json, type);
                    httpCallBack.onSuccess(tag, okHttpResult.getCode(), t);
                }
            }
        }
    }


    private Type getTType(Class<?> clazz) {
        Type mySuperClassType = clazz.getGenericSuperclass();
        Type[] types = ((ParameterizedType) mySuperClassType).getActualTypeArguments();
        if (types != null && types.length > 0) {
            //T
            return types[0];
        }
        return null;
    }

}
