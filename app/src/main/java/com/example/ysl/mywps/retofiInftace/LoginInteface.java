package com.example.ysl.mywps.retofiInftace;

import com.example.ysl.mywps.bean.LoginBean;
import com.example.ysl.mywps.bean.PostQueryInfo;
import com.google.gson.jpush.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by ysl on 2018/1/17.
 */

public interface LoginInteface {
    @POST("query")
    Call<String> login(@Query("username") String username, @Query("password") String password, @Query("regid") String regId);

    @POST("query")
    Call<PostQueryInfo> search(@Query("type") String type, @Query("postid") String postid);
}
