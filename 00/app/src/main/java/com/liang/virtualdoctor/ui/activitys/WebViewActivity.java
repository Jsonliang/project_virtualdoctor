package com.liang.virtualdoctor.ui.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.liang.virtualdoctor.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * webView
 */
public class WebViewActivity extends AppCompatActivity  {
    private static final String TAG =WebViewActivity.class.getCanonicalName() ;
    private String url;
    @BindView(R.id.medical_webview)
    public WebView mWebview;
    public static final String URLKEYWORD="url_Key";
    public static final String TOOLBARTITLE="title";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(WebViewActivity.this);
        initWebView();
        initToolBar();
        Intent intent=getIntent();
        if(intent!=null){
           url= intent.getExtras().getString(URLKEYWORD);
            String title=intent.getExtras().getString(TOOLBARTITLE);
            Log.i(TAG, "onCreate: loadurl--->"+url);
            if(!TextUtils.isEmpty(url)&&!TextUtils.isEmpty(title)){
                toolBarTitle.setText(title);
                //solve encoding messy code problem
                //mWebview.loadDataWithBaseURL(null, url, "text/html", "utf-8", null);
                mWebview.loadUrl(url);
            }
        }

    }
    TextView toolBarTitle;
    private void initToolBar() {
        view=findViewById(R.id.webview_toolbar);
        view.findViewById(R.id.toolbar_rightText).setVisibility(View.GONE);
        view.findViewById(R.id.toolbar_lineview).setVisibility(View.GONE);
        view.findViewById(R.id.toolbar_goback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mWebview.canGoBack()){
                    mWebview.goBack();
                }else {
                    WebViewActivity.this.finish();
                }
            }
        });
        toolBarTitle= (TextView) view.findViewById(R.id.toolbar_centerText);

    }

    private View view;
    private void initWebView() {
        // mWebview= (WebView) findViewById(R.id.medical_webview);
        // make the webview support javascript show dialog
        mWebview.setWebChromeClient(new WebChromeClient());
        //show the other url at same webview;
        mWebview.setWebViewClient(new WebViewClient());
        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
    }
        @Override
        public boolean onKeyDown ( int keyCode, KeyEvent event){
            if ((keyCode == event.KEYCODE_BACK) && mWebview.canGoBack()) {
                mWebview.goBack(); // goBack()表示返回WebView的上一页面
                return true;
            }
            return super.onKeyDown(keyCode, event);
        }
}
