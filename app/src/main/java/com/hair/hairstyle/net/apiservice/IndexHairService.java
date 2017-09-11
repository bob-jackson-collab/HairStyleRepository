package com.hair.hairstyle.net.apiservice;

import com.hair.hairstyle.base.BaseEntity;
import com.hair.hairstyle.net.param.HairIndexParam;
import com.hair.hairstyle.net.param.HairTypeParam;
import com.hair.hairstyle.net.result.HairStoreResult;
import com.hair.hairstyle.net.result.HairTypeResult;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by yunshan on 17/8/31.
 */

public interface IndexHairService {

    @POST("nearStore")
    Observable<BaseEntity<HairStoreResult>> getStoreList(@Body HairIndexParam param);

    @POST("hairstyleList")
    Observable<BaseEntity<HairTypeResult>> getHairTypeList(@Body HairTypeParam param);
}
