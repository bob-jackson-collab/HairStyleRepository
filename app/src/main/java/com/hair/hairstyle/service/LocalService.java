package com.hair.hairstyle.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Random;

/**
 * Created by yunshan on 17/8/17.
 */

public class LocalService extends Service {

    private IBinder mBinder = new LocalBinder();
    private Random mGenerator = new Random();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public int getRandomNumber(){
        return mGenerator.nextInt(100);
    }

    public class LocalBinder extends Binder {

        public LocalService getService() {
            return LocalService.this;
        }
    }

}
