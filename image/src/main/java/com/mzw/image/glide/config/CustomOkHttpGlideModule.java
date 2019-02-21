package com.mzw.image.glide.config;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.LibraryGlideModule;

import java.io.InputStream;


public class CustomOkHttpGlideModule extends LibraryGlideModule {

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);
        if (registry != null) {
            registry.replace(GlideUrl.class, InputStream.class, OkHttpUrlLoader.factory);
        }
    }
}
