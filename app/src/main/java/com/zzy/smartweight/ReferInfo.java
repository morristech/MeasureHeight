package com.zzy.smartweight;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ReferInfo {

    public static final int REFER_ID_CARD = 0;
    public static final int REFER_ID_CASH = 1;
    public static final int REFER_ID_PHONE = 2;
    public static final int REFER_ID_OTHER =3;

    private int id;
    private int resId;
    private int resStr;
    private boolean check;
    public ArrayList<ReferChildInfo> lsChild;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public int getResStr() {
        return resStr;
    }

    public void setResStr(int resStr) {
        this.resStr = resStr;
    }
}
