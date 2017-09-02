package com.hair.hairstyle.presenter;

import com.hair.hairstyle.activity.view.IRegisterView;
import com.hair.hairstyle.model.IRegisterModel;
import com.hair.hairstyle.model.RegisterModelImp;
import com.hair.hairstyle.net.result.RegisterResult;

/**
 * Created by yunshan on 17/8/30.
 */

public class RegisterPresenterImp implements RegisterPresenter, RegisterListener {

    private IRegisterModel model;
    private IRegisterView view;

    public RegisterPresenterImp(IRegisterView view) {
        model = new RegisterModelImp();
        this.view = view;
    }

    @Override
    public void getRegisterResult(String username, String phone, String password) {
        view.showLoading();
        model.register(username, phone, password, this);
    }

    @Override
    public void registerSuccess(RegisterResult registerResult) {
        view.hideLoading();
        view.showRegisterResult();
    }

    @Override
    public void registerError() {
        view.hideLoading();
    }
}
