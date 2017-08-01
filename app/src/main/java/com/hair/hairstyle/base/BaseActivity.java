package com.hair.hairstyle.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.hair.hairstyle.net.NetChangeObserver;
import com.hair.hairstyle.net.NetStateReceiver;
import com.hair.hairstyle.net.NetUtils;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by yunshan on 17/7/28.
 */

public abstract class BaseActivity<V, P extends BasePresenter<V>> extends AppCompatActivity {

    private BaseActivity mContext;
    private P mPresenter;
    private NetChangeObserver mObserver;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        mContext = this;
        setBaseConfig();
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detach();
        }
        NetStateReceiver.removeRegisterObserver(mObserver);
    }

    public abstract P createPresenter();

    protected void onNetConnected(NetUtils.NetType type) {
    }

    protected void onNetDisConnect() {
    }

    private void setStatusBarColor(int color) {
        //如果Api版本大于19的
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

            SystemBarTintManager systemBarTintManager = new SystemBarTintManager(this);
            systemBarTintManager.setNavigationBarTintEnabled(false);
            systemBarTintManager.setStatusBarTintEnabled(true);
            systemBarTintManager.setStatusBarTintColor(color);
        }
    }

    private void setBaseConfig() {
        //监听网络的广播
        mObserver = new NetChangeObserver() {
            @Override
            public void onNetConnected(NetUtils.NetType type) {
                onNetConnected(type);
            }

            @Override
            public void onNetDisConnect() {
                onNetDisConnect();
            }
        };
        //注册监听
        NetStateReceiver.registerObserver(mObserver);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //加入activity管理容器
//        AppManager.getAppManager().addActivity(this);
        //设置状态栏的颜色 默认是红色
        setStatusBarColor(Color.RED);
        //设置屏幕只能竖直
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void gotoActivity(Class<?> clz) {
        gotoActivity(clz, false, null);
    }

    public void gotoActivity(Class<?> clz, boolean isCurrentActivity) {
        gotoActivity(clz, isCurrentActivity, null);
    }

    //activity之间的跳转 默认不关闭当前Activity
    public void gotoActivity(Class<?> clz, boolean isCurrentActivity, Bundle ex) {
        Intent intent = new Intent(this, clz);
        if (ex != null) {
            intent.putExtras(ex);
        }
        startActivity(intent);
        if (isCurrentActivity) {
            finish();
        }
    }
}
