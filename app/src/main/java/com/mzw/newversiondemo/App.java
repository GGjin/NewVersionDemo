package com.mzw.newversiondemo;

import android.app.Application;

import com.mzw.image.base.ImageLoader;

/**
 * Create by GG on 2019/2/20
 * mail is gg.jin.yu@gmail.com
 */
public class App extends Application {


    public static App gApp;

    @Override
    public void onCreate() {
        super.onCreate();
        gApp = this;
        //初始化图片库
        ImageLoader.getInstance().setGlobalImageLoader(new GlideLoader());
    }

}
