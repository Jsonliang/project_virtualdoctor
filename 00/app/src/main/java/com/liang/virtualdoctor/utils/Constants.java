package com.liang.virtualdoctor.utils;

/**
 * Created by Administrator on 2016/7/10 0010.
 */
public class Constants {

    public static int HEAD = 0 ; // 头
    public static int NECK = 1 ; // 颈部
    public static int UPPER = 2 ; // 上肢
    public static int CHEST = 3 ; // 胸部
    public static int ABDOMEN = 4 ; // 腹部
    public static int GENITALS = 5 ; // 生殖器
    public static int LOWER = 6 ; //下肢

    public static int BACK = 7 ; // 背部
    public static int BUUT = 8 ; //臀部

    public static int ALL = 9 ;// 全部
    public static String WEATHERPATH =  "https://api.thinkpage.cn/v3/weather/daily.json?"
    +"key=h3h2bsufdpl600e6&language=zh-Hans&unit=c&start=0&days=3&location=";

    /**
     * defult guangzhou weather
     *
     */
    public static final String DEFAULTCITY="广州";
    public static String DEFAULT_WEATHER ="https://api.thinkpage.cn/v3/weather/daily.json?"
            +"key=h3h2bsufdpl600e6&language=zh-Hans&unit=c&start=0&days=3&location="+DEFAULTCITY;



    /**
     * 手术资讯url
     */
    public static final String MEDICAL_SUEGREY_URL=
            "http://www.tngou.net/api/operation/list?row=20&page=";

    /**
     * 病状资讯url
     */
    public static final String MEDICAL_MORBIDITY_URL=
            "http://www.tngou.net/api/symptom/list?row=20&page=";

    /**
     * 医生列表信息url
     */
    public static final String DOCTOR_LIST ="http://api.wws.xywy.com/" +
            "api.php/xywyapp/familydoctor/index?sign=ff9fd2dc547468977fb005fc47b71ab8" +
            "&pro=xywyf32l24WmcqquqqTdhXaEng&api=939&os=android&source=xywyapp&token=" +
            "&version=1.1";

    /**
     * doctor personal Info
     * need doctor id number
     */
    public static final String DOCTOR_INFO="http://3g.club.xywy.com/" +
            "doctor_center.php?app=1&docid=";

    /**
     * Query by keyword(doctor or sickness)
     * url  need append keyword in the last
     */
    public static final String QUER_BY_KEYWORD="http://3g.club.xywy.com/" +
            "familyDoctor/list/so?keyword=";

    /**this url can get the info by input keyword what is you want
     * need keyword
     */
    public static final String QUER_KEYWORDINFO="http://m.so.xywy.com/" +
            "comse.php?from=app&keyword=";

    /**
     * this url can get (sickness's)Subject
     */
    public static final String QUER_SUBJECT="http://3g.club.xywy.com/familyDoctor/keshi?" +
            "ajax=1&xywyapp=1&callback=returnMsg&channel=ExpertSpeciality&id=1&iscall=1" +
            "&_=1468317757820";

    /**
     * this url get subject item by id
     * need id(subject's id)
     */
    public static final String QUER_SUBJECTITEM ="http://3g.club.xywy.com/familyDoctor" +
            "/jib?ajax=1&xywyapp=1&callback=returnMsg&channel=ExpertSpeciality&iscall" +
            "=1&_=1468169971670&departid=";

    public static final String CITYPATH = "http://fjapi.chufadian.com/City/V1602GetCityList.aspx";

}
