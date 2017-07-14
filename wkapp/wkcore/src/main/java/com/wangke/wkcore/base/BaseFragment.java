package com.wangke.wkcore.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wangke.wkcore.http.HttpCallBack;
import com.wangke.wkcore.http.OkHttpUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;


public abstract class BaseFragment extends Fragment {

    protected Context mContext;
    private boolean mIsFirstVisible = true;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
        if (hasEventBus()) {
            EventBus.getDefault().register(this);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (hasEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        OkHttpUtil.getInstance().cancelTag(this);
    }

    public Context getContext() {
        return mContext;
    }
    /**
     * 使用EventBus必须重写此方法，返回true
     */
    public boolean hasEventBus() {
        return false;
    }

    public void home() {
    }

    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isResumed()) {
            if (isVisibleToUser) {
                if (mIsFirstVisible) {
                    mIsFirstVisible = false;
                    onLazyLoad();
                }
            }
        }
    }



    /**
     * 延迟加载 仅在fragment第一次对用户可见时调用一次该方法（建议放网络请求）
     * 子类必须重写此方法
     */
    public abstract void onLazyLoad() ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = setRootView(inflater, container, savedInstanceState);
        initView(view);
        initData();
        return view;
    }

    public abstract View setRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    public abstract void initView(View view) ;

    public abstract void initData();


    /**
     *  默认 穿 this 为网络请求 tag 方便退出activity取消请求
     * @param method    get or post...
     * @param url
     * @param map       参数map合集
     * @param httpCallBack   必须new
     * @param <T>               返回值的泛型
     */
    public  <T> void OkRequest(int method, String url, Map<String, String> map, final HttpCallBack<T> httpCallBack){

        OkHttpUtil.getInstance().request(this , method, url, map , httpCallBack);

    }

}
