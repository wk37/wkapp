package com.wangke.wkapp;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.github.mzule.activityrouter.annotation.Router;
import com.wangke.wkapp.adapter.MyFragmentPagerAdapter;
import com.wangke.wkapp.fragment.TestFargment;
import com.wangke.wkcore.base.BaseWkActivity;

import java.util.ArrayList;
import java.util.List;

@Router("WKMainActivity2")
public class WKMainActivity extends BaseWkActivity {

    private AppBarLayout appBarLayout;
    private TabLayout mAppbarLayoutTablayout;
    private ViewPager mViewpager;


    @Override
    public void setRootView() {
        setContentView(R.layout.activity_wkmain);

    }

    @Override
    public void initView() {
        mAppbarLayoutTablayout = (TabLayout) findViewById(R.id.appbar_layout_tablayout);
        mViewpager = (ViewPager) findViewById(R.id.viewpager);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar_layout);

    }

    @Override
    public void initData() {
//        toolbar.setTitleTextColor(Color.TRANSPARENT);
////        collapsingToolbarLayout.setTitle("");
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

        List<Fragment> mFragmentList = new ArrayList<>();
        mFragmentList.add(TestFargment.newInstance(1+""));
        mFragmentList.add(TestFargment.newInstance(2+""));
        mFragmentList.add(TestFargment.newInstance(3+""));


        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(
                getSupportFragmentManager(), mFragmentList);

        mViewpager.setAdapter(adapter);

        mAppbarLayoutTablayout.setupWithViewPager(mViewpager);

    }





}
