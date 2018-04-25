package com.zzy.smartweight;

import java.util.ArrayList;

public class ReferChildInfo{
    private int id;
    private int cataId;
    private int resId;
    private int resStr;
    private String name;
    private int height;
    private int width;
    private boolean check;
    public ReferChildInfo()
    {
        name = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCataId() {
        return cataId;
    }

    public void setCataId(int cataId) {
        this.cataId = cataId;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public int getResStr() {
        return resStr;
    }

    public void setResStr(int resStr) {
        this.resStr = resStr;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name==null)
        {
            name="";
        }
        this.name = name;
    }
}
