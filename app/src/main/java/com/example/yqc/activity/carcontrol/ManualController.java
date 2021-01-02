package com.example.yqc.activity.carcontrol;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.yqc.R;
import com.example.yqc.bean.DefaultSendBean;
import com.example.yqc.customview.MyRoundButton;
import com.example.yqc.customview.SingleRockerView;
import com.example.yqc.customview.ThrottleView;
import com.example.yqc.util.LogTool;
import com.example.yqc.util.MathTool;
import com.example.yqc.util.StringTool;
import com.hikvision.netsdk.HCNetSDK;
import com.hikvision.netsdk.PTZCommand;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUIFloatLayout;

import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.MODE_PRIVATE;

public class ManualController extends HomeController{
    private QMUIFloatLayout mFloatLayout;
    private Timer send_timer = new Timer( );
    private static long DirectionTimeinterval ;
    private boolean MyRoundButton9DOWN=false;
    private boolean MyRoundButton10DOWN=false;
    private boolean MyRoundButton11DOWN=false;
    private boolean MyRoundButton12DOWN=false;
    //用于存储app参数
    private static final String SET_FILENAME = "ano_set_filename";
    public ManualController(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.fragment_manual, this);
        initSetting();
        initView();
    }

    private void initSetting() {
        // 得到SP对象   
        SharedPreferences sp = getContext().getSharedPreferences(SET_FILENAME, MODE_PRIVATE);
        // 从存储的XML文件中根据相应的键获取数据，没有数据就返回默认值    
        DirectionTimeinterval = sp.getInt("Set_DirectionTimeinterval",50);
    }

    private void initView() {
        //    小车数据
        mFloatLayout=findViewById(R.id.qmuidemo_floatlayout);

        //位置复位
        MyRoundButton MyRoundButton1= new MyRoundButton(getContext());
        MyRoundButton1.setText("位置复位");
        MyRoundButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DefaultSendBean bean = new DefaultSendBean();
                bean.setThreebyte((byte) 0x08);
                bean.setFourbyte((byte) 0xA5);
                sendDataByteOnce(bean);
                LogTool.d("位置复位", StringTool.byteToString(bean.parse()));
            }
        });
        //桅杆收拢
        MyRoundButton MyRoundButton2= new MyRoundButton(getContext());
        MyRoundButton2.setText("桅杆收拢");
        MyRoundButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DefaultSendBean bean = new DefaultSendBean();
                bean.setThreebyte((byte) 0x09);
                bean.setFourbyte((byte) 0x01);
                sendDataByteOnce(bean);
                LogTool.d("桅杆收拢",StringTool.byteToString(bean.parse()));
            }
        });
        //桅杆起立
        MyRoundButton MyRoundButton3= new MyRoundButton(getContext());
        MyRoundButton3.setText("桅杆起立");
        MyRoundButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DefaultSendBean bean = new DefaultSendBean();
                bean.setThreebyte((byte) 0x09);
                bean.setFourbyte((byte) 0x02);
                sendDataByteOnce(bean);
                LogTool.d("桅杆起立",StringTool.byteToString(bean.parse()));
            }
        });
        //帆板收拢
        MyRoundButton MyRoundButton4= new MyRoundButton(getContext());
        MyRoundButton4.setText("帆板收拢");
        MyRoundButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DefaultSendBean bean = new DefaultSendBean();
                bean.setThreebyte((byte) 0x0A);
                bean.setFourbyte((byte) 0x01);
                sendDataByteOnce(bean);
                LogTool.d("帆板收拢",StringTool.byteToString(bean.parse()));
            }
        });
        //帆板展开
        MyRoundButton MyRoundButton5= new MyRoundButton(getContext());
        MyRoundButton5.setText("帆板展开");
        MyRoundButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DefaultSendBean bean = new DefaultSendBean();
                bean.setThreebyte((byte) 0x0A);
                bean.setFourbyte((byte) 0x02);
                sendDataByteOnce(bean);
                LogTool.d("帆板展开",StringTool.byteToString(bean.parse()));
            }
        });

        //原地转弯
        MyRoundButton MyRoundButton6= new MyRoundButton(getContext());
        MyRoundButton6.setText("原地转弯");
        final GestureDetector gestureDetector6 = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            /**
             * 发生确定的单击时执行
             * @param e
             * @return
             */
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {//单击事件
//                Toast.makeText(getContext(),"这是单击事件", Toast.LENGTH_SHORT).show();
                return super.onSingleTapConfirmed(e);
            }
            /**
             * 双击发生时的通知
             * @param e
             * @return
             */
            @Override
            public boolean onDoubleTap(MotionEvent e) {//双击事件
//                Toast.makeText(getContext(),"这是双击事件",Toast.LENGTH_SHORT).show();
                DefaultSendBean bean = new DefaultSendBean();
                bean.setThreebyte((byte) 0x16);
                bean.setFourbyte((byte) 0xA5);
                sendDataByteOnce(bean);
                LogTool.d("原地转弯",StringTool.byteToString(bean.parse()));
                return super.onDoubleTap(e);
            }

            /**
             * 双击手势过程中发生的事件，包括按下、移动和抬起事件
             * @param e
             * @return
             */
            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                return super.onDoubleTapEvent(e);
            }
        });
        MyRoundButton6.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector6.onTouchEvent(event);
            }
        });



