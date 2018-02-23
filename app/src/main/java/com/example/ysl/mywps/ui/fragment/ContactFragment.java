package com.example.ysl.mywps.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.ysl.mywps.R;
import com.example.ysl.mywps.ui.adapter.ContactAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/12/23 0023.
 */

public class ContactFragment extends BaseFragment {
    @BindView(R.id.contact_listview)
    ListView listView;
    private ContactAdapter adapter;

    @Override
    public void initData() {

    }

    @Override
    public View setView(LayoutInflater inflater, ViewGroup container) {

        View view = inflater.inflate(R.layout.fragment_contact_layout, container, false);
        ButterKnife.bind(this,view);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 6; ++i) {
            list.add(i + "");
        }
        adapter = new ContactAdapter(list, getActivity());

        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void afterView(View view) {

    }

    @Override
    public void setKindFlag(int kindFlag) {

    }
}
