package com.example.ysl.mywps.application;


import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.support.multidex.MultiDexApplication;
import android.telecom.Call;
import android.util.Log;

import com.example.ysl.mywps.R;
import com.example.ysl.mywps.net.HttpUtl;
import com.example.ysl.mywps.ui.activity.LoginActivity;
import com.example.ysl.mywps.utils.SharedPreferenceUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;


import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;


/**
 * Created by Administrator on 2017/12/23 0023.
 */

public class MyApplication extends MultiDexApplication implements Thread.UncaughtExceptionHandler {
    private String TAG = "aaa";
    private ImageLoaderConfiguration config;
    private static Context context;
    //24874914
    private String appId = "24874914-1";
    //    f8846531340ff4ef9f2d8f1af309a339
    private String appSecret = "f8846531340ff4ef9f2d8f1af309a339";
    private String rsaSecret = "MIIEwAIBADANBgkqhkiG9w0BAQEFAASCBKowggSmAgEAAoIBAQDY4yyMqEDxQZyHVHso3NTi5BpJeuhh77hdUya3AcKevbTZDbjUFaP7FtffX0AhU21ol3e7/93CjuJRuiR08NdnsagPeFwai5vp7FcOUzaBXHKEFWwyMMQgwNH7RtdGVACaiNblOemHfn7Ud8Yd7XuGigs5P5e+q7yIIllAX7l1C2UeZryq5dHKmjh7TXH438AV/GpW7qZ4ztExmF6OF03G9iv6DMbsctilSq6bc1XsTit4bqVttPaTUoehKR2p0HLoGzkGcXvUqeBqZ6WwqaD+RFDOMAba09RQyzyvLfLH4VR/x02j/g2nEC6HcY76TbT8LcUM/Q2WTTgHEwUlYyPrAgMBAAECggEBAKzJVy17wbkyTzSVPkUQoV1c4gBWNZoIc7WN0BMOCPGUKuadAvmbf/dCfJqM9LdYEpq3yQDyV8ZvNKhthivPz5vhsgUZGy9et8jFq5lS++JVNzo5IuhdZ3K8is0H4+s4cv6qM6GM4Jo8UWiNPz2Ke9V0Vnq2SfryMJG346jgWgx+nvxPFbplwyp0IQ+r/my2O42olmx8VK/gGfIdfyICgOjwmjVhxHbz4BGuSLkQQS6ybyrajC9wKf0VpJm5Spcw3RdBeiZMBKq4k+dJJxKryZ2bnxxOv3UWqClSv2/76b8Os/OLjR+hS7hH2v8Sr2Npklzrrjkq0UgAiHqwjgPLlNkCgYEA8FCuMYEG01/7BuTb2Ati6i2bvOcdn+ixLl5+VgoSbspCm69MaSrI80kmtgU4YosOGGbmBPIop+/Vrs8O0RMOaHAmKxEfzkCWtT/468JKEFwg+3EDV/Lb6T3RBpZmdWVlKpkXvjrzJ5LXZT88bN3fTVsGPnSmIcUxZKXKkeSdwTcCgYEA5wsMrVddg0MhU5ZZrt7gug7ldwzi31Ohk50nm+SeToNAg8EwGcZ3lFKDirg0XBAjiyVflQij6zcxY3dXKSu9KS03G7K2on0bb+27GfS2u3m0iaMaupsdi0aff6zdCSKktUIUIEYf/ZJ8WIikTkODxOF1m7Q6h4/3R6mrI1MU3O0CgYEA6kS8i2XM0aJvhHj2F9aBxuhPOgfTyV992kYjwpxu+7IQ3NQ3GQ6sdv6IzkiofugwBXnKImgvWgdjt9YCC68YDJfHnZRD/TzZrjd+4dW1fd1SCcxSBhqqZuXsLw/PMgJ2bOBxVPECuUFc2UacEUT5ut9RZvEwQxuI2yUEJNVJNGECgYEAn2r8YE6782pKgIZpc0POBBNrOW3mzPnDrzrsraBdqwbdaQvPyLksoWcd5IWdeziXjddH0GmZ0R7QMTWTKpFHgDX2pGH3qr5wRBbg1tQHkew0KhQkyUVmaUddnpcEHN2OkJL7mBlK0YhVzDDcTjQ/o4V9xOXizM8FbKkhsMoOxzECgYEAwpNdo6tclsOZEYsIJovt73JiW3bI1tkB0NFHp0sQQcW95JLI73YS1Tifcvw/MXRSC8y6qmIgQwsqFV/EQIEzrlyP+9dRDB7jECGPD3jd+jbDxl5DoJDfEZleyByEXDn9vjyYMb9t56KurlW8plo2ppoYlYJ3kAn2q/2+Q4Gy4+I=";
// 错误的code码说明,有问题自己查
//    /兼容老版本的code说明
//    int CODE_LOAD_SUCCESS = 1;//加载阶段, 成功
//    int CODE_ERR_INBLACKLIST = 4;//加载阶段, 失败设备不支持
//    int CODE_REQ_NOUPDATE = 6;//查询阶段, 没有发布新补丁
//    int CODE_REQ_NOTNEWEST = 7;//查询阶段, 补丁不是最新的
//    int CODE_DOWNLOAD_SUCCESS = 9;//查询阶段, 补丁下载成功
//    int CODE_DOWNLOAD_BROKEN = 10;//查询阶段, 补丁文件损坏下载失败
//    int CODE_UNZIP_FAIL = 11;//查询阶段, 补丁解密失败
//    int CODE_LOAD_RELAUNCH = 12;//预加载阶段, 需要重启
//    int CODE_REQ_APPIDERR = 15;//查询阶段, appid异常
//    int CODE_REQ_SIGNERR = 16;//查询阶段, 签名异常
//    int CODE_REQ_UNAVAIABLE = 17;//查询阶段, 系统无效
//    int CODE_REQ_SYSTEMERR = 22;//查询阶段, 系统异常
//    int CODE_REQ_CLEARPATCH = 18;//查询阶段, 一键清除补丁
//    int CODE_PATCH_INVAILD = 20;//加载阶段, 补丁格式非法
//    //查询阶段的code说明
//    int CODE_QUERY_UNDEFINED = 31;//未定义异常
//    int CODE_QUERY_CONNECT = 32;//连接异常
//    int CODE_QUERY_STREAM = 33;//流异常
//    int CODE_QUERY_EMPTY = 34;//请求空异常
//    int CODE_QUERY_BROKEN = 35;//请求完整性校验失败异常
//    int CODE_QUERY_PARSE = 36;//请求解析异常
//    int CODE_QUERY_LACK = 37;//请求缺少必要参数异常
//    //预加载阶段的code说明
//    int CODE_PRELOAD_SUCCESS = 100;//预加载成功
//    int CODE_PRELOAD_UNDEFINED = 101;//未定义异常
//    int CODE_PRELOAD_HANDLE_DEX = 102;//dex加载异常
//    int CODE_PRELOAD_NOT_ZIP_FORMAT = 103;//基线dex非zip格式异常
//    int CODE_PRELOAD_REMOVE_BASEDEX = 105;//基线dex处理异常
//    //加载阶段的code说明 分三部分dex加载, resource加载, lib加载
//    //dex加载
//    int CODE_LOAD_UNDEFINED = 71;//未定义异常
//    int CODE_LOAD_AES_DECRYPT = 72;//aes对称解密异常
//    int CODE_LOAD_MFITEM = 73;//补丁SOPHIX.MF文件解析异常
//    int CODE_LOAD_COPY_FILE = 74;//补丁拷贝异常
//    int CODE_LOAD_SIGNATURE = 75;//补丁签名校验异常
//    int CODE_LOAD_SOPHIX_VERSION = 76;//补丁和补丁工具版本不一致异常
//    int CODE_LOAD_NOT_ZIP_FORMAT = 77;//补丁zip解析异常
//    int CODE_LOAD_DELETE_OPT = 80;//删除无效odex文件异常
//    int CODE_LOAD_HANDLE_DEX = 81;//加载dex异常
//    // 反射调用异常
//    int CODE_LOAD_FIND_CLASS = 82;
//    int CODE_LOAD_FIND_CONSTRUCTOR = 83;
//    int CODE_LOAD_FIND_METHOD = 84;
//    int CODE_LOAD_FIND_FIELD = 85;
//    int CODE_LOAD_ILLEGAL_ACCESS = 86;
//    //resource加载
//    public static final int CODE_LOAD_RES_ADDASSERTPATH = 123;//新增资源补丁包异常
//    //lib加载
//    int CODE_LOAD_LIB_UNDEFINED = 131;//未定义异常
//    int CODE_LOAD_LIB_CPUABIS = 132;//获取primaryCpuAbis异常
//    int CODE_LOAD_LIB_JSON = 133;//json格式异常
//    int CODE_LOAD_LIB_LOST = 134;//lib库不完整异常
//    int CODE_LOAD_LIB_UNZIP = 135;//解压异常
//    int CODE_LOAD_LIB_INJECT = 136;//注入异常

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        String appVersion = getResources().getString(R.string.version_name);
        Log.e("aaa","版本号"+appVersion);
        // initialize必须放在attachBaseContext最前面，初始化代码直接写在Application类里面，切勿封装到其他类。
        SophixManager.getInstance().setContext(this)
                .setAppVersion(appVersion)
                .setSecretMetaData(appId, appSecret, rsaSecret)
                .setAesKey(null)
                .setEnableDebug(true)
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        // 补丁加载回调通知
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            // 表明补丁加载成功
                            Log.e("aaa", "补丁加载成功");
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
                            // 建议: 用户可以监听进入后台事件, 然后调用killProcessSafely自杀，以此加快应用补丁，详见1.3.2.3
                            Log.e("aaa", "补丁生效，需要重启");
                            SophixManager.getInstance().killProcessSafely();
                        } else {
                            // 其它错误信息, 查看PatchStatus类说明
                            Log.e("aaa", "其他错误:  " + code);
                        }
                    }
                }).initialize();

    }

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


        SophixManager.getInstance().queryAndLoadNewPatch();

        JPushInterface.getAlias(this, myALias);

