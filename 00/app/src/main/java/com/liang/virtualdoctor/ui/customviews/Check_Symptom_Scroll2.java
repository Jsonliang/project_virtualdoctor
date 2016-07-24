package com.liang.virtualdoctor.ui.customviews;

import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liang.virtualdoctor.R;
import com.liang.virtualdoctor.beans.Check_Symptom_Scroll2_Data;
import com.liang.virtualdoctor.utils.UnitConvertUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/7/10 0010.
 */
public class Check_Symptom_Scroll2 extends HorizontalScrollView {
    //数据源
    List<Check_Symptom_Scroll2_Data> list;
    //临时数据源
    int[] iamges=new int[]{R.drawable.examine_a1,R.drawable.examine_a2,R.drawable.examine_a3,R.drawable.examine_a4,R.drawable.examine_a5,R.drawable.examine_a1,R.drawable.examine_a2,R.drawable.examine_a3,R.drawable.examine_a4,R.drawable.examine_a5};
   String [] name=new String[]{"浆膜腔积液","指测血糖","过敏原检查","呕吐物检查","呼气检查","血气分析","阴道分泌物","大便检查","尿液检查","血液检查"};
    String[] num=new String[]{"3项指标","1项指标","2项指标","1项指标","4项指标","4项指标","2项指标","13项指标","20项指标","119项指标"};
    private boolean isFirst = true;
    private int currentItem = 1;
    private int previousItem = 1;

    private int mMargin = 15; // 左右 margin各 15
    private int mPaddding = 15;
    private Context mContext;
    private int mWrapperWidth; //屏幕的宽度
    private int mWidth; // 确定HorizontalScrollView 显示的宽度

    private int mChildWidth; //每个子控件的宽度
    private LinearLayout Layout; // HorizontalScrollView控件的总布局
    private LinearLayout ChildLayout; //子控件,布局

    public Check_Symptom_Scroll2(Context context) {
        this(context, null, 0);
    }

    public Check_Symptom_Scroll2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Check_Symptom_Scroll2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        mWrapperWidth = dm.widthPixels; //得到屏幕的宽

        //确定确定HorizontalScrollView
        mWidth = mWrapperWidth -2* UnitConvertUtils.dip2Px(mContext, mMargin);

