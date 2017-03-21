package com.wangke.wksign;

import android.content.Intent;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.blankj.utilcode.utils.AppUtils;
import com.github.mzule.activityrouter.router.Routers;
import com.wangke.wkcore.base.BaseWkActivity;
import com.wangke.wkcore.others.SPConstants;
import com.wangke.wkcore.utils.WkSpUtil;


/**
 * 闪屏页，背景图片，通过 manifest 中 设置 theme
 */
public class SplashActivity extends BaseWkActivity {

    private RelativeLayout mActivitySignMain;
    private ImageView mImgAd;

    private boolean hasNewGuide = true;  //相对上一版，是否有更新 引导图，有，设置该值 为true
    private boolean isLogin;            //是否登录过
    private boolean mustReLogin;      // 是否要求用户重新登录

    @Override
    public void setRootView() {
        setContentView(R.layout.wksign_activity_main);
    }

    @Override
    public void initView() {
        mActivitySignMain = (RelativeLayout) findViewById(R.id.activity_sign_main);
        mImgAd = (ImageView) findViewById(R.id.img_ad);
    }

    @Override
    public void initData() {
        jumpTo();

    }


    private void jumpTo() {

        // 获取当前版本号
        int appVersionCode = AppUtils.getAppVersionCode(this);
        int spVersionCode = (int) WkSpUtil.get(SPConstants.SP_VERSION_CODE, 0);

        if (spVersionCode < appVersionCode) {  // 新安装APP   覆盖安装

            if ((spVersionCode == 0) || hasNewGuide) {
                toWecome();     // 为 0 ，即新安装的 APP ，直接 去欢迎界面
            } else {
                toLoginOrMain(mustReLogin);
            }
            WkSpUtil.put(SPConstants.SP_VERSION_CODE, appVersionCode);
        } else {     // 正常启动
            toLoginOrMain(false);
        }

    }


    //去登录还是 主页
    private void toLoginOrMain(boolean mustLogin) {
        if (mustLogin || !isLogin) {
            // 必须重新登录 或者  没登录过
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
        } else {
            long time = adTime();
            // 跳转到 主页
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
/*                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);*/
                    Routers.open(SplashActivity.this, "wkapp://WKMainActivity");
                    finish();

                }
            }, time);
        }
    }

    //去欢迎界面 ， 延时操作是为了 让APP初始化，也可以不延时
    private void toWecome() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
                    startActivity(intent);
                finish();

            }
        }, 1500);
    }


    // 广告时间，考虑到 跳过广告 ，暂时实现
    private long adTime() {
        if (!isLogin) {
            return 1000;
        } else {

            // TODO: 2017/3/19  广告 时间判断
//            mImgAd.setImageResource();
            return 5000;
        }
//        return 1000;
    }


}
