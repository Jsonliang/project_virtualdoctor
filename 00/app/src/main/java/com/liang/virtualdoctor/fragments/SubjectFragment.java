package com.liang.virtualdoctor.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.liang.virtualdoctor.R;
import com.liang.virtualdoctor.beans.Subject;
import com.liang.virtualdoctor.utils.Constants;
import com.liang.virtualdoctor.utils.HttpHelper;
import com.liang.virtualdoctor.utils.JsonParserUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/12.
 */
public class SubjectFragment extends ListFragment {
    private static final String TAG = SubjectFragment.class.getSimpleName();
    private View view;
    //data
    private List<Subject> subs = new ArrayList<>();

    public List<Subject> getSubs() {
        return subs;
    }

    //show subject listview
    private List<String> names = new ArrayList<>();
    //need listenner return data
    private DataChangeListener listener;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sb_subject_sublayout,
                container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private ArrayAdapter<String> adapter;
    private void initView() {
        //com in dowload data
        downloadDataFromNet();
    }

    private void downloadDataFromNet() {
        HttpHelper.getInstance().requestByGet(Constants.QUER_SUBJECT,
                new HttpHelper.StringCallBack() {
                    @Override
                    public void onFailure(Exception e) {
                        Log.e(TAG, "onFailure: " + e.getMessage());
                    }
                    @Override
                    public void onResult(Object string) {
                        String json = (String) string;
                        if (!TextUtils.isEmpty(json)) {
                            List<Subject> subjects = JsonParserUtils.parserJSON2Subject(json);
                            if (subjects.size() != 0 && subjects != null) {
                                subs.clear();
                                subs.addAll(subjects);
                                names = getNames(subs);
                                if (adapter == null) {
                                    adapter = new ArrayAdapter<String>(getContext(),
                                            android.R.layout.simple_list_item_1, names);
                                    SubjectFragment.this.getListView().setAdapter(adapter);
                                } else {
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }
                });
    }



    public static List<String> getNames(List<Subject> subs) {
        if (subs == null || subs.size() == 0) return null;
        List<String> listNames = new ArrayList<>();
        for (int i = 0; i < subs.size(); i++) {
            Subject s = subs.get(i);
            listNames.add(s.getName());
        }
        return listNames;
    }

    //pass the data interface
    public interface DataChangeListener {
        public void Datachange(Subject subject);
    }

}
