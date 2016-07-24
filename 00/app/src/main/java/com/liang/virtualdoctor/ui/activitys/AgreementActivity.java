package com.liang.virtualdoctor.ui.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.liang.virtualdoctor.R;

/**
 * 用户协议
 */
public class AgreementActivity extends AppCompatActivity implements View.OnClickListener {

    private WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);
        findViewById(R.id.agreement_toolbar).setOnClickListener(this);
        mWebView= (WebView) findViewById(R.id.agreement_webview);
        //防止跳转到系统自带的浏览器中打开
        mWebView.setWebViewClient(new WebViewClient());
        String data="用户协议";
        mWebView.loadData(data,"text/html; charset=UTF-8", null);
    }

    @Override
    public void onClick(View v) {
        //销毁当前activity()
        AgreementActivity.this.finish();
    }
}
