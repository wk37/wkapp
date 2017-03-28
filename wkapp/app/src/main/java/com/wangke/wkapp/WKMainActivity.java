package com.wangke.wkapp;

import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;

import com.github.mzule.activityrouter.annotation.Router;
import com.wangke.wkcore.base.BaseWkActivity;

@Router("WKMainActivity")
public class WKMainActivity extends BaseWkActivity {

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_wkmain);

    }

    @Override
    public void initView() {
        toolbar = (Toolbar) findViewById(R.id.appbar_layout_toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar_layout);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse_layout);

    }

    @Override
    public void initData() {
//        toolbar.setTitleTextColor(Color.TRANSPARENT);
////        collapsingToolbarLayout.setTitle("");
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        collapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);
//        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                Log.e("zhouwei","appbarHeight:"+appBarLayout.getHeight()+" getTotalScrollRange:"+appBarLayout.getTotalScrollRange()+" offSet:"+verticalOffset);
//                if(Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()){
//                    toolbar.setTitleTextColor(getResources().getColor(R.color.white));
////                    collapsingToolbarLayout.setTitle("wkapp");
//                }else{
////                    collapsingToolbarLayout.setTitle("");
//                }
//            }
//        });
    }
}
