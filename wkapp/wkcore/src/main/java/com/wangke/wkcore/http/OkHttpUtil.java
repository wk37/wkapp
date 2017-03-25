package com.wangke.wkcore.http;

import com.blankj.utilcode.utils.LogUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by wk37 on 2016/7/22.
 */
public class OkHttpUtil {

    private static  Gson mGson = new Gson();
 public final static  int DEPRECATED_GET_OR_POST = -1;
 public final static  int GET = 0;
 public final static  int POST = 1;
 public final static  int PUT = 2;
 public final static  int DELETE = 3;
 public final static  int HEAD = 4;
 public final static  int OPTIONS = 5;
 public final static  int TRACE = 6;
 public final static  int PATCH = 7;


    public static <T> void request(int method, String url, Map<String, String> map, final HttpCallBack<T> httpCallBack){
        switch (method) {

            case GET:
                get(url, map, httpCallBack);
                break;
            case POST:
                post(url, map, httpCallBack);

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




    public  static <T> void get(String url, Map<String, String> map, final HttpCallBack<T> httpCallBack) {

        OkHttpUtils
                .get()
                .url(url)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (httpCallBack == null) {
                            return;
                        }
                        String msg = e.getMessage();
                        LogUtils.e("OK  fail ",msg );

                        httpCallBack.onFail(msg);
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        if (httpCallBack == null) {
                            return;
                        }
                        LogUtils.e("OK success",response );

                        Type type = getTType(httpCallBack.getClass());

                        OkHttpResult okHttpResult = mGson.fromJson(response, OkHttpResult.class);
                        if (okHttpResult != null) {
                     /*       if (volleyHttpResult.getCode() != 200) {//失败
                                httpCallBack.onFail(volleyHttpResult.getMsg());
                            } else */{//成功
                                //获取data对应的json字符串
                                String json = mGson.toJson(okHttpResult.getData());
                                if (type == String.class) {//泛型是String，返回结果json字符串
                                    httpCallBack.onSuccess(okHttpResult.getCode(), (T) json);
                                } else {//泛型是实体或者List<>
                                    T t = mGson.fromJson(json, type);
                                    httpCallBack.onSuccess( okHttpResult.getCode(), t);
                                }
                            }
                        }

                    }
                });
    }

    public static <T> void post(String url, Map<String, String> map,  final HttpCallBack<T> httpCallBack) {
        OkHttpUtils
                .post()
                .url(url)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (httpCallBack == null) {
                            return;
                        }
                        String msg = e.getMessage();
                        LogUtils.e("OK  fail ",msg );
                        httpCallBack.onFail(msg);
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        if (httpCallBack == null) {
                            return;
                        }
                        LogUtils.e("OK success",response );

                        Type type = getTType(httpCallBack.getClass());

                        OkHttpResult okHttpResult = mGson.fromJson(response, OkHttpResult.class);
                        if (okHttpResult != null) {
                     /*       if (volleyHttpResult.getCode() != 200) {//失败
                                httpCallBack.onFail(volleyHttpResult.getMsg());
                            } else */{//成功
                                //获取data对应的json字符串
                                String json = mGson.toJson(okHttpResult.getData());
                                if (type == String.class) {//泛型是String，返回结果json字符串
                                    httpCallBack.onSuccess(okHttpResult.getCode(), (T) json);
                                } else {//泛型是实体或者List<>
                                    T t = mGson.fromJson(json, type);
                                    httpCallBack.onSuccess( okHttpResult.getCode(), t);
                                }
                            }
                        }

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

    private static Type getTType(Class<?> clazz) {
        Type mySuperClassType = clazz.getGenericSuperclass();
        Type[] types = ((ParameterizedType) mySuperClassType).getActualTypeArguments();
        if (types != null && types.length > 0) {
            //T
            return types[0];
        }
        return null;
    }

}
