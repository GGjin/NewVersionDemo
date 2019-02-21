package com.mzw.image.glide.config;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelCache;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader;

import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomBaseGlideUrlLoader extends BaseGlideUrlLoader<String> {


    private ModelLoader concreteLoader;
    private ModelCache modelCache;

    /**
     * Url的匹配规则
     */
    Pattern pattern = Pattern.compile("__w-((?:-?\\d+)+)__");

    protected CustomBaseGlideUrlLoader(ModelLoader<GlideUrl, InputStream> concreteLoader, @Nullable ModelCache<String, GlideUrl> modelCache) {
        super(concreteLoader, modelCache);
        this.concreteLoader = concreteLoader;
        this.modelCache = modelCache;
    }

    @Override
    protected String getUrl(String s, int width, int height, Options options) {
        Matcher matcher = pattern.matcher(s);
        int bestBucket = 0;
        if (matcher.find()) {
            String[] found = matcher.group(1).split("-");
            for (String item : found) {
                bestBucket = Integer.parseInt(item);
                if (bestBucket >= width) break;
            }
        }
        return s;
    }

    @Override
    public boolean handles(@NonNull String s) {
        return true;
    }


    private static ModelCache urlCache = new ModelCache(150);

    public static final ModelLoaderFactory factory = new ModelLoaderFactory() {
        @NonNull
        @Override
        public ModelLoader build(@NonNull MultiModelLoaderFactory multiFactory) {
            return new  CustomBaseGlideUrlLoader(multiFactory.build(GlideUrl.class, InputStream.class), urlCache);
        }

        @Override
        public void teardown() {

        }
    };
}
