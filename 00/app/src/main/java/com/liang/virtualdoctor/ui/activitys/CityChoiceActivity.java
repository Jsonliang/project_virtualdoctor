package com.liang.virtualdoctor.ui.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.liang.virtualdoctor.R;
import com.liang.virtualdoctor.adapter.CitysAdapter;
import com.liang.virtualdoctor.fragments.Nearly_Fragment;
import com.liang.virtualdoctor.ui.customviews.CityChoiceLayout;
import com.liang.virtualdoctor.utils.Constants;
import com.liang.virtualdoctor.utils.HttpHelper;
import com.liang.virtualdoctor.utils.JsonParserUtils;
import com.liang.virtualdoctor.utils.PinYinUtils;

import java.text.CollationKey;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CityChoiceActivity extends AppCompatActivity {
    /**
     * result code for return city name
     * by jack
     */
    public static final int CITYNAME_RETURN_CODE = 300;

    public ListView mListCitys;
    public CityChoiceLayout mView;
    public static final String CITYNAMEKEY="cityName";
    private HttpHelper helper;
    private List<String> citys = new ArrayList<>();
    private List<String> firsWorlds  = new ArrayList<>();
    private CitysAdapter adapter;
    /*
    * declaration return city name
    * by jack
    * */
    private String cityName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_ciyt_choice);
        initToolBar();
        initView();

    }

    private void initToolBar() {
        Toolbar tool = (Toolbar) findViewById(R.id.toolbar);
        tool.findViewById(R.id.toolbar_centerText).setVisibility(View.GONE);
        tool.findViewById(R.id.toolbar_rightText).setVisibility(View.GONE);
        TextView goBack = (TextView) tool.findViewById(R.id.toolbar_goback);
        goBack.setText("城市切换");
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CityChoiceActivity.this.finish();
            }
        });
    }

    /**
     *
     */
    public void returnCityName() {
        int type = getIntent().getIntExtra("type",0);
        Intent data=new Intent();
        if(type == Nearly_Fragment.NEARLY_REQUESTCODE){
            data.setClass(CityChoiceActivity.this,Nearly_Fragment.class);
            data.putExtra("nearly_city", cityName);
            setResult(Activity.RESULT_OK,data);
        }else {
            data.setClass(CityChoiceActivity.this, WeatherActivity.class);
            data.putExtra(CITYNAMEKEY, cityName);
            setResult(CITYNAME_RETURN_CODE, data);
        }
        CityChoiceActivity.this.finish();
    }

    private void initView() {
        mListCitys = (ListView) findViewById(R.id.gps_cityName_list);
        mView = (CityChoiceLayout) findViewById(R.id.city_citychoice_list);
        adapter = new CitysAdapter(citys,firsWorlds);
        mListCitys.setAdapter(adapter);
        mListCitys.setHeaderDividersEnabled(true);
        helper = HttpHelper.getInstance();
        helper.requestByGet(Constants.CITYPATH, new HttpHelper.StringCallBack() {
            @Override
            public void onFailure(Exception e) {
            }

            @Override
            public void onResult(Object string) {
                List<String> lists = JsonParserUtils.getCitysList((String) string);
                if (lists != null && lists.size() != 0) {
                    citys.clear();
                    firsWorlds.clear();
                    listSort(lists);//按中文首字母排序
                    for (int i = 0; i < lists.size(); i++) {
                        firsWorlds.add(PinYinUtils.getPinYinHead(lists.get(i)));
                    }
                    citys.addAll(lists);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        //设置回调监听
        mView.SetOnTouchListener(new CityChoiceLayout.OnTouchListener() {
            @Override
            public void onLetter(String str) {
                if(firsWorlds.contains(str)){
                    final int index = firsWorlds.indexOf(str);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mListCitys.requestFocus();
                            mListCitys.setItemChecked(index, true);
                            mListCitys.setSelection(index);
                            mListCitys.smoothScrollToPosition(index);
                        }
                    });
                }
            }
        });

        /*
        * when choice the item reference item's text
        * by jack
        * */
        mListCitys.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*get city name
                * */
                cityName= citys.get(position);
                /*
                    when click goback, go back to weather activity and return city name
                    by jack
                 */
                returnCityName();
            }
        });
    }

    public void listSort(List<String>  lists) {
        // 中文排序
        MyArrayComparator comparator = new MyArrayComparator();
        Collections.sort(lists, comparator);
    }

    public class MyArrayComparator implements Comparator {
        //关于Collator。
        private Collator collator = Collator.getInstance();//点击查看中文api详解

        public MyArrayComparator() {
        }

        @Override
        public int compare(Object lhs, Object rhs) {
            //把字符串转换为一系列比特，它们可以以比特形式与 CollationKeys 相比较
            CollationKey key1=collator.getCollationKey(lhs.toString());
            //要想不区分大小写进行比较用o1.toString().toLowerCase()
            CollationKey key2=collator.getCollationKey(rhs.toString());

            return key1.compareTo(key2);
            //返回的分别为1,0,-1 分别代表大于，等于，小于。要想按照字母降序排序的话 加个“-”号
        }
    }
}
