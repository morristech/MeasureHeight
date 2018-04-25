package com.zzy.smartweight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by zerozhao on 2017/12/13.
 */


public class AdpReferChild extends BaseAdapter{

    private LayoutInflater la;
    private ArrayList<ReferChildInfo> lsRefer;

    public AdpReferChild(Context context, ArrayList<ReferChildInfo> lsRefer) {
        this.lsRefer = lsRefer;
        this.la = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return lsRefer.size();
    }

    @Override
    public ReferChildInfo getItem(int position) {
        return lsRefer.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = la.inflate(R.layout.refer_child_item, parent, false);
        }

        ReferChildInfo referChildInfo = lsRefer.get(position);

        ImageView imgvIcon = convertView.findViewById(R.id.imgvIcon);
        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvSize = convertView.findViewById(R.id.tvSize);
        ImageView imgvCheck = convertView.findViewById(R.id.imgvCheck);

        imgvIcon.setImageResource(referChildInfo.getResId());
        tvName.setText(referChildInfo.getResStr());

        tvSize.setText(referChildInfo.getWidth()+"X"+referChildInfo.getHeight());

        if(referChildInfo.isCheck()) {
            imgvCheck.setImageResource(R.mipmap.terms_checked);
        }else{
            imgvCheck.setImageResource(R.mipmap.terms_unchecked);
        }

        return convertView;
    }
}