package com.wangke.wksign;

import android.content.Intent;

import com.wangke.wkcore.base.BaseWkActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wking on 2017/3/19.
 */

public class WelcomeActivity extends BaseWkActivity {
    private Banner mBanner;



    @Override
    public void setRootView() {
        setContentView(R.layout.wksign_activity_welcome);
    }

    @Override
    public void initView() {
        mBanner = (Banner) findViewById(R.id.banner);

    }

    @Override
    public void initData() {

        final List<Integer> images = new ArrayList<>();
        images.add(R.drawable.write);
        images.add(R.drawable.fish2);
        images.add(R.drawable.fish);
        images.add(R.drawable.facebook);
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (position == images.size() - 1 );

                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mBanner.setOffscreenPageLimit(4);
        mBanner.isLoopPlay(false);
        mBanner.setBannerStyle(BannerConfig.NUM_INDICATOR);
        mBanner.setImages(images).setImageLoader(new GlideImageLoader()).start();
    }
}
