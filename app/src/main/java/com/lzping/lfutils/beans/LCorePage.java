package com.lzping.lfutils.beans;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.lzping.lfutils.tool.IOHelper;

/**
 * Created by user on 2017/1/10.
 * 页面实体
 */
public class LCorePage implements Parcelable {
    private String name;//唯一标识
    private String classpath; //用来反射创建对象
    private String data;//传入的数据 - 可以用来构建bundle


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.classpath);
        dest.writeString(this.data);
    }

    public LCorePage() {}

    public LCorePage(String name, String fullClazz, String jsonData) {
        this.name = name;
        this.classpath = fullClazz;
        this.data = jsonData;
    }

    protected LCorePage(Parcel in) {
        this.name = in.readString();
        this.classpath = in.readString();
        this.data = in.readString();
    }


    public String toInfo() {
        return "{"
                +
                "[name = " + name + "],[classpath = " + classpath + "],[data = " + data + "]"
                +
                "}";
    }

    //完整性
    public boolean isComplete() {
        boolean flag = true;
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(classpath)) {
            flag = false;
        }
        return flag;
    }
    //获取bundle 对象
    public Bundle buildBundle(){
        if (data == null || data.equals("")){
            return null;
        }
        String nData = IOHelper.checkData(data);
        Bundle b = null;
        try {
           b  = IOHelper.jsonString2Bundle(nData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }



    public static final Parcelable.Creator<LCorePage> CREATOR = new Parcelable.Creator<LCorePage>() {
        @Override
        public LCorePage createFromParcel(Parcel source) {
            return new LCorePage(source);
        }

        @Override
        public LCorePage[] newArray(int size) {
            return new LCorePage[size];
        }
    };


    //get set ---------------


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClasspath() {
        return classpath;
    }

    public void setClasspath(String classpath) {
        this.classpath = classpath;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
