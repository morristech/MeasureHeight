package com.zzy.smartweight.dot;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;

import com.zzy.smartweight.Common;


public class ColorPointHintView extends ShapeHintView {
    private int focusColor;
    private int normalColor;

    public ColorPointHintView(Context context) {
        super(context);
    }

    public ColorPointHintView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setColor(int focusColor, int normalColor)
    {
        this.focusColor = focusColor;
        this.normalColor = normalColor;
    }

    @Override
    public Drawable makeFocusDrawable() {
        GradientDrawable dot_focus = new GradientDrawable();
        dot_focus.setColor(focusColor);
        dot_focus.setCornerRadius(Common.dip2px(getContext(), 4));
        dot_focus.setSize(Common.dip2px(getContext(), 8), Common.dip2px(getContext(), 8));
        return dot_focus;
    }

    @Override
    public Drawable makeNormalDrawable() {
        GradientDrawable dot_normal = new GradientDrawable();
        dot_normal.setColor(normalColor);
        dot_normal.setCornerRadius(Common.dip2px(getContext(), 4));
        dot_normal.setSize(Common.dip2px(getContext(), 8), Common.dip2px(getContext(), 8));
        return dot_normal;
    }
}
