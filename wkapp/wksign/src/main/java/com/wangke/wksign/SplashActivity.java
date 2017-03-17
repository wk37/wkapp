package com.wangke.wksign;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.blankj.utilcode.utils.AppUtils;
import com.wangke.wkcore.base.BaseWkActivity;
import com.wangke.wkcore.others.SPConstants;
import com.wangke.wkcore.utils.WkSpUtil;

public class SplashActivity extends BaseWkActivity {

    private RelativeLayout mActivitySignMain;
    private ImageView mImgAd;

    private boolean hasNewGuide = true;  //相对上一版，是否有更新 引导图，有，设置该值 为true
    private boolean isLogin;            //是否登录过
    private boolean mustReLogin;      // 是否要求用户重新登录


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wksign_activity_main);
        initView();
        jumpTo();

    }


    private void initView() {
        mActivitySignMain = (RelativeLayout) findViewById(R.id.activity_sign_main);
        mImgAd = (ImageView) findViewById(R.id.img_ad);
    }


    private void jumpTo() {

        // 获取当前版本号
        int appVersionCode = AppUtils.getAppVersionCode(this);
        int spVersionCode = (int) WkSpUtil.get(SPConstants.SP_VERSION_CODE, 0);

        if (spVersionCode < appVersionCode) {  // 新安装APP   覆盖安装

            if ((spVersionCode == 0) || hasNewGuide) {
                toWecome();
            } else {
                toLoginOrMain(mustReLogin);
            }
            WkSpUtil.put(SPConstants.SP_VERSION_CODE, appVersionCode);
        } else {     // 正常启动
            toLoginOrMain(false);
        }
        finish();

    }


    private void toLoginOrMain(boolean mustLogin) {
        if (mustLogin || !isLogin) {
            // TODO: 2017/3/17  LoginActivity
            /*                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);*/
        } else {
            long time = adTime();
            // TODO: 2017/3/17  MainActivity
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
/*                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);*/
                }
            }, time);
        }
    }

    private void toWecome() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO: 2017/3/17  WelcomeActivity
                /*                    Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
                    startActivity(intent);*/
            }
        }, 1500);
    }

    private long adTime() {
        if (!isLogin) {
            return 1000;
        } else {

        }
        return 1000;
    }


}
