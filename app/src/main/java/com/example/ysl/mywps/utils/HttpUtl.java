package com.example.ysl.mywps.utils;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by ysl on 2018/1/17.
 */

public class HttpUtl {

    public static Retrofit getRetrofit(String httpurl) {


        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(httpurl)
                .build();
        return retrofit;
    }


}
