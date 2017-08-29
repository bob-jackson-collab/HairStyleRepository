package com.hair.hairstyle.property;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;

import java.io.File;

/**
 * Created by Administrator on 2017/2/22.
 */

@GlideModule
public class HairGlideModule extends AppGlideModule {

    private String path;  //设置缓存的路径

    public static final int FILE_CACHE_SIZE = 50 * 1024 * 1024;

    public static final int MEMORY_CACHE_SIZE = 10 * 1024 * 1024;

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

        path = context.getExternalCacheDir().getAbsolutePath() + "hair_cache";
        final File cacheFile = new File(path);

        builder.setDiskCache(new DiskCache.Factory() {
            @Override
            public DiskCache build() {
                return DiskLruCacheWrapper.get(cacheFile, FILE_CACHE_SIZE);
            }
        });

        builder.setMemoryCache(new LruResourceCache(MEMORY_CACHE_SIZE));

    }

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        super.registerComponents(context, glide, registry);
    }
}
