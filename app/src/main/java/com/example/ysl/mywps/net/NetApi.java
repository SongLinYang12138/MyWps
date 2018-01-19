package com.example.ysl.mywps.net;

import com.example.ysl.mywps.bean.PostQueryInfo;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by ysl on 2018/1/17.
 */

public interface NetApi {

    @POST("query")
    Call<String> login(@Query("username") String username, @Query("password") String password, @Query("regid") String regId);

    @POST("query")
    Call<String> doucmentList(@Query("token") String token, @Query("page") String page, @Query("pagelimit") String pagelimit);

}
