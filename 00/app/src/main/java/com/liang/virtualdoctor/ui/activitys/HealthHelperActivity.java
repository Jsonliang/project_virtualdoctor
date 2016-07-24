package com.liang.virtualdoctor.ui.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import com.liang.virtualdoctor.R;

public class HealthHelperActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_helper);

        initView();
    }

    private void initView() {
        findViewById(R.id.rl_helper_back).setOnClickListener(this);
        findViewById(R.id.helper_iv_add).setOnClickListener(this);
        findViewById(R.id.helper_empty_head).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_helper_back:
                HealthHelperActivity.this.finish();
                break;
            case R.id.helper_iv_add:
                startActivity(new Intent(this,LoginActivity.class));
                break;
            case R.id.helper_empty_head:
                startActivity(new Intent(this,LoginActivity.class));
                break;
        }
    }
}
