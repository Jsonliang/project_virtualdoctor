package com.liang.virtualdoctor.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liang.virtualdoctor.R;
import com.liang.virtualdoctor.utils.UnitConvertUtils;

import java.util.List;


/**
 * Created by Administrator on 2016/7/14 0014.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private List<String>  data ;

    public RecyclerAdapter(List<String>  data) {
      this.data = data ;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.receyleview_item,parent,false);
        GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        params.rightMargin = UnitConvertUtils.dip2Px(parent.getContext(),20);
        view.setLayoutParams(params);
       // MyLinearLayout layout = (MyLinearLayout) view.findViewById(R.id.recycle_layout);

       /* layout.setClick(new OnPositionClick());*/

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.scrollview_text.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * 自定义ViewHolder
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView scrollview_text ;
        public MyViewHolder(View itemView) {
            super(itemView);
            scrollview_text = (TextView) itemView.findViewById(R.id.symptom_info);
        }
    }


    //定义单击回调接口对象
    private CallBack callBack;

    public void setCallBack(CallBack call) {
        this.callBack = call;
    }

    //定义单击回调接口
    public interface CallBack {
        /**
         * 当前点击的位置
         *
         * @param position
         */
        void onClick(int position);
    }

    public static class OnPositionClick implements  View.OnClickListener{
        private int position =0 ;
        private RecyclerAdapter.CallBack callBack ;
        @Override
        public void onClick(View v) {
            callBack.onClick(position);
        }
    }
}
