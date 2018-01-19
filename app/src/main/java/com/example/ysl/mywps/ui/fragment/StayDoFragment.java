package com.example.ysl.mywps.ui.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ysl.mywps.R;
import com.example.ysl.mywps.bean.DocumentListBean;
import com.example.ysl.mywps.ui.activity.WpcDetailActivity;
import com.example.ysl.mywps.ui.adapter.StayDoAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/1/14 0014.
 */

public class StayDoFragment extends BaseFragment {

    @BindView(R.id.stay_to_listview)
    ListView listView;
    private StayDoAdapter adapter;
    private ArrayList<String> list = new ArrayList<>();
    private ArrayList<DocumentListBean> documents = new ArrayList<>();

    @Override
    public View setView(LayoutInflater inflater, ViewGroup container) {

        View view = inflater.inflate(R.layout.frament_stay_to_do_layout, container, false);
        ButterKnife.bind(this, view);
        for (int i = 0; i < 3; ++i) {
            list.add(" " + i);
        }
        adapter = new StayDoAdapter(getActivity(), list);
        listView.setAdapter(adapter);

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
//    User/Oa/doc_list
    private void netWork() {

        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> observableEmitter) throws Exception {

            }
        });

        Consumer<String> observer = new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        };
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }
}
