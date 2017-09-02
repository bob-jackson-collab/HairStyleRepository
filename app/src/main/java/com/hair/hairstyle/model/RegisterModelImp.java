package com.hair.hairstyle.model;

import com.hair.hairstyle.net.ServiceGenerator;
import com.hair.hairstyle.net.apiservice.LoginService;
import com.hair.hairstyle.net.param.RegisterParam;
import com.hair.hairstyle.net.result.RegisterResult;
import com.hair.hairstyle.presenter.RegisterListener;
import com.hair.hairstyle.rx.ResultFun;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yunshan on 17/8/30.
 */

public class RegisterModelImp implements IRegisterModel {

    @Override
    public void register(String username, String phone, String password, RegisterListener listener) {
        RegisterParam param = new RegisterParam();
        param.setName(username);
        param.setPhone(phone);
        param.setPassword(password);

        ServiceGenerator.createService(LoginService.class, "http://192.168.63.11:8890/")
                .register(param)
                .flatMap(new ResultFun<RegisterResult>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RegisterResult>() {
                    @Override
                    public void accept(RegisterResult registerResult) throws Exception {
                        listener.registerSuccess(registerResult);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        listener.registerError();
                    }
                });
    }

}
