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

    public void setProce_id(String proce_id) {
        this.proce_id = proce_id;
    }

    protected DocumentListBean(Parcel in) {
        id = in.readString();
        uid = in.readString();
        title = in.readString();
        dept = in.readString();
        des = in.readString();
        doc_name = in.readString();
        doc_url = in.readString();
        doc_imgs = in.createTypedArrayList(DocumentImageBean.CREATOR);
        status = in.readString();
        ctime = in.readString();
        now_uid = in.readString();
        from_uid = in.readString();
        new_time = in.readString();
        username = in.readString();
        nickname = in.readString();
    }

    public static final Creator<DocumentListBean> CREATOR = new Creator<DocumentListBean>() {
        @Override
        public DocumentListBean createFromParcel(Parcel in) {
            return new DocumentListBean(in);
        }

        @Override
        public DocumentListBean[] newArray(int size) {
            return new DocumentListBean[size];
        }
    };

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
        dest.writeString(id);
        dest.writeString(uid);
        dest.writeString(title);
        dest.writeString(dept);
        dest.writeString(des);
        dest.writeString(doc_name);
        dest.writeString(doc_url);
        dest.writeTypedList(doc_imgs);
        dest.writeString(status);
        dest.writeString(ctime);
        dest.writeString(now_uid);
        dest.writeString(from_uid);
        dest.writeString(new_time);
        dest.writeString(username);
        dest.writeString(nickname);
    }
}
