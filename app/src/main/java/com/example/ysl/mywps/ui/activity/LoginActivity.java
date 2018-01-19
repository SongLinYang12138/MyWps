package com.example.ysl.mywps.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.ysl.mywps.R;
import com.example.ysl.mywps.utils.CommonUtil;
import com.example.ysl.mywps.net.HttpUtl;
import com.example.ysl.mywps.utils.NoDoubleClickListener;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
//import okhttp3.FormBody;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ysl on 2018/1/16.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.login_et_password)
    EditText etPassword;
    @BindView(R.id.login_et_phone)
    EditText etPhone;
    @BindView(R.id.login_rl_confirm)
    RelativeLayout rlConfirm;

    private String name;
    private String password;
    private String identity;
    private boolean havIdentity = false;

    private MyclickListener clik = new MyclickListener();
    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_layout);
        ButterKnife.bind(this);
        rlConfirm.setOnClickListener(clik);
        getLogin();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    public void login(final ObservableEmitter<String> emitter) {

        Call<String> call = HttpUtl.login("User/Login/user_login/", name, password, identity);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

//                Logger.i(" " + response.body().toString());
                emitter.onNext(response.body().toString());
                call.cancel();
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {

//                Logger.i(" \n " + throwable.getMessage());
                emitter.onNext(throwable.getMessage());
                call.cancel();
            }
        });


    }


    private void saveLogin() {

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("name", name);
        editor.putString("password", password);
        if (!havIdentity)
            editor.putString("identity", identity);

        editor.commit();
    }

    private void getLogin() {
        preferences = getSharedPreferences("login.xml", Context.MODE_PRIVATE);
        String name = preferences.getString("name", "");
        String passowrd = preferences.getString("password", "");
        identity = preferences.getString("identity", "");
        String token = preferences.getString("token", "");
        if (CommonUtil.isEmpty(identity)) {
            havIdentity = false;
        } else {
            havIdentity = true;
        }
        if (CommonUtil.isNotEmpty(name)) {
            etPhone.setText(name);
        }
        if (CommonUtil.isNotEmpty(passowrd)) {
            etPassword.setText(passowrd);
        }
        if (CommonUtil.isNotEmpty(token)) {
            loginSuccess();
        }

    }


    private void doLogin() {

        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {

                login(emitter);
            }
        });

        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) {

                Logger.i("accept  " + s);
                JSONObject object = null;
                try {
                    object = new JSONObject(s);
                    int code = object.getInt("code");
                    CommonUtil.showLong(getApplicationContext(), object.getString("msg"));

                    JSONObject object1 = object.getJSONObject("data");

                    if (code == 0) {
                        String token = object1.getString("token");
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("token", token);
                        editor.commit();
                        loginSuccess();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    CommonUtil.showLong(getApplicationContext(), s);
                }


            }

        };


        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);

    }


    private void loginSuccess() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private class MyclickListener extends NoDoubleClickListener {

        @Override
        public void click(View v) {

            switch (v.getId()) {

                case R.id.login_rl_confirm:

                    name = etPhone.getText().toString();
                    password = etPassword.getText().toString();
                    if (!havIdentity) identity = CommonUtil.getAndroidId(LoginActivity.this);

                    if (CommonUtil.isEmpty(name)) {
                        CommonUtil.showShort(LoginActivity.this, "请填写手机号");
                        return;
                    }
                    if (CommonUtil.isEmpty(password)) {
                        CommonUtil.showShort(LoginActivity.this, "请填写密码");
                        return;
                    }
                    saveLogin();
                    doLogin();
                    break;


            }

        }
    }
}