//        //原地右转
//        MyRoundButton MyRoundButton7= new MyRoundButton(getContext());
//        MyRoundButton7.setText("原地右转");
//        final GestureDetector gestureDetector7 = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
//            /**
//             * 发生确定的单击时执行
//             * @param e
//             * @return
//             */
//            @Override
//            public boolean onSingleTapConfirmed(MotionEvent e) {//单击事件
////                Toast.makeText(getContext(),"这是单击事件", Toast.LENGTH_SHORT).show();
//                return super.onSingleTapConfirmed(e);
//            }
//            /**
//             * 双击发生时的通知
//             * @param e
//             * @return
//             */
//            @Override
//            public boolean onDoubleTap(MotionEvent e) {//双击事件
////                Toast.makeText(getContext(),"这是双击事件",Toast.LENGTH_SHORT).show();
//                DefaultSendBean bean = new DefaultSendBean();
//                bean.setThreebyte((byte) 0x17);
//                bean.setFourbyte((byte) 0xA5);
//                sendDataByteOnce(bean);
//                return super.onDoubleTap(e);
//            }
//
//            /**
//             * 双击手势过程中发生的事件，包括按下、移动和抬起事件
//             * @param e
//             * @return
//             */
//            @Override
//            public boolean onDoubleTapEvent(MotionEvent e) {
//                return super.onDoubleTapEvent(e);
//            }
//        });
//        MyRoundButton7.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return gestureDetector7.onTouchEvent(event);
//            }
//        });

        //车轮复位
        MyRoundButton MyRoundButton8= new MyRoundButton(getContext());
        MyRoundButton8.setText("车轮复位");
        final GestureDetector gestureDetector8 = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            /**
             * 发生确定的单击时执行
             * @param e
             * @return
             */
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {//单击事件
//                Toast.makeText(getContext(),"这是单击事件", Toast.LENGTH_SHORT).show();
                return super.onSingleTapConfirmed(e);
            }
            /**
             * 双击发生时的通知
             * @param e
             * @return
             */
            @Override
            public boolean onDoubleTap(MotionEvent e) {//双击事件
//                Toast.makeText(getContext(),"这是双击事件",Toast.LENGTH_SHORT).show();
                DefaultSendBean bean = new DefaultSendBean();
                bean.setThreebyte((byte) 0x1A);
                bean.setFourbyte((byte) 0xA5);
                sendDataByteOnce(bean);
                LogTool.d("车轮复位",StringTool.byteToString(bean.parse()));
                return super.onDoubleTap(e);
            }

            /**
             * 双击手势过程中发生的事件，包括按下、移动和抬起事件
             * @param e
             * @return
             */
            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                return super.onDoubleTapEvent(e);
            }
        });
        MyRoundButton8.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector8.onTouchEvent(event);
            }
        });

        //云台向上
        MyRoundButton MyRoundButton9= new MyRoundButton(getContext());
        MyRoundButton9.setText("云台向上");
        MyRoundButton9.setOnTouchListener(new OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        MyRoundButton9DOWN=true;
                        return false;
                    case MotionEvent.ACTION_UP:
                        MyRoundButton9DOWN=false;
                        return false;
                }
                return false;
            }
        });

        //云台向下
        MyRoundButton MyRoundButton10= new MyRoundButton(getContext());
        MyRoundButton10.setText("云台向下");
        MyRoundButton10.setOnTouchListener(new OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        MyRoundButton10DOWN=true;
                        return false;
                    case MotionEvent.ACTION_UP:
                        MyRoundButton10DOWN=false;
                        return false;
                }
                return false;
            }
        });

        //云台向左
        MyRoundButton MyRoundButton11= new MyRoundButton(getContext());
        MyRoundButton11.setText("云台向左");
        MyRoundButton11.setOnTouchListener(new OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        MyRoundButton11DOWN=true;
                        return false;
                    case MotionEvent.ACTION_UP:
                        MyRoundButton11DOWN=false;
                        return false;
                }
                return false;
            }
        });

        //云台向右
        MyRoundButton MyRoundButton12= new MyRoundButton(getContext());
        MyRoundButton12.setText("云台向右");
        MyRoundButton12.setOnTouchListener(new OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        MyRoundButton12DOWN=true;
                        return false;
                    case MotionEvent.ACTION_UP:
                        MyRoundButton12DOWN=false;
                        return false;
                }
                return false;
            }
        });

        //充电位置
        MyRoundButton MyRoundButton13= new MyRoundButton(getContext());
        MyRoundButton13.setText("充电位置");
        MyRoundButton13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DefaultSendBean bean = new DefaultSendBean();
                bean.setThreebyte((byte) 0x05);
                bean.setFourbyte((byte) 0xA5);
                sendDataByteOnce(bean);
                LogTool.d("充电位置",StringTool.byteToString(bean.parse()));
            }
        });

        int textViewSize = QMUIDisplayHelper.dpToPx(60);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(textViewSize, textViewSize);
        mFloatLayout.addView(MyRoundButton1,lp);
        mFloatLayout.addView(MyRoundButton2,lp);
        mFloatLayout.addView(MyRoundButton3,lp);
        mFloatLayout.addView(MyRoundButton4,lp);
        mFloatLayout.addView(MyRoundButton5,lp);
        mFloatLayout.addView(MyRoundButton6,lp);
//        mFloatLayout.addView(MyRoundButton7,lp);
        mFloatLayout.addView(MyRoundButton8,lp);
        mFloatLayout.addView(MyRoundButton9,lp);
        mFloatLayout.addView(MyRoundButton10,lp);
        mFloatLayout.addView(MyRoundButton11,lp);
        mFloatLayout.addView(MyRoundButton12,lp);
//        mFloatLayout.addView(MyRoundButton13,lp);

        //发送陀螺仪数据的任务
        send_timer.schedule(send_task,1000,DirectionTimeinterval);
    }

    @Override
    public void setButtonEnble(boolean enble){
        int currentChildCount = mFloatLayout.getChildCount();
        for(int i=0;i<currentChildCount;i++){
            MyRoundButton button=(MyRoundButton)mFloatLayout.getChildAt(i);
            button.setEnabled(enble);
            button.setTextColor(enble? getResources().getColor(R.color.white):getResources().getColor(R.color.app_color_description));
        }
    }
    @Override
    public void setButtonEnble(boolean enble,String tag){
        int currentChildCount = mFloatLayout.getChildCount();
        for(int i=0;i<currentChildCount;i++){
            MyRoundButton button=(MyRoundButton)mFloatLayout.getChildAt(i);
            if(button.getText().equals(tag)){
                button.setEnabled(enble);
                button.setTextColor(enble? getResources().getColor(R.color.white):getResources().getColor(R.color.app_color_description));
            }
        }
    }
    TimerTask send_task = new TimerTask( ) {
        public void run ( )
        {
            if(MyRoundButton9DOWN){
                DefaultSendBean bean = new DefaultSendBean();
                bean.setThreebyte((byte) 0x18);
                bean.setFourbyte((byte) 0xA5);
                sendDataByteOnce(bean);
                LogTool.d("云台向上",StringTool.byteToString(bean.parse()));
            }
            if(MyRoundButton10DOWN){
                DefaultSendBean bean = new DefaultSendBean();
                bean.setThreebyte((byte) 0x19);
                bean.setFourbyte((byte) 0xA5);
                sendDataByteOnce(bean);
                LogTool.d("云台向下",StringTool.byteToString(bean.parse()));
            }
            if(MyRoundButton11DOWN){
                DefaultSendBean bean = new DefaultSendBean();
                bean.setThreebyte((byte) 0x1B);
                bean.setFourbyte((byte) 0xA5);
                sendDataByteOnce(bean);
                LogTool.d("云台向左",StringTool.byteToString(bean.parse()));
            }
            if(MyRoundButton12DOWN){
                DefaultSendBean bean = new DefaultSendBean();
                bean.setThreebyte((byte) 0x1C);
                bean.setFourbyte((byte) 0xA5);
                sendDataByteOnce(bean);
                LogTool.d("云台向右",StringTool.byteToString(bean.parse()));
            }
        }
    };

}
