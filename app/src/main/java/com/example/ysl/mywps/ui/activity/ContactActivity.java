package com.example.ysl.mywps.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ysl.mywps.R;
import com.example.ysl.mywps.bean.ContactBean;
import com.example.ysl.mywps.net.HttpUtl;
import com.example.ysl.mywps.ui.adapter.ContactAdapter;
import com.example.ysl.mywps.ui.adapter.ContactMyAdapter;
import com.example.ysl.mywps.utils.CommonSetting;
import com.example.ysl.mywps.utils.CommonUtil;
import com.example.ysl.mywps.utils.SharedPreferenceUtils;
import com.example.ysl.mywps.utils.ToastUtils;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ysl on 2018/1/16.
 */

public class ContactActivity extends BaseActivity {

    @BindView(R.id.contact_listview)
    ListView listView;

    private ContactMyAdapter adapter;
    private ArrayList<ContactBean> list = new ArrayList<>();
    private String docId = "";
    private String token = "";
    private String docName = "";
    private String docPath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contact_layout);
        ButterKnife.bind(this);

        docId = getIntent().getStringExtra("docid");
       docName = getIntent().getStringExtra("name");
        docPath = getIntent().getStringExtra("path");

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                commitAudit(list.get((int) id).getUid());

            }
        });
        token = SharedPreferenceUtils.loginValue(this, "token");
        showLeftButton(true, "", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setTitleText("通讯录");
        netWork();
    }

    private void commitAudit(final String uid) {

        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) {

                Call<String> call = HttpUtl.commitAudit("User/Oa/submit_review/", docId, uid, token,docName,docPath);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body());

                            int code = jsonObject.getInt("code");
                            String msg = jsonObject.getString("msg");

                            emitter.onNext(msg);
                            if (code == 0) emitter.onNext("Y");
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        emitter.onNext(t.getMessage());
                        emitter.onNext("N");
                    }
                });

            }
        });
        Consumer<String> observer = new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

                if (s.equals("Y") || s.equals("N")) {

                    if (s.equals("Y")) {
                        finish();
                    }

                } else {
                    ToastUtils.showShort(ContactActivity.this, s);
                }

            }
        };
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);


    }

    private void netWork() {

        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> e) {

                String token = SharedPreferenceUtils.loginValue(ContactActivity.this, "token");
                Call<String> call = HttpUtl.contact("User/User/contacts/", token);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        String data = response.body().toString();
                        String msg = null;
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            int code = jsonObject.getInt("code");
                            msg = jsonObject.getString("msg");

                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            Gson gson = new Gson();
                            for (int i = 0; i < jsonArray.length(); ++i) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                JSONArray jsonArray1 = object.getJSONArray("contact");

                                for (int j = 0; j < jsonArray1.length(); ++j) {

                                    JSONObject childObject = jsonArray1.getJSONObject(j);
                                    ContactBean bean = gson.fromJson(childObject.toString(), ContactBean.class);
                                    bean.setCapital(CommonUtil.getPinYinHeadChar(bean.getUsername()));
                                    list.add(bean);
                                }
                            }
                            e.onNext("Y");
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                            e.onNext(msg);
                        }
                        Logger.i("通讯录  " + response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                        Logger.i("通讯录  " + t.getMessage());
                        e.onNext(t.getMessage());
                    }
                });

            }
        });
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

                if (s.equals("Y")) {

                    adapter = new ContactMyAdapter(list, ContactActivity.this);
                    listView.setAdapter(adapter);
                } else {
                    ToastUtils.showShort(ContactActivity.this, s);
                }

            }
        };

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }
}
