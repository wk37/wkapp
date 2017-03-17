package com.wangke.wkcore.base;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;


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
                    onLazyInitView();
                }
            }
        }
    }



    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    public void onLazyInitView() {
    }



}
