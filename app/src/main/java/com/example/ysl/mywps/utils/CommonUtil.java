package com.example.ysl.mywps.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.Settings;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import net.sourceforge.pinyin4j.PinyinHelper;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ysl on 2017/7/13.
 */
public class CommonUtil {

    public static String myPath;

    public static void showShort(Context context, String msg) {


        if(isEmpty(msg)){
            return;
        }
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(Context context, String msg) {

        if(isEmpty(msg)){
            return;
        }
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static boolean isNotEmpty(String msg) {

        if (msg == null)
            return false;
        else {
            if (msg.trim().isEmpty())
                return false;
            else
                return true;
        }
    }

    public static boolean isEmpty(String msg) {

        if (msg == null)
            return true;
        else {

            if (msg.trim().isEmpty())
                return true;
            else
                return false;
        }
    }

    public static String y_m_d(Date date) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);

        return dateFormat.format(date);
    }

    public static int[] getScreenWH(Context context) {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        Display display = wm.getDefaultDisplay();
        int[] wh = new int[2];

        wh[0] = display.getWidth();
        wh[1] = display.getHeight();
        return wh;
    }


    public static String getPinYinHeadChar(String str) {

        String convert = "";
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert += pinyinArray[0].charAt(0);
            } else {
                convert += word;
            }
        }
        //截取第一个汉字的字母，convert获得的是所有汉字的首字母相加，转换成大写字母，才能进行排序，利用正则表达式匹配
        return convert.substring(0, 1).toUpperCase();
    }

    public static String getAndroidId(Context context) {

        String serialnum = null;
        try {

            Class<?> c = Class.forName("android.os.SystemProperties");

            Method get = c.getMethod("get", String.class, String.class);

            serialnum = (String) (get.invoke(c, "ro.serialno", "unknown"));

        } catch (Exception ignored) {

            Logger.i(" " + ignored.toString());
        }
    serialnum =   Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return serialnum;
    }


}