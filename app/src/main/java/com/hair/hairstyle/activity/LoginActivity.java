package com.hair.hairstyle.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.hair.hairstyle.R;
import com.hair.hairstyle.activity.view.ILoginView;
import com.hair.hairstyle.base.BaseActivity;
import com.hair.hairstyle.databinding.ActivityLoginBinding;
import com.hair.hairstyle.net.result.LoginResult;
import com.hair.hairstyle.presenter.LoginPresenter;
import com.hair.hairstyle.presenter.LoginPresenterImp;

/**
 * Created by yunshan on 17/7/28.
 */

public class LoginActivity extends BaseActivity implements ILoginView {

    private static final String TAG = "LoginActivity";
    private ActivityLoginBinding mBinding;
    private LoginPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        mPresenter = new LoginPresenterImp(this);

    }

    public void login(View v) {
        mPresenter.getLoginResult(mBinding.username.getText().toString().trim(), mBinding.password.getText().toString().trim());
    }

    @Override
    public void showLoading() {
        Log.e(TAG, "showLoading: ");
    }

    @Override
    public void showUser(LoginResult user) {
        Log.e(TAG, user.getName() + "--" + user.getPassword());
    }


    @Override
    public void hideLoading() {
        Log.e(TAG, "hideLoading: ");
    }


}
