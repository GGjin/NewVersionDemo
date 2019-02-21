package com.mzw.newversiondemo;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.mzw.image.base.ILoaderStrategy;
import com.mzw.image.base.LoaderOptions;
import com.mzw.image.glide.RotateTransformation;
import com.mzw.image.glide.RoundCorner;
import com.mzw.image.glide.config.GlideApp;
import com.mzw.image.glide.config.GlideRequest;
import com.mzw.image.glide.config.GlideRequests;

/**
 * Create by GG on 2019/2/20
 * mail is gg.jin.yu@gmail.com
 */
public class GlideLoader implements ILoaderStrategy {
    @Override
    public void loadImage(final LoaderOptions options) {
        GlideRequests glideRequests = null;
        if (options.targetView != null)
            glideRequests = GlideApp.with(options.targetView.getContext());
        else
            glideRequests = GlideApp.with(App.gApp);

        GlideRequest glideRequest = null;
        if (options.url != null) {
            glideRequest = glideRequests.load(options.url);
        } else if (options.file != null) {
            glideRequest = glideRequests.load(options.file);
        } else if (options.drawableResId != 0) {
            glideRequest = glideRequests.load(options.drawableResId);
        } else if (options.uri != null) {
            glideRequest = glideRequests.load(options.uri);
        }

        if (glideRequest == null) {
            throw new NullPointerException("requestCreator must not be null");
        }


        if (options.isCenterInside) {
            glideRequest.centerInside();
        } else if (options.isCenterCrop) {
            glideRequest.centerCrop();
        }
        if (options.config != null) {
            if (options.config == Bitmap.Config.RGB_565)
                glideRequest.format(DecodeFormat.PREFER_RGB_565);
            else
                glideRequest.format(DecodeFormat.PREFER_ARGB_8888);

        }
        if (options.targetHeight != 0 && options.targetWidth != 0)
            glideRequest.override(options.targetWidth, options.targetHeight);

        if (options.targetSize!=0)
            glideRequest.override(options.targetSize );

        if (options.errorResId != 0)
            glideRequest.error(options.errorResId);

        if (options.placeholderResId != 0)
            glideRequest.placeholder(options.placeholderResId);

        if (options.placeholder != null)
            glideRequest.placeholder(options.placeholder);


        if (options.bitmapAngle != 0)
            glideRequest.transform(new RoundCorner(App.gApp, options.bitmapAngle, options.bitmapAngle,
                    options.bitmapAngle, options.bitmapAngle));

        if (options.skipLocalCache)
            glideRequest.skipMemoryCache(true);

        if (options.skipNetCache)
            glideRequest.diskCacheStrategy(DiskCacheStrategy.NONE);

        if (options.degrees != 0)
            glideRequest.transform(new RotateTransformation(options.degrees));


        if (options.callBack != null)
            glideRequest.listener(new RequestListener<Bitmap>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {
                    options.callBack.onBitmapFailed(e);
                    return false;
                }

                @Override
                public boolean onResourceReady(Bitmap bitmap, Object model, Target target,
                                               DataSource dataSource, boolean isFirstResource) {
                    options.callBack.onBitmapLoaded(bitmap);
                    return false;
                }
            }).preload();


        if (options.targetView instanceof ImageView) {
            glideRequest.into(((ImageView) options.targetView));
        }

    }

    @Override
    public void clearMemoryCache() {

    }

    @Override
    public void clearDiskCache() {

    }


    class GlideTarget extends CustomTarget {

        @Override
        public void onResourceReady(@NonNull Object resource, @Nullable Transition transition) {

        }

        @Override
        public void onLoadCleared(@Nullable Drawable placeholder) {

        }
    }
}
