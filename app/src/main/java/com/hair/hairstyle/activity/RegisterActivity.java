package com.hair.hairstyle.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.hair.hairstyle.R;
import com.hair.hairstyle.activity.view.IRegisterView;
import com.hair.hairstyle.base.BaseActivity;
import com.hair.hairstyle.databinding.ActivityRegisterBinding;
import com.hair.hairstyle.presenter.RegisterPresenter;
import com.hair.hairstyle.presenter.RegisterPresenterImp;

/**
 * Created by yunshan on 17/8/8.
 */

public class RegisterActivity extends BaseActivity implements IRegisterView {

    private static final String TAG = "RegisterActivity";
    private ActivityRegisterBinding mBinding;
    private RegisterPresenter mPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        mPresenter = new RegisterPresenterImp(this);
    }

    public void register(View view) {
        String name = mBinding.username.getText().toString().trim();
        String phone = mBinding.phone.getText().toString().trim();
        String password = mBinding.password.getText().toString().trim();

        mPresenter.getRegisterResult(name, phone, password);
    }

    @Override
    public void showLoading() {
        Log.e(TAG, "showLoading: ");
    }

    @Override
    public void hideLoading() {
        Log.e(TAG, "hideLoading: ");
    }

    @Override
    public void showRegisterResult() {
        Log.e(TAG, "showRegisterResult: " );
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
