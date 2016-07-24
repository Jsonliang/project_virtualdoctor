package com.liang.virtualdoctor.ui.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liang.virtualdoctor.R;
import com.liang.virtualdoctor.utils.UnitConvertUtils;

/**
 * Created by Administrator on 2016/7/10 0010.
 */
public class Check_Symptom_Scroll extends LinearLayout {

    private int[]  images = new int[]{R.drawable.man,R.drawable.woman,R.drawable.summer,
            R.drawable.oldman,R.drawable.child};
    private String[] strings = new String[]{"男人","女人","夏季","老人","儿童"};
    private boolean isFirst = true ;

    private int mMargin  = 15 ; // 左右 margin各 15
    private int mPaddding = 10 ;
    private Context mContext ;
    private int mWrapperWidth ; //屏幕的宽度
    private int mWidth ; // 确定HorizontalScrollView 显示的宽度

    private int mChildWidth ; //每个子控件的宽度
    private LinearLayout  mLayout ; // HorizontalScrollView控件的总布局
    private View mChildLayout; //子控件,布局
    private ImageView mImage ;

    private float mDownX ;
    private float event_x ;
    private int distance ;

    private ImageView image = null ;
    private TextView text = null ;
    private LinearLayout.LayoutParams params ;
    public Check_Symptom_Scroll(Context context) {
        this(context, null, 0);
    }

    public Check_Symptom_Scroll(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Check_Symptom_Scroll(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context ;
        initView();
    }

    private void initView() {
       WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics  dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        mWrapperWidth =  dm.widthPixels ; //得到屏幕的宽

        //确定确定HorizontalScrollView
        mWidth = mWrapperWidth - (2*UnitConvertUtils.dip2Px(mContext, mMargin));

        //确定到显示子控件的个数和单个子控件的宽度
        mChildWidth = mWidth/5;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(isFirst){
            mLayout = (LinearLayout) this.getChildAt(0);
            mImage = (ImageView) this.getChildAt(1);
            createChildLinearLayout();//添加子控件
            isFirst = false ;
        }
    }
    
    /**
     * 添加子控件布局
     */

    private void createChildLinearLayout(){
        params = new LinearLayout.LayoutParams(
                mChildWidth,(mChildWidth + UnitConvertUtils.dip2Px(mContext,20)));
        mPaddding = UnitConvertUtils.dip2Px(mContext,10);
        for(int i = 0 ;i< 5 ;i++){
            //添加到 mLayout
            mLayout.addView(addChildView(i));
        }

        LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(mChildWidth,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        imgParams.width = mChildWidth ;
        imgParams.leftMargin = 2 * mChildWidth ;
        mImage.setLayoutParams(imgParams);
        imgParam = (LayoutParams) mImage.getLayoutParams();
        // 默认中间夏季 放大
        cancelPadding(2);

    }
    LinearLayout.LayoutParams imgParam ;
    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch(ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getX() - UnitConvertUtils.dip2Px(mContext,20);
                distance =(int) (mDownX / mChildWidth );
                imgParam = (LayoutParams) mImage.getLayoutParams();
                imgParam.leftMargin = (mChildWidth * distance);
                mImage.setLayoutParams(imgParam);
                cancelPadding(distance);
                return true ;
            case MotionEvent.ACTION_MOVE:
                mDownX = ev.getX() - UnitConvertUtils.dip2Px(mContext,20);
                distance =(int) (mDownX / mChildWidth );
                imgParam = (LayoutParams) mImage.getLayoutParams();
                imgParam.leftMargin = (mChildWidth * distance);
                mImage.setLayoutParams(imgParam);
                cancelPadding(distance);
                return true ;
            case MotionEvent.ACTION_UP:
                mDownX = ev.getX() - UnitConvertUtils.dip2Px(mContext,20);
                distance =(int) (mDownX / mChildWidth );
                imgParam = (LayoutParams) mImage.getLayoutParams();
                imgParam.leftMargin = (mChildWidth * distance);
                mImage.setLayoutParams(imgParam);
                cancelPadding(distance);
                l.scrollCallBack(distance);
                return true;
        }

        return super.onTouchEvent(ev);
    }

    private View addChildView(int index){
        mChildLayout = LayoutInflater.from(mContext).inflate(R.layout.horizontalscrollview_item,
                mLayout,false);
        mChildLayout.setLayoutParams(params);
        mChildLayout.setPadding(mPaddding,mPaddding,mPaddding,mPaddding);

        image = (ImageView) mChildLayout.findViewById(R.id.scrollview_image);
        image.setImageResource(images[index]);
        text = (TextView) mChildLayout.findViewById(R.id.scrollview_text);
        text.setText(strings[index]);

        return mChildLayout ;
    }


    private void cancelPadding(int index){
       for(int i=0 ;i<mLayout.getChildCount() ;i++){
           if(i == index){
               mLayout.getChildAt(i).setPadding(mPaddding/2,mPaddding/2,mPaddding/2,mPaddding/2);
           }else {
               mLayout.getChildAt(i).setPadding(mPaddding,mPaddding,mPaddding,mPaddding);
           }
       }
    }

    private ScrollCallBackListener l;
    public void setScrollCallBackListener(ScrollCallBackListener l){
       this.l = l ;
    }
    public interface  ScrollCallBackListener{
        public void scrollCallBack(int index);
    }

}
