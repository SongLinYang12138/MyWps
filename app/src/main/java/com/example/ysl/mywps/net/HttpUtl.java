package com.example.ysl.mywps.net;


import com.example.ysl.mywps.net.NetApi;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by ysl on 2018/1/17.
 */

public class HttpUtl {
    //    User/Oa/doc_list
    private static final String HTTP_URL = "http://oa.wgxmcb.top/index.php/";

    public static Retrofit getRetrofit(String httpurl) {


        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(httpurl)
                .build();
        return retrofit;
    }

    public static Call<String> login(String url, String name, String password, String identity) {

        String httpurl = HTTP_URL + url;
        NetApi inteface = getRetrofit(url).create(NetApi.class);

        return inteface.login(name, password, identity);
    }


}
