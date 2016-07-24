package com.liang.virtualdoctor.ui.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.liang.virtualdoctor.R;
import com.liang.virtualdoctor.ui.customviews.ClearEditText;
import com.liang.virtualdoctor.utils.LogUtils;
import com.liang.virtualdoctor.utils.ProgressDialogUtils;
import com.liang.virtualdoctor.utils.RegexUtils;
import com.liang.virtualdoctor.utils.ShareConstants;
import com.liang.virtualdoctor.utils.SpUtils;
import com.liang.virtualdoctor.utils.ToastUtils;
import com.liang.virtualdoctor.utils.Utils;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.exception.SocializeException;

import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "loginActivity";
    private static final int REQUEST_CODE_TO_REGISTER = 0x001;

    private LinearLayout ll_login;
    private TextView forgetpw;
    private TextView register;
    private ClearEditText et_account;
    private ClearEditText et_password;
    private Button btn_login;
    private ImageView iv_wechat, iv_qq, iv_sina,iv_close_window;

    // 整个平台的Controller，负责管理整个SDK的配置、操作等处理
    private UMSocialService mController = UMServiceFactory
            .getUMSocialService(ShareConstants.DESCRIPTOR);

    // 第三方平台获取的访问token，有效时间，uid
    private String accessToken;
    private String expires_in;
    private String uid;
    private String sns;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

      /*  ll_login= (LinearLayout) findViewById(R.id.ll_login);
        ll_login.getBackground().setAlpha(20);*/

        initView();
    }

    /**
     * 视图初始化
     */
    private void initView() {
        iv_wechat = (ImageView) findViewById(R.id.iv_wechat);
        iv_qq = (ImageView) findViewById(R.id.iv_qq);
        iv_sina = (ImageView) findViewById(R.id.iv_sina);
        iv_close_window= (ImageView) findViewById(R.id.iv_close_window);

        iv_close_window.setOnClickListener(this);
        iv_wechat.setOnClickListener(this);
        iv_qq.setOnClickListener(this);
        iv_sina.setOnClickListener(this);

        btn_login = (Button) findViewById(R.id.btn_login);
        forgetpw = (TextView) findViewById(R.id.login_forgetpw);
        register = (TextView) findViewById(R.id.register);
        forgetpw.setOnClickListener(this);
        register.setOnClickListener(this);

        et_account = (ClearEditText) findViewById(R.id.et_account);
        et_account.setImeOptions(EditorInfo.IME_ACTION_NEXT);//下一步
        et_account.setTransformationMethod(HideReturnsTransformationMethod
                .getInstance());

        et_password = (ClearEditText) findViewById(R.id.et_password);
        et_password.setImeOptions(EditorInfo.IME_ACTION_NONE);
        et_password.setImeOptions(EditorInfo.IME_ACTION_GO);
        et_password.setTransformationMethod(PasswordTransformationMethod
                .getInstance());
        et_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE
                        || actionId == EditorInfo.IME_ACTION_GO) {
                    clickLogin();
                }
                return false;
            }
        });

    }

    private void clickLogin() {
        String account = et_account.getText().toString();
        String password = et_password.getText().toString();
        if (checkInput(account, password)) {
            // TODO: 请求服务器登录账号
        }
    }

    /**
     * 检查输入的用户名和密码格式是否正确
     *
     * @param account
     * @param password
     * @return
     */
    private boolean checkInput(String account, String password) {
        // 账号为空时提示
        if (account == null || account.trim().equals("")) {

            Toast.makeText(this, R.string.tip_account_empty, Toast.LENGTH_LONG)
                    .show();
            Log.d(TAG, "checkInput: "+account);
        } else {
            // 账号不匹配手机号格式（11位数字且以1开头）
            if (!RegexUtils.checkMobile(account)) {
                Toast.makeText(this, R.string.tip_account_regex_not_right,
                        Toast.LENGTH_LONG).show();
            } else if (password == null || password.trim().equals("")) {
                Toast.makeText(this, R.string.tip_password_can_not_be_empty,
                        Toast.LENGTH_LONG).show();
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //登录
            case R.id.btn_login:
                clickLogin();
                break;
            //忘记密码
            case R.id.login_forgetpw:
                startActivity(new Intent(this, UserPwForgetActivity.class));
                break;
            //快速注册
            case R.id.register:
                enterRegister();
                break;
            //wechat登录
            case R.id.iv_wechat:
                clickLoginWexin();
                break;
            //qq登录
            case R.id.iv_qq:
                clickLoginQQ();
                break;
            //sina登录
            case R.id.iv_sina:
                loginThirdPlatform(SHARE_MEDIA.SINA);
                break;
            //关闭登录窗口,返回上一个页面
            case R.id.iv_close_window:
                LoginActivity.this.finish();
                break;
            default:
                break;
        }
    }

    /**
     * 跳转到注册页面
     */
    private void enterRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        //返回一个请求码
        startActivityForResult(intent, REQUEST_CODE_TO_REGISTER);
    }
    /**
     * 点击使用QQ快速登录
     */
    private void clickLoginQQ() {
        if (!Utils.isQQClientAvailable(this)) {
            ToastUtils.showShort(LoginActivity.this,
                    getString(R.string.no_install_qq));
        } else {
            loginThirdPlatform(SHARE_MEDIA.QZONE);
        }
    }
    /**
     * 点击微信登录
     */
    private void clickLoginWexin() {
        if (!Utils.isWeixinAvilible(this)) {
            ToastUtils.showShort(LoginActivity.this,
                    getString(R.string.no_install_wechat));
        } else {
            //loginThirdPlatform(SHARE_MEDIA.WEIXIN);
        }
    }

    /**
     * 授权。如果授权成功，则获取用户信息
     *
     * @param platform
     */
    private void loginThirdPlatform(final SHARE_MEDIA platform) {
        mController.doOauthVerify(LoginActivity.this, platform,
                new SocializeListeners.UMAuthListener() {

                    @Override
                    public void onStart(SHARE_MEDIA platform) {
                        LogUtils.i(TAG, "onStart------"
                                + Thread.currentThread().getId());
                        ProgressDialogUtils.getInstance().show(
                                LoginActivity.this,
                                getString(R.string.tip_begin_oauth));
                    }

                    @Override
                    public void onError(SocializeException e,
                                        SHARE_MEDIA platform) {
                        LogUtils.i(TAG, "onError------"
                                + Thread.currentThread().getId());
                        ToastUtils.showShort(LoginActivity.this,
                                getString(R.string.oauth_fail));
                        ProgressDialogUtils.getInstance().dismiss();
                    }

                    @Override
                    public void onComplete(Bundle value, SHARE_MEDIA platform) {
                        LogUtils.i(TAG, "onComplete------" + value.toString());
                        if (platform == SHARE_MEDIA.SINA) {
                            accessToken = value.getString("access_key");
                        } else {
                            accessToken = value.getString("access_token");
                        }
                        expires_in = value.getString("expires_in");
                        // 获取uid
                        uid = value.getString(ShareConstants.UID);
                        if (value != null && !TextUtils.isEmpty(uid)) {
                            // uid不为空，获取用户信息
                            getUserInfo(platform);
                        } else {
                            ToastUtils.showShort(LoginActivity.this,
                                    getString(R.string.oauth_fail));
                        }
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA platform) {
                        LogUtils.i(TAG, "onCancel------"
                                + Thread.currentThread().getId());
                        ToastUtils.showShort(LoginActivity.this,
                                getString(R.string.oauth_cancle));
                        ProgressDialogUtils.getInstance().dismiss();

                    }
                });
    }

    /**
     * 获取用户信息
     *
     * @param platform
     */
    private void getUserInfo(final SHARE_MEDIA platform) {
        mController.getPlatformInfo(LoginActivity.this, platform,
                new SocializeListeners.UMDataListener() {

                    @Override
                    public void onStart() {
                        // 开始获取
                        LogUtils.i("getUserInfo", "onStart------");
                        ProgressDialogUtils.getInstance().dismiss();
                        ProgressDialogUtils.getInstance().show(
                                LoginActivity.this, "正在请求...");
                    }

                    @Override
                    public void onComplete(int status, Map<String, Object> info) {

                        try {
                            String sns_id = "";
                            String sns_avatar = "";
                            String sns_loginname = "";
                            if (info != null && info.size() != 0) {
                                LogUtils.i("third login", info.toString());

                                if (platform == SHARE_MEDIA.SINA) { // 新浪微博
                                    sns = ShareConstants.SINA;
                                    if (info.get(ShareConstants.UID) != null) {
                                        sns_id = info.get(ShareConstants.UID)
                                                .toString();
                                    }
                                    if (info.get(ShareConstants.PROFILE_IMAGE_URL) != null) {
                                        sns_avatar = info
                                                .get(ShareConstants.PROFILE_IMAGE_URL)
                                                .toString();
                                    }
                                    if (info.get(ShareConstants.SCREEN_NAME) != null) {
                                        sns_loginname = info.get(
                                                ShareConstants.SCREEN_NAME)
                                                .toString();
                                    }
                                } else if (platform == SHARE_MEDIA.QZONE) { // QQ
                                    sns = ShareConstants.QQ;
                                    if (info.get(ShareConstants.UID) == null) {
                                        ToastUtils
                                                .showShort(
                                                        LoginActivity.this,
                                                        getString(R.string.oauth_fail));
                                        return;
                                    }
                                    sns_id = info.get(ShareConstants.UID)
                                            .toString();
                                    sns_avatar = info.get(
                                            ShareConstants.PROFILE_IMAGE_URL)
                                            .toString();
                                    sns_loginname = info.get(
                                            ShareConstants.SCREEN_NAME)
                                            .toString();
                                } else if (platform == SHARE_MEDIA.WEIXIN) { // 微信
                                    sns = ShareConstants.WECHAT;
                                    sns_id = info.get(ShareConstants.OPENID)
                                            .toString();
                                    sns_avatar = info.get(
                                            ShareConstants.HEADIMG_URL)
                                            .toString();
                                    sns_loginname = info.get(
                                            ShareConstants.NICKNAME).toString();
                                }

                                // 这里直接保存第三方返回来的用户信息
                                SpUtils.putBoolean(LoginActivity.this,
                                        ShareConstants.THIRD_LOGIN, true);

                                LogUtils.e("info", sns + "," + sns_id + ","
                                        + sns_loginname);

                                // TODO: 这里执行第三方连接(绑定服务器账号）


                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                });
    }
}