/**
 * 初始化common库
 * 参数1:上下文，不能为空
 * 参数2:友盟 app key
 * 参数3:友盟 channel
 * 参数4:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
 * 参数5:Push推送业务的secret
 */
        UMConfigure.init(this, "5abcde498f4a9d29b700004b", "comyslexample", UMConfigure.DEVICE_TYPE_PHONE, null);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        UMConfigure.setLogEnabled(true);

//崩溃捕获并处理
        Thread.setDefaultUncaughtExceptionHandler(this);

//
    }


    public static Context getMyContext() {
        return context;
    }


    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {

        String result = getStackTrace(throwable);
        Log.e("aaa", "crash " + result);
        SharedPreferenceUtils.loginSave(this, "token", "");
        stopActivity();
    }

    private void stopActivity() {


        // 跳转到崩溃提示Activity
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        System.exit(0);// 关闭已奔溃的app进程
    }

    private String getStackTrace(Throwable th) {

        final Writer result = new StringWriter();

        final PrintWriter printWriter = new PrintWriter(result);

        // If the exception was thrown in a background thread inside

        // AsyncTask, then the actual exception can be found with getCause

        Throwable cause = th;

        while (cause != null) {

            cause.printStackTrace(printWriter);

            cause = cause.getCause();

        }

        final String stacktraceAsString = result.toString();

        printWriter.close();

        return stacktraceAsString;

    }
}
