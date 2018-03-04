package com.example.ysl.mywps.interfaces;

import android.webkit.JavascriptInterface;

/**
 * Created by ysl on 2018/2/28.
 * 介绍:
 */

public class JavascriptBridge extends Object {

    private JSCallBack jsCallBack;

    public JavascriptBridge(JSCallBack jsCallBack) {

        this.jsCallBack = jsCallBack;
    }

    @JavascriptInterface
    public void callCamera(String s) {

        jsCallBack.jsCallBack("callCamera", s);
    }

    @JavascriptInterface
    public void callDocument(){

        jsCallBack.jsCallBack("callDocument","callDocument");

    }


    @JavascriptInterface
    public void callCommit(){

        jsCallBack.jsCallBack("callCommit","callDocument");

    }



}
