package com.example.yqc.customview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.AttributeSet;

import com.example.yqc.R;
import com.qmuiteam.qmui.util.QMUIDeviceHelper;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButtonDrawable;

public class MyRoundButton extends QMUIRoundButton {

    public MyRoundButton(Context context) {
        super(context);
        setWidth(QMUIDisplayHelper.dpToPx(60));
        setHeight(QMUIDisplayHelper.dpToPx(60));
        setTextSize(14);
       setEnabled(false);
       setSingleLine(false);
       setTextColor(getResources().getColor(R.color.app_color_description));

        QMUIRoundButtonDrawable qmuiRoundButtonDrawable= (QMUIRoundButtonDrawable) this.getBackground();
        qmuiRoundButtonDrawable.setCornerRadius(QMUIDisplayHelper.dpToPx(4));
        qmuiRoundButtonDrawable.setStrokeData(QMUIDisplayHelper.dpToPx(2),(ColorStateList)getResources().getColorStateList(R.color.button_text));

    }


    public MyRoundButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRoundButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