        //确定到显示子控件的个数和单个子控件的宽度
        mChildWidth = mWidth / 3;


    }

    /**
     * 滚动事件
     *
     * @param l    水平位置坐标，就是x
     * @param t    垂直位置坐标，就是Y
     * @param oldl
     * @param oldt
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {

        if (oldl > l) {
            Log.i("TAG", "onScrollChanged:  向右移动， x --> " + l);
        } else {
            Log.i("TAG", oldl + "    onScrollChanged:  向右移动 , x --> " + l);
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }

    /**
     * 触摸离开屏幕时  监听事件
     *
     * @param ev
     * @return
     */
    private int lastX = 0;
    private int firstX = 0;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 1) {

                if (lastX == getScrollX()) {
                    handleStop(this);
                } else {
                    handler.sendMessageDelayed(handler.obtainMessage(1), 5);
                    lastX = getScrollX();
                }
            }
        }
    };

    private void handleStop(Object view) {
        //向右滑动
        int m = (lastX + UnitConvertUtils.dip2Px(mContext, 15)) / mChildWidth;
        int x = (lastX + UnitConvertUtils.dip2Px(mContext, 15)) % mChildWidth;
        previousItem=currentItem;
        Log.d("fromsky",mChildWidth+"");
        if (lastX - firstX > 0) {
            if (x > mChildWidth/2) {
                currentItem = m + 2;
                smoothScrollTo((m + 1) * mChildWidth, 0);
            } else {
                currentItem = m + 1;
                smoothScrollTo((m) * mChildWidth, 0);
            }


        } else {

            if (x > mChildWidth/2 || x < 0) {
                currentItem = m + 1;
                smoothScrollTo((m) * mChildWidth, 0);
            } else {
                currentItem = m + 2;
                smoothScrollTo((m + 1) * mChildWidth, 0);
            }
        }

        if (previousItem!=currentItem)
        {
            mMinterface.changeListView(currentItem);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                firstX = getScrollX();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if (firstX != getScrollX())
                    handler.sendMessageDelayed(handler.obtainMessage(1), mWidth);

                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
//        super.setOnClickListener(l);
//        //向右滑动
        Log.d("fromsky",111+"");
        int m = (lastX - mChildWidth + UnitConvertUtils.dip2Px(mContext, 15)) / 230;
        if (m!=0||m!=list.size()-1)
        smoothScrollTo(30+m*230,0);
    }

    @Override
    public boolean isSmoothScrollingEnabled() {
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (isFirst) {
            Layout = (LinearLayout) findViewById(R.id.activity_examine_ll);
            createChildLinearLayout();//添加子控件
            smoothScrollTo(mChildWidth, 0);
            isFirst = false;
        }
        smoothScrollTo(mChildWidth, 0);
    }

    /**
     * 添加子控件布局
     */
//    private void createChildLinearLayout() {
//        ImageView image = null;
//        TextView text = null;
//        //增加7 个控件
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                mChildWidth, (mChildWidth + UnitConvertUtils.dip2Px(mContext, 10)));
//        mPaddding = UnitConvertUtils.dip2Px(mContext, 10);
//            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
//                    mChildWidth - UnitConvertUtils.dip2Px(mContext, 10), (mChildWidth - UnitConvertUtils.dip2Px(mContext, 10)));
//        for (int i = 0; i < 7; i++) {
//            // 控制每个子控件布局是 宽和高 (mChildWidth,mChildWidth+10dp)
//            //对父容器的设置
//            mChildLayout = LayoutInflater.from(mContext).inflate(R.layout.horizontalscrollview_item_2,
//                    mLayout, false);
//            mChildLayout.setLayoutParams(params);
//            mChildLayout.setPadding(mPaddding, mPaddding, mPaddding, mPaddding);
//
//            if (i == 0) {
//                image = (ImageView) mChildLayout.findViewById(R.id.scrollview_image_2);
//                image.setImageResource(images[images.length - 1]);
//                text = (TextView) mChildLayout.findViewById(R.id.scrollview_text_2);
//                text.setText(strings[strings.length - 1] + "");
//            } else if (i == 6) {
//                image = (ImageView) mChildLayout.findViewById(R.id.scrollview_image_2);
//                image.setImageResource(images[0]);
//                text = (TextView) mChildLayout.findViewById(R.id.scrollview_text_2);
//                text.setText(strings[0] + "");
//            } else {
//                image = (ImageView) mChildLayout.findViewById(R.id.scrollview_image_2);
//                image.setImageResource(images[i - 1]);
//                text = (TextView) mChildLayout.findViewById(R.id.scrollview_text_2);
//                text.setText(strings[i - 1] + "");
//            }
//            image.setLayoutParams(params1);
//            //添加到 mLayout
//            mLayout.addView(mChildLayout);
//        }
//    }
    private void createChildLinearLayout() {
        //容器的宽高
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                mChildWidth, (mChildWidth + UnitConvertUtils.dip2Px(mContext,5)));
        Layout = (LinearLayout) findViewById(R.id.activity_examine_ll);
        View v = new ImageView(mContext);
        v.setLayoutParams(new ViewGroup.LayoutParams(UnitConvertUtils.dip2Px(mContext, 10), mChildWidth + UnitConvertUtils.dip2Px(mContext, 0)));
        Layout.addView(v);
        ImageView image = null;
        TextView text2 = null;
        TextView text3 = null;
        if (list==null||list.size()==0){
            for (int i = 0; i < name.length; i++) {
                ChildLayout = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.horizontalscrollview_item_2, null);
                ChildLayout.setLayoutParams(params);
                ChildLayout.setPadding(mPaddding, mPaddding, mPaddding, mPaddding);
                image = (ImageView) ChildLayout.findViewById(R.id.scrollview_image_2);
                text2 = (TextView) ChildLayout.findViewById(R.id.scrollview_text_2);
                text3 = (TextView) ChildLayout.findViewById(R.id.scrollview_text_3);
                image.setImageResource(iamges[i]);
                text2.setText(name[i]);
                text3.setText(num[i]);
                Layout.addView(ChildLayout);
            }
            v = new ImageView(mContext);
            v.setLayoutParams(new ViewGroup.LayoutParams(UnitConvertUtils.dip2Px(mContext, 10), mChildWidth + UnitConvertUtils.dip2Px(mContext, 30)));
            Layout.addView(v);
            return;
        }
                for (int i = 0; i < list.size(); i++) {
            ChildLayout = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.horizontalscrollview_item_2, null);
            ChildLayout.setLayoutParams(params);
            ChildLayout.setPadding(mPaddding, mPaddding, mPaddding, mPaddding);
            image = (ImageView) ChildLayout.findViewById(R.id.scrollview_image_2);
            text2 = (TextView) ChildLayout.findViewById(R.id.scrollview_text_2);
            text3 = (TextView) ChildLayout.findViewById(R.id.scrollview_text_3);
            image.setImageBitmap(list.get(i).getBitmap());
            text2.setText(list.get(i).getTitle() + i);
            text3.setText(list.get(i).getIndex());
            Layout.addView(ChildLayout);
        }
        v = new ImageView(mContext);
        v.setLayoutParams(new ViewGroup.LayoutParams(UnitConvertUtils.dip2Px(mContext, 10), mChildWidth + UnitConvertUtils.dip2Px(mContext, 30)));
        Layout.addView(v);

    }

    @Override
    public void fling(int velocityY) {
        super.fling(velocityY / 3);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public int getCurrentItem() {
        return currentItem;
    }

    public void setMiinterface(Iinterface miinterface) {
        this.mMinterface = miinterface;
    }

    private Iinterface mMinterface;
    public interface  Iinterface{
        public abstract  void  changeListView(int i);
    }
    public void setData(List<Check_Symptom_Scroll2_Data> list){
        this.list=list;
    }
}
