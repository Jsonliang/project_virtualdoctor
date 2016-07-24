package com.liang.virtualdoctor.ui.customviews;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.liang.virtualdoctor.R;

/**
 * Created by Administrator on 2016/7/13 0013.
 */
public class CityChoiceLayout extends View {
    private Paint paint;  // 画笔
    private float x, y;
    private int mWith;//屏幕的宽
    private Context context;
    private Dialog mDialog  ;
    private TextView  mTv_FirstWorld ;
    private View mDialogLayout ;
    // 字母数组
    private String[] letter = {"#","A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O", "P", "Q", "R", "S","T","U","V","W","X","Y", "Z"};

    public CityChoiceLayout(Context context) {
        this(context, null, 0);
    }

    public CityChoiceLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CityChoiceLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        paint.setTextSize(20);
        paint.setStrokeWidth(1);
        paint.setAntiAlias(true);

        //获得屏幕的宽度
        WindowManager wm = (WindowManager)
                context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetris = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetris);
        mWith = outMetris.widthPixels;

        // 初始化 Dialog
        mDialogLayout = LayoutInflater.from(context).inflate(
                R.layout.dialog_layout,null);
        mTv_FirstWorld = (TextView) mDialogLayout.findViewById(R.id.dialog_firstWorld);
        mDialog = new Dialog(context,R.style.CityDialogTheme);
        mDialog.setContentView(mDialogLayout);
    }

    private void showDialog(){

        mDialog.show();
    }

    private void setText(String text){
        mTv_FirstWorld.setText(text + "");
    }

    private void dismess(){
        mDialog.dismiss();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //首先得控件宽度
        int width = getWidth();
        //得到x的坐标
        x = width / 2 - (paint.measureText("A") / 2);
        //得到y的坐标
        y = getHeight()  / letter.length;
        getView(canvas);
    }

    private void getView(Canvas canvas) {
        for (int i = 0; i < letter.length; i++) {
           canvas.drawText(letter[i], x, (i + 1) * y, paint);
        }
    }
    int current ;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float eventY = event.getY();
                current = (int) (eventY / y);
                //显示对话框
                showDialog();
                //设置文字

                if(current >= letter.length){ current = letter.length - 1 ; }
                if(current < 0){ current = 0 ;}
                setText(letter[current]);
                //回调
                if(listener != null){
                    listener.onLetter(letter[current]);
                }
                // 改变自已背景
                this.setBackgroundResource(R.drawable.citychoice_press);
                return true;
            case MotionEvent.ACTION_MOVE:
                current = (int) (event.getY() / y);
                //设置文字
                if(current >= letter.length){ current = letter.length - 1 ; }
                if(current < 0){ current = 0 ;}

                setText(letter[current]);
                //回调
                if(listener != null){
                    listener.onLetter(letter[current]);
                }
                break;
            case MotionEvent.ACTION_UP:
                this.setBackgroundResource(R.drawable.citychoice_unpress);
                //取消对话框
                dismess();
                return true;
        }
        return true;
    }
    private OnTouchListener listener;

    public void SetOnTouchListener(OnTouchListener listener) {
        this.listener = listener;
    }

    public interface OnTouchListener {
        public void onLetter(String str);
    }
}
