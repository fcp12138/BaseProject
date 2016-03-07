package com.fcp.baseproject.application;

import android.app.Application;

import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

/**
 * 自定义应用类
 * Created by fcp on 2016/3/6.
 */
public class AppManager extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化图片缓存管理器
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(getApplicationContext())
                .memoryCacheExtraOptions(480, 800) // max width, max height
                .threadPoolSize(3)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)  //降低线程的优先级保证主UI线程不受太大影响
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheSize(50 * 1024 * 1024)// 50 Mb sd卡(本地)缓存的最大值
                .memoryCache(new LruMemoryCache(10 * 1024 * 1024)) //建议内存设在5-10M,可以有比较好的表现
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(getApplicationContext(), 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)
                .writeDebugLogs() // Remove for release app
                .build();
        ImageLoader.getInstance().init(config); //初始化
    }
}
