package com.liang.virtualdoctor.ui.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.liang.virtualdoctor.R;
import com.liang.virtualdoctor.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BodyMapCheckActivity extends AppCompatActivity {

    private Boolean mIsMan = true;// 默认男性
    private Boolean mIsFront = true; // 默认前面

    @BindView(R.id.body_switch_manOrWoman)
    public ImageButton mSwitchManOrWoman;//切换是男是女的标签
    @BindView(R.id.body_switch_frontOrback)
    public ImageButton mSwitchFrontOrBack;//切换前后的标签
    @BindView(R.id.body_check_all)
    public ImageButton mCheckAll; //全身相关

    @BindView(R.id.body_img_manOrwoman)
    public ImageView mShowManOrWoman; //显示man 或者 woman的图片
    @BindView(R.id.body_part_info)
    public ImageView mShowInfo; // 显示前或者后的 全部信息
    private Intent intent;
    @BindView(R.id.body_man_head)
    public ImageView body_man_head;//头部按下时
    @BindView(R.id.body_man_neck)
    public ImageView body_man_neck;//颈部按下时
    @BindView(R.id.body_man_chest)
    public ImageView body_man_chest;//胸部按下时
    @BindView(R.id.body_man_upper)
    public ImageView body_man_upper;//上肢部按下时
    @BindView(R.id.body_man_abdomen)
    public ImageView body_man_abdomen;//腹部按下时
    @BindView(R.id.body_man_genital)
    public ImageView body_man_genital;//生殖器按下时
    @BindView(R.id.body_man_lower)
    public ImageView body_man_lower;//生殖器按下时
    @BindView(R.id.body_man_back)
    public ImageView body_man_back;//背部按下时
    @BindView(R.id.body_man_butt)
    public ImageView body_man_butt;//臀部器按下时
    private boolean body_man_buttTransparent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_body_map_check);
        initToolBar();
        initView();
        ImageView body_img_mzanOrwoman= (ImageView) findViewById(R.id.body_img_manOrwoman);
        body_img_mzanOrwoman.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Bitmap bitmap = ((BitmapDrawable) (body_man_butt.getDrawable())).getBitmap();
                if(bitmap.getPixel((int)(event.getX()),((int)event.getY()))==0)
                {
                    Log.i("test", "透明区域"+(int)event.getX()+":"+(int)event.getY());
                    Log.i("test",bitmap.getPixel(278,273)+"");
                    body_man_buttTransparent=true;
                }
                else
                {
                    Log.i("test", "实体区域"+(int)event.getX()+":"+(int)event.getY());
                    body_man_buttTransparent=false;
                }
                return false;
            }
        });
    }

    /**
     * 初始化　ToolBar
     */
    private void initToolBar() {
        Toolbar tool = (Toolbar) findViewById(R.id.toolbar);
        tool.findViewById(R.id.toolbar_centerText).setVisibility(View.GONE);
        tool.findViewById(R.id.toolbar_rightText).setVisibility(View.GONE);
        TextView goBack = (TextView) tool.findViewById(R.id.toolbar_goback);
        goBack.setText("选择查询部位");
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BodyMapCheckActivity.this.finish();
            }
        });
    }

    /**
     * 初始化View
     */
    private void initView() {
        ButterKnife.bind(this);
    }

    /**
     * 单击监听事件
     */
    @OnClick({R.id.body_switch_manOrWoman, R.id.body_switch_frontOrback,
            R.id.body_check_all})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.body_switch_manOrWoman:
                showManOrWoman();
                break;
            case R.id.body_switch_frontOrback:
                showFrontOrBack();
                break;
            case R.id.body_check_all:
                //TODO
                intent = new Intent(BodyMapCheckActivity.this, SymptomChoiceActivity.class);
                intent.putExtra("all", Constants.ALL);
                startActivityForResult(intent, Constants.ALL);
                break;
            case R.id.body_man_head:
                Log.d("Fromsky","头");
                intent = new Intent(BodyMapCheckActivity.this, SymptomChoiceActivity.class);
                intent.putExtra("all", Constants.HEAD);
                startActivityForResult(intent, Constants.HEAD);
                break;
            case R.id.body_man_neck:
                Log.d("Fromsky","颈");
                intent = new Intent(BodyMapCheckActivity.this, SymptomChoiceActivity.class);
                intent.putExtra("all", Constants.NECK);
                startActivityForResult(intent, Constants.NECK);
                break;
            case R.id.body_man_chest:
                Log.d("Fromsky","胸");
                intent = new Intent(BodyMapCheckActivity.this, SymptomChoiceActivity.class);
                intent.putExtra("all", Constants.CHEST);
                startActivityForResult(intent, Constants.CHEST);
                break;
            case R.id.body_man_upper:
                Log.d("Fromsky","15");
                intent = new Intent(BodyMapCheckActivity.this, SymptomChoiceActivity.class);
                intent.putExtra("all", Constants.UPPER);
                startActivityForResult(intent, Constants.UPPER);
                break;
            case R.id.body_man_abdomen:
                Log.d("Fromsky","腹部");
                intent = new Intent(BodyMapCheckActivity.this, SymptomChoiceActivity.class);
                intent.putExtra("all", Constants.ABDOMEN);
                startActivityForResult(intent, Constants.ABDOMEN);
                break;
            case R.id.body_man_genital:
                Log.d("Fromsky","17");
                intent = new Intent(BodyMapCheckActivity.this, SymptomChoiceActivity.class);
                intent.putExtra("all", Constants.GENITALS);
                startActivityForResult(intent, Constants.GENITALS);
                break;
            case R.id.body_man_lower:
                Log.d("Fromsky","18");
                intent = new Intent(BodyMapCheckActivity.this, SymptomChoiceActivity.class);
                intent.putExtra("all", Constants.LOWER);
                startActivityForResult(intent, Constants.LOWER);
                break;
            case R.id.body_man_back:
                Log.d("Fromsky","19");
                 intent = new Intent(BodyMapCheckActivity.this, SymptomChoiceActivity.class);
                intent.putExtra("all", Constants.BACK);
                startActivityForResult(intent, Constants.BACK);
                break;
            case R.id.body_man_butt:
                Log.d("Fromsky","臀部");
