package com.zzy.smartweight;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ReferChildActivity extends BaseActivity {
    public static final String CATA_ID_EXTRA = "cataId";
    private int cataId;
    private AdpReferChild adpReferChild;
    private ReferChildInfo selectInfo = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refer_child);

        Intent intent = getIntent();
        if(intent==null || !intent.hasExtra(CATA_ID_EXTRA))
        {
            this.finish();
            return;
        }

        cataId = intent.getIntExtra(CATA_ID_EXTRA,0);
        if(!(cataId ==ReferInfo.REFER_ID_CARD || cataId ==ReferInfo.REFER_ID_CASH))
        {
            this.finish();
            return;
        }

        ListView lvRefer = findViewById(R.id.lvRefer);
        ArrayList<ReferChildInfo> lsRefer = initData(cataId);
        if(lsRefer==null || lsRefer.size()<1)
        {
            return;
        }

        ReferChildInfo referChildInfo = SmartWeightApp.getInstance().getCurrRefer();
        ReferChildInfo tmp = null;

        if(referChildInfo != null) {
            for (int i = 0; i < lsRefer.size(); i++) {
                tmp = lsRefer.get(i);
                if (tmp.getId() == referChildInfo.getId() && tmp.getCataId() == referChildInfo.getCataId()) {
                    tmp.setCheck(true);
                    selectInfo = tmp;
                    break;
                }
            }
        }

        adpReferChild = new AdpReferChild(this,lsRefer);
        lvRefer.setAdapter(adpReferChild);
        lvRefer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(selectInfo !=null) {
                    selectInfo.setCheck(false);
                }
                selectInfo = adpReferChild.getItem(i);
                selectInfo.setCheck(true);

                adpReferChild.notifyDataSetChanged();

            }
        });
    }

    private ArrayList<ReferChildInfo> initData(int cataId)
    {
        switch (cataId)
        {
            case ReferInfo.REFER_ID_CARD:
                return initCardData(cataId);
            default:
                return initCashData(cataId);
        }
    }

    private ArrayList<ReferChildInfo> initCardData(int cataId)
    {
        ArrayList<ReferChildInfo> lsRefer = new ArrayList<>();

        ReferChildInfo referChildInfo = new ReferChildInfo();
        referChildInfo.setId(0);
        referChildInfo.setCataId(cataId);
        referChildInfo.setResId(R.mipmap.card);
        referChildInfo.setResStr(R.string.STR_CARD);
        referChildInfo.setWidth(34);
        referChildInfo.setHeight(64);
        referChildInfo.setCheck(false);
        lsRefer.add(referChildInfo);

        referChildInfo = new ReferChildInfo();
        referChildInfo.setId(2);
        referChildInfo.setCataId(cataId);
        referChildInfo.setResId(R.mipmap.card);
        referChildInfo.setResStr(R.string.STR_CARD);
        referChildInfo.setWidth(34);
        referChildInfo.setHeight(64);
        referChildInfo.setCheck(false);
        lsRefer.add(referChildInfo);


        referChildInfo = new ReferChildInfo();
        referChildInfo.setId(3);
        referChildInfo.setCataId(cataId);
        referChildInfo.setResId(R.mipmap.card);
        referChildInfo.setResStr(R.string.STR_CARD);
        referChildInfo.setWidth(34);
        referChildInfo.setHeight(64);
        referChildInfo.setCheck(false);
        lsRefer.add(referChildInfo);


        referChildInfo = new ReferChildInfo();
        referChildInfo.setId(4);
        referChildInfo.setCataId(cataId);
        referChildInfo.setResId(R.mipmap.card);
        referChildInfo.setResStr(R.string.STR_CARD);
        referChildInfo.setWidth(34);
        referChildInfo.setHeight(64);
        referChildInfo.setCheck(false);
        lsRefer.add(referChildInfo);


        referChildInfo = new ReferChildInfo();
        referChildInfo.setId(5);
        referChildInfo.setCataId(cataId);
        referChildInfo.setResId(R.mipmap.card);
        referChildInfo.setResStr(R.string.STR_CARD);
        referChildInfo.setWidth(34);
        referChildInfo.setHeight(64);
        referChildInfo.setCheck(false);
        lsRefer.add(referChildInfo);

        referChildInfo = new ReferChildInfo();
        referChildInfo.setId(6);
        referChildInfo.setCataId(cataId);
        referChildInfo.setResId(R.mipmap.card);
        referChildInfo.setResStr(R.string.STR_CARD);
        referChildInfo.setWidth(34);
        referChildInfo.setHeight(64);
        referChildInfo.setCheck(false);
        lsRefer.add(referChildInfo);

        referChildInfo = new ReferChildInfo();
        referChildInfo.setId(7);
        referChildInfo.setCataId(cataId);
        referChildInfo.setResId(R.mipmap.card);
        referChildInfo.setResStr(R.string.STR_CARD);
        referChildInfo.setWidth(34);
        referChildInfo.setHeight(64);
        referChildInfo.setCheck(false);
        lsRefer.add(referChildInfo);
        return lsRefer;
    }

    private ArrayList<ReferChildInfo> initCashData(int cataId)
    {
        ArrayList<ReferChildInfo> lsRefer = new ArrayList<>();

        ReferChildInfo referChildInfo = new ReferChildInfo();
        referChildInfo.setId(0);
        referChildInfo.setCataId(cataId);
        referChildInfo.setResId(R.mipmap.cash);
        referChildInfo.setResStr(R.string.STR_CASH);
        referChildInfo.setWidth(34);
        referChildInfo.setHeight(64);
        referChildInfo.setCheck(false);
        lsRefer.add(referChildInfo);

        referChildInfo = new ReferChildInfo();
        referChildInfo.setId(2);
        referChildInfo.setCataId(cataId);
        referChildInfo.setResId(R.mipmap.cash);
        referChildInfo.setResStr(R.string.STR_CASH);
        referChildInfo.setWidth(34);
        referChildInfo.setHeight(64);
        referChildInfo.setCheck(false);
        lsRefer.add(referChildInfo);


        referChildInfo = new ReferChildInfo();
        referChildInfo.setId(3);
        referChildInfo.setCataId(cataId);
        referChildInfo.setResId(R.mipmap.cash);
        referChildInfo.setResStr(R.string.STR_CASH);
        referChildInfo.setWidth(34);
        referChildInfo.setHeight(64);
        referChildInfo.setCheck(false);
        lsRefer.add(referChildInfo);


        referChildInfo = new ReferChildInfo();
        referChildInfo.setId(4);
        referChildInfo.setCataId(cataId);
        referChildInfo.setResId(R.mipmap.cash);
        referChildInfo.setResStr(R.string.STR_CASH);
        referChildInfo.setWidth(34);
        referChildInfo.setHeight(64);
        referChildInfo.setCheck(false);
        lsRefer.add(referChildInfo);


        referChildInfo = new ReferChildInfo();
        referChildInfo.setId(5);
        referChildInfo.setCataId(cataId);
        referChildInfo.setResId(R.mipmap.cash);
        referChildInfo.setResStr(R.string.STR_CASH);
        referChildInfo.setWidth(34);
        referChildInfo.setHeight(64);
        referChildInfo.setCheck(false);
        lsRefer.add(referChildInfo);

        referChildInfo = new ReferChildInfo();
        referChildInfo.setId(6);
        referChildInfo.setCataId(cataId);
        referChildInfo.setResId(R.mipmap.cash);
        referChildInfo.setResStr(R.string.STR_CASH);
        referChildInfo.setWidth(34);
        referChildInfo.setHeight(64);
        referChildInfo.setCheck(false);
        lsRefer.add(referChildInfo);

        referChildInfo = new ReferChildInfo();
        referChildInfo.setId(7);
        referChildInfo.setCataId(cataId);
        referChildInfo.setResId(R.mipmap.cash);
        referChildInfo.setResStr(R.string.STR_CASH);
        referChildInfo.setWidth(34);
        referChildInfo.setHeight(64);
        referChildInfo.setCheck(false);
        lsRefer.add(referChildInfo);
        return lsRefer;
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    public void ok_click(View view)
    {
        if(selectInfo!=null)
        {
            SmartWeightApp.getInstance().saveCurrRefer(selectInfo);
        }
        this.finish();
    }

    public void cancel_click(View view)
    {
        this.finish();
    }
}
