package com.hair.hairstyle.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.hair.hairstyle.R;
import com.hair.hairstyle.activity.view.ILoginView;
import com.hair.hairstyle.base.BaseActivity;
import com.hair.hairstyle.databinding.ActivityLoginBinding;
import com.hair.hairstyle.net.ServiceGenerator;
import com.hair.hairstyle.net.apiservice.LoginService;
import com.hair.hairstyle.net.param.LoginParam;
import com.hair.hairstyle.net.result.LoginResult;
import com.hair.hairstyle.presenter.LoginPresenter;
import com.hair.hairstyle.rx.ResultFun;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yunshan on 17/7/28.
 */

public class LoginActivity extends BaseActivity<ILoginView, LoginPresenter> {

    private ActivityLoginBinding mBinding;
    private LoginService mService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        mService = ServiceGenerator.createService(LoginService.class, "http://192.168.63.240:8890/");
//        UpdaterConfig config = new UpdaterConfig.Builder(this)
//                .setTitle(getResources().getString(R.string.app_name))
//                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
//                .setDescription("去哪里发")
//                .setFileUrl("http://app.yunshanmeicai.com/wms_rf/Rf-debug-stage-2.9.2-201707311702.apk")
//                .setCanMediaScanner(true)
//                .build();
//        Updater.get().download(config);


    }

    public void login(View v) {
        LoginParam param = new LoginParam();
        param.setPhone(mBinding.username.getText().toString().trim());
        param.setPassword(mBinding.password.getText().toString().trim());

        Disposable success = mService.login(param).flatMap(new ResultFun<LoginResult>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LoginResult>() {
                    @Override
                    public void accept(LoginResult loginResult) throws Exception {
                        Toast.makeText(LoginActivity.this, "success", Toast.LENGTH_SHORT).show();
                    }

                });
    }

    @Override
    public LoginPresenter createPresenter() {
        return null;
    }
}
