package com.mzw.image.glide.config;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;

import java.io.InputStream;

import okhttp3.Call;

public class OkHttpUrlLoader implements ModelLoader<GlideUrl,InputStream> {
    @NonNull
    private Call.Factory client;
    @NonNull
    public static ModelLoaderFactory factory = (ModelLoaderFactory) (new ModelLoaderFactory() {
        private volatile Call.Factory internalClient;
        private Call.Factory client = this.getInternalClient();

        private final Call.Factory getInternalClient() {
            if (this.internalClient == null) {
                synchronized (this) {
                }

                try {
                    if (this.internalClient == null) {
                        this.internalClient = OkHttpProvider.createOkHttpClient();
                    }

                } finally {
                    ;
                }
            }

            return this.internalClient;
        }

        @NonNull
        public ModelLoader build(@Nullable MultiModelLoaderFactory multiFactory) {
            return new OkHttpUrlLoader(client);
        }

        public void teardown() {
        }
    });



    @Nullable
    @Override
    public LoadData buildLoadData(@NonNull GlideUrl model, int width, int height, @NonNull Options options) {
        return  new LoadData(model, new OkHttpStreamFetcher(client, model));
    }

    @Override
    public boolean handles(@NonNull GlideUrl glideUrl) {
        return true;
    }


    public OkHttpUrlLoader(@NonNull Call.Factory client) {
        super();
        this.client = client;
    }

}
