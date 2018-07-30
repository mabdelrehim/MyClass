package com.example.android.myclass.data;

/**
 * Created by lenovo on 7/25/2018.
 */

public class Student {

    private String mName;
    private Boolean mIsChecked = false;

    public Student(String mName) {
        this.mName = mName;
        // this.mIsChecked = mIsChecked;
    }

    public String getmName() {
        return mName;
    }

    public void setmIsChecked(Boolean mIsChecked) {
        this.mIsChecked = mIsChecked;
    }

    public Boolean getmIsChecked() {
        return mIsChecked;
    }
}
