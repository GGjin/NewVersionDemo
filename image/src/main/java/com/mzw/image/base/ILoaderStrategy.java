package com.mzw.image.base;

/**
 * Create by GG on 2019/2/20
 * mail is gg.jin.yu@gmail.com
 */

public interface ILoaderStrategy {

    void loadImage(LoaderOptions options);

    /**
     * 清理内存缓存
     */
    void clearMemoryCache();

    /**
     * 清理磁盘缓存
     */
    void clearDiskCache();

}