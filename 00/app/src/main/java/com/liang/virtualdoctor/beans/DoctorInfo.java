package com.liang.virtualdoctor.beans;

/**
 * Created by Administrator on 2016/7/9 0009.
 * 医生信息
 */
public class DoctorInfo {
    private String depart_name;//: "眼科",
    private String doct_id;//": "37333147",
    private String doct_name;//": "刘丽君",
    private String goodat;//": "擅长眼科的常见病多发病的诊治：眼睑疾病如麦粒肿、霰粒肿，干眼症，结膜炎、角膜病、葡萄膜病、晶状体疾病如白内障、青光眼、眼外伤、眼眶疾病、眼底疾病以及屈光不正、斜视弱视的诊治，具有丰富的临床经验。",
    private String hospital;//": "沈阳医学院附属第二医院",
    private String month_price;//": "100.00",
    private String more_url;//": "http://3g.club.xywy.com/familyDoctor/list/subject?xywyapp=1&id=294",
    private String photo;//": "http://i0.wkimg.com/source/74/37333147.jpg",
    private String service_num;//": "1939",
    private String service_score;//": "4.70",
    private String status;//": "1",
    private String title;//": "主治医师",
    private String week_price;//": "50.00


    public String getDepart_name() {
        return depart_name;
    }

    public void setDepart_name(String depart_name) {
        this.depart_name = depart_name;
    }

    public String getDoct_id() {
        return doct_id;
    }

    public void setDoct_id(String doct_id) {
        this.doct_id = doct_id;
    }

    public String getDoct_name() {
        return doct_name;
    }

    public void setDoct_name(String doct_name) {
        this.doct_name = doct_name;
    }

    public String getGoodat() {
        return goodat;
    }

    public void setGoodat(String goodat) {
        this.goodat = goodat;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getMonth_price() {
        return month_price;
    }

    public void setMonth_price(String month_price) {
        this.month_price = month_price;
    }

    public String getMore_url() {
        return more_url;
    }

    public void setMore_url(String more_url) {
        this.more_url = more_url;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getService_num() {
        return service_num;
    }

    public void setService_num(String service_num) {
        this.service_num = service_num;
    }

    public String getService_score() {
        return service_score;
    }

    public void setService_score(String service_score) {
        this.service_score = service_score;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWeek_price() {
        return week_price;
    }

    public void setWeek_price(String week_price) {
        this.week_price = week_price;
    }

    @Override
    public String toString() {
        return "DoctorInfo{" +
                "depart_name='" + depart_name + '\'' +
                ", doct_id='" + doct_id + '\'' +
                ", doct_name='" + doct_name + '\'' +
                ", goodat='" + goodat + '\'' +
                ", hospital='" + hospital + '\'' +
                ", month_price='" + month_price + '\'' +
                ", more_url='" + more_url + '\'' +
                ", headerImageurl='" + photo + '\'' +
                ", service_num='" + service_num + '\'' +
                ", service_score='" + service_score + '\'' +
                ", status='" + status + '\'' +
                ", title='" + title + '\'' +
                ", week_price='" + week_price + '\'' +
                '}';
    }


}
