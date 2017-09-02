package com.hair.hairstyle.presenter;

import com.hair.hairstyle.net.result.LoginResult;

/**
 * Created by yunshan on 17/8/30.
 */

public interface LoginListener {

    void success(LoginResult user);

    void error();
}
