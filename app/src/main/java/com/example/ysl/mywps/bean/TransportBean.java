package com.example.ysl.mywps.bean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ysl.mywps.utils.CommonFun;
import com.example.ysl.mywps.utils.CommonUtil;

import java.util.ArrayList;

/**
 * Created by ysl on 2018/2/9.
 * 介绍: 下载和上传文件的属性
 */

public class TransportBean {


    public static final String NAME = "file_name";
    public static final String DATE = "file_date";
    public static final String SIZE = "file_size";
    public static final String PATH = "file_path";

    public static final String[] TRANSPORTBEANS = new String[]{NAME,DATE,SIZE,PATH};


    private String name;
    private String date;
    private String size;
    private String path;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ContentValues toContentValues() {

        ContentValues values = new ContentValues();

       if(CommonUtil.isEmpty(date)){
           long currentDate = System.currentTimeMillis();
           date = CommonFun.MinuteToString(currentDate);
       }
        values.put(NAME, name);
        values.put(DATE, date);
        values.put(SIZE, size);
        values.put(PATH, path);
        return values;
    }

    public static ArrayList<TransportBean> getTransportBeans(Cursor cursor) {

        ArrayList<TransportBean> list = new ArrayList<>();

        while (cursor.moveToNext()) {

            TransportBean info = new TransportBean();

            String name = cursor.getString(cursor.getColumnIndex(NAME));
            String date = cursor.getString(cursor.getColumnIndex(DATE));
            String size = cursor.getString(cursor.getColumnIndex(SIZE));
            String path = cursor.getString(cursor.getColumnIndex(PATH));

            info.setName(name);
            info.setDate(date);
            
            info.setSize(size);
            info.setPath(path);
            list.add(info);
        }
        cursor.close();
        return list;


    }

}
