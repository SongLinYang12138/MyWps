package com.example.ysl.mywps.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2018/1/20 0020.
 */

public class SharedPreferenceUtils {


    public static void loginSave(Context context,String key,String value){

        SharedPreferences preferences = context.getSharedPreferences("login.xml",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(key,value);
        editor.commit();

    }

    public static String loginValue(Context context,String key){
        String value = "";
        SharedPreferences preferences = context.getSharedPreferences("login.xml",Context.MODE_PRIVATE);

        value = preferences.getString(key,"");

        return value;
    }


}
