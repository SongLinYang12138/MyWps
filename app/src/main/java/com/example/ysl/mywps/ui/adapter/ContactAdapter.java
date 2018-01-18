package com.example.ysl.mywps.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ysl.mywps.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/24 0024.
 */

public class ContactAdapter extends MyBaseAdapter {

    private Context context;
    private ArrayList<String> list;


    public ContactAdapter(ArrayList<String> list, Context context) {
        super(list, context);

        this.list = (ArrayList<String>) list;
        this.context = context;

    }

    @Override
    public View myView(int i, View view, ViewGroup viewGroup) {

        if (i == list.size() - 1) {

            view = LayoutInflater.from(context).inflate(R.layout.contact_last_item_layout, null);

        } else {

            view = LayoutInflater.from(context).inflate(R.layout.message_listview_item_layout, null);
        }
        return view;
    }
}
