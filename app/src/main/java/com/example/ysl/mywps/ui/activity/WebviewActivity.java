package com.example.ysl.mywps.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;


import com.example.ysl.mywps.R;
import com.example.ysl.mywps.interfaces.JSCallBack;
import com.example.ysl.mywps.interfaces.JavascriptBridge;
import com.example.ysl.mywps.utils.ToastUtils;
import com.gc.materialdesign.views.ButtonRectangle;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ysl on 2018/2/28.
 * 介绍:
 */

public class WebviewActivity extends BaseActivity implements JSCallBack {

    private static final String TAG = "aaa";
    @BindView(R.id.webview_webview)
    WebView webView;
    @BindView(R.id.webview_bt_left)
    Button btLeft;
    @BindView(R.id.webview_bt_right)
    Button btRight;

    private final int CAMERA_REQUEST_CODE = 111;

    private final int DOCUMENT_REQUEST_CODE = 222;
    private final int VIDEO_WITH_CAMERA = 333;

    private String cameraPath = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_webview_layout);
        ButterKnife.bind(this);
        setTitleText("社情民意");
        showLeftButton(true, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initView();
        afterView();

    }

    @Override
    public void initView() {

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//设置js交互
//        webSettings.setUseWideViewPort(true);//设置图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true);//缩放至屏幕的大小

        //缩放操作
        webSettings.setSupportZoom(true);//支持缩放
        webSettings.setBuiltInZoomControls(true);///设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false);//隐藏内置的原生缩放控件

        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); //缓存模式如下：
        //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
        //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
        //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
        //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。

        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

    }

    @Override
    public void initData() {

    }


    private void afterView() {

        MyWebChromeClient chromeClient = new MyWebChromeClient();
        MyWebviewClient client = new MyWebviewClient();


        webView.addJavascriptInterface(new JavascriptBridge(this), "test");
        webView.loadUrl("file:///android_asset/index.html");
        webView.setWebChromeClient(chromeClient);
        webView.setWebViewClient(client);

        btLeft.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                if (version < 18) {
                    webView.loadUrl("javascript:hadCommit('" + path + "')");
                } else {

                    webView.evaluateJavascript("javascript:hadCommit('" + path + "')", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String s) {

                            Log.i("aaa", "return  " + s);
                        }
                    });

                }

            }
        });
    }

    // Android版本变量
    final int version = Build.VERSION.SDK_INT;

    private void commitHavDone() {

        webView.post(new Runnable() {
            @Override
            public void run() {
                if (version < 18) {
                    webView.loadUrl("javascript:hadCommit('" + path + "')");
                } else {

                    webView.evaluateJavascript("javascript:hadCommit('" + path + "')", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String s) {

                            Log.i("aaa", "return  " + s);
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        writePermission();
    }

    /**
     * 检查存储权限，如果没有就请求
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void writePermission() {
        if (Build.VERSION.SDK_INT < 23) {
            return;
        }
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 11);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 11) {

            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i("aaa", "权限被允许");
            } else {
                Log.i("aaa", "权限被拒绝");
                ToastUtils.showShort(this, "请开启文件存储权限");
                finish();
//                writePermission();
            }

        } else {
            ToastUtils.showShort(this, "请开启文件存储权限");
            finish();
//            writePermission();
//            finish();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        webView.clearCache(true);
        webView.clearHistory();
        webView.clearFormData();

    }


    @Override
    public void jsCallBack(String method, String msg) {

        switch (method) {

            case "callCamera":

                ToastUtils.showShort(this, "随意拍");
                callCamera();
                break;
            case "callDocument":
                ToastUtils.showShort(this, "选择文件");
                findDocuments();
                break;

            case "callCommit":

                commitHavDone();
                break;


        }
    }

    String path = "来自android的消息";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4以后


            try {
                path = getPath(this, data.getData());
            } catch (Exception e) {

            }
        } else {//4.4以下下系统调用方法
            path = getRealPathFromURI(data.getData());
        }
        Log.i(TAG, "默认content地址：" + path);
    }

    private void callCamera() {

        showBottomWindow();

    }

    private void takePhoto() {

        Intent intent = new Intent();
        // 指定开启系统相机的Action
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    private void takeVideo() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
//设置视频录制的最长时间
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30);
//设置视频录制的画质
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        startActivityForResult(intent, VIDEO_WITH_CAMERA);

    }


    private void findDocuments() {

        /***
         *打开文件管理器
         */
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, DOCUMENT_REQUEST_CODE);
    }


    private PopupWindow bottomWindow;


    private void showBottomWindow() {


        if (bottomWindow == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.choose_photo_or_video, null);
            bottomWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);


            ButtonRectangle btPhoto = (ButtonRectangle) view.findViewById(R.id.popupwind_bt_photo);
            ButtonRectangle btVideo = (ButtonRectangle) view.findViewById(R.id.popupwind_bt_video);

            btPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    takePhoto();
                }
            });
            btVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    takeVideo();
                }
            });

//            bottomWindow.setBackgroundDrawable(new ColorDrawable());
            bottomWindow.setOutsideTouchable(true);
            bottomWindow.setAnimationStyle(R.style.Popupwindow);
            bottomWindow.setFocusable(false);

            bottomWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {

                    bottomWindow.dismiss();
                }
            });
            bottomWindow.showAtLocation(webView, Gravity.BOTTOM, 0, 0);
        } else {

            bottomWindow.showAtLocation(webView, Gravity.BOTTOM, 0, 0);
        }
    }


    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (null != cursor && cursor.moveToFirst()) {

            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
            cursor.close();
        }
        return res;
    }

    /**
     * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
     */
    @SuppressLint("NewApi")
    public String getPath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];

                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }


    public String getDataColumn(Context context, Uri uri, String selection,
                                String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private static class MyWebviewClient extends WebViewClient {


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);

            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
        }

    }

    private static class MyWebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);

            Log.i(TAG, "progress  " + newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);

            Log.i(TAG, "title  " + title);
        }
    }
}
