package com.example.yqc.activity.carcontrol;

import android.content.Context;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.yqc.R;
import com.example.yqc.bean.DefaultSendBean;
import com.example.yqc.customview.MyRoundButton;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUIFloatLayout;

public class ManualController extends HomeController{
    private QMUIFloatLayout mFloatLayout;
    public ManualController(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.fragment_manual, this);
        initView();
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
            }
        });
        //桅杆复位
        MyRoundButton MyRoundButton2= new MyRoundButton(getContext());
        MyRoundButton2.setText("桅杆复位");
        MyRoundButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DefaultSendBean bean = new DefaultSendBean();
                bean.setThreebyte((byte) 0x09);
                bean.setFourbyte((byte) 0x01);
                sendDataByteOnce(bean);
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
            }
        });
        //帆板复位
        MyRoundButton MyRoundButton4= new MyRoundButton(getContext());
        MyRoundButton4.setText("帆板复位");
        MyRoundButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DefaultSendBean bean = new DefaultSendBean();
                bean.setThreebyte((byte) 0x0A);
                bean.setFourbyte((byte) 0x01);
                sendDataByteOnce(bean);
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
            }
        });

        //原地左转
        MyRoundButton MyRoundButton6= new MyRoundButton(getContext());
        MyRoundButton6.setText("原地左转");
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



        //原地右转
        MyRoundButton MyRoundButton7= new MyRoundButton(getContext());
        MyRoundButton7.setText("原地右转");
        final GestureDetector gestureDetector7 = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
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
                bean.setThreebyte((byte) 0x17);
                bean.setFourbyte((byte) 0xA5);
                sendDataByteOnce(bean);
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
        MyRoundButton7.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector7.onTouchEvent(event);
            }
        });

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
        MyRoundButton9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DefaultSendBean bean = new DefaultSendBean();
                bean.setThreebyte((byte) 0x18);
                bean.setFourbyte((byte) 0xA5);
                sendDataByteOnce(bean);
            }
        });

        //云台向下
        MyRoundButton MyRoundButton10= new MyRoundButton(getContext());
        MyRoundButton10.setText("云台向下");
        MyRoundButton10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DefaultSendBean bean = new DefaultSendBean();
                bean.setThreebyte((byte) 0x19);
                bean.setFourbyte((byte) 0xA5);
                sendDataByteOnce(bean);
            }
        });

        //充电位置
        MyRoundButton MyRoundButton11= new MyRoundButton(getContext());
        MyRoundButton11.setText("充电位置");
        MyRoundButton11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DefaultSendBean bean = new DefaultSendBean();
                bean.setThreebyte((byte) 0x05);
                bean.setFourbyte((byte) 0xA5);
                sendDataByteOnce(bean);
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
        mFloatLayout.addView(MyRoundButton7,lp);
        mFloatLayout.addView(MyRoundButton8,lp);
        mFloatLayout.addView(MyRoundButton9,lp);
        mFloatLayout.addView(MyRoundButton10,lp);
        mFloatLayout.addView(MyRoundButton11,lp);
    }

    @Override
    public void setButtonEnble(boolean enble){
        int currentChildCount = mFloatLayout.getChildCount();
        for(int i=0;i<currentChildCount;i++){
            MyRoundButton button=(MyRoundButton)mFloatLayout.getChildAt(i);
            button.setEnabled(enble);
            button.setTextColor(getResources().getColor(R.color.white));
        }
    }
}
