package com.liang.virtualdoctor.ui.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.liang.virtualdoctor.R;
import com.liang.virtualdoctor.ui.customviews.ClearEditText;
import com.liang.virtualdoctor.utils.RegexUtils;
import com.liang.virtualdoctor.utils.ToastUtils;
import com.liang.virtualdoctor.utils.VerifyCodeManager;

public class UserPwForgetActivity extends AppCompatActivity implements View.OnClickListener {

    private ClearEditText et_phone;
    private EditText et_verifiCode;
    private ClearEditText et_password;
    private Button btnSMSCode;
    private VerifyCodeManager codeManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pw_forget);

        initView();
        codeManager = new VerifyCodeManager(this, et_phone, btnSMSCode);
    }

    private void initView() {
        et_phone= (ClearEditText) findViewById(R.id.et_Mobile);
        et_verifiCode= (EditText) findViewById(R.id.et_verifiCode);
        et_password= (ClearEditText) findViewById(R.id.et_Password);
        btnSMSCode= (Button) findViewById(R.id.btnSMSCode);
        findViewById(R.id.forgetpw_toolbar).setOnClickListener(this);
        et_phone.setImeOptions(EditorInfo.IME_ACTION_NEXT);//
        et_verifiCode.setImeOptions(EditorInfo.IME_ACTION_NEXT);//
        et_password.setImeOptions(EditorInfo.IME_ACTION_DONE);
        et_password.setImeOptions(EditorInfo.IME_ACTION_GO);
        et_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE
                        || actionId == EditorInfo.IME_ACTION_GO) {
                    commit();
                }
                return false;
            }
        });
        btnSMSCode.setOnClickListener(this);
    }

    private void commit() {
        String phone = et_phone.getText().toString().trim();
        String code = et_verifiCode.getText().toString().trim();
        String password=et_password.getText().toString().trim();
        if (checkInput(phone , code,password)) {
            // TODO:请求服务器数据
        }
    }

    private boolean checkInput(String phone, String code, String password) {
        if (TextUtils.isEmpty(phone)) { 
            ToastUtils.showShort(this, R.string.tip_phone_can_not_be_empty);
        } else {
            if (!RegexUtils.checkMobile(phone)) { 
                ToastUtils.showShort(this, R.string.tip_phone_regex_not_right);
            } else if (TextUtils.isEmpty(code)) { 
                ToastUtils.showShort(this, R.string.tip_please_input_code);
            } else if (password.length() < 6 || password.length() > 32
                    || TextUtils.isEmpty(password)) { 
                ToastUtils.showShort(this,
                        R.string.tip_please_input_6_25_password);
            } else {
                return true;
            }
        }

        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSMSCode:
                // TODO 请求服务器数据
                codeManager.getVerifyCode(VerifyCodeManager.RESET_PWD);
                break;
            case R.id.forgetpw_toolbar:
                UserPwForgetActivity.this.finish();
                break;
            default:
                break;
        }
    }
}
