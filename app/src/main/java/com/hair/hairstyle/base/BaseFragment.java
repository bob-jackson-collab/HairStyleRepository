package com.hair.hairstyle.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;

/**
 * Created by yunshan on 17/7/31.
 */

public abstract class BaseFragment extends Fragment {

    protected Activity mContext;
    private boolean mIsVisible;
    private boolean mIsPrepare;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (Activity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //设置强制竖屏展示
        mContext.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (0 != getContentViewLayoutID()) {
            View inflateView = getFragmentView(inflater,container,getContentViewLayoutID());
            // 解决点击穿透问题
            inflateView.setOnTouchListener((v, event) -> true);
            return inflateView;
        } else {
            throw new IllegalStateException("no layout id");
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mIsPrepare = true;
        initViewsAndEvents(view);
        onLazyLoad();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsVisible = isVisibleToUser;
        if (mIsVisible && mIsPrepare) {
            onVisibleToUser();
        }
    }

    protected void onVisibleToUser() {
        if (mIsPrepare && mIsVisible) {
            onLazyLoad();
        }
    }

    /**
     * Case 1：当使用Fragment去嵌套另外一些子Fragment的时候，我们需要去管理子Fragment，这时候需要调用ChildFragmentManager去管理这些子Fragment，由此可能产生的Exception主要是：
     * java.lang.IllegalStateException: No activity
     * 首先我们来分析一下Exception出现的原因：
     * 通过DEBUG发现，当第一次从一个Activity启动Fragment，然后再去启动子Fragment的时候，存在指向Activity的变量，但当退出这些Fragment之后回到Activity，然后再进入Fragment的时候，这个变量变成null，这就很容易明了为什么抛出的异常是No activity
     * 这个Exception是由什么原因造成的呢？如果想知道造成异常的原因，那就必须去看Fragment的相关代码，发现Fragment在detached之后都会被reset掉，但是它并没有对ChildFragmentManager做reset，所以会造成ChildFragmentManager的状态错误。
     * 找到异常出现的原因后就可以很容易的去解决问题了，我们需要在Fragment被detached的时候去重置ChildFragmentManager
     */
    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = android.support.v4.app.Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 用户可见时会加载
     */
    protected void onLazyLoad() {

    }


    protected abstract int getContentViewLayoutID();

    protected abstract void initViewsAndEvents(View view);

    protected abstract View getFragmentView(LayoutInflater inflater, @Nullable ViewGroup container,int layoutId);
}
