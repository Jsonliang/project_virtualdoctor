package com.liang.virtualdoctor.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2016/7/12.
 */
public class Subject implements Parcelable {

    /**
     * 0 : 1
     * 1 : 内科
     * id : 272
     * name : 内科
     */
        private int value0;
        private String value1;
        private int id;
        private String name;

    protected Subject(Parcel in) {
        value0 = in.readInt();
        value1 = in.readString();
        id = in.readInt();
        name = in.readString();
    }
    public Subject(){

    }

    public static final Creator<Subject> CREATOR = new Creator<Subject>() {
        @Override
        public Subject createFromParcel(Parcel in) {
            return new Subject(in);
        }

        @Override
        public Subject[] newArray(int size) {
            return new Subject[size];
        }
    };

    public int getValue0() {
            return value0;
        }
        public void setValue0(int value0) {
            this.value0 = value0;
        }

        public String getValue1() {
            return value1;
        }

        public void setValue1(String value1) {
            this.value1 = value1;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    @Override
    public String toString() {
        return "Subject{" +
                "value0=" + value0 +
                ", value1='" + value1 + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(value0);
        dest.writeString(value1);
        dest.writeInt(id);
        dest.writeString(name);
    }
}
