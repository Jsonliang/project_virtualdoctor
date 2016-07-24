package com.liang.virtualdoctor.ui.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.liang.virtualdoctor.R;
import com.liang.virtualdoctor.adapter.Examine_Activity_ListView_Adapter;
import com.liang.virtualdoctor.beans.Check_Symptom_Scroll2_Data;
import com.liang.virtualdoctor.beans.Examine_Activity_ListView_Data;
import com.liang.virtualdoctor.ui.customviews.Check_Symptom_Scroll2;
import com.liang.virtualdoctor.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExamineActivity extends AppCompatActivity {

    @BindView(R.id.activity_examine_toolbar_toHospital)
    ImageView toHospital;
    @BindView(R.id.activity_examine_infos)
    Check_Symptom_Scroll2 scrolView;
    @BindView(R.id.activity_examine_ll_listview)
    ListView listView;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_examine);
        ButterKnife.bind(this);
        IntiView();
        setData();
    }
    List<Examine_Activity_ListView_Data> list;
    Examine_Activity_ListView_Adapter adapter;
    private void setData() {
      list=new ArrayList<>();
        list.add(new Examine_Activity_ListView_Data(null,"空腹血糖","暂时没有数据",null));
        adapter = new Examine_Activity_ListView_Adapter(list, this);
        listView.setAdapter(adapter);
        List<Check_Symptom_Scroll2_Data> list2=new ArrayList<>();

    }

    String [] name=new String[]{"浆膜腔积液","指测血糖","过敏原检查","呕吐物检查","呼气检查","血气分析","阴道分泌物","大便检查","尿液检查","血液检查"};

    private void IntiView() {
        scrolView.setMiinterface(new Check_Symptom_Scroll2.Iinterface() {
            @Override
            public void changeListView(int i) {
                list.get(0).setName(name[i]);
                adapter.notifyDataSetChanged();
            }
        });

    }
    @OnClick(R.id.activity_examine_toolbar_goback)
    public void onClick(View view) {
                Log.d("fromsky","1111");
        switch (view.getId()) {
            case R.id.activity_examine_toolbar_goback:
                finish();
                break;
            case R.id.activity_examine_toolbar_toHospital:
                intent = new Intent(this, NearlyActivity.class);
                startActivity(intent);
                break;
        }
    }
}
