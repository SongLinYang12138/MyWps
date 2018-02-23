package com.example.ysl.mywps.ui.fragment;

import android.content.Intent;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.example.ysl.mywps.R;
import com.example.ysl.mywps.bean.FileListBean;
import com.example.ysl.mywps.bean.FileListChildBean;
import com.example.ysl.mywps.bean.FileType;
import com.example.ysl.mywps.interfaces.PassFileChildList;
import com.example.ysl.mywps.interfaces.PasssString;
import com.example.ysl.mywps.net.HttpUtl;
import com.example.ysl.mywps.ui.adapter.DocumentAdapter;
import com.example.ysl.mywps.utils.NoDoubleClickListener;
import com.example.ysl.mywps.utils.SharedPreferenceUtils;
import com.example.ysl.mywps.utils.ToastUtils;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.orhanobut.logger.Logger;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/2/4 0004.
 */

public class DocufragmentsFragment extends BaseFragment implements PassFileChildList, PasssString {

    @BindView(R.id.fragment_documents_listview)
    PullToRefreshListView listView;
    @BindView(R.id.av_loading)
    AVLoadingIndicatorView loading;

    private DocumentAdapter adapter;
    private int kindFlag;
    private List<FileType> fileTypes;
    private String token;
    private String fileType;
    private int pageNumber = 1;
    private int pageSize = 20;
    private ArrayList<FileListBean> fileListBeens = new ArrayList<>();
    private boolean loadMore = false;
    private PassFileChildList passFileChild;
    private Window myWindow;
    private WindowManager.LayoutParams myLp;
    private MyclickListener click = new MyclickListener();
    private ArrayList<FileListChildBean> selectList = new ArrayList<>();


    @Override
    public void initData() {

        myWindow = getActivity().getWindow();
        myLp = myWindow.getAttributes();
        adapter = new DocumentAdapter(fileListBeens, getActivity(), this);

        fileTypes = SharedPreferenceUtils.getFileTypeDataList(getActivity());
        token = SharedPreferenceUtils.loginValue(getActivity(), "token");

        if (fileTypes != null) {
            for (FileType fileInfo : fileTypes) {

                if (kindFlag == 1 && fileInfo.getValue().equals("图片")) {
                    fileType = fileInfo.getCode();
                    netWork();
                } else if (kindFlag == 2 && fileInfo.getValue().equals("文档")) {
                    fileType = fileInfo.getCode();
                    netWork();
                } else if (kindFlag == 3 && fileInfo.getValue().equals("视频")) {

                    fileType = fileInfo.getCode();
                    netWork();
                } else if (kindFlag == 4 && fileInfo.getValue().equals("应用")) {

                    fileType = fileInfo.getCode();
                    netWork();
                }


            }
        }


        switch (kindFlag) {
            case 1:

                adapter.setKindFlag(1);
                break;

            case 2:
                adapter.setKindFlag(2);

                break;
            case 3:
                adapter.setKindFlag(3);

                break;
            case 4:
                adapter.setKindFlag(4);

                break;

        }
    }

    public void setPassFileChild(PassFileChildList passFileChildList) {
        this.passFileChild = passFileChildList;
    }

    @Override
    public View setView(LayoutInflater inflater, ViewGroup container) {

        View view = inflater.inflate(R.layout.fragments_document_fragment_layout, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void afterView(View view) {

        listView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (loadMore) {
                    ++pageNumber;
                    netWork();
                } else {
                    finishLoad();
                }
            }
        });
        listView.setAdapter(adapter);

