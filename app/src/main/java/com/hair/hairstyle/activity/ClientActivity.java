package com.hair.hairstyle.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.hair.hairstyle.Book;
import com.hair.hairstyle.BookManager;
import com.hair.hairstyle.R;
import com.hair.hairstyle.databinding.ActivityBindingBinding;

import java.util.List;

/**
 * Created by yunshan on 17/8/18.
 */

public class ClientActivity extends Activity {

    private static final String TAG = "ClientActivity";

    private List<Book> mBooks;
    private BookManager mBookManager;
    private boolean isBound;
    private ActivityBindingBinding mBinding;

    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (mBookManager == null) {
                return;
            }

            mBookManager.asBinder().unlinkToDeath(mDeathRecipient, 0);
            mBookManager = null;

            attemptBindService();
        }
    };

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "service connected");
            mBookManager = BookManager.Stub.asInterface(service);
            try {
                service.linkToDeath(mDeathRecipient, 0);
                mBooks = mBookManager.getBooks();
                Log.e(TAG, mBooks.toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBookManager = null;
            isBound = false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_binding);

        mBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isBound)
                    attemptBindService();
            }
        });


        mBinding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book();
                book.setId("1234567");
                book.setName("C language");
                try {
                    if (mBookManager == null) return;
                    Log.e(TAG, book.toString());
                    mBookManager.addBook(book);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void attemptBindService() {
        Intent intent = new Intent();
        intent.setAction("com.hair.aidl");
        intent.setPackage("com.hair.hairstyle");
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isBound)
            unbindService(mConnection);
    }
}
