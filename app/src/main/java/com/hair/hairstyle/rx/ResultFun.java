package com.hair.hairstyle.rx;

import com.hair.hairstyle.base.BaseEntity;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;


/**
 * Created by yunshan on 17/7/28.
 */

public class ResultFun<T> implements Function<BaseEntity<T>, Observable<T>> {

    @Override
    public Observable<T> apply(@NonNull BaseEntity<T> tBaseEntity) throws Exception {
        if (tBaseEntity == null) {
            return Observable.error(new Throwable("获取内容失败"));
        }
        int code = tBaseEntity.getCode();
        if (code == 0) {
            return Observable.just(tBaseEntity.getData());
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(code).append(":").append(tBaseEntity.getMessage());
            return Observable.error(new HairException(sb.toString()));
        }
    }
}
