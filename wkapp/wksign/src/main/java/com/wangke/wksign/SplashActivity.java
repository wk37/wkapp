package com.wangke.wksign;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.blankj.utilcode.utils.AppUtils;
import com.bumptech.glide.Glide;
import com.github.mzule.activityrouter.router.Routers;
import com.wangke.wkcore.base.BaseWkActivity;
import com.wangke.wkcore.http.temptest.MD5Util;
import com.wangke.wkcore.others.SPConstants;
import com.wangke.wkcore.utils.WkSpUtil;

import java.io.File;


/**
 * 闪屏页，背景图片，通过 manifest 中 设置 theme
 */
public class SplashActivity extends BaseWkActivity implements View.OnClickListener {

    private RelativeLayout mActivitySignMain;
    private ImageView mImgAd;
    private Button mSpJumpBtn;

    private boolean hasNewGuide = false;  //相对上一版，是否有更新 引导图，有，设置该值 为true
    private boolean isLogin;            //是否登录过
    private boolean mustReLogin = true;      // 是否要求用户重新登录
    private boolean isUpdateAppVersionCode = false;      // 是否 需要写入 当前版本号
    private int appVersionCode;

    @Override
    public void setRootView() {
        setContentView(R.layout.wksign_activity_main);
    }

    @Override
    public void initView() {
        mActivitySignMain = (RelativeLayout) findViewById(R.id.activity_sign_main);
        mImgAd = (ImageView) findViewById(R.id.img_ad);
        mSpJumpBtn = (Button) findViewById(R.id.sp_jump_btn);
        mImgAd.setOnClickListener(this);
        mSpJumpBtn.setOnClickListener(this);
    }

    // 点击广告后去 广告web
    private void toWebActivity() {


    }

    @Override
    public void initData() {
        jumpTo();
    }


    private void jumpTo() {
        // 获取当前版本号
        appVersionCode = AppUtils.getAppVersionCode(this);
        int spVersionCode = (int) WkSpUtil.get(SPConstants.SP_VERSION_CODE, 0);
        isLogin = (boolean) WkSpUtil.get(SPConstants.SP_IS_LOGIN, false);

        if (spVersionCode < appVersionCode) {  // 新安装APP  /  覆盖安装
            isUpdateAppVersionCode = true;
            if (mustReLogin) {//覆盖安装时，如果新版要求重新登录，先将登录状态置否
                WkSpUtil.put(SPConstants.SP_IS_LOGIN, false);
                isLogin = false;
            }
            if ((spVersionCode == 0) || hasNewGuide) {
                toWelcomeActivity();     // 为 0 ，即新安装的 APP ，直接 去欢迎界面
            } else {
                toLoginOrMainActivity(mustReLogin, 1000);
            }
        } else {     // 正常启动
            if (hasAD()) {      //加载广告
                mSpJumpBtn.setVisibility(View.VISIBLE);
                countDownTimer.start();
            } else {
                toLoginOrMainActivity(false, 0);
            }
        }
    }


    /**
     * 去登录还是 主页
     *
     * @param mustLogin 是否强制登录
     * @param loadTime  延时时间，初始化需要时间
     */
    private void toLoginOrMainActivity(boolean mustLogin, int loadTime) {
        if (mustLogin || !isLogin) {
            // 必须重新登录 或者  没登录过
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();

        } else {
            // 跳转到 主页
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
/*                    Intent intent = new Intent(SplashActivity.this, NetTestActivity.class);
                    startActivity(intent);*/
                    Routers.open(SplashActivity.this, "wkapp://WKMainActivity");
                    finish();
                }
            }, loadTime);
        }
    }

    //去欢迎界面 ， 延时操作是为了 让APP初始化，也可以不延时
    private void toWelcomeActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1500);
    }


    // 广告设计： 接口返回 广告图 与 广告过期时间戳
    // 本地下载广告图的时候，将 该图名 定为 过期时间戳
    private boolean hasAD() {
        boolean hasAD = false;
        String adMD5 = "";
        long adDeadLine = (long) WkSpUtil.get(SPConstants.SP_AD_DEADLINE, 0L);
        long timeMillis = System.currentTimeMillis();
        // 广告时间未过期
        if (adDeadLine > timeMillis) {
            File file = new File(this.getApplicationContext().getFilesDir().getPath() + "/" + adDeadLine);
            if ((file != null) && file.exists()) {
                adMD5 = MD5Util.MD5(file.getAbsolutePath());
                // TODO: 2017/6/22 此处应该用 封装过的 Glide
                Glide.with(this).load(file).into(mImgAd);
                hasAD = true;
            }
        }
        downloadNewAD(adDeadLine+"", adMD5);
        return hasAD;

    }

    /**
     *       用 IntentService 下载广告，并将其命名为 时间戳, 并将  时间戳 存为 sp
     * @param adDeadLine
     * @param adMD5     将 本地广告图 的 MD5 上传，让服务器确定下次下载的  新广告图 和之前的不一样
     */
    private void downloadNewAD(String adDeadLine, String adMD5) {

        Intent downloadADIntent = new Intent(this.getApplicationContext(), DownloadADIntentService.class);
        downloadADIntent.putExtra("adDeadLine", adDeadLine);
        downloadADIntent.putExtra("adMD5", adMD5);
        this.getApplicationContext().startService(downloadADIntent);
    }

    private CountDownTimer countDownTimer = new CountDownTimer(3200, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            mSpJumpBtn.setText("跳过(" + millisUntilFinished / 1000 + "s)");
        }

        @Override
        public void onFinish() {
            mSpJumpBtn.setText("跳过(" + 0 + "s)");
            toLoginOrMainActivity(false, 0);
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        if (isUpdateAppVersionCode) {// 只在 新安装APP 和 覆盖安装APP 时，写入 1 次 版本号
            WkSpUtil.put(SPConstants.SP_VERSION_CODE, appVersionCode);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.sp_jump_btn) {
            toLoginOrMainActivity(false, 0);
        } else if (i == R.id.img_ad) {
            toWebActivity();
        }
    }

}
