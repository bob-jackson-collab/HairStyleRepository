package com.hair.hairstyle.test;

/**
 * Created by yunshan on 17/8/16.
 */

public class NDKJniUtils {

    static {
        System.loadLibrary("jni");
    }
    public native String getStr();
}
