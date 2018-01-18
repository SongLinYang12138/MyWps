package com.example.ysl.mywps.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ysl.mywps.R;

/**
 * Created by Administrator on 2017/12/23 0023.
 */

public class MineFragment extends BaseFragment {
    @Override
    public View setView(LayoutInflater inflater, ViewGroup container) {

        View view = inflater.inflate(R.layout.fragment_mine_layout,container,false);

        return view;
    }

    @Override
    public void afterView(View view) {

    }

    @Override
    public void setKindFlag(int kindFlag) {

    }
}
