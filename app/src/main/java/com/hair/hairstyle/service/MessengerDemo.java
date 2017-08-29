package com.hair.hairstyle.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by yunshan on 17/8/17.
 */

public class MessengerDemo extends Service{

    public final static int MSG_FLAG = 0010;

    private final Messenger messenger = new Messenger(new ServiceHandler());
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

    class ServiceHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_FLAG:
                    Log.e("service",msg.obj.toString());
                    Message message = Message.obtain();
                    Bundle bundle = new Bundle();
                    bundle.putString("reply","hello client ,the message i received");
                    msg.setData(bundle);
                    Messenger s = msg.replyTo;
                    try {
                        s.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }
}
