package com.liang.virtualdoctor.fragments;

import android.content.Intent;
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
import com.liang.virtualdoctor.ui.activitys.WebViewActivity;
import com.liang.virtualdoctor.utils.Constants;
import com.liang.virtualdoctor.utils.HttpHelper;
import com.liang.virtualdoctor.utils.JsonParserUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/12.
 */
public class SubjectItemFragment extends ListFragment {
    //right subjectitme fragment
    private static final String TAG = SubjectItemFragment.class.getSimpleName();
    private SubjectFragment subFragment;
    private ArrayAdapter<String> adapter;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sb_subject_subitemlayout,
                container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private int subjectId;
    private int itemId;
    private String url = "";
    private void initView() {
            //set listfragment'listview listenner
            setListViewListenner();

    }

    private void setListViewListenner() {
        SubjectItemFragment.this.getListView().setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        //show item subject detail
                        gotoWebview(position);
                    }
                });
    }

    private void gotoWebview(int position) {
        Subject sub = items.get(position);
        itemId = sub.getId();
        url = "http://3g.club.xywy.com/familyDoctor/jib/subclass/" +
                +subjectId + "/" + itemId + "?xywyapp=1";
        Intent intent = new Intent(SubjectItemFragment.
                this.getActivity(), WebViewActivity.class);

        //put url in intent
        intent.putExtra(WebViewActivity.URLKEYWORD, url);
        intent.putExtra(WebViewActivity.TOOLBARTITLE,sub.getName());
       getActivity().startActivity(intent);
    }

    private List<Subject> items = new ArrayList<>();
    private List<String> itemNames=new ArrayList<>();

    public void Datachange(Subject subject) {
        subjectId = subject.getId();
        MedicalNews_Fragment.showToast(getContext(),""+subjectId);
        String url=Constants.QUER_SUBJECTITEM+subjectId;
        Log.i("TAG", "onFailure: "+url);
        HttpHelper.getInstance().requestByGet(url, new HttpHelper.StringCallBack() {
            @Override
            public void onFailure(Exception e) {

                Log.e(TAG, "onFailure: " + e.getMessage());
            }
            @Override
            public void onResult(Object string) {
                String json = (String) string;
                if (!TextUtils.isEmpty(json)) {
                    List<Subject> subs = JsonParserUtils.parserJSON2Subject(json);
                    if (subs != null && subs.size() != 0) {
                        items.clear();
                        items.addAll(subs);
                        itemNames.clear();
                        itemNames.addAll(SubjectFragment.getNames(items));
                        if (adapter == null) {
                            adapter = new ArrayAdapter<String>(SubjectItemFragment.
                                    this.getContext(), android.R.layout.simple_list_item_1,
                                    itemNames);
                            Log.i("TAG", "onResult: "+adapter.isEmpty());
                            SubjectItemFragment.this.getListView().setAdapter(adapter);
                        } else {
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }
}


