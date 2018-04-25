package com.zzy.smartweight;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.zzy.smartweight.ruler.HeightView;

public class RulerActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private double physicsH,physicsW;
    private EditText etW;
    private EditText etH;
    private EditText etName;
    private int cataId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ruler);

        HeightView hv = (HeightView) findViewById(R.id.hvRuler);
        getPhysicsSize(hv);

        hv.setOnItemChangedListener(new HeightView.OnItemChangedListener() {
            @Override
            public void onItemChanged(int index, int value) {
                Log.i(TAG, String.format("onItemChanged index == %d value == %d ", index, value));
            }
        });

        Intent intent =getIntent();
        if(intent==null)
        {
            this.finish();
            return;
        }
        etW = findViewById(R.id.etW);
        etH = findViewById(R.id.etH);
        etName = findViewById(R.id.etName);

        cataId = intent.getIntExtra(ReferChildActivity.CATA_ID_EXTRA,ReferInfo.REFER_ID_OTHER);
        TextView tvTips = findViewById(R.id.tvTips);
        if(cataId==ReferInfo.REFER_ID_PHONE) {
            tvTips.setText(R.string.STR_RULER_TIPS_PHONE);
            etW.setText("0");
            etW.setEnabled(false);
            etName.setText("phone");
        }else{
            tvTips.setText(R.string.STR_RULER_TIPS_OTHER);
            etW.setEnabled(true);
        }

       ReferChildInfo referChildInfo = SmartWeightApp.getInstance().getCurrRefer();
       if(referChildInfo!=null && cataId == referChildInfo.getCataId()) {
           etW.setText(String.valueOf(referChildInfo.getWidth()));
           etH.setText(String.valueOf(referChildInfo.getHeight()));
           etName.setText(referChildInfo.getName());
       }
    }

    private void getPhysicsSize(HeightView hv) {
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getRealSize(point);
        DisplayMetrics dm = getResources().getDisplayMetrics();

        Common.LogEx("w:"+dm.widthPixels+" h:"+dm.heightPixels);
        physicsW = (point.x/ dm.xdpi)*25.4;
        physicsH = (point.y / dm.ydpi)*25.4;

        Common.LogEx("point.x:"+point.x+" point.y:"+point.y);
        Common.LogEx("physicsW:"+physicsW+" physicsH:"+physicsH);

        hv.setPhysicSize(physicsW,physicsH,point.x,point.y);

    }



    public void apply_click(View view)
    {
        String sTmpW = etW.getText().toString().trim();
        String sTmpH = etH.getText().toString().trim();
        String sName = etName.getText().toString().trim();

        if(etName.getText().toString().trim().length()<1)
        {
            showToast(R.string.STR_INPUT_NAME);
            return;
        }

        if(TextUtils.isEmpty(sTmpW) || TextUtils.isEmpty(sTmpH))
        {
            showToast(R.string.STR_INPUT_EMPTY);
            return;
        }

        int w= Common.strToInt(sTmpW,0);
        if(w<20 && cataId== ReferInfo.REFER_ID_OTHER)
        {
            showToast(R.string.STR_ABOVE_TIPS);
            return;
        }

        int h= Common.strToInt(sTmpH,0);
        if(h<20)
        {
            showToast(R.string.STR_ABOVE_TIPS);
            return;
        }

        ReferChildInfo referChildInfo = new ReferChildInfo();
        referChildInfo.setId(0);
        referChildInfo.setCataId(cataId);
        referChildInfo.setCheck(true);
        referChildInfo.setWidth(w);
        referChildInfo.setHeight(h);
        referChildInfo.setResStr(0);
        referChildInfo.setResStr(0);
        referChildInfo.setName(sName);

        SmartWeightApp.getInstance().saveCurrRefer(referChildInfo);

        this.finish();

    }

}