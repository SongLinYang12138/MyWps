package com.example.ysl.mywps.ui.fragment;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.ysl.mywps.R;
import com.example.ysl.mywps.bean.FileListBean;
import com.example.ysl.mywps.bean.FileListChildBean;
import com.example.ysl.mywps.bean.TransportBean;
import com.example.ysl.mywps.bean.UploadBean;
import com.example.ysl.mywps.interfaces.HttpFileCallBack;
import com.example.ysl.mywps.interfaces.PassFileChildList;
import com.example.ysl.mywps.interfaces.PasssString;
import com.example.ysl.mywps.net.HttpUtl;
import com.example.ysl.mywps.net.ProgressListener;
import com.example.ysl.mywps.provider.DownLoadProvider;
import com.example.ysl.mywps.provider.UploadProvider;
import com.example.ysl.mywps.ui.adapter.DocumentAdapter;
import com.example.ysl.mywps.ui.adapter.TransportAdater;
import com.example.ysl.mywps.ui.view.MatchListView;
import com.example.ysl.mywps.utils.CommonUtil;
import com.example.ysl.mywps.utils.FileUtils;
import com.example.ysl.mywps.utils.SharedPreferenceUtils;
import com.example.ysl.mywps.utils.ToastUtils;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.orhanobut.logger.Logger;
import com.wang.avi.AVLoadingIndicatorView;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/2/4 0004.
 */

public class TransportFragmentsFragment extends BaseFragment implements PasssString, PassFileChildList {

    @BindView(R.id.fragment_documents_listview)
    MatchListView listView;

    @BindView(R.id.transport_loading)
    LinearLayout loadingContent;
    @BindView(R.id.av_loading)
    AVLoadingIndicatorView loading;


    TransportAdater adapter;
    ArrayList<TransportBean> list = new ArrayList<>();
    private int kindFlag;
    private String token;
    private ContentResolver contentResolver;
    private ArrayList<UploadBean> uploadList = new ArrayList<>();
    private ArrayList<UploadBean> loadingBean = new ArrayList<>();
    private UploadBean cunrrentBean;
    private ExecutorService fixedThreadPool;
    private FileListChildBean downloadBean;
    private ArrayList<View> downLoadView = new ArrayList<>();
    private ArrayList<View> uploadView = new ArrayList<>();


    @Override
    public void initData() {
        uploadList.clear();
        token = SharedPreferenceUtils.loginValue(getActivity(), "token");
        adapter = new TransportAdater(list, getActivity());


    }

    private void getDownloadData() {

        Cursor cursor = contentResolver.query(DownLoadProvider.CONTENT_URI, TransportBean.TRANSPORTBEANS, null, null, null);
        if (cursor != null) {

            list = TransportBean.getTransportBeans(cursor);

            adapter.update(list);
        }
        cursor.close();
    }

    private void getUploadData() {


        Cursor cursor = contentResolver.query(UploadProvider.CONTENT_URI, TransportBean.TRANSPORTBEANS, null, null, null);

        if (cursor != null) {

            list = TransportBean.getTransportBeans(cursor);

            adapter.update(list);
        }
        cursor.close();


    }


