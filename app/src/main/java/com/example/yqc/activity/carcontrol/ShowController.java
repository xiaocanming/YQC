package com.example.yqc.activity.carcontrol;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.yqc.R;
import com.example.yqc.activity.CarControlActivity;
import com.example.yqc.bean.DefaultSendBean;
import com.example.yqc.customview.MyRoundButton;
import com.example.yqc.util.LogTool;
import com.example.yqc.util.StringTool;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUIFloatLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import static android.content.Context.MODE_PRIVATE;

public class ShowController extends HomeController{
    private QMUIFloatLayout mFloatLayout;

    private static final String SET_FILENAME = "ano_set_filename";
    private static boolean IsShowDemo;
    private static int MagneticFieldA ;
    private static int MagneticFieldB ;
    private static int MagneticFieldY1 ;
    private static int MagneticFieldY2;
    public ShowController(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.fragment_show, this);
        initSetting();
        initView();
    }

    private void initSetting() {
        // 得到SP对象   
        SharedPreferences sp = getContext().getSharedPreferences(SET_FILENAME, MODE_PRIVATE);
        // 从存储的XML文件中根据相应的键获取数据，没有数据就返回默认值    
        IsShowDemo = sp.getInt(getResources().getString(R.string.set_tag_isshowdemo),0)==1?true:false;
        MagneticFieldA = sp.getInt("Set_MagneticFieldA",getResources().getInteger(R.integer.set_magneticfielda));
        MagneticFieldB = sp.getInt("Set_MagneticFieldB",getResources().getInteger(R.integer.set_magneticfieldb));
        MagneticFieldY1 = sp.getInt("Set_MagneticFieldY1",getResources().getInteger(R.integer.set_magneticfieldy1));
        MagneticFieldY2 = sp.getInt("Set_MagneticFieldY2",getResources().getInteger(R.integer.set_magneticfieldy2));
    }

    private void initView() {
        //    小车数据
        mFloatLayout=findViewById(R.id.qmuidemo_floatlayout);

        //单程演示
        MyRoundButton MyRoundButton1= new MyRoundButton(getContext());
        MyRoundButton1.setText("单程演示");
        MyRoundButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DefaultSendBean bean = new DefaultSendBean();
                bean.setThreebyte((byte) 0x01);
                bean.setFourbyte((byte) 0x01);
                sendDataByteOnce(bean);
                startChronometer();
                LogTool.d("单程演示",StringTool.byteToString(bean.parse()));
            }
        });
        //往复演示
        MyRoundButton MyRoundButton2= new MyRoundButton(getContext());
        MyRoundButton2.setText("往复演示");
        MyRoundButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DefaultSendBean bean = new DefaultSendBean();
                bean.setThreebyte((byte) 0x01);
                bean.setFourbyte((byte) 0x02);
                sendDataByteOnce(bean);
                startChronometer();
                LogTool.d("往复演示",StringTool.byteToString(bean.parse()));
            }
        });

        //磁场设置
        MyRoundButton MyRoundButton4= new MyRoundButton(getContext());
        MyRoundButton4.setText("磁场设置");
        MyRoundButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] itemstitle = new String[]{"A点磁场角度", "B点磁场角度", "Y轴正方向磁场角度","Y轴负方向磁场角度"};
                final String[] itemstag = new String[]{"Set_MagneticFieldA", "Set_MagneticFieldB", "Set_MagneticFieldY1","Set_MagneticFieldY2"};
                final String[] itemsshow = new String[]{"A点磁场角度"+String.valueOf(MagneticFieldA/10.0)+" °", "B点磁场角度"+String.valueOf(MagneticFieldB/10.0)+" °", "Y轴正方向磁场角度"+String.valueOf(MagneticFieldY1/10.0)+" °","Y轴负方向磁场角度"+String.valueOf(MagneticFieldY2/10.0)+" °"};
                new QMUIDialog.MenuDialogBuilder(getContext())
                        .setSkinManager(QMUISkinManager.defaultInstance(getContext()))
                        .addItems(itemsshow, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, final int which) {
                                dialog.dismiss();
                                final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(getContext());
                                builder.setTitle(itemstitle[which])
                                        .setPlaceholder("请输入您的磁场角度")
                                        .setInputType(InputType.TYPE_CLASS_TEXT)
                                        .addAction("取消", new QMUIDialogAction.ActionListener() {
                                            @Override
                                            public void onClick(QMUIDialog dialog, int index) {
                                                dialog.dismiss();
                                            }
                                        })
                                        .addAction("确定", new QMUIDialogAction.ActionListener() {
                                            @Override
                                            public void onClick(QMUIDialog dialog, int index) {
                                                String text = builder.getEditText().getText().toString();
                                                if(StringTool.StringIsNumber(text)){
                                                    dialog.dismiss();
                                                    handcountMagneticField=0;
                                                    type=which;
                                                    int numpares=Integer.parseInt(new java.text.DecimalFormat("0").format((Float.valueOf(text)*10.0)));
                                                    num=numpares;
                                                    SaveSharedPreferencesInt(itemstag[which],numpares);
                                                    sendMagneticField.postDelayed(runnableMagneticField, delayMillisMagneticField);
                                                    initSetting();
                                                }else {
                                                    Toast.makeText(getContext(), "请输入有效的坐标值,精度保留一位小数" , Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        })
                                        .create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show();
                            }
                        })
                        .create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show();
            }
        });

        int textViewSize = QMUIDisplayHelper.dpToPx(60);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(textViewSize, textViewSize);
        mFloatLayout.addView(MyRoundButton1, lp);
        mFloatLayout.addView(MyRoundButton2, lp);
        mFloatLayout.addView(MyRoundButton4, lp);

        //演示指令
        if(IsShowDemo){
            MyRoundButton MyRoundButton3= new MyRoundButton(getContext());
            MyRoundButton3.setText("开始演示");
            MyRoundButton3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DefaultSendBean bean = new DefaultSendBean();
                    bean.setThreebyte((byte) 0x02);
                    bean.setFourbyte((byte)0xA5);
                    sendDataByteOnce(bean);
                    startChronometer();
                    LogTool.d("开始演示",StringTool.byteToString(bean.parse()));
                }
            });
            mFloatLayout.addView(MyRoundButton3, lp);
        }

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


    private int handcountMagneticField=0;
    private int type;
    private int num;
    private final int delayMillisMagneticField = 100;
    private Handler sendMagneticField = new Handler();
    public Runnable runnableMagneticField  = new Runnable(){//推送runnable，定期2s执行一次
        @Override
        public void run() {
            if (handcountMagneticField==3){
                sendMagneticField.removeCallbacks(runnableMagneticField);
            }else {
                byte[] bytes=StringTool.toLH(num);
                DefaultSendBean bean1 = new DefaultSendBean();
                DefaultSendBean bean2 = new DefaultSendBean();
                switch (type){
                    case 0:
                        bean1.setThreebyte((byte)0x1E);
                        bean2.setThreebyte((byte)0x1F);
                        break;
                    case 1:
                        bean1.setThreebyte((byte)0x20);
                        bean2.setThreebyte((byte)0x21);
                        break;
                    case 2:
                        bean1.setThreebyte((byte)0x22);
                        bean2.setThreebyte((byte)0x23);
                        break;
                    case 3:
                        bean1.setThreebyte((byte)0x24);
                        bean2.setThreebyte((byte)0x25);
                        break;
                }
                bean1.setFourbyte((byte)( bytes[1] & 0xFF));
                bean2.setFourbyte((byte)( bytes[0] & 0xFF));
                sendDataByteOnce(bean1);
                try {
                    Thread.sleep(delayMillisMagneticField);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sendDataByteOnce(bean2);
                LogTool.d("角度设置",StringTool.byteToString(bean1.parse()));
                LogTool.d("角度设置",StringTool.byteToString(bean2.parse()));
                handcountMagneticField++;
                sendMagneticField.postDelayed(runnableMagneticField, delayMillisMagneticField);
            }
        }
    };

    private void SaveSharedPreferencesInt(String tag,int value ) {
        // 得到编辑器对象   
        SharedPreferences.Editor editor = getContext().getSharedPreferences(SET_FILENAME, MODE_PRIVATE).edit();
        // 存入键值对   
        editor.putInt(tag,value);
        // 将内存中的数据写到XML文件中去   
        editor.commit();
    }

}
