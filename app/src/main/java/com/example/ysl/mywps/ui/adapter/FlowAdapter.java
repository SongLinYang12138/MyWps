package com.example.ysl.mywps.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.ysl.mywps.R;

import java.util.ArrayList;

/**
 * Created by ysl on 2018/1/16.
 */

public class FlowAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> list;

    public FlowAdapter(Context context,ArrayList<String> list){

        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        view = LayoutInflater.from(context).inflate(R.layout.listview_item_flow_progress_layout,null);

        return view;
    }
}
