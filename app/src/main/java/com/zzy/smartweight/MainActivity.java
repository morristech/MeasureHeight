package com.zzy.smartweight;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.zzy.smartweight.dot.ColorPointHintView;
import com.zzy.smartweight.photo.MeasureActivity;
import com.zzy.smartweight.photo.TakePhotoActivity;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    private AdView adView1;
    private AdView adView2;
    private AdView adView3;
    private SlipLineLayout sllAd;
    private AdListener adListener = null;
    private RecyclerView rvReference;
    private AdpRefer adpRefer;
    private ArrayList<ReferInfo> lsData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        MobileAds.initialize(this, "ca-app-pub-7554436602823340~3553617611");
        setListen();
        initAd();
        lsData = initItemData();

        rvReference = findViewById(R.id.rvReference);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rvReference.setLayoutManager(gridLayoutManager);

        adpRefer = new AdpRefer(this,lsData);
        rvReference.setAdapter(adpRefer);
        adpRefer.setOnItemClickListener(new AdpRefer.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                int id = adpRefer.getItem(position).getId();
                if(id == ReferInfo.REFER_ID_OTHER || id == ReferInfo.REFER_ID_PHONE) {
                    Intent intent = new Intent(MainActivity.this, RulerActivity.class);
                    intent.putExtra(ReferChildActivity.CATA_ID_EXTRA,id);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(MainActivity.this, ReferChildActivity.class);
                    intent.putExtra(ReferChildActivity.CATA_ID_EXTRA, id);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        sllAd.play();

        ReferInfo referInfo;
        ReferChildInfo referChildInfo = SmartWeightApp.getInstance().getCurrRefer();
        if(referChildInfo !=null) {
            for (int i = 0; i < lsData.size(); i++) {
                referInfo = lsData.get(i);
                if (referInfo.getId() == referChildInfo.getCataId()) {
                    referInfo.setCheck(true);
                }else{
                    referInfo.setCheck(false);
                }
            }
            adpRefer.notifyDataSetChanged();
        }
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        sllAd.stop();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        sllAd.onDestroy();
    }


    public void btnCamera_click(View view)
    {
//        Intent intent = new Intent(this, MeasureActivity.class);
//        intent.putExtra(TakePhotoActivity.IMAGE_PATH_EXTRA,"/storage/emulated/0/SmartMeasure/phone/15233443612441143769878.jpg");
//        startActivity(intent);

        Intent intent = new Intent(this, TakePhotoActivity.class);
        startActivity(intent);
    }
    private void initAd()
    {
        sllAd = findViewById(R.id.sllAd);

        adView1=findViewById(R.id.adView1);
        adView1.setVisibility(View.VISIBLE);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView1.loadAd(adRequest);
        adView1.setAdListener(adListener);

        adView2=findViewById(R.id.adView2);
        adView2.setVisibility(View.GONE);
        adRequest = new AdRequest.Builder().build();
        adView2.loadAd(adRequest);
        adView2.setAdListener(adListener);

        adView3=findViewById(R.id.adView3);
        adView3.setVisibility(View.GONE);
        adRequest = new AdRequest.Builder().build();
        adView3.loadAd(adRequest);
        adView3.setAdListener(adListener);

        sllAd.AddItem(adView1);
        sllAd.AddItem(adView2);
        sllAd.AddItem(adView3);

        sllAd.initHintView((ColorPointHintView)findViewById(R.id.cphDot),3,R.color.colorDotSelect,R.color.colorDotunSelect);
        sllAd.play();
    }



    private void setListen()
    {
        adListener = new AdListener(){

            @Override
            public void onAdLoaded() {
                Common.LogEx("onAdLoaded");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Common.LogEx("onAdFailedToLoad");
            }

            @Override
            public void onAdOpened() {
                Common.LogEx("onAdOpened");
            }

            @Override
            public void onAdLeftApplication() {
                Common.LogEx("onAdLeftApplication");
            }

            @Override
            public void onAdClosed() {
                Common.LogEx("onAdClosed");
            }

            public void onAdClicked() {

                Common.LogEx("onAdClicked");
            }
        };

    }


    private ArrayList<ReferInfo> initItemData()
    {
        ArrayList<ReferInfo> lsData = new ArrayList<>();
        ReferInfo info = new ReferInfo();
        info.setId(ReferInfo.REFER_ID_CARD);
        info.setResId(R.mipmap.card);
        info.setResStr(R.string.STR_CARD);
        info.setCheck(false);
        lsData.add(info);

        info = new ReferInfo();
        info.setId(ReferInfo.REFER_ID_CASH);
        info.setResId(R.mipmap.cash);
        info.setResStr(R.string.STR_CASH);
        info.setCheck(false);
        lsData.add(info);

        info = new ReferInfo();
        info.setId(ReferInfo.REFER_ID_PHONE);
        info.setResId(R.mipmap.phone);
        info.setResStr(R.string.STR_PHONE);
        info.setCheck(false);
        lsData.add(info);

        info = new ReferInfo();
        info.setId(ReferInfo.REFER_ID_OTHER);
        info.setResId(R.mipmap.ruler);
        info.setResStr(R.string.STR_RULER);
        info.setCheck(false);
        lsData.add(info);

        return lsData;
    }
}
