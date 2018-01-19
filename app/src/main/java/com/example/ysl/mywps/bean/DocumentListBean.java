package com.example.ysl.mywps.bean;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ysl on 2018/1/18.
 */

public class DocumentListBean {


//    {
//        "id": "40",          //文档id
//            "uid": "215",
//            "title": "12312",   //文档标题
//            "dept": "123123",   //部门名称
//            "des": "123123123", //文档描述
//            "doc_name": "40_中文.docx",
//            "doc_url": "p2c152618.bkt.clouddn.com/40_中文.docx", //文档下载地址
//            "doc_imgs": {
//        "1": "p2c152618.bkt.clouddn.com/40_中文.docx_1.png", //文档图片地址
//                "2": "p2c152618.bkt.clouddn.com/40_中文.docx_2.png",
//                "3": "p2c152618.bkt.clouddn.com/40_中文.docx_3.png"
//    },
//        "status": "1",   //文档当前状态   1拟稿 2审核 3签发 4流转
//            "ctime": "2018-01-12 17:39:32",
//            "now_uid": "215",   //当前流程所属用户
//            "from_uid": "0",
//            "new_time": "2018-01-12 17:40:59",  //最新更新时间
//            "username": "18688654478",   //当前流程所属用户用户名
//            "nickname": "不知道啊",     //当前流程所属用户昵称
//            "realname": null
//    },

    private String id;
    private String uid;
    private String title;
    private String dept;
    private String des;
    private String doc_name;
    private String doc_url;
    private DocumentImageBean doc_imgs;
    private String status;
    private String ctime;
    private String now_uid;
    private String from_uid;
    private String new_time;
    private String username;
    private String nickname;
    private Object realname;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getDoc_name() {
        return doc_name;
    }

    public void setDoc_name(String doc_name) {
        this.doc_name = doc_name;
    }

    public String getDoc_url() {
        return doc_url;
    }

    public void setDoc_url(String doc_url) {
        this.doc_url = doc_url;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getNow_uid() {
        return now_uid;
    }

    public void setNow_uid(String now_uid) {
        this.now_uid = now_uid;
    }

    public String getFrom_uid() {
        return from_uid;
    }

    public void setFrom_uid(String from_uid) {
        this.from_uid = from_uid;
    }

    public String getNew_time() {
        return new_time;
    }

    public void setNew_time(String new_time) {
        this.new_time = new_time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Object getRealname() {
        return realname;
    }

    public void setRealname(Object realname) {
        this.realname = realname;
    }
}
