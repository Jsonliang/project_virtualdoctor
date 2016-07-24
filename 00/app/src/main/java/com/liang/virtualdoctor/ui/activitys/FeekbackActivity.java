package com.liang.virtualdoctor.ui.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.liang.virtualdoctor.R;

public class FeekbackActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText feekback_et;
    private String text;
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feekback);

        initView();

    }

    private void initView() {
        findViewById(R.id.feekback_toolbar).setOnClickListener(this);
        findViewById(R.id.feekback_send).setOnClickListener(this);
        feekback_et = (EditText) findViewById(R.id.feekback_et);
        tv= (TextView) findViewById(R.id.feekback_tv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.feekback_send:
            tv.setVisibility(View.VISIBLE);
            text=feekback_et.getText().toString();
            tv.setText(text);
                break;
            case R.id.feekback_toolbar:
                FeekbackActivity.this.finish();
                break;
        }

    }

}
