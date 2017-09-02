package com.hair.hairstyle.property;

import android.content.Context;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;

import java.io.File;

/**
 * Created by Administrator on 2017/2/22.
 */

@GlideModule
public class HairGlideModule extends AppGlideModule {


    private static final int FILE_CACHE_SIZE = 50 * 1024 * 1024;

    private static final int MEMORY_CACHE_SIZE = 10 * 1024 * 1024;

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

        String path;  //设置缓存的路径
        if (context.getExternalCacheDir() != null) {
            path = context.getExternalCacheDir().getAbsolutePath() + "hair_cache";
        } else {
            path = Environment.getExternalStoragePublicDirectory(Environment.MEDIA_CHECKING).getAbsolutePath() + "hair_cache";
        }
        final File cacheFile = new File(path);
        builder.setDiskCache(() -> DiskLruCacheWrapper.get(cacheFile, FILE_CACHE_SIZE));


        builder.setMemoryCache(new LruResourceCache(MEMORY_CACHE_SIZE));

//        RequestOptions options = new RequestOptions();
//        options.placeholder(new ColorDrawable(Color.RED))
//                .error(new ColorDrawable(Color.BLUE))
//                .fallback(new ColorDrawable(Color.GREEN))
//                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
//                .encodeFormat(Bitmap.CompressFormat.PNG);
//
//        builder.setDefaultRequestOptions(options);
    }

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        super.registerComponents(context, glide, registry);
    }
}
