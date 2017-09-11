package com.hair.hairstyle.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.hair.hairstyle.R;
import com.hair.hairstyle.net.NetChangeObserver;
import com.hair.hairstyle.net.NetStateReceiver;
import com.hair.hairstyle.net.NetUtils;
import com.zr.library.StatusBarManager;

/**
 * Created by yunshan on 17/7/28.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private BaseActivity mContext;
    private NetChangeObserver mObserver;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        mContext = this;
        setBaseConfig();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetStateReceiver.removeRegisterObserver(mObserver);
    }


    protected void onNetConnected(NetUtils.NetType type) {
    }

    protected void onNetDisConnect() {
    }

    private void setStatusBarColor(int color) {
        StatusBarManager.getsInstance().setColor(this, color);
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
        //设置状态栏的颜色 默认是透明的
        setStatusBarColor(getResources().getColor(R.color.colorTab));
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            View v = getCurrentFocus();

            //如果不是落在EditText区域，则需要关闭输入法
            if (HideKeyboard(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    // 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
    private boolean HideKeyboard(View view, MotionEvent event) {
        if (view != null && (view instanceof EditText)) {

            int[] location = { 0, 0 };
            view.getLocationInWindow(location);

            //获取现在拥有焦点的控件view的位置，即EditText
            int left = location[0], top = location[1], bottom = top + view.getHeight(), right = left + view.getWidth();
            //判断我们手指点击的区域是否落在EditText上面，如果不是，则返回true，否则返回false
            boolean isInEt = (event.getX() > left && event.getX() < right && event.getY() > top
                    && event.getY() < bottom);
            return !isInEt;
        }
        return false;
    }

}