    @Override
    public View setView(LayoutInflater inflater, ViewGroup container) {


        View view = inflater.inflate(R.layout.fragment_transport_fragment_layou, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void afterView(View view) {
//loadingContent.setVisibility(View.GONE);

        loading.setVisibility(View.GONE);

        contentResolver = getActivity().getContentResolver();
        if (kindFlag == 2) {
            getUploadData();
        } else if (kindFlag == 1) {
            getDownloadData();
        }

        listView.setAdapter(adapter);
    }

    @Override
    public void setKindFlag(int kindFlag) {

        this.kindFlag = kindFlag;
    }
/***
 * 上传文件
 * */
    @Override
    public void setString(String... args) {

        final UploadBean bean = new UploadBean();
        bean.setPath(args[0]);
        bean.setName(args[1]);
        bean.setType(args[2]);
        cunrrentBean = bean;

//        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
//        for (int i = 0; i < 30; i++) {
//            final int finalI = i;
//            Runnable runnable = new Runnable(){
//                @Override
//                public void run() {
//                    SystemClock.sleep(3000);
//                    Log.d("google_lenve_fb", "run: "+ finalI);
//                }
//            };
//            fixedThreadPool.execute(runnable);
//        }


//        uploadList.add(bean);
//        if (llProgress1 != null) {
//
//            llProgress1.setVisibility(View.VISIBLE);
//            tvTitle1.setText(bean.getName());
//            tvDate1.setText(bean.getPath());
//
//            if (CommonUtil.isNotEmpty(bean.getPath())) {
//
//                try {
//                    File file = new File(bean.getPath());
//                    String size = CommonUtil.getFileSize(file.length());
//                    tvSize1.setText(size);
//                } catch (Exception e) {
//
//                }
//
//            }
//
//        }

//        uploadFile(bean.getPath(), bean.getName(), bean.getType(), token);


    }

    private synchronized void uploadFile(final String path, final String name, String fileType, String token) {


        ProgressListener progressListener = new ProgressListener() {
            @Override
            public void onProgress(long hasWrittenLen, long totalLen, boolean hasFinish) {

                int progress = ((int) (hasWrittenLen * 100 / totalLen));
                Log.i("aaa", "progress " + progress);
                progressHandler.sendEmptyMessage(progress);
            }
        };


        Call<String> call = HttpUtl.sharedUpload("User/Share/upload_file/", fileType, token, name, path, progressListener);
        if (call != null) call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Logger.i("data  " + response.body() + "\n" + response.message());

                File file = new File(path);
                String length = CommonUtil.getFileSize(file.length());
                TransportBean bean = new TransportBean();
                bean.setName(name);
                bean.setPath(path);
                bean.setSize(length);
                ContentValues values = bean.toContentValues();
                getActivity().getContentResolver().insert(UploadProvider.CONTENT_URI, values);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getUploadData();
                    }
                });

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Logger.i("failure  " + t.getMessage());

            }
        });
    }

    private synchronized void downLoadFile() {


        final Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) {

                String url = downloadBean.getDownload_url();
                int headIndex = url.indexOf("com/") + 3;
                String headUrl = url.substring(0, headIndex + 1);
                String bodyUrl = url.substring(headIndex + 1);


                Call<ResponseBody> call = HttpUtl.donwoldWps(headUrl, bodyUrl);
                call.enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {

                            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "wpsSign";

                            File file = new File(path);
                            if (!file.exists()) {
                                file.mkdirs();
                            }
                            String name = downloadBean.getFilename();
                            path = path + "/" + name;
                            file = new File(path);

                            FileUtils.writeFile2Disk(response, file, new HttpFileCallBack() {
                                @Override
                                public void onLoading(long currentLength, long totalLength) {

                                    int precent = (int) (currentLength * 100 / totalLength);
                                    progressHandler.sendEmptyMessage(precent);
                                }
                            });

                            TransportBean bean = new TransportBean();
                            bean.setName(downloadBean.getFilename());
                            bean.setDate(downloadBean.getCtime());
                            bean.setPath(path);
                            bean.setSize(CommonUtil.getFileSize(file.length()));

                            contentResolver.insert(DownLoadProvider.CONTENT_URI, bean.toContentValues());

                            emitter.onNext("Y");
                        } catch (Exception e) {
                            emitter.onNext("N");
                        }

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
                loading.setVisibility(View.GONE);


                if (s.equals("Y")) {
                    ToastUtils.showShort(getContext(), "下载成功");
                    getDownloadData();
                } else {
                    ToastUtils.showShort(getContext(), "下载失败");

                }


            }
        };

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    private Handler progressHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//                       if (progressBar1 != null) progressBar1.setProgress(msg.what);
//            if (msg.what == 100) {
//                llProgress1.setVisibility(View.GONE);
//            }

        }
    };

/***
 *
 * 下载文件
 */
    @Override
    public void passFileChild(ArrayList<FileListChildBean> files, int kind) {

        if (files != null && files.size() > 0) {
            downloadBean = files.get(0);

//            llProgress1.setVisibility(View.VISIBLE);
//            tvTitle1.setText(downloadBean.getFilename());
//            tvDate1.setText(downloadBean.getDownload_url());
//            tvSize1.setText(downloadBean.getCtime());
//            downLoadFile();
        }

    }
}


