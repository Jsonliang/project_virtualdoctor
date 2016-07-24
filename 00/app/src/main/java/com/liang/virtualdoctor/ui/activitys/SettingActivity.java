package com.liang.virtualdoctor.ui.activitys;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.liang.virtualdoctor.R;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.media.UMImage;

/**
 * 用户设置
 */
public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout rl_share;
    private TextView toolbar_goback;
    private TextView setting_cache;
    private ProgressBar setting_pb;
    /*private UMSocialService mController = UMServiceFactory
            .getUMSocialService(ShareConstants.DESCRIPTOR);*/
    final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
        // 设置分享内容
        mController.setShareContent("友盟社会化组件（SDK）让移动应用快速整合社交分享功能，http://www.umeng.com/social");
// 设置分享图片, 参数2为图片的url地址
        mController.setShareMedia(new UMImage(this,
                "http://www.baidu.com/img/bdlogo.png"));
        //打开分享
        mController.openShare(getActivity(), false);
        mController.getConfig().setPlatforms(SHARE_MEDIA.RENREN, SHARE_MEDIA.DOUBAN,
                SHARE_MEDIA.TENCENT, SHARE_MEDIA.SINA,SHARE_MEDIA.SMS, SHARE_MEDIA.WEIXIN);

    }

    private void initView() {
        setting_pb= (ProgressBar) findViewById(R.id.setting_pb);
        setting_cache= (TextView) findViewById(R.id.setting_cache);
        toolbar_goback= (TextView) findViewById(R.id.toolbar_goback);
        rl_share= (RelativeLayout) findViewById(R.id.rl_share);
        rl_share.setOnClickListener(this);
        toolbar_goback.setOnClickListener(this);

        findViewById(R.id.rl_about).setOnClickListener(this);
        findViewById(R.id.rl_agreement).setOnClickListener(this);
        findViewById(R.id.rl_update).setOnClickListener(this);
        findViewById(R.id.rl_cache).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //分享
            case R.id.rl_share:
               // mController.openShare(getActivity(), false);
                mController.postShare(this, SHARE_MEDIA.WEIXIN, new SocializeListeners.SnsPostListener() {
                    @Override
                    public void onStart() {
                        //Toast.makeText(getApplication(), "开始分享.", Toast.LENGTH_SHORT).show();
                        Uri smsToUri = Uri.parse("smsto:10000");
                        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
                        intent.putExtra("sms_body", "测试发送短信");
                        startActivity(intent);
                    }
                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, SocializeEntity socializeEntity) {
                        /*if (eCode == 200) {
                            Toast.makeText(mContext, "分享成功.", Toast.LENGTH_SHORT).show();
                        } else {
                            String eMsg = "";
                            if (eCode == -101){
                                eMsg = "没有授权";
                            }
                            Toast.makeText(mContext, "分享失败[" + eCode + "] " +
                                    eMsg,Toast.LENGTH_SHORT).show();
                        }
                    }
                    }*/
                    }
                });
                break;
           
            case R.id.rl_about:
                startActivity(new Intent(this,AboutActivity.class));
                break;
            //用户协议
            case R.id.rl_agreement:
                startActivity(new Intent(this,AgreementActivity.class));
                break;
            //更新
            case R.id.rl_update:
                Toast.makeText(SettingActivity.this, "当前已是最新版本，无需更新！", Toast.LENGTH_LONG).show();
                break;
            case R.id.toolbar_goback:
                SettingActivity.this.finish();
                break;
            case R.id.rl_cache:
                //clearCache();
                Toast.makeText(SettingActivity.this, "当前没有缓存，无需清理", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void clearCache() {
        setting_cache.setVisibility(View.INVISIBLE);
        setting_pb.setVisibility(View.VISIBLE);
        //三秒钟后
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setting_cache.setVisibility(View.VISIBLE);
        setting_pb.setVisibility(View.INVISIBLE);
    }

    public Activity getActivity() {

        return activity;
    }
}
