package com.hair.hairstyle.property;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

/**
 * Created by yunshan on 17/8/31.
 */

public class BindImageProperty {

    @BindingAdapter({"bind:image"})
    public static void displayImage(ImageView imageView, String url) {
        GlideApp.with(imageView.getContext()).load(url).into(imageView);
    }


}
