package com.liang.virtualdoctor.ui.activitys;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.liang.virtualdoctor.R;
import com.liang.virtualdoctor.ui.customviews.ClearEditText;
import com.liang.virtualdoctor.utils.RegexUtils;
import com.liang.virtualdoctor.utils.ToastUtils;
import com.liang.virtualdoctor.utils.VerifyCodeManager;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private ClearEditText et_Mobile;
    private EditText et_verifiCode;
    private Button btnSMSCode;
    private VerifyCodeManager codeManager;
    private CheckBox cbAgree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
        codeManager = new VerifyCodeManager(this, et_Mobile, btnSMSCode);
    }

    private void initView() {
        findViewById(R.id.cbAgree).setOnClickListener(this);
        findViewById(R.id.reg_toolbar_goback).setOnClickListener(this);
        et_Mobile = (ClearEditText) findViewById(R.id.et_Mobile);
        et_verifiCode = (EditText) findViewById(R.id.et_verifiCode);
        btnSMSCode = (Button) findViewById(R.id.btnSMSCode);

        btnSMSCode.setOnClickListener(this);
        et_Mobile.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        et_verifiCode.setImeOptions(EditorInfo.IME_ACTION_DONE);
        et_verifiCode.setImeOptions(EditorInfo.IME_ACTION_GO);
        et_verifiCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
               
                if (actionId == EditorInfo.IME_ACTION_DONE
                        || actionId == EditorInfo.IME_ACTION_GO) {
                    commit();
                }
                return false;
            }
        });

    }

    private void commit() {
        String phone = et_Mobile.getText().toString().trim();
        String code = et_verifiCode.getText().toString().trim();

        if (checkInput(phone , code)) {
            // TODO:
        }
    }

    private boolean checkInput(String phone, String code) {
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showShort(this, R.string.tip_phone_can_not_be_empty);
        } else {
            if (!RegexUtils.checkMobile(phone)) {
                ToastUtils.showShort(this, R.string.tip_phone_regex_not_right);
            } else if (TextUtils.isEmpty(code)) {
                ToastUtils.showShort(this, R.string.tip_please_input_code);
            }  else {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSMSCode:
                // TODO 请求服务器数据
                codeManager.getVerifyCode(VerifyCodeManager.REGISTER);
                break;
            case R.id.reg_toolbar_goback:
                RegisterActivity.this.finish();
                break;
            case R.id.cbAgree:
               // changeButtonColor();
                break;
        }

    }

    private void changeButtonColor() {
        if (!cbAgree.isChecked()){
            btnSMSCode.setBackgroundColor(Color.GRAY);
        }
    }
}
