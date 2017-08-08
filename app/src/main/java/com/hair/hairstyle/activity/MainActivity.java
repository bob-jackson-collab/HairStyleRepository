package com.hair.hairstyle.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hair.hairstyle.R;
import com.hair.hairstyle.base.BaseActivity;
import com.hair.hairstyle.base.BasePresenter;

/**
 * Created by yunshan on 17/8/8.
 */

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }
}
