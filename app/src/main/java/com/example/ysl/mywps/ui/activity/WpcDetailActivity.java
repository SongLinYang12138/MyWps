package com.example.ysl.mywps.ui.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ysl.mywps.BuildConfig;
import com.example.ysl.mywps.R;
import com.example.ysl.mywps.bean.DocumentListBean;
import com.example.ysl.mywps.interfaces.HttpFileCallBack;
import com.example.ysl.mywps.net.HttpUtl;
import com.example.ysl.mywps.ui.adapter.WpsDetailAdapter;
import com.example.ysl.mywps.ui.view.MoviewImage;
import com.example.ysl.mywps.ui.view.WritingPadView;
import com.example.ysl.mywps.utils.CommonUtil;
import com.example.ysl.mywps.utils.FileUtils;
import com.example.ysl.mywps.utils.NoDoubleClickListener;
import com.example.ysl.mywps.utils.ToastUtils;
import com.example.ysl.mywps.utils.WpsModel;
import com.example.ysl.mywps.utils.WpsUtils;
import com.lx.fit7.Fit7Utils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.orhanobut.logger.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/1/14 0014.
 */

public class WpcDetailActivity extends BaseActivity {

    @BindView(R.id.wpcdetail_iv_artical)
    ImageView ivArtical;
    @BindView(R.id.wpcdetail_ll_artical)
    LinearLayout llArtival;
    @BindView(R.id.wpcdetail_iv_message)
    ImageView ivMessage;
    @BindView(R.id.wpcdetail_ll_message)
    LinearLayout llMessage;
    @BindView(R.id.wpcdetail_iv_sign)
    ImageView ivSign;
    @BindView(R.id.wpcdetail_ll_sign)
    LinearLayout llSign;
    @BindView(R.id.wpcdetail_iv_send)
    ImageView ivSend;
    @BindView(R.id.wpcdetail_ll_send)
    LinearLayout llSend;
    @BindView(R.id.wpcdetail_iv_icon)
    MoviewImage ivIcon;
    @BindView(R.id.wpcdetal_pb_top)
    ProgressBar progressBar;
    @BindView(R.id.wpcdetail_listview)
    ListView listView;
    @BindView(R.id.wpcdetail_rl_loading)
    RelativeLayout rlLoading;
    @BindView(R.id.wpcdetail_rl_content)
    RelativeLayout rlContent;

    private MyclickListener click = new MyclickListener();
    private WpsBroadCast reciver = new WpsBroadCast();
    private int screenH = 0;
    private DocumentListBean documentInfo = null;
    private WpsDetailAdapter adapter;
    private float x1, x2;
    private String uploadImagePath = "";
    private String downloadWpsPath = "";

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            progressBar.setProgress(msg.what);
            if (msg.what == 100) {
                rlLoading.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
            }
//            Log.i("aaa", "progress  " + msg.what);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_wpc_details_layout);
        ButterKnife.bind(this);


        ivArtical.setOnClickListener(click);
        llArtival.setOnClickListener(click);
        ivMessage.setOnClickListener(click);
        llMessage.setOnClickListener(click);
        ivSign.setOnClickListener(click);
        llSign.setOnClickListener(click);
        ivSend.setOnClickListener(click);
        llSend.setOnClickListener(click);

//        rlLoading.setVisibility(View.GONE);
//        progressBar.setVisibility(View.GONE);
        ivIcon.setVisibility(View.GONE);
        this.registerReceiver(reciver, new IntentFilter("com.example.ysl.mywps.sign"));

