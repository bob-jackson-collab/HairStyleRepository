package com.hair.hairstyle.net.apiservice;

import com.hair.hairstyle.base.BaseEntity;
import com.hair.hairstyle.net.param.LoginParam;
import com.hair.hairstyle.net.result.LoginResult;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by yunshan on 17/7/31.
 */

public interface LoginService {

    @POST("auth/login")
    Observable<BaseEntity<LoginResult>> login(@Body LoginParam param);
}
