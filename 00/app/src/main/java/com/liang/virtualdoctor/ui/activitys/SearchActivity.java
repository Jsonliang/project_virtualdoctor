package com.liang.virtualdoctor.ui.activitys;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.liang.virtualdoctor.R;
import com.liang.virtualdoctor.utils.UnitConvertUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.search_edit_content)
    public EditText mSearchContent ;
    @BindView(R.id.search_btn_search)
    public Button mBtn_Search ;
    @BindView(R.id.search_content_clear)
    public ImageButton mClear_Content ;

    private TextView  mMoodCheck ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search);
        initToolBar();
        ButterKnife.bind(this);
        initView();
    }

    private void initToolBar() {
        Toolbar tool = (Toolbar) findViewById(R.id.toolbar);
        tool.findViewById(R.id.toolbar_centerText).setVisibility(View.GONE);
        mMoodCheck = (TextView) tool.findViewById(R.id.toolbar_rightText);
        mMoodCheck.setVisibility(View.VISIBLE);
        mMoodCheck.setText("情绪测试");
        mMoodCheck.setTextColor(Color.MAGENTA);
        mMoodCheck.setBackgroundResource(R.drawable.mood_test_shap);
        mMoodCheck.setCompoundDrawables(null, null, null, null);
        mMoodCheck.setTextSize(10);
        mMoodCheck.setPadding(UnitConvertUtils.dip2Px(this,5),UnitConvertUtils.dip2Px(this,5),
                UnitConvertUtils.dip2Px(this,5),UnitConvertUtils.dip2Px(this,5));
        mMoodCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this,MoodActivity.class);
                startActivity(intent);
            }
        });

        TextView goBack = (TextView) tool.findViewById(R.id.toolbar_goback);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.this.finish();
            }
        });
    }

    private void initView(){
        mClear_Content.setVisibility(View.GONE);//默认清楚按钮不可视

        //EditText 内容变化监听
        mSearchContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0){
                    mClear_Content.setVisibility(View.VISIBLE);
                }else {
                    mClear_Content.setVisibility(View.GONE);
                }
            }
        });
    }

    @OnClick({R.id.search_btn_search,R.id.search_content_clear})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.search_btn_search:
                if(mSearchContent.getText().toString().length() > 0){

                }else{
                    Toast.makeText(this,"搜索内容不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.search_content_clear:
                mSearchContent.setText("");
                break;
            default:
        }
    }
}
