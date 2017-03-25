package com.wangke.wksign;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.wangke.wkcore.base.BaseWkActivity;
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
            // 单独测试类处理
            HttpTest.OKHttpUtilTest(OkHttpUtil.GET);

        } else if (i == R.id.btn_post_ok) {
            tag = "OK POST";

            Map<String, String> map = new TreeMap<>();
            map.put("json", HttpTest.getJsonStr());

            // BaseActivity request() 请求
            request(OkHttpUtil.POST, HttpTest.url, map, new HttpCallBack<String>() {
                @Override
                public void onSuccess(int code, String data) {
                    // 接口返回数据，UI线程，可直接操作控件
                    mText.setText(tag+"\n"+data);

                }

                @Override
                public void onFail(String msg) {
                    mText.setText(tag+"\n"+msg);

                }
            });

                //写法2： 后面写 this ， 在Activity ，里面处理，暂时不用这种
                // 考虑到 一个界面可能会请求多个接口，需要在请求前 传入tag，暂时未实现
//            request(OkHttpUtil.POST, HttpTest.url, map, this);



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

    // EventBus 3.0 需要加此注解，来接受消息，测试用 String 类型，实际需要自定义一个类来处理
    @Subscribe
    public void onEventMainThread(String event) {

        mText.setText(tag+"\n"+event);
    }

}

