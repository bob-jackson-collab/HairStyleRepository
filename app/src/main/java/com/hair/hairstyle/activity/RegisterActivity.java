package com.hair.hairstyle.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hair.hairstyle.R;
import com.hair.hairstyle.base.BaseActivity;
import com.hair.hairstyle.base.BasePresenter;
import com.hair.hairstyle.databinding.ActivityRegisterBinding;
import com.hair.hairstyle.net.ServiceGenerator;
import com.hair.hairstyle.net.apiservice.LoginService;
import com.hair.hairstyle.net.param.RegisterParam;
import com.hair.hairstyle.net.result.RegisterResult;
import com.hair.hairstyle.rx.ResultFun;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yunshan on 17/8/8.
 */

public class RegisterActivity extends BaseActivity {

    private ActivityRegisterBinding mBinding;
    private LoginService mService;

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);


    }

    public void register(View view) {
        String name = mBinding.username.getText().toString().trim();
        String phone = mBinding.phone.getText().toString().trim();
        String password = mBinding.password.getText().toString().trim();

        mService = ServiceGenerator.createService(LoginService.class, "http://192.168.63.240:8890/");
        RegisterParam param = new RegisterParam();
        param.setPhone(phone);
        param.setName(name);
        param.setPassword(password);

        mService.register(param)
                .flatMap(new ResultFun<RegisterResult>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RegisterResult>() {
                    @Override
                    public void accept(RegisterResult registerResult) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

    }
}
