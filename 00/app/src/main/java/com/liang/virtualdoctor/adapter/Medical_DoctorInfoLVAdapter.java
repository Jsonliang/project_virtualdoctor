package com.liang.virtualdoctor.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.liang.virtualdoctor.R;
import com.liang.virtualdoctor.beans.DoctorInfo;
import com.liang.virtualdoctor.fragments.MedicalNews_Fragment;
import com.liang.virtualdoctor.ui.activitys.WebViewActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/10.
 * 医生的ListView 的适配器
 */
public class Medical_DoctorInfoLVAdapter extends BaseAdapter{
    private List<DoctorInfo> list;
    private Context context;
    public Medical_DoctorInfoLVAdapter(List list,Context context) {
        this.list = list;
        this.context=context;

    }
    //lagyout id
    private int layout=R.layout.fragment_medical_lsitem_layout;
    //set the value in widget
    private void onBindViewHolder(ViewHolder holder, int position) {
        final DoctorInfo info = list.get(position);
        holder.doctorImage.setImageURI(info.getPhoto());
        holder.doctorTitle.setText(info.getTitle());
        holder.doctorName.setText(info.getDoct_name());
        holder.depart_name.setText(info.getDepart_name());
         holder.dotorGoodat.setText(info.getGoodat() );
        holder.serviceScore.setStar(Float.parseFloat(info.getService_score()));
        holder.weekPrice.setText("包周: "+info.getWeek_price());
        holder.monthPrice.setText("包月: "+info.getMonth_price());
        holder.serviceNum.setText("已签约"+info.getService_num()+"人");
        // download more info by tag when onclike
        holder.more.setTag(position);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public DoctorInfo getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh=null;
        if(convertView==null){
           convertView= LayoutInflater.from(parent.getContext()).inflate(layout,null);
            vh=new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh= (ViewHolder) convertView.getTag();
        }
        onBindViewHolder(vh,position);
        return convertView;
    }

    public class ViewHolder {
        @BindView(R.id.frag_medical_doctor_depart_name)
        public TextView depart_name;//科室名称
        @BindView(R.id.frag_medical_doctor_more)
        public TextView more;//更多..
        @BindView(R.id.frag_medical_lsitem_headIamge)
        public SimpleDraweeView doctorImage;//医生头像
        @BindView(R.id.frag_medical_doctor_name)
        public TextView doctorName;//医生名字
        @BindView(R.id.frag_medical_doctor_title)
        public TextView doctorTitle;//医生职务
        @BindView(R.id.frag_medical_doctor_goodat)
        public TextView dotorGoodat;
        //已签约多少人
        @BindView(R.id.frag_medical_doctor_service_num)
        public TextView serviceNum;
        @BindView(R.id.frag_medica_doctor_service_score)
        public com.hedgehog.ratingbar.RatingBar serviceScore;
        @BindView(R.id.frag_medica_doctor_weekprice)
        public TextView weekPrice;
        @BindView(R.id.frag_medical_doctor_monthprice)
        public TextView monthPrice;
        public ViewHolder(final View convertView) {
            ButterKnife.bind(ViewHolder.this,convertView);
            more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   int id= (int) v.getTag();
                   DoctorInfo doctor= list.get(id);
                   String url= doctor.getMore_url();
                    Intent intent=new Intent(context.getApplicationContext(),WebViewActivity.class);
                    intent.putExtra(WebViewActivity.URLKEYWORD,url);
                    intent.putExtra(WebViewActivity.TOOLBARTITLE,"更多信息");
                    context.startActivity(intent);
                }
            });


        }
    }
}
