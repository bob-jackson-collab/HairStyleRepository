package com.hair.hairstyle.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hair.hairstyle.Book;
import com.hair.hairstyle.BookManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yunshan on 17/8/18.
 */

public class AIDLService extends Service {

    private static final String TAG = "AIDLService";
    private List<Book> mBooks = new ArrayList<>(10);

    private Binder mBinder = new BookManager.Stub() {
        @Override
        public List<Book> getBooks() throws RemoteException {
            if (mBooks != null) {
                Log.e(TAG, mBooks.toString());
                return mBooks;
            }
            return new ArrayList<>();
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            if (mBooks == null) {
                mBooks = new ArrayList<>(10);
            }
            if (book == null) {
                book = new Book();
            }

            book.setName("Head First");
            if (!mBooks.contains(book)) {
                mBooks.add(book);
            }

            Log.e(TAG, mBooks.toString());
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate()");
        Book book = new Book();
        book.setId("111");
        book.setName("C++");
        mBooks.add(book);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
