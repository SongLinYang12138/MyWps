package com.example.ysl.mywps.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.ysl.mywps.R;
import com.example.ysl.mywps.net.HttpUtl;
import com.example.ysl.mywps.utils.CommonUtil;
import com.example.ysl.mywps.utils.NoDoubleClickListener;
import com.example.ysl.mywps.utils.SharedPreferenceUtils;
import com.example.ysl.mywps.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

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
 * Created by ysl on 2018/1/15.
 */

public class CommitActivity extends BaseActivity {

    @BindView(R.id.commit_iv_delete)
    ImageView ivBack;
    @BindView(R.id.commit_rl_upload)
    RelativeLayout rlCommit;
    @BindView(R.id.commit_et_opinion)
    EditText etOpinion;

    private MyclickListener click = new MyclickListener();
    private String downloadPath = "";
    private String downLoadName = "";
    private String docId = "";
    private String token = "";
    private String proce_id = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commit_layout);
        showLeftButton(false, "", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setTitleText("提交");

        ButterKnife.bind(this);

        downloadPath = getIntent().getStringExtra("path");
        downLoadName = getIntent().getStringExtra("name");
        docId = getIntent().getStringExtra("docId");
        proce_id = getIntent().getStringExtra("proceId");
        token = SharedPreferenceUtils.loginValue(this, "token");
        ivBack.setOnClickListener(click);
        rlCommit.setOnClickListener(click);
        setTitleContent(View.GONE);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    private void uploadFile(final String opinion) {

        final Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {

                Call<String> call = HttpUtl.uploadWps("User/Oa/back_doc/", docId, proce_id, token, opinion, downLoadName, downloadPath);

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String msg = response.body();
//                        {"code":0,"msg":"返回文档给拟稿人成功"}
                        try {
                            JSONObject jsonObject = new JSONObject(msg);

                            int code = jsonObject.getInt("code");
                            String message = jsonObject.getString("msg");

                            emitter.onNext(message);
                            if (code == 0) {
                                emitter.onNext("Y");
                            } else {
                                emitter.onNext("N");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            emitter.onNext(e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                        emitter.onNext(t.getMessage());
                    }
                });
            }
        });
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

                if (s.equals("Y") || s.equals("N")) {
                    finish();
                } else {
                    ToastUtils.showShort(CommitActivity.this, s);
                }

            }
        };
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);


    }


    private class MyclickListener extends NoDoubleClickListener {
        @Override
        public void click(View v) {

            switch (v.getId()) {

                case R.id.commit_iv_delete:

                    finish();
                    break;
                case R.id.commit_rl_upload:

                    String opinion = etOpinion.getText().toString();

                    if (CommonUtil.isEmpty(opinion)) {
                        ToastUtils.showShort(CommitActivity.this, "请输入意见");
                        return;
                    }
                    uploadFile(opinion);
                    break;


            }

        }
    }
}
