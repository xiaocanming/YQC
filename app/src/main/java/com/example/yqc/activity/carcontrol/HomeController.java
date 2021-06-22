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

    protected void resetSleepTime() {
        if (mHomeControlListener != null) {
            mHomeControlListener.resetSleepTime();
        }
    }

    protected void startChronometer() {
        if (mHomeControlListener != null) {
            mHomeControlListener.startChronometer();
        }
    }

    protected void initHomeSetting() {
        if (mHomeControlListener != null) {
            mHomeControlListener.initHomeSetting();
        }
    }

    protected void setMainButtonEnble(boolean enble) {
        if (mHomeControlListener != null) {
            mHomeControlListener.setMainButtonEnble(enble);
        }
    }

    public void setHomeControlListener(HomeControlListener homeControlListener) {
        mHomeControlListener = homeControlListener;
    }
    public interface HomeControlListener {
        void sendDataByteOnce(DefaultSendBean bean);
        void startChronometer();
        void initHomeSetting();
        void resetSleepTime();
        void setMainButtonEnble(boolean enble);
    }

    public void setButtonEnble(boolean enble){

    }

    public void setButtonEnble(boolean enble,String tag){

    }
}