//                intent = new Intent(BodyMapCheckActivity.this, SymptomChoiceActivity.class);
//                intent.putExtra("all", Constants.BUUT);
//                startActivityForResult(intent, Constants.BUUT);
                break;
            default:

        }
    }

    /**
     * 男女切换 处理逻辑
     */
    private void showManOrWoman() {
        if (mIsMan) {
            //如果已经是 man,切换到 woman
            if (mIsFront) {
                mShowManOrWoman.setImageResource(R.drawable.body_front_woman);
            } else {
                mShowManOrWoman.setImageResource(R.drawable.body_back_woman);
            }
            mSwitchManOrWoman.setImageResource(R.drawable.cb_female);
            mIsMan = false;
        } else {
            if (mIsFront) {
                mShowManOrWoman.setImageResource(R.drawable.body_front_man);
            } else {
                mShowManOrWoman.setImageResource(R.drawable.body_back_man);
            }
            mSwitchManOrWoman.setImageResource(R.drawable.cb_male);
            mIsMan = true;
        }
    }

    /**
     * 前后切换 处理逻辑
     */
    private void showFrontOrBack() {
        if (mIsFront) {
            //如果已经是 front,切换到 back
            if (mIsMan) {
                mShowManOrWoman.setImageResource(R.drawable.body_back_man);
            } else {
                mShowManOrWoman.setImageResource(R.drawable.body_back_woman);
            }
            mShowInfo.setImageResource(R.drawable.body_back_part_intro);
            mIsFront = false;
        } else {
            if (mIsMan) {
                mShowManOrWoman.setImageResource(R.drawable.body_front_man);
            } else {
                mShowManOrWoman.setImageResource(R.drawable.body_front_woman);
            }
            mShowInfo.setImageResource(R.drawable.body_front_part_intro);
            mIsFront = true;
        }
    }

}
