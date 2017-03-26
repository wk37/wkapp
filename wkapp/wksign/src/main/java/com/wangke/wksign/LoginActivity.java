package com.wangke.wksign;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.wangke.wkcore.base.BaseWkActivity;
import com.wangke.wkcore.http.EventBusBean;
import com.wangke.wkcore.http.HttpCallBack;
import com.wangke.wkcore.http.HttpTest;
import com.wangke.wkcore.http.OkHttpUtil;

import org.greenrobot.eventbus.Subscribe;

import java.util.Map;
import java.util.TreeMap;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseWkActivity implements View.OnClickListener {
    private TextView mText;
    private Button mBtnGet;
    private Button mBtnPost;
    private Button mBtnPut;
    private Button mBtnDelete;
    private Button mBtnUpLoad;
    private Button mBtnClear;
    private Button mBtnGetOk;
    private Button mBtnPostOk;
    private Button mBtnPutOk;
    private Button mBtnDeleteOk;
    private Button mBtnUpLoadOk;
    private Button mBtnClearOk;

    String tag = "";


    @Override
    public void setRootView() {
        setContentView(R.layout.wksign_activity_login);
    }

    @Override
    public void initView() {
        mText = (TextView) findViewById(R.id.text);

        mBtnGet = (Button) findViewById(R.id.btn_get);
        mBtnPost = (Button) findViewById(R.id.btn_post);
        mBtnPut = (Button) findViewById(R.id.btn_put);
        mBtnDelete = (Button) findViewById(R.id.btn_delete);
        mBtnUpLoad = (Button) findViewById(R.id.btn_upLoad);

        mBtnGetOk = (Button) findViewById(R.id.btn_get_ok);
        mBtnPostOk = (Button) findViewById(R.id.btn_post_ok);
        mBtnPutOk = (Button) findViewById(R.id.btn_put_ok);
        mBtnDeleteOk = (Button) findViewById(R.id.btn_delete_ok);
        mBtnUpLoadOk = (Button) findViewById(R.id.btn_upLoad_ok);

        mBtnGet.setOnClickListener(this);
        mBtnPost.setOnClickListener(this);
        mBtnPut.setOnClickListener(this);
        mBtnDelete.setOnClickListener(this);
        mBtnUpLoad.setOnClickListener(this);

        mBtnGetOk.setOnClickListener(this);
        mBtnPostOk.setOnClickListener(this);
        mBtnPutOk.setOnClickListener(this);
        mBtnDeleteOk.setOnClickListener(this);
        mBtnUpLoadOk.setOnClickListener(this);

    }

    @Override
    public void initData() {

    }


    @Override
    public void onClick(View view) {

        Map<String, String> map = new TreeMap<>();
        map.put("json", HttpTest.getJsonStr());

        int i = view.getId();
        if (i == R.id.btn_get) {
            tag = "Volley GET";
                    HttpTest.VolleyHttpUtilTest(Request.Method.GET);
        } else if (i == R.id.btn_post) {
            tag = "Volley POST";
            HttpTest.VolleyHttpUtilTest(Request.Method.POST);

        } else if (i == R.id.btn_put) {
            tag = "Volley PUT";

        } else if (i == R.id.btn_delete) {
            tag = "Volley DELETE";

        } else if (i == R.id.btn_upLoad) {
            tag = "Volley UPLOAD";

        } else if (i == R.id.btn_get_ok) {
            tag = "OK GET";
            //写法2：
            // 考虑到 一个界面可能会请求多个接口，需要在请求前 传入tag
            OkRequest(100, OkHttpUtil.GET, HttpTest.url, map);

        } else if (i == R.id.btn_post_ok) {
            tag = "OK POST";

            // BaseActivity request() 请求
            OkRequest(OkHttpUtil.POST, HttpTest.url, map, new HttpCallBack<String>() {
                @Override
                public void onSuccess(Object tag, int code, String data) {
                    // 接口返回数据，UI线程，可直接操作控件
                    mText.setText(tag+"\n"+data);

                }

                @Override
                public void onFail(Object tag , String msg) {
                    mText.setText(tag+"\n"+msg);
                }
            });

        } else if (i == R.id.btn_put_ok) {
            tag = "OK PUT";

        } else if (i == R.id.btn_delete_ok) {
            tag = "OK DELETE";

        } else if (i == R.id.btn_upLoad_ok) {
            tag = "OK UPLOAD";

        }

    }


    @Override
    public boolean hasEventBus() {
        return true;
    }

    // EventBus 3.0 需要加此注解，来接受消息
    @Subscribe
    public void onEventMainThread(EventBusBean edata) {

        mText.setText(tag+"\n"+edata.getTag()+"\n"+edata.getData());
    }

}

