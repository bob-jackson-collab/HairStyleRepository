package com.hair.hairstyle.presenter;

import com.hair.hairstyle.activity.view.ILoginView;
import com.hair.hairstyle.model.ILoginModel;
import com.hair.hairstyle.model.LoginModelImp;
import com.hair.hairstyle.net.result.LoginResult;

/**
 * Created by yunshan on 17/8/30.
 */

public class LoginPresenterImp implements LoginPresenter,LoginListener{

    private ILoginModel model;
    private ILoginView loginView;

    public LoginPresenterImp(ILoginView loginView){
        model = new LoginModelImp();
        this.loginView = loginView;
    }

    @Override
    public void getLoginResult(String username, String password) {
        loginView.showLoading();
         model.requestLogin(username,password,this);
    }


    @Override
    public void success(LoginResult user) {
        loginView.hideLoading();
        loginView.showUser(user);
    }

    @Override
    public void error() {
        loginView.hideLoading();
    }
}
