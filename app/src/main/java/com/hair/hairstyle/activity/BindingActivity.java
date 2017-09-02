package com.hair.hairstyle.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.hair.hairstyle.R;
import com.hair.hairstyle.databinding.ActivityBindingBinding;
import com.hair.hairstyle.service.LocalService;

import io.reactivex.Observable;

/**
 * Created by yunshan on 17/8/17.
 */

public class BindingActivity extends AppCompatActivity {


    private static final String TAG = "BindingActivity";
    private LocalService mService;
    private boolean mBound;
    private ActivityBindingBinding mBinding;
    private Messenger messenger;

    private Messenger replyMessenger = new Messenger(new MessageHandler());

    class MessageHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 1:
                    Bundle data = msg.getData();
                    String info = data.getString("reply");
                    Log.e(TAG, info);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
//            LocalService.LocalBinder binder = (LocalService.LocalBinder) service;
//            mService = binder.getService();

            messenger = new Messenger(service);
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            messenger = null;
            mBound = false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_binding);
        mBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.e("info",mService.getRandomNumber()+"");
//                Message msg = Message.obtain(null, MessengerDemo.MSG_FLAG, 0, 0);
//                msg.replyTo = replyMessenger;
//                try {
//                    messenger.send(msg);
//                } catch (RemoteException e
//                        ) {
//                    e.printStackTrace();
//                }
                Observable.just("11").map(s ->
                        Integer.parseInt(s)
                ).subscribe(integer ->
                        System.out.println(integer + "")
                );
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent();
        intent.setAction("com.hair.hairstyle.messenger");
        intent.setPackage("com.hair.hairstyle");
        mBound = true;
//        Intent intent = new Intent(this,LocalService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
    }
}
