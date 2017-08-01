package com.hair.hairstyle.net;


import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSource;

/**
 * http日志
 * Created at 2016/01/29 15:12
 * Copyright (c) 2015年 Beijing Yunshan Information Technology Co., Ltd. All rights reserved.
 */
public class LoggingInterceptor implements Interceptor {

    public static final String TAG = LoggingInterceptor.class.getSimpleName();
    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        //打印Request数据
        logRequest(request);
        Response response = chain.proceed(request);
        logResponse(response);
        return response;
    }

    private void logRequest(Request request) throws IOException {

        Log.i(TAG, request.method() + "   " + request.url());
        Headers headers = request.headers();
        for (int i = 0, count = headers.size(); i < count; i++) {
            String name = headers.name(i);
            // Skip headers from the request body as they are explicitly logged above.
            if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                Log.i(TAG, name + ": " + headers.value(i));
            }
        }

        if (request.body() != null) {
            Buffer buffer = new Buffer();
            request.body().writeTo(buffer);
            Charset charset = UTF8;
            MediaType contentType = request.body().contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }
            Log.i(TAG, buffer.readString(charset));
            Log.i(TAG, "--> END " + request.method() + " (" + request.body().contentLength() + "-byte body)");
        }
    }

    private void logResponse(Response response) throws IOException {
        if (response != null) {
            Log.i(TAG, response.code() + "  " + response.message());
            Headers headers = response.headers();
            for (int i = 0, count = headers.size(); i < count; i++) {
                Log.i(TAG, headers.name(i) + ": " + headers.value(i));
            }
            long contentLength = response.body().contentLength();
            BufferedSource source = response.body().source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();

            Charset charset = UTF8;
            MediaType contentType = response.body().contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }
            if (contentLength != 0) {
                Log.i(TAG, buffer.clone().readString(charset));
            }

        } else {
            Log.i(TAG, "Response is null");
        }
    }

}