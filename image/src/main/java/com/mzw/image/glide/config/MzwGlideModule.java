package com.mzw.image.glide.config;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;

@GlideModule
public class MzwGlideModule extends AppGlideModule {

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        super.applyOptions(context, builder);
        long cacheSize = 50 * 1024 * 1024;
        builder.setMemoryCache(new LruResourceCache(cacheSize));
    }


    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);
        registry.append(String.class, InputStream.class, CustomBaseGlideUrlLoader.factory);
    }
}
