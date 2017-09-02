package com.hair.hairstyle.model;

import com.hair.hairstyle.presenter.LoginListener;

/**
 * Created by yunshan on 17/7/28.
 */

public interface ILoginModel {

    void requestLogin(String username, String password,LoginListener loginListener);
}
