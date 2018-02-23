package com.example.ysl.mywps.bean;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ysl on 2018/2/8.
 * 介绍:filelist数组的bean文件
 */

public class FileListChildBean {
//    {"id":"1","filename":"my_doc.docx","filename_qn":"my_doc.docx","ctime":"2018-02-05 14:44:47","download_url":"http:\/\/p2c152618.bkt.clouddn.com\/my_doc.docx"}

    /**
     * filename : my_doc.docx
     * ctime : 2018-02-05 14:44:47
     * download_url : http://p2c152618.bkt.clouddn.com/my_doc.docx
     */

    private String id;
    private String filename_qn;
    private String filename;
    private String ctime;
    private String download_url;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilename_qn() {
        return filename_qn;
    }

    public void setFilename_qn(String filename_qn) {
        this.filename_qn = filename_qn;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {



        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "{id: "+id+",filename_qn:"+filename_qn+",filename:"+filename+",ctime:"+ctime+",download_url:"+download_url+"}";
    }
}
