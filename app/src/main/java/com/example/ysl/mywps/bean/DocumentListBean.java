package com.example.ysl.mywps.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by ysl on 2018/1/18.
 */

public class DocumentListBean implements Parcelable {


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
//    {"code":0,"msg":"公文列表获取成功",
// "data":{"total":1,"list":[
// {"id":"1","uid":"2","title":"1231","dept":"123123","des":"123123",
// "doc_name":"1_测试中文.docx","doc_url":"http:\/\/p2c152618.bkt.clouddn.com\/1_测试中文.docx",
// "doc_imgs":[{"page":1,"img":"http:\/\/p2c152618.bkt.clouddn.com\/1_1_测试中文.docx_1.png"},
// {"page":2,"img":"http:\/\/p2c152618.bkt.clouddn.com\/1_1_测试中文.docx_2.png"},
// {"page":3,"img":"http:\/\/p2c152618.bkt.clouddn.com\/1_1_测试中文.docx_3.png"}],
// "status":"2","ctime":"2018-01-16 16:47:17",
// "proce_id":"34","now_uid":"209","from_uid":"216","n_time":"2018-01-24 16:46:08","is_writable":0,
// "username":"18511234650","nickname":"吃啥好呢1222","realname":null}]

    private String id;
    private String uid;
    private String title;
    private String dept;
    private String des;
    private String doc_name;
    private String doc_url;
    private ArrayList<DocumentImageBean> doc_imgs;
    private String status;
    private String ctime;
    private String now_uid;
    private String from_uid;
    private String new_time;
    private String username;
    private String nickname;
    private Object realname;
    private String proce_id;
//"status":"1","ctime":"2018-01-16 16:47:17","now_uid":"2","from_uid":"2",
// "n_time":"2018-01-16 16:47:19","is_writable":0,"username":"18688654475","nickname":"系统消息","realname":null},

    public DocumentListBean() {

    }

    public String getProce_id() {
        return proce_id;
    }


    public ArrayList<DocumentImageBean> getDoc_imgs() {
        return doc_imgs;
    }

    public void setDoc_imgs(ArrayList<DocumentImageBean> doc_imgs) {
        this.doc_imgs = doc_imgs;
    }

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
