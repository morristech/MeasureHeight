package com.zzy.smartweight;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by zerozhao on 2017/12/7.
 */

public class AdpRefer extends RecyclerView.Adapter<AdpRefer.ViewHolder> {

    private ArrayList<ReferInfo> list;
    private LayoutInflater la;

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(View view, int position);
    }
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }


    public AdpRefer(Context context,ArrayList<ReferInfo> list){

        this.list=list;
        this.la = LayoutInflater.from(context);
    }

    public ReferInfo getItem(int postion)
    {
        return list.get(postion);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = la.inflate(R.layout.main_item, parent,false);
        ViewHolder holder=new ViewHolder(view, viewType);

        return holder;
    }

    @Override
    public void onBindViewHolder(final  ViewHolder holder, int position) {

        ReferInfo referInfo = list.get(position);
        if(referInfo.isCheck()) {
            holder.imgvCheck.setVisibility(View.VISIBLE);
        }else{
            holder.imgvCheck.setVisibility(View.GONE);
        }

        holder.imgvItem.setImageResource(referInfo.getResId());
        holder.tvItem.setText(referInfo.getResStr());


        if (mOnItemClickListener != null) {
            holder.viewParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.viewParent, position);
                }
            });
        }
        if (mOnItemLongClickListener != null) {
            holder.viewParent.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemLongClickListener.onItemLongClick(holder.viewParent, position);
                    return true;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgvItem;
        private final ImageView imgvCheck;
        private final TextView tvItem;
        private final View viewParent;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            viewParent = itemView.findViewById(R.id.rlParent);
            imgvItem = (ImageView) itemView.findViewById(R.id.imgvItem);
            imgvCheck = (ImageView) itemView.findViewById(R.id.imgvCheck);
            tvItem = (TextView) itemView.findViewById(R.id.tvItem);
        }
    }
}
