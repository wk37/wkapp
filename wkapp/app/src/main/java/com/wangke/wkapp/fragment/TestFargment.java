package com.wangke.wkapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wangke.wkapp.R;
import com.wangke.wkcore.base.BaseFragment;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wk37 on 2017/3/29.
 */

public class TestFargment extends BaseFragment {

    private String msg;
    private RecyclerView mRecyclercview;

    public static TestFargment newInstance(String msg) {

        Bundle args = new Bundle();

        TestFargment fragment = new TestFargment();
        args.putString("msg", msg);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onLazyLoad() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View setRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            msg = args.getString("msg");
        }

        View view = inflater.inflate(R.layout.fragment_test, container, false);
        initView(view);
        return view;
    }

    @Override
    public void initView(View view) {
        mRecyclercview = (RecyclerView) view.findViewById(R.id.recyclercview);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i+"  wk");
        }

        CommonAdapter adapter = new CommonAdapter<String>(getActivity(), R.layout.item_test, list) {
            @Override
            protected void convert(ViewHolder holder, String o, int position) {
                holder.setText(R.id.text, o);
            }

        };
        mRecyclercview.setAdapter(adapter);
    }


    @Override
    public void initData() {

    }
}
