package com.example.yqc.customview;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.qmuiteam.qmui.widget.QMUIViewPager;


public class NoScrollViewPager extends QMUIViewPager {

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollViewPager(Context context) {
        super(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false && super.onTouchEvent(ev);
    }
}
