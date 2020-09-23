package com.example.yqc.activity.carcontrol;

import android.content.Context;
import android.content.Intent;

import com.example.yqc.bean.DefaultSendBean;
import com.qmuiteam.qmui.widget.QMUIWindowInsetLayout;

public class HomeController extends QMUIWindowInsetLayout {

    private HomeControlListener mHomeControlListener;

    public HomeController(Context context) {
        super(context);
    }

    protected void sendDataByteOnce(DefaultSendBean bean) {
        if (mHomeControlListener != null) {
            mHomeControlListener.sendDataByteOnce(bean);
        }
    }

    public void setHomeControlListener(HomeControlListener homeControlListener) {
        mHomeControlListener = homeControlListener;
    }
    public interface HomeControlListener {
        void sendDataByteOnce(DefaultSendBean bean);
    }

    public void setButtonEnble(boolean enble){

    }
}