        screenH = CommonUtil.getScreenWH(this)[1];
        showLeftButton(true, "", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        showRight(true, "流程", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WpcDetailActivity.this, FlowActivity.class);
                startActivity(intent);
            }
        });
        writePermission();
        afterData();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {


    }

    private void afterData() {

        Bundle bundle = getIntent().getExtras();
        documentInfo = bundle.getParcelable("documentben");
        setTitleText(documentInfo.getDoc_name());
        adapter = new WpsDetailAdapter(documentInfo.getDoc_imgs(), this);
        listView.setAdapter(adapter);
        downLoadWps();

        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                x1 = event.getX();
                x2 = ivIcon.getX();
                float sum = Math.abs(x2 - x1);
                if (sum < 90) {
                    ivIcon.autoMouse(event);
                    return true;
                }
                return false;
            }
        });

    }

    public void downLoadWps() {
//        String wpsPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath() + "/" + documentInfo.getDoc_name();
//
//        File file = new File(wpsPath);
//        if (file.exists()) {
//            return;
//        }
        rlLoading.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) {

                String url = documentInfo.getDoc_url();
                int headIndex = url.indexOf("com/") + 3;
                String headUrl = url.substring(0, headIndex + 1);
                String bodyUrl = url.substring(headIndex + 1);

                Logger.i("head  " + headUrl + "  body " + bodyUrl + " \n" + url);
                Call<ResponseBody> call = HttpUtl.donwoldWps(headUrl, bodyUrl);
                call.enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath();

                        File file = new File(path);

                        if (!file.exists()) {
                            file.mkdirs();
                        }
                        downloadWpsPath = path + "/" + documentInfo.getDoc_name();
                        file = new File(downloadWpsPath);

                        FileUtils.writeFile2Disk(response, file, new HttpFileCallBack() {
                            @Override
                            public void onLoading(long currentLength, long totalLength) {

                                int precent = (int) (currentLength * 100 / totalLength);
                                handler.sendEmptyMessage(precent);

                            }
                        });

                        emitter.onNext("Y");

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        emitter.onNext(t.getMessage());
                    }
                });

            }
        });
        Consumer<String> observer = new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

                if (s.equals("Y")) {
                    openWps(downloadWpsPath);
                    ToastUtils.showShort(getApplicationContext(), "文件下载成功");
                } else {
                    ToastUtils.showShort(getApplicationContext(), s);
                }

            }
        };

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    /**
     * @param myPath
     */
    private boolean openWps(String myPath) {


        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(WpsModel.OPEN_MODE, WpsModel.OpenMode.NORMAL); // 打开模式
        bundle.putBoolean(WpsModel.SEND_CLOSE_BROAD, true); // 关闭时是否发送广播
        bundle.putString(WpsModel.THIRD_PACKAGE, getPackageName()); // 第三方应用的包名，用于对改应用合法性的验证
        bundle.putBoolean(WpsModel.CLEAR_TRACE, true);// 清除打开记录
        // bundle.putBoolean(CLEAR_FILE, true); //关闭后删除打开文件
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setClassName(WpsModel.PackageName.NORMAL, WpsModel.ClassName.NORMAL);

        File file = new File(myPath);
        if (file == null || !file.exists()) {
            ToastUtils.showShort(WpcDetailActivity.this, "文件为空或者不存在");
            return false;
        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        Uri uri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = Fit7Utils.getUriForFile(this,file);
        } else {
            uri = Uri.fromFile(file);
        }
        Logger.i("uri path  " + uri.getPath());
        intent.setData(uri);

        intent.setDataAndType(uri, WpsUtils.getMIMEType(file));
        intent.putExtras(bundle);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            System.out.println("打开wps异常：" + e.toString());
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * 保存签署文件
     */
    private void saveImage() {

//        holder.ivIcon.setDrawingCacheEnabled(true);
//        imgBitmap = holder.ivIcon.getDrawingCache();
        if (adapter == null) {
            return;
        }
        ivIcon.setDrawingCacheEnabled(true);
        final Bitmap forBitmap = ivIcon.getDrawingCache();
        final Bitmap backBitmap = adapter.getImgBitmap();
        final float left = Math.abs(ivIcon.getX() - 50);
        final float top = Math.abs(ivIcon.getY() - 40);
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) {

                try {
                    Bitmap myBitmap = toConformBitmap(backBitmap, forBitmap, left, top);
                    String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath() + "/uploadSign.png";
                    saveDownload(path, myBitmap);
                    uploadImagePath = path;
                    e.onNext("Y");
                } catch (Exception ex) {
                    e.onNext("N");
                }
            }
        });
        Consumer<String> oberver = new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

                ToastUtils.showShort(WpcDetailActivity.this, s);
            }
        };
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(oberver);
    }

    /**
     * 合成bitmap
     */
    private Bitmap toConformBitmap(Bitmap background, Bitmap foreground, float left, float top) {
        if (background == null) {
            return null;
        }

        int bgWidth = background.getWidth();
        int bgHeight = background.getHeight();
        //int fgWidth = foreground.getWidth();
        //int fgHeight = foreground.getHeight();
        //create the new blank bitmap 创建一个新的和SRC长度宽度一样的位图
        Bitmap newbmp = Bitmap.createBitmap(bgWidth, bgHeight, Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(newbmp);
        //draw bg into
        cv.drawBitmap(background, 0, 0, null);//在 0，0坐标开始画入bg
        //draw fg into
        cv.drawBitmap(foreground, left, top, null);//在 0，0坐标开始画入fg ，可以从任意位置画入
        //save all clip
        cv.save(Canvas.ALL_SAVE_FLAG);//保存
        //store
        cv.restore();//存储
        return newbmp;
    }

    /**
     * 保存画板
     * 保存图片
     */
    public void saveDownload(String path, Bitmap myBitmap) {

        Bitmap bitmap = myBitmap;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] buffer = bos.toByteArray();
        if (buffer != null) {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            try {
                OutputStream outputStream = new FileOutputStream(file);
                outputStream.write(buffer);
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    /**
     * 检查存储权限，如果没有就请求
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void writePermission() {
        if (Build.VERSION.SDK_INT < 23) {
            return;
        }
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 11);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 11) {
            int grantResult = grantResults[0];
            boolean granted = grantResult == PackageManager.PERMISSION_GRANTED;

            Log.i("aaa", " " + granted);
        } else {
            Toast.makeText(WpcDetailActivity.this, "请开启存储权限", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    PopupWindow popupWindow;
    WritingPadView writingPadView;
    TextView tvCancel;
    TextView tvClear;
    RelativeLayout rlSignConfirm;

    private void setSign() {

        if (popupWindow == null) {

            View view = LayoutInflater.from(this).inflate(R.layout.sign_layout, null);

            writingPadView = (WritingPadView) view.findViewById(R.id.sign_writing);
            tvCancel = (TextView) view.findViewById(R.id.sign_tv_cancel);
            tvClear = (TextView) view.findViewById(R.id.sign_tv_clear);
            rlSignConfirm = (RelativeLayout) view.findViewById(R.id.sign_rl_confirm);
            popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT, screenH / 2, true);

            // 菜单背景色。加了一点透明度
            ColorDrawable dw = new ColorDrawable(0xddffffff);
            popupWindow.setBackgroundDrawable(dw);
            // 设置背景半透明
            view.setAlpha(0.7f);
            popupWindow.setOutsideTouchable(true);
//        popupWindow.setWidth();
            popupWindow.setFocusable(true);
            //TODO 注意：这里的 R.layout.activity_main，不是固定的。你想让这个popupwindow盖在哪个界面上面。就写哪个界面的布局。这里以主界面为例
            popupWindow.showAtLocation(LayoutInflater.from(this).inflate(R.layout.activity_wpc_details_layout, null),
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

        } else {

            popupWindow.showAtLocation(LayoutInflater.from(this).inflate(R.layout.activity_wpc_details_layout, null),
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

        }

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writingPadView.clear();
            }
        });
        tvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writingPadView.clear();
            }
        });
        rlSignConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WpcDetailActivity.this, "正在保存", Toast.LENGTH_SHORT).show();
                Thread saveThread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "wpsSign";
                        File file = new File(path);
                        if (!file.exists()) file.mkdirs();
                        path += File.separator + "qianming.png";
                        try {
                            writingPadView.save(path);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Logger.i("原始   " + path);

                        final String mypath = path;
                        WpcDetailActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                ImageLoader.getInstance().displayImage("file://" + mypath, ivIcon);
                                popupWindow.dismiss();
                            }
                        });
                    }
                });
                saveThread.setDaemon(true);
                saveThread.start();
            }
        });


    }


    private class WpsBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "mybitmap", Toast.LENGTH_SHORT).show();
            Logger.i("static  " + CommonUtil.myPath);

        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(reciver);
    }


    private class MyclickListener extends NoDoubleClickListener {
        @Override
        public void click(View v) {

            int id = v.getId();

            if (id == R.id.wpcdetail_iv_artical || id == R.id.wpcdetail_ll_artical) {


                File file = new File(downloadWpsPath);
                if (!file.exists()) {
                    downLoadWps();
                } else {
                    openWps(downloadWpsPath);
                }


            } else if (id == R.id.wpcdetail_iv_message || id == R.id.wpcdetail_ll_message) {

                saveImage();

                Intent intent = new Intent(WpcDetailActivity.this, CommitActivity.class);
                intent.putExtra("path", downloadWpsPath);
                intent.putExtra("name", documentInfo.getDoc_name());
                intent.putExtra("docId", documentInfo.getId());
                intent.putExtra("proceId", documentInfo.getProce_id());
                startActivity(intent);
            } else if (id == R.id.wpcdetail_iv_sign || id == R.id.wpcdetail_ll_sign) {

                ivIcon.setVisibility(View.VISIBLE);
                setSign();
            } else if (id == R.id.wpcdetail_iv_send || id == R.id.wpcdetail_ll_send) {

                Intent intent = new Intent(WpcDetailActivity.this, ContactActivity.class);
                intent.putExtra("docid", documentInfo.getId());
                intent.putExtra("name", documentInfo.getDoc_name());
                intent.putExtra("path", downloadWpsPath);

                startActivity(intent);
            }
        }
    }
    // 根据路径获得图片并压缩，返回bitmap用于显示
//        private Bitmap getSmallBitmap(String filePath) {
//            final BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inJustDecodeBounds = true;
//            BitmapFactory.decodeFile(filePath, options);
//
//            // Calculate inSampleSize
//            options.inSampleSize = calculateInSampleSize(options, 480, 800);
//
//            // Decode bitmap with inSampleSize set
//            options.inJustDecodeBounds = false;
//
//            return BitmapFactory.decodeFile(filePath, options);
//        }

}
