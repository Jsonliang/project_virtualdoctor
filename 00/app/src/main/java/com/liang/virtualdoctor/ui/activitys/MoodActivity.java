package com.liang.virtualdoctor.ui.activitys;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.emokit.sdk.InitListener;
import com.emokit.sdk.basicinfo.AdvancedInformation;
import com.emokit.sdk.heartrate.EmoRateListener;
import com.emokit.sdk.heartrate.RateDetect;
import com.emokit.sdk.record.EmotionDetect;
import com.emokit.sdk.record.EmotionVoiceListener;
import com.emokit.sdk.senseface.ExpressionDetect;
import com.emokit.sdk.senseface.ExpressionListener;
import com.emokit.sdk.util.SDKAppInit;
import com.emokit.sdk.util.SDKConstant;
import com.liang.virtualdoctor.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoodActivity extends AppCompatActivity {
    @BindView(R.id.isr_recognize)
    public Button stplayer;
    @BindView(R.id.isr_recognize_stop)
    public Button ststop;
    @BindView(R.id.isr_facedetect)
    public Button face;
    @BindView(R.id.isr_expressdetect)
    public Button express;


    private EmotionDetect emotiondetect;
    protected Context mcontext;
    private ExpressionDetect expressdetect;

    private RateDetect rt;
    @BindView(R.id.voiceshow)
    public TextView t1 ;
    @BindView(R.id.faceshow)
    public TextView t2;

    private String resultt2 = "";
    private boolean showt2 = false;

    private Handler mainhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case SDKConstant.VIEWFINSIH:

                    t2.setVisibility(View.VISIBLE);
                    t2.setText((String) msg.obj);
                    break;

                case 1901:
                    t1.setVisibility(View.VISIBLE);
                    t1.setText((String) msg.obj);
                    break;
                default:
                    break;
            }

        };
    };

    /**
     * 面部表情识别器
     */
    private ExpressionListener expresslisten = new ExpressionListener() {

        @Override
        public void endDetect(String result) {

            Log.i("EMOKITSDK>>>", "THE Express result IS" + result.toString());

            Message msg = new Message();
            msg.what = 1901;
            msg.obj = result;
            mainhandler.sendMessage(msg);
            Log.i("ENDINFO", "THE RESULT INFO IS" + result);
        }

        @Override
        public void beginDetect() {


        }
    };
    /**
     * 语音识别监听器。
     */
    private EmotionVoiceListener mRecognizerListener = new EmotionVoiceListener() {

        @Override
        public void onVolumeChanged(int volume) {
            Log.e("当前正在说话，音量大小：", volume + "");
        }

        @Override
        public void onEndOfSpeech() {
            Log.e("EmotionVoiceListener", "结束说话");
        }

        @Override
        public void onBeginOfSpeech() {
            Log.e("EmotionVoiceListener", "开始说话");
        }

        @Override
        public void onVoiceResult(String Result) {

            Message msg = new Message();
            msg.what = 1901;
            msg.obj = Result;
            mainhandler.sendMessage(msg);
            Log.i("ENDINFO", "THE RESULT INFO IS" + Result);
        }

    };

    /**
     * 心率识别监听器。
     */
    EmoRateListener recoginze_listener = new EmoRateListener() {

        @Override
        public void beginDetect() {

        }

        @Override
        public void endDetect(String result) {

            Log.i("MainActivity get the result is", "[" + result + "]");
            Message msg = new Message();
            msg.what = SDKConstant.VIEWFINSIH;
            msg.obj = result;
            mainhandler.sendMessage(msg);
        }

        @Override
        public void monitor(double rgb) {

            Log.i("recognizetag", "" + rgb);

        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_mood);
        initToolBar(); // 初始化toolbar
        ButterKnife.bind(this); // 初始化控件
        mcontext = this;
        SDKAppInit.createInstance(this); // 初始化
        emotiondetect = EmotionDetect.createRecognizer(this, mInitListener);
        rt = RateDetect.createRecognizer(this, mInitListener);

        expressdetect = ExpressionDetect.createRecognizer(this, mInitListener);


        t1.setVisibility(View.GONE);
        t2.setVisibility(View.GONE);
        if (showt2) {
            t2.setVisibility(View.VISIBLE);
            t2.setText(resultt2);
        }

        /**
         * 面部表情
         */
        express.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                expressdetect.setParameter(SDKConstant.FACING,
                        SDKConstant.CAMERA_FACING_FRONT);
                expressdetect.startRateListening(expresslisten);
            }
        });

        /**
         * 测试心率
         */
        face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rt.setParameter(SDKConstant.FACING, SDKConstant.CAMERA_FACING_FRONT);
                rt.startRateListening(recoginze_listener);
            }
        });

        /**
         * 语音分析
         */
        stplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                emotiondetect.startListening(mRecognizerListener);
            }
        });

        /**
         * 停止分析
         */
        ststop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View paramView) {

                emotiondetect.stopListening();

            }
        });

    }

    /**
     * 初始化 Toolbar
     */
    private void initToolBar() {
        Toolbar tool = (Toolbar) findViewById(R.id.toolbar);
        tool.findViewById(R.id.toolbar_centerText).setVisibility(View.GONE);
        tool.findViewById(R.id.toolbar_rightText).setVisibility(View.GONE);
        TextView goBack = (TextView) tool.findViewById(R.id.toolbar_goback);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoodActivity.this.finish();
            }
        });
    }

    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            // 获取设备ID
            AdvancedInformation pp = AdvancedInformation.getSingleton(mcontext);
            //SDKAppInit.registerforuid(platflag, userName, password);
            SDKAppInit.registerforuid("1603test",
                    "Jsonliang", null);
        }
    };
}
