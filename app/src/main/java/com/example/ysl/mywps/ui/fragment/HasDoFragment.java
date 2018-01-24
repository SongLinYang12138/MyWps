package com.example.ysl.mywps.ui.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ysl.mywps.R;
import com.example.ysl.mywps.ui.activity.WpcDetailActivity;
import com.example.ysl.mywps.ui.adapter.StayDoAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/14 0014.
 */

public class HasDoFragment extends BaseFragment {

    @BindView(R.id.stay_have_do_listview)
    ListView listView;

    private ArrayList<String> list = new ArrayList<>();
//    private StayDoAdapter adapter;

    @Override
    public View setView(LayoutInflater inflater, ViewGroup container) {

        View view = inflater.inflate(R.layout.fragment_have_do_layout, container, false);

        ButterKnife.bind(this, view);

        for (int i = 0; i < 3; ++i) {
            list.add(" " + i);
        }
//        adapter = new StayDoAdapter(getActivity(), list);
//        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), WpcDetailActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void afterView(View view) {


    }

    @Override
    public void setKindFlag(int kindFlag) {

    }
}
