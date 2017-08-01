package com.hair.hairstyle.base;

import java.lang.ref.WeakReference;

/**
 * Created by yunshan on 17/7/28.
 */

public abstract class BasePresenter<V> {

    private WeakReference mRefView;

    public void attachView(V view) {
        mRefView = new WeakReference<V>(view);
    }

    public abstract void fetch();

    public void detach() {
        if (mRefView != null) {
            mRefView.clear();
            mRefView = null;
        }
    }
}
