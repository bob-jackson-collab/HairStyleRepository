package com.hair.hairstyle.presenter;

import com.hair.hairstyle.net.result.RegisterResult;

/**
 * Created by yunshan on 17/8/30.
 */

public interface RegisterListener {

    void registerSuccess(RegisterResult registerResult);

    void registerError();
}