        loading.setVisibility(View.GONE);
    }

    private void finishLoad() {
        listView.postDelayed(new Runnable() {
            @Override
            public void run() {
                listView.onRefreshComplete();

            }
        }, 1000);
    }

    private void netWork() {

        if (loading != null) loading.setVisibility(View.VISIBLE);
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<String> emitter) throws Exception {

                Call<String> call = HttpUtl.fileList("User/Share/filelist/", fileType, pageNumber + "", pageSize + "", token);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        if (response.isSuccessful()) {
                            String data = response.body();
                            Logger.i("data   " + fileType + "  " + data);
                            if (data == null) {
                                emitter.onNext("N");
                                return;
                            }
                            try {
                                JSONObject jsonObject = new JSONObject(data);
                                int code = jsonObject.getInt("code");
                                String msg = jsonObject.getString("msg");

                                if (code != 0) {

                                    emitter.onNext(msg);
                                    emitter.onNext("N");
                                } else {

                                    JSONArray array = jsonObject.getJSONArray("data");

                                    Gson gson = new Gson();
                                    for (int i = 0; i < array.length(); ++i) {

                                        JSONObject child = array.getJSONObject(i);

                                        FileListBean bean = gson.fromJson(child.toString(), FileListBean.class);
                                        fileListBeens.add(bean);

                                    }

                                    loadMore = true;
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                loadMore = false;
                            }


                        } else {
                            String msg = response.message();

                            emitter.onNext(msg);
                            emitter.onNext("N");

                        }
                        emitter.onComplete();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable throwable) {

                        emitter.onNext(throwable.getMessage());
                        emitter.onNext("N");
                        emitter.onComplete();
                    }
                });


            }
        });

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable disposable) {

            }

            @Override
            public void onNext(@NonNull String s) {

                if (!s.equals("N") && !s.equals("Y"))
                    ToastUtils.showShort(getActivity(), s);

            }

            @Override
            public void onError(@NonNull Throwable throwable) {

            }

            @Override
            public void onComplete() {
                adapter.update(fileListBeens);
                listView.onRefreshComplete();
                if (loading != null) loading.setVisibility(View.GONE);
            }
        };

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void setKindFlag(int kindFlag) {

        this.kindFlag = kindFlag;
    }

    private PopupWindow bottomWindow;


    private void showBottomWindow() {


        View view = LayoutInflater.from(getActivity()).inflate(R.layout.material_popupwindow_bottom, null);

        myLp.alpha = 0.4f;
        myWindow.setAttributes(myLp);

        if (bottomWindow == null) {
            bottomWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, 240, true);

            RelativeLayout rlDownload = (RelativeLayout) view.findViewById(R.id.material_pop_bottom_download);
            RelativeLayout rlDelete = (RelativeLayout) view.findViewById(R.id.material_pop_bottom_delete);
            RelativeLayout rlMessage = (RelativeLayout) view.findViewById(R.id.material_pop_bottom_message);


            rlDownload.setOnClickListener(click);
            rlDelete.setOnClickListener(click);
            rlMessage.setOnClickListener(click);
//            bottomWindow.setBackgroundDrawable(new ColorDrawable());
            bottomWindow.setOutsideTouchable(true);
            bottomWindow.setAnimationStyle(R.style.Popupwindow);
            bottomWindow.setFocusable(true);
            rlDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    passFileChild.passFileChild(selectList,0);
                    bottomWindow.dismiss();
                }
            });
            rlDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteDocument();
                    bottomWindow.dismiss();
                }
            });
            rlMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    bottomWindow.dismiss();
                }
            });
            bottomWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    myLp.alpha = 1f;
                    myWindow.setAttributes(myLp);
                    adapter.notifyDataSetChanged();

                }
            });
            bottomWindow.showAtLocation(listView, Gravity.BOTTOM, 0, 0);
        } else {

            bottomWindow.showAtLocation(listView, Gravity.BOTTOM, 0, 0);
        }
    }

    private void deleteDocument() {

        loading.setVisibility(View.VISIBLE);

        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<String> emitter) throws Exception {

                Call<String> call = HttpUtl.deleteFile("User/Share/del_file/", selectList.get(0).getId(), token);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        if (response.isSuccessful()) {

                            String data = response.body();

                            Logger.i("删除信息  " + data);
                            try {
                                JSONObject jsonObject = new JSONObject(data);

                                int code = jsonObject.getInt("code");
                                String msg = jsonObject.getString("msg");

                                emitter.onNext(msg);
                                if (code == 0) {
                                    emitter.onNext("Y");
                                } else {
                                    emitter.onNext("N");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable throwable) {
                        emitter.onNext(throwable.getMessage());
                        emitter.onNext("N");
                    }
                });
            }
        });
        Consumer<String> observer = new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                if (loading.getVisibility() == View.VISIBLE) loading.setVisibility(View.GONE);
                if (s.equals("Y") || s.equals("N")) {

                    if (s.equals("Y")) {
                        fileListBeens.clear();
                        netWork();
                    }
                }else {
                    ToastUtils.showLong(getActivity(), s);
                }


            }
        };

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    @Override
    public void passFileChild(ArrayList<FileListChildBean> files, int kind) {


        if (bottomWindow == null) {
            showBottomWindow();
        } else if (!bottomWindow.isShowing()) {
            showBottomWindow();
        }

        if (files != null) {
            selectList.clear();
            selectList = files;
        }
    }

    @Override
    public void setString(String... args) {

        if (args[0].equals("0") && bottomWindow != null && bottomWindow.isShowing())
            bottomWindow.dismiss();
    }


    private class MyclickListener extends NoDoubleClickListener {
        @Override
        public void click(View v) {

            switch (v.getId()) {

                case R.id.material_top_picture:

                    break;
                case R.id.material_top_documents:

                    break;
                case R.id.material_top_video:

                    break;
                case R.id.material_top_application:

                    break;
            }

        }
    }
}