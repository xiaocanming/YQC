package com.example.yqc.handler;

import com.example.yqc.R;
import com.example.yqc.activity.CarControlActivity;
import com.example.yqc.bean.DefaultSendBean;
import com.example.yqc.hkws.HC_DVRManager;
import com.example.yqc.util.LogTool;
import com.example.yqc.util.StringTool;
import com.xuhao.didi.socket.client.sdk.client.connection.IConnectionManager;

import java.util.TimerTask;

public class SleepTimeTimerTask extends TimerTask {
    private int AutoSleep;
    private int YuntaiDown;
    private IConnectionManager mManager;

    public SleepTimeTimerTask(IConnectionManager mManager,int AutoSleep, int YuntaiDown){
        this.mManager=mManager;
        this.AutoSleep=AutoSleep;
        this.YuntaiDown=YuntaiDown;
    }

    @Override
    public void run() {
        if(!CarControlActivity.isSleepTime){
            CarControlActivity.sleepTimeCount++;
            if(CarControlActivity.sleepTimeCount==AutoSleep+1){
                CarControlActivity.isSleepTime=true;
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            for (int i = 0; i < YuntaiDown; i++) {
                                DefaultSendBean bean = new DefaultSendBean();
                                bean.setThreebyte((byte) 0x19);
                                bean.setFourbyte((byte) 0xA5);
                                if(mManager.isConnect()){
                                    mManager.send(bean);
                                    LogTool.d("云台向下", StringTool.byteToString(bean.parse()));
                                }else {
                                    LogTool.d("发送失败",StringTool.byteToString(bean.parse()));
                                }
                                Thread.sleep(1000);
                            }
                            DefaultSendBean bean = new DefaultSendBean();
                            bean.setThreebyte((byte) 0x04);
                            bean.setFourbyte((byte) 0xA5);
                            if(mManager.isConnect()){
                                mManager.send(bean);
                                LogTool.d("急停", StringTool.byteToString(bean.parse()));
                            }else {
                                LogTool.d("发送失败",StringTool.byteToString(bean.parse()));
                            }
                            LogTool.d("急停",StringTool.byteToString(bean.parse()));
                        } catch (Exception e) {
                            LogTool.d("睡眠倒计时报错", e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        }
    }
}
