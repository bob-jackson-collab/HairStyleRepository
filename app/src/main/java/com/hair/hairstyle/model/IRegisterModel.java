package com.hair.hairstyle.model;

import com.hair.hairstyle.presenter.RegisterListener;

/**
 * Created by yunshan on 17/8/30.
 */

public interface IRegisterModel {


    void register(String username, String phone, String password, RegisterListener registerListener);
}
