package com.wangke.wksign;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.github.mzule.activityrouter.router.Routers;
import com.wangke.wkcore.base.BaseWkActivity;
import com.wangke.wkcore.http.HttpCallBack;
import com.wangke.wkcore.http.HttpTest;
import com.wangke.wkcore.http.OkHttpUtil;
import com.wangke.wkcore.others.SPConstants;
import com.wangke.wkcore.utils.WkSpUtil;
import com.wangke.wkviews.ClearEditText;

import java.util.Map;
import java.util.TreeMap;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseWkActivity {
    private TextView mText;
    private ClearEditText mLoginEditName;
    private ClearEditText mLoginEditPwd;
    private Button mBtnLogin;





    @Override
    public void setRootView() {
        setContentView(R.layout.wksign_activity_login);
        WkSpUtil.put(SPConstants.SP_IS_LOGIN, false);
    }

    @Override
    public void initView() {
        mText = (TextView) findViewById(R.id.text);
        mLoginEditName = (ClearEditText) findViewById(R.id.login_edit_name);
        mLoginEditPwd = (ClearEditText) findViewById(R.id.login_edit_pwd);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    @Override
    public void initData() {
        mLoginEditName.setText("13207090037");
    }

    private void login(){
        String phone = mLoginEditName.getText().toString();
        String pwd = mLoginEditPwd.getText().toString();
        if (TextUtils.isEmpty(phone)){
            ToastUtils.showShortToast("手机号不能为空");
            return;
        }
        if (phone.length() != 11){
            ToastUtils.showShortToast("手机号位数不对");
            return;
        }
        if (TextUtils.isEmpty(pwd)){
            ToastUtils.showShortToast("密码不能为空");
            return;
        }


        Map<String, String> map = new TreeMap<>();
        map.put("json", HttpTest.getJsonStr());
        OkRequest(OkHttpUtil.POST, HttpTest.url, map, new HttpCallBack<String>() {
            @Override
            public void onSuccess(Object tag, int code, String data) {

                ToastUtils.showShortToast("登录成功");
                Routers.open(LoginActivity.this, "wkapp://WKMainActivity");
                WkSpUtil.put(SPConstants.SP_IS_LOGIN, true);
                finish();
            }

            @Override
            public void onFail(Object tag , String msg) {
                mText.setText(tag+"\n"+msg);
                ToastUtils.showShortToast("登录失败，请重试");

            }
        });
    }


}

