package com.example.ysl.mywps.bean;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by ysl on 2018/1/18.
 */

public class DoucmentListBean {


    private int code;
    private String msg;
    private List<DataBean> data;

    public static DoucmentListBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), DoucmentListBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    class DataBean {

        private String id;
        private String uid;
        private String title;
        private String dept;
        private String des;
        private String doc_name;
        private String doc_url;
        private DocImgsBean doc_imgs;
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

        public DocImgsBean getDoc_imgs() {
            return doc_imgs;
        }

        public void setDoc_imgs(DocImgsBean doc_imgs) {
            this.doc_imgs = doc_imgs;
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

        public  class DocImgsBean {
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
    }
}
