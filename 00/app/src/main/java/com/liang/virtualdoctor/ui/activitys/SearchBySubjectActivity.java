package com.liang.virtualdoctor.ui.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.text.TextUtils;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.TextView;


import com.liang.virtualdoctor.R;
import com.liang.virtualdoctor.beans.Subject;
import com.liang.virtualdoctor.fragments.MedicalNews_Fragment;
import com.liang.virtualdoctor.fragments.SubjectFragment;
import com.liang.virtualdoctor.fragments.SubjectItemFragment;
import com.liang.virtualdoctor.utils.Constants;
import com.liang.virtualdoctor.utils.HttpHelper;
import com.liang.virtualdoctor.utils.JsonParserUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * create for medical search by subject
 */
public class SearchBySubjectActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_searchbysubject);
        initToolBar();
    }

    private SubjectFragment subjectFrag;
    private SubjectItemFragment subjectItemFrag;

    private void initToolBar() {
        Toolbar tool = (Toolbar) findViewById(R.id.toolbar);
        //tool.findViewById(R.id.toolbar_centerText).setVisibility(View.GONE);
        TextView title = (TextView) tool.findViewById(R.id.toolbar_centerText);
        title.setText("半个小医生");
        tool.findViewById(R.id.toolbar_rightText).setVisibility(View.GONE);
        TextView goBack = (TextView) tool.findViewById(R.id.toolbar_goback);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchBySubjectActivity.this.finish();
            }
        });

        subjectFrag = (SubjectFragment) getSupportFragmentManager().findFragmentById(R.
                id.frag_search_subject);
        subjectItemFrag = (SubjectItemFragment) getSupportFragmentManager().findFragmentById(R.
                id.frag_search_itemsubject);
        setlistViewListenner();

    }

    private void setlistViewListenner() {
        subjectFrag.getListView().setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        //MedicalNews_Fragment.showToast(getApplicationContext(), "点击了" + position);
                        Subject sub = subjectFrag.getSubs().get(position);
                        //send data to right fragment
                        subjectItemFrag.Datachange(sub);
                    }
                });
    }
}
