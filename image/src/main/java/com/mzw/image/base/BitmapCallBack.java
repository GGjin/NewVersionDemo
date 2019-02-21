package com.mzw.image.base;

import android.graphics.Bitmap;

/**
 * Create by GG on 2019/2/20
 * mail is gg.jin.yu@gmail.com
 */
public interface BitmapCallBack {

    void onBitmapLoaded(Bitmap bitmap);

    void onBitmapFailed(Exception e);

    public static class EmptyCallback implements BitmapCallBack {


        @Override
        public void onBitmapLoaded(Bitmap bitmap) {

        }

        @Override
        public void onBitmapFailed(Exception e) {

        }
    }
}