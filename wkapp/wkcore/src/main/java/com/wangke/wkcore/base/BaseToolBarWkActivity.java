package com.wangke.wkcore.base;


import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wangke.wkcore.R;


public abstract class BaseToolBarWkActivity extends BaseWkActivity {

    private View mContainer;
    private Toolbar mToolbar;
    private ImageView mImg;
    private TextView mTvMunu;
    private TextView mToolbarTitle;
    private LinearLayout mLlMenu;
    private FrameLayout mActionbarContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContainer = getLayoutInflater().inflate(R.layout.activity_base, null);
        mToolbar = (Toolbar) mContainer.findViewById(R.id.toolbar);
        mImg = (ImageView) mContainer.findViewById(R.id.img);
        mTvMunu = (TextView) mContainer.findViewById(R.id.tv_munu);
        mToolbarTitle = (TextView) mContainer.findViewById(R.id.toolbar_title);
        mLlMenu = (LinearLayout) mContainer.findViewById(R.id.ll_menu);
        mActionbarContent = (FrameLayout) mContainer.findViewById(R.id.actionbar_content);

        mToolbar.setTitle("");
        mToolbar.setBackgroundColor(getResources().getColor(R.color.actionbar_bg));

        setRootView(); // 必须放在annotate之前调用
//        AnnotateUtil.initBindView(this);

        getIntentInfo();
        initView();

        initData();
    }

    public void getIntentInfo() {
    }

    @Override
    public boolean hasR() {
        return false;
    }


    public void setContentView() {
        super.setContentView(mContainer);
    }

    @Override
    public void setContentView(int layoutResID) {
        mActionbarContent.addView(getLayoutInflater()
                .inflate(layoutResID, null), new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        super.setContentView(mContainer);
        setSupportActionBar(mToolbar);

    }

    /**
     * 设置返回键
     *
     * @param resId 图片ID
     */
    public void setBackRes(int resId) {
        mToolbar.setNavigationIcon(resId);
    }

    public void hasBack(){
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home();
            }
        });
    }
    /**
     * 隐藏返回键
     */
    public void noBackRes() {
        mToolbar.setNavigationIcon(null);
/*        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });*/
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        mToolbarTitle.setText(title);
    }
    public void setTitle(String title, View.OnClickListener mOnClickListener) {
        mToolbarTitle.setText(title);
        mToolbarTitle.setOnClickListener(mOnClickListener);
    }
    public void setTitle(int resId) {
        mToolbarTitle.setText(getResources().getText(resId));
    }
    public void setTitle(int resId, View.OnClickListener mOnClickListener) {
        mToolbarTitle.setText(getResources().getText(resId));
        mToolbarTitle.setOnClickListener(mOnClickListener);
    }
    public void setTitle(String title, int resId , View.OnClickListener mOnClickListener) {
        mToolbarTitle.setText(title);
        mToolbarTitle.setCompoundDrawablesWithIntrinsicBounds(null, null,getResources().getDrawable(resId), null);
        mToolbarTitle.setCompoundDrawablePadding(10);
        mToolbarTitle.setOnClickListener(mOnClickListener);
    }

    /**
     * 添加Toobar右边功能，传入文字
     * @param textId
     */
    public void addMenuTextItme(int textId, View.OnClickListener mOnClickListener) {
        mImg.setVisibility(View.GONE);
        mTvMunu.setText(textId);
        mTvMunu.setTextSize(17);
        mTvMunu.setOnClickListener(mOnClickListener);
    }

    public void addMenuTextItme(int textId, int size , View.OnClickListener mOnClickListener) {
        mImg.setVisibility(View.GONE);
        mTvMunu.setText(textId);
        mTvMunu.setTextSize(size);
        mTvMunu.setOnClickListener(mOnClickListener);
    }
    public void addMenuTextItme(int textId, int size ,int color, View.OnClickListener mOnClickListener) {
        mImg.setVisibility(View.GONE);
        mTvMunu.setText(textId);
        mTvMunu.setTextSize(size);
        mTvMunu.setTextColor(color);
        mTvMunu.setOnClickListener(mOnClickListener);
    }

    public void changeMenuTextItme(int textId) {
        mImg.setVisibility(View.GONE);
        mTvMunu.setText(textId);
    }
    //情景模式
    public void changeMenuTextItme(String textId) {
        mImg.setVisibility(View.GONE);
        mTvMunu.setText(textId);
    }
    //情景模式
    public void changeMenuTextItme(String textId,View.OnClickListener mOnClickListener) {
        mImg.setVisibility(View.GONE);
        mTvMunu.setVisibility(View.VISIBLE);
        mTvMunu.setText(textId);
        mTvMunu.setOnClickListener(mOnClickListener);
    }
    //情景模式
    public void changeMenuTextItme(int textId, View.OnClickListener mOnClickListener) {
        mImg.setVisibility(View.VISIBLE);
        mTvMunu.setBackgroundResource(textId);
        mTvMunu.setVisibility(View.GONE);
        mTvMunu.setOnClickListener(mOnClickListener);
    }
    //情景模式
    public void changeMenuImgItme(int textId) {
        mImg.setVisibility(View.VISIBLE);
        mImg.setBackgroundResource(textId);
        mTvMunu.setVisibility(View.GONE);
    }
    @Override
    public void onBackPressed() {
        this.finish();
    }

    /**
     * 添加Toobar右边功能，传入图片
     * @param resId
     */
    public void addMenuImageItme(int resId, View.OnClickListener mOnClickListener) {
        mTvMunu.setVisibility(View.GONE);
        mImg.setBackgroundResource(resId);
        mImg.setOnClickListener(mOnClickListener);
    }

    /**
     * Toobar添加 View，传入view 的 , 在方法里重写view的事件
     * @param view
     */
    public void addMenuView(View view ) {
        mToolbarTitle.setVisibility(View.GONE);
//        View  view = getLayoutInflater().inflate(resId, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER);
        view.setLayoutParams(params);

        mLlMenu.addView(view);
        mLlMenu.setVisibility(View.VISIBLE);

    }


    public TextView getaddMenuTextItme() {
        return mTvMunu;
    }

    public void initTitleView(){

    }
    
    public void setTitleBG(int color) {

        mToolbar.setBackgroundColor(color);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(color);
        }
    }

}
