package com.wangke.wkcore.base;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.wangke.wkcore.http.HttpCallBack;
import com.wangke.wkcore.http.OkHttpUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;


public abstract class BaseWkActivity extends AppCompatActivity  {

    protected Context mContext;
    private HomeReceiver mHomeReceiver;
    private OkHttpUtil okHttpUtil;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        if (hasEventBus()) {
            EventBus.getDefault().register(this);
        }
        if (hasHomeReceiver()) {
            mHomeReceiver = new HomeReceiver(this);
            mHomeReceiver.register();
        }
        mContext = this;
        Log.e("-->ActivityName:", getClass().getSimpleName());
        okHttpUtil = OkHttpUtil.getInstance();

        setRootView(); // 必须放在annotate之前调用
//        AnnotateUtil.initBindView(this);

        initView();

        initData();

    }


    public abstract void setRootView();

    public abstract void initView();

    public abstract void initData();

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (hasHomeReceiver() && mHomeReceiver != null) {
            mHomeReceiver.unRegister();
        }
        if (hasEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config=new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }
    /**
     * 返回键复写
     */
    public void home() {
       this.finish();
    }

    /**
     * 使用EventBus必须重写此方法，返回true
     */
    public boolean hasEventBus() {
        return false;
    }

    /**
     * 使用Home监听必须重写此方法，返回true
     */
    public boolean hasHomeReceiver() {
        return false;
    }

    public class HomeReceiver extends BroadcastReceiver {
        String SYSTEM_REASON = "reason";
        String SYSTEM_HOME_KEY = "homekey";
        String SYSTEM_HOME_KEY_LONG = "recentapps";
        private Context mContext;

        public HomeReceiver(Context context) {
            mContext = context;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_REASON);
                if (TextUtils.equals(reason, SYSTEM_HOME_KEY)) {
                    home();
                } else if (TextUtils.equals(reason, SYSTEM_HOME_KEY_LONG)) {
                }
            }
        }

        public void register() {
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            mContext.registerReceiver(this, filter);
        }

        public void unRegister() {
            mContext.unregisterReceiver(this);
        }
    }


    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void startAct(Class class1) {
        Intent intent = new Intent(BaseWkActivity.this, class1);
        this.startActivity(intent);
    }

    public void startAct(Class class1,String name,String str) {
        Intent intent = new Intent(BaseWkActivity.this, class1);
        intent.putExtra(name,str);
        this.startActivity(intent);
    }

    public void startActForResult(Class class1,String name,String str, int requestCode) {
        Intent intent = new Intent(BaseWkActivity.this, class1);
        intent.putExtra(name,str);
        this.startActivityForResult(intent, requestCode);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     *  默认 穿 this 为网络请求 tag 方便退出activity取消请求
     * @param method    get or post...
     * @param url
     * @param map       参数map合集
     * @param httpCallBack   必须new
     * @param <T>               返回值的泛型
     */
    public  <T> void OkRequest(int method, String url, Map<String, String> map, final HttpCallBack<T> httpCallBack){

        okHttpUtil.request(this , method, url, map , httpCallBack);

    }

    /**
     *  该方法 网络请求需要在 EventBus 的接受方法里面处理 数据，返回数据为 String
     * @param tag  自定义tag
     * @param method
     * @param url
     * @param map
     */
    public   void OkRequest(Object tag, int method, String url, Map<String, String> map ){

        okHttpUtil.request(tag, method, url, map);

    }



}
