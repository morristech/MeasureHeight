package com.zzy.smartweight;

import android.app.Application;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;

import com.google.android.gms.ads.MobileAds;

public class SmartWeightApp extends Application {

    private static SmartWeightApp mInstance;
    private ReferChildInfo currRefer;

    @Override
    public void onCreate()
    {
        super.onCreate();
        mInstance = this;
    }

    public static SmartWeightApp getInstance()
    {
        return mInstance;
    }

    public ReferChildInfo getCurrRefer()
    {
        if(currRefer!=null)
        {
            return currRefer;
        }

        SharedPreferences sp = getSharedPreferences("curr_refer", Context.MODE_PRIVATE);
        if(sp.contains("id")) {
            currRefer = new ReferChildInfo();
            currRefer.setId(sp.getInt("id", 0));
            currRefer.setCataId(sp.getInt("cataId", 0));
            currRefer.setResId(sp.getInt("resId", 0));
            currRefer.setResStr(sp.getInt("resStr", 0));
            currRefer.setWidth(sp.getInt("weight", 0));
            currRefer.setHeight(sp.getInt("height", 0));
            currRefer.setCheck(true);
            currRefer.setName(sp.getString("name",""));
        }else{
            currRefer = null;
        }
        return currRefer;
    }

    public void saveCurrRefer(ReferChildInfo referChildInfo)
    {
        referChildInfo.setCheck(true);
        SharedPreferences sp = getSharedPreferences("curr_refer", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("id", referChildInfo.getId());
        editor.putInt("cataId", referChildInfo.getCataId());
        editor.putInt("resId", referChildInfo.getResId());
        editor.putInt("resStr", referChildInfo.getResStr());
        editor.putInt("height", referChildInfo.getHeight());
        editor.putInt("weight", referChildInfo.getWidth());
        editor.putString("name",referChildInfo.getName());
        editor.commit();

        currRefer = referChildInfo;
    }

}
