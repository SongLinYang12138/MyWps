package com.example.ysl.mywps.application;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;


/**
 * Created by Administrator on 2017/12/23 0023.
 */

public class MyApplication extends Application {

    private ImageLoaderConfiguration config;

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.addLogAdapter(new AndroidLogAdapter());
        config = new ImageLoaderConfiguration.Builder(getBaseContext())
                .threadPoolSize(1)
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(10)
                .diskCacheSize(100 * 1024 * 1024)
                .diskCacheFileCount(100)
                .build();
        ImageLoader.getInstance().init(config);
    }
}
