package com.liang.virtualdoctor.ui.activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.liang.virtualdoctor.R;

import java.util.Timer;
import java.util.TimerTask;

public class FlashScreenActivity extends AppCompatActivity {
    private boolean mFirst = true;

    private Timer mTimer ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_screen);
        initView();
    }
    private void initView() {
        SharedPreferences preferences = getSharedPreferences("first", Context.MODE_PRIVATE);
        mFirst = preferences.getBoolean("first",true);

        if(mFirst){
            //跳到广告页 ，并且销毁自已
            Intent intent = new Intent (this,AdActivity.class);
            //startActivity(intent);
            //finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //获取焦点之后
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(FlashScreenActivity.this,HomeActivity.class);
                startActivity(intent);
                FlashScreenActivity.this.finish();//销毁自已
            }
        },2000);//2秒之后执行
    }

    @Override
    protected void onDestroy() {
        if(mTimer != null ){
            mTimer.cancel();
        }
        super.onDestroy();
    }
}
