package com.example.ysl.mywps.bean;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ysl on 2018/1/19.
 */

public class DocumentImageBean {

    /**
     * 1 : p2c152618.bkt.clouddn.com/40_中文.docx_1.png
     * 2 : p2c152618.bkt.clouddn.com/40_中文.docx_2.png
     * 3 : p2c152618.bkt.clouddn.com/40_中文.docx_3.png
     */

    @SerializedName("1")
    private String _$1;
    @SerializedName("2")
    private String _$2;
    @SerializedName("3")
    private String _$3;


    public String get_$1() {
        return _$1;
    }

    public void set_$1(String _$1) {
        this._$1 = _$1;
    }

    public String get_$2() {
        return _$2;
    }

    public void set_$2(String _$2) {
        this._$2 = _$2;
    }

    public String get_$3() {
        return _$3;
    }

    public void set_$3(String _$3) {
        this._$3 = _$3;
    }
}
