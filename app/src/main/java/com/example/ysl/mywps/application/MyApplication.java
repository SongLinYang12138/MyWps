package com.example.ysl.mywps.application;


import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;


import java.io.File;

import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;


/**
 * Created by Administrator on 2017/12/23 0023.
 */

public class MyApplication extends MultiDexApplication {
    private String TAG = "aaa";
    String token = "ZSFFgGPsC2K8I9vy66QMO3v12hfQwFsD5nuDZRzqzhhSqTrkwzfFgbooxXrLy3SVcIyWlDQxhV1P/ZVCvWD5Aw==";
    private ImageLoaderConfiguration config;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Logger.addLogAdapter(new AndroidLogAdapter());
        config = new ImageLoaderConfiguration.Builder(getBaseContext())
                .threadPoolSize(1)
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(10)
                .diskCacheSize(100 * 1024 * 1024)
                .diskCacheFileCount(100)
                .build();
        ImageLoader.getInstance().init(config);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        int myALias = 3;
        JPushInterface.getAlias(this, myALias);

        try {

            rongYun();
        }catch (Exception e){

        }
    }


    public static Context getMyContext() {
        return context;
    }


    private void rongYun(){

        try {
            RongIM.init(this);
        }catch (Exception e){
            e.printStackTrace();
        }
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {

                Log.i(TAG,"onTokenIncorrect");
            }

            @Override
            public void onSuccess(String s) {
                Log.i(TAG,"链接成功" +s);

            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.i(TAG,"链接失败" +errorCode);

            }
        });

    }
}
