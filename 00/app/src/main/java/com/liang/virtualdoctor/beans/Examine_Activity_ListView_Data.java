package com.liang.virtualdoctor.beans;

/**
 * Created by from -sky on 2016/7/14.
 */
public class Examine_Activity_ListView_Data {
    private String name;
    private   String normalValue;
    private   String clinical;
    private   String precautions;

    public Examine_Activity_ListView_Data(String clinical, String name, String normalValue, String precautions) {
        this.clinical = clinical;
        this.name = name;
        this.normalValue = normalValue;
        this.precautions = precautions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrecautions() {
        return precautions;
    }

    public void setPrecautions(String precautions) {
        this.precautions = precautions;
    }

    public String getClinical() {
        return clinical;
    }

    public void setClinical(String clinical) {
        this.clinical = clinical;
    }

    public String getNormalValue() {
        return normalValue;
    }

    public void setNormalValue(String normalValue) {
        this.normalValue = normalValue;
    }
}
