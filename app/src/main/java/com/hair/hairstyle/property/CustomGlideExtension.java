package com.hair.hairstyle.property;

import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by yunshan on 17/9/2.
 */

@GlideExtension
public class CustomGlideExtension {

    private static final int MINI_THUMB_SIZE = 100;


    private CustomGlideExtension(){

    }

    @GlideOption
    public static void miniThumb(RequestOptions options,int thumbSize){
        if(thumbSize == 0){
            options.fitCenter().override(MINI_THUMB_SIZE);
        }else {
            options.fitCenter().override(thumbSize);
        }
    }
}
