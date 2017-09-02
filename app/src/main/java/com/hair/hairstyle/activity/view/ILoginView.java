package com.hair.hairstyle.activity.view;

import com.hair.hairstyle.net.result.LoginResult;

/**
 * Created by yunshan on 17/7/28.
 */

public interface ILoginView {

    void showLoading();

    void showUser(LoginResult user);

    void hideLoading();

}
