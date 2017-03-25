package com.wangke.wkcore.http;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.blankj.utilcode.utils.LogUtils;
import com.google.gson.Gson;
import com.wangke.wkcore.utils.WkAppUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by wk37 on 2017/3/24.
 */

public class VolleyHttpUtil {

    private static VolleyHttpUtil mVolleyHttpUtil;
    private Gson mGson;

    //请求连接的前缀
    private static final String BASE_URL = "";

    //连接超时时间
    private static final int REQUEST_TIMEOUT_TIME = 60 * 1000;

    //volley请求队列
    public static RequestQueue mRequestQueue;

    private VolleyHttpUtil() {
        mGson = new Gson();
        //这里使用Application创建全局的请求队列
        mRequestQueue = Volley.newRequestQueue(WkAppUtil.getContext().getApplicationContext());
    }

    public static VolleyHttpUtil getInstance() {
        if (mVolleyHttpUtil == null) {
            synchronized (VolleyHttpUtil.class) {
                if (mVolleyHttpUtil == null) {
                    mVolleyHttpUtil = new VolleyHttpUtil();
                }
            }
        }
        return mVolleyHttpUtil;
    }

    /**
     * http请求
     *
     * @param url          http地址（后缀）
     * @param param        参数
     * @param httpCallBack 回调
     */
    public <T> void request(int method, String url, final Map<String, String> param, final HttpCallBack<T> httpCallBack) {

        StringRequest stringRequest = new StringRequest(method, BASE_URL + url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (httpCallBack == null) {
                            return;
                        }
                        LogUtils.e("Volley success",response );

                        Type type = getTType(httpCallBack.getClass());

                        OkHttpResult okHttpResult = mGson.fromJson(response, OkHttpResult.class);

                        if (okHttpResult != null) {
                     /*       if (volleyHttpResult.getCode() != 200) {//失败
                                httpCallBack.onFail(volleyHttpResult.getMsg());
                            } else*/ {//成功
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
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (httpCallBack == null) {
                    return;
                }

                String msg = error.getMessage();
                LogUtils.e("Volley  fail ",msg );
                httpCallBack.onFail(msg);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //请求参数
                return param;
            }

        };
        //设置请求超时和重试
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(REQUEST_TIMEOUT_TIME, 1, 1.0f));
        //加入到请求队列
        if (mRequestQueue != null)
            mRequestQueue.add(stringRequest.setTag(url));
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