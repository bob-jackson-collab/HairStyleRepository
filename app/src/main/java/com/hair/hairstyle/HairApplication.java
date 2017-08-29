package com.hair.hairstyle;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.hair.hairstyle.utils.PreferenceManager;
import com.zr.library.StatusBarManager;
import com.zr.library.config.DefaultStatusBarCompatConfig;

/**
 * Created by yunshan on 17/7/28.
 */

public class HairApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceManager.init(getApplicationContext());
        SDKInitializer.initialize(getApplicationContext());
        StatusBarManager.getsInstance().init(new DefaultStatusBarCompatConfig());
    }
}
