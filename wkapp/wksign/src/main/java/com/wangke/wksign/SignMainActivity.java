package com.wangke.wksign;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.blankj.utilcode.utils.Utils;

public class SignMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_main);
        Utils.init(this);
    }
}
