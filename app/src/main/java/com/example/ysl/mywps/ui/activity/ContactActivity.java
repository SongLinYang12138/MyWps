package com.example.ysl.mywps.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ysl.mywps.R;
import com.example.ysl.mywps.bean.ContactBean;
import com.example.ysl.mywps.ui.adapter.ContactAdapter;
import com.example.ysl.mywps.ui.adapter.ContactMyAdapter;
import com.example.ysl.mywps.utils.CommonUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ysl on 2018/1/16.
 */

public class ContactActivity extends BaseActivity {

    @BindView(R.id.contact_listview)
    ListView listView;

    private ContactMyAdapter adapter;
    private ArrayList<ContactBean> list = new ArrayList<>();

    private String[] contact = {"阿达", "阿2", "阿三", "贝尔", "曹操", "张飞", "关于"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contact_layout);
        ButterKnife.bind(this);

        for (int i = 0; i < contact.length; ++i) {
            ContactBean bean = new ContactBean();
            bean.setCapital(CommonUtil.getPinYinHeadChar(contact[i]));
            bean.setName(contact[i]);
            list.add(bean);
        }
        adapter = new ContactMyAdapter(list, this);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                finish();
            }
        });
        showLeftButton(true, "", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setTitleText("通讯录");
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }
}
