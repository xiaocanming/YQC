package com.example.yqc.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;

import com.example.yqc.R;
import com.example.yqc.activity.carcontrol.HomeController;
import com.example.yqc.activity.carcontrol.ManualController;
import com.example.yqc.activity.carcontrol.ShowController;
import com.example.yqc.bean.DefaultSendBean;
import com.example.yqc.customview.BatteryView;
import com.example.yqc.customview.MyBreatheView;
import com.example.yqc.customview.MyRoundButton;
import com.example.yqc.customview.NoScrollViewPager;
import com.example.yqc.customview.SingleRockerView;
import com.example.yqc.customview.ThrottleView;
import com.example.yqc.hkws.DeviceBean;
import com.example.yqc.hkws.HC_DVRManager;
import com.example.yqc.util.CubbyHole;
import com.example.yqc.util.LogTool;
import com.example.yqc.util.MathTool;
import com.example.yqc.util.RedirectException;
import com.example.yqc.util.StringTool;
import com.hikvision.netsdk.ExceptionCallBack;
import com.hikvision.netsdk.HCNetSDK;
import com.hikvision.netsdk.NET_DVR_DEVICEINFO_V30;
import com.hikvision.netsdk.NET_DVR_PREVIEWINFO;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.util.QMUIDeviceHelper;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.qmuiteam.qmui.widget.tab.QMUITab;
import com.qmuiteam.qmui.widget.tab.QMUITabBuilder;
import com.qmuiteam.qmui.widget.tab.QMUITabSegment;
import com.xuhao.didi.core.pojo.OriginalData;
import com.xuhao.didi.core.protocol.IReaderProtocol;
import com.xuhao.didi.socket.client.impl.client.action.ActionDispatcher;
import com.xuhao.didi.socket.client.sdk.OkSocket;
import com.xuhao.didi.socket.client.sdk.client.ConnectionInfo;
import com.xuhao.didi.socket.client.sdk.client.OkSocketOptions;
import com.xuhao.didi.socket.client.sdk.client.action.SocketActionAdapter;
import com.xuhao.didi.socket.client.sdk.client.connection.IConnectionManager;
import com.xuhao.didi.socket.client.sdk.client.connection.NoneReconnect;

import java.nio.ByteOrder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;


public class CarControlActivity extends AppCompatActivity  {
    private BatteryView batteryView;
    private ThrottleView throttleView;
    //    小车数据
    private TextView mTextView11;
    private TextView mTextView12;
    private TextView mTextView13;
    private TextView mTextView14;
    private TextView mTextView15;
    private TextView mTextView16;

    //    驱动数据
    private TextView mTextView21;
    private TextView mTextView22;
    private TextView mTextView23;
    private TextView mTextView24;
    private TextView mTextView25;
    private TextView mTextView26;
    private TextView mTextView27;
    //    小车状态
    private TextView mTextView31;
    private TextView mTextView32;
    private TextView mTextView33;
    private TextView mTextView34;
    private TextView mTextView35;
    //九轴传感器
    private TextView mTextView18;
    private TextView mTextView41;
    private TextView mTextView42;
    private TextView mTextView43;
    private TextView mTextView44;
    private TextView mTextView45;
    private TextView mTextView46;
    private TextView mTextView47;
    private TextView mTextView48;
    //磁场位置
    private TextView mTextView51;
    private TextView mTextView52;
    private TextView mTextView53;
    private TextView mTextView54;
    private TextView mTextView56;
    //    连接状态
    private TextView mTextViewTcpStatus;
    //    连接状态
    private TextView mTextViewCarmerStatus;
    //分页
    private NoScrollViewPager mViewPager;
    private QMUITabSegment mTabSegment;
    private HashMap<Pager, HomeController> mPages;

    //边界设置
    private QMUIRoundButton qmuiRoundButton4;
    // 停止运动
    private QMUIRoundButton qmuiRoundButton1;
    //速度固化
    private QMUIRoundButton qmuiRoundButton2;
    //恢复运动
    private QMUIRoundButton qmuiRoundButton3;
    //接收数据
    private QMUIRadiusImageView qmuiRoundButtonrz;
    //速度固化
    private QMUIRoundButton qmuiRoundButtonsugh;

    //呼吸灯
    private MyBreatheView breatheView;
    private ImageView circularView;

    //计时器
    private Chronometer ch;
    private TextView TimeDifference;

    private HomeController homeUtilController;
    private HomeController homeComponentsController;

    private SurfaceView m_osurfaceView = null;
    Handler handler;
    int startStep=0;

    //油门数据存储
    private int VAL_THR;

    //用于存储app参数
    private static final String SET_FILENAME = "ano_set_filename";
    private static String 	HostIP;
    private static int 	HostPort;
    private static Set<String> 	TimerSetList;
    private static int LeftX ;
    private static int LeftY ;
    private static int RightX ;
    private static int RightY ;
    private static int MiddleX ;
    private static int MiddleY ;
    private static int MagneticFieldA ;
    private static int MagneticFieldB ;
    private static int MagneticFieldY1 ;
    private static int MagneticFieldY2;
    private String ipaddr = "";
    private static int ipaddrport ;
    private String username = "";
    private String password = ""; // fill in with appkey
    private static int  carvalTHR;
    private static int  realtimecarvalTHR;

    //摄像头
    private final StartRenderingReceiver receiver = new StartRenderingReceiver();
    /**
     * 返回标记
     */
    private boolean backflag;

    private final String TAG = "CarControlActivity";

    //数据传输参数
    private static long DirectionTimeinterval ;
    private static long ReceiveTimeinterval ;
    private static long SendTimeinterval ;


//    private Timer getsenddate_timer = new Timer( );
    private Timer run_timer = new Timer( );
    private final Handler ui_handler = new Handler();

    //oksocket
    private  IConnectionManager mManager;
    private ConnectionInfo mInfo;
    private OkSocketOptions mOkOptions;
    // 通过静态方法创建ScheduledExecutorService的实例
    private ScheduledExecutorService mScheduledExecutorService = Executors.newScheduledThreadPool(5);
    private int sendtype = 1;

    //回传数据
    public static int VAL_Voltage_24V_Int=0;
    public static String VAL_Voltage_5V="",VAL_Voltage_12V="",VAL_Voltage_24V="",VAL_Wheel_Status="",VAL_TurnWheel_Status="";
    public static String VAL_TurnWheel_A_Angle="",VAL_TurnWheel_B_Angle="",VAL_TurnWheel_C_Angle="",VAL_TurnWheel_D_Angle="",VAL_Updown_Mast_Angle="",VAL_TurnWheel_Mast_Angle="", VAL_TurnWheel_Sailboard_Angle="",VAL_SelfDetection_Status="",VAL_Sport_Mode="",VAL_Sport_Sign="",VAL_X_Ordinate="",VAL_Y_Ordinate="",VAL_Z_Ordinate="";
    public static String VAL_TOF_Front1="",VAL_TOF_Front2="",VAL_TOF_LeftFront="",VAL_TOF_RightFront="",VAL_TOF_Back1="",VAL_TOF_Back2="",VAL_TOF_LeftBack="",VAL_TOF_RightBack="",VAL_Yuntai_Angle="";
    public static String VAL_AttitudeSensor_X="",VAL_AttitudeSensor_Y="",VAL_AttitudeSensor_Z="";
    public static String VAL_Reserved_Fields1="",VAL_Reserved_Fields2="";
    //TCP的body长度
    public static final int  BodyLength= 58;

    //调节阶段用于查看接收的数据
    private TextView textView;
    private boolean updatetextView=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//禁止屏幕变暗
        setContentView(R.layout.activity_carcontrolpad);

        // 设置用于发广播的上下文
        HC_DVRManager.getInstance().setContext(getApplicationContext());
        initSetting();
        initView();
        initTcp();
        initTabs();
        initPagers();

    }


    private void initView() {
        //设置油门
        throttleView=findViewById(R.id.thr_bar);
        throttleView.Value_height=realtimecarvalTHR*100;
        throttleView.Value=realtimecarvalTHR;
        VAL_THR=realtimecarvalTHR;
        //电池
        batteryView=findViewById(R.id.battery_24v);
        //    小车数据
        mTextView11=findViewById(R.id.text11_value);
        mTextView12=findViewById(R.id.text12_value);
        mTextView13=findViewById(R.id.text13_value);
        mTextView14=findViewById(R.id.text14_value);
        mTextView15=findViewById(R.id.text15_value);
        mTextView16=findViewById(R.id.text16_value);

        //    状态数据
        mTextView21=findViewById(R.id.text21_value);
        mTextView22=findViewById(R.id.text22_value);
        mTextView23=findViewById(R.id.text23_value);
        mTextView24=findViewById(R.id.text24_value);
        mTextView25=findViewById(R.id.text25_value);
        mTextView26=findViewById(R.id.text26_value);
        mTextView27=findViewById(R.id.text27_value);
        //    控制数据
        mTextView31=findViewById(R.id.text31_value);
        mTextView32=findViewById(R.id.text32_value);
        mTextView33=findViewById(R.id.text33_value);
        mTextView34=findViewById(R.id.text34_value);
        mTextView35=findViewById(R.id.text35_value);
        //九轴传感器
        mTextView18=findViewById(R.id.text18_value);
        mTextView41=findViewById(R.id.text41_value);
        mTextView42=findViewById(R.id.text42_value);
        mTextView43=findViewById(R.id.text43_value);
        mTextView44=findViewById(R.id.text44_value);
        mTextView45=findViewById(R.id.text45_value);
        mTextView46=findViewById(R.id.text46_value);
        mTextView47=findViewById(R.id.text47_value);
        mTextView48=findViewById(R.id.text48_value);
        //磁场位置
        mTextView51=findViewById(R.id.text51_value);
        mTextView52=findViewById(R.id.text52_value);
        mTextView53=findViewById(R.id.text53_value);
        mTextView54=findViewById(R.id.text54_value);
        mTextView56=findViewById(R.id.text56_value);
        //    连接状态
        mTextViewTcpStatus=findViewById(R.id.tcpstatus);
        mTextViewCarmerStatus=findViewById(R.id.carmerstatus);
        //分页
        mViewPager=findViewById(R.id.main_view_pager);
        mTabSegment=findViewById(R.id.tabs);
        //摄像头
        m_osurfaceView = (SurfaceView) findViewById(R.id.Sur_Player);
        m_osurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                Log.d("DEBUG", getLocalClassName() + " surfaceDestroyed");
                m_osurfaceView.destroyDrawingCache();
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Log.d("DEBUG", getLocalClassName() + " surfaceCreated");
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
                Log.d("DEBUG", getLocalClassName() + " surfaceChanged");
            }
        });
        //呼吸灯
        breatheView=(MyBreatheView) findViewById(R.id.breatheview);
        circularView=(ImageView) findViewById(R.id.circularview);
//        breatheView.setInterval(1000) //设置闪烁间隔时间
//                .setCoreRadius(5f)//设置中心圆半径
//                .setDiffusMaxWidth(20f)//设置闪烁圆的最大半径
//                .setDiffusColor(Color.parseColor("#1afa29"))//设置闪烁圆的颜色
//                .setCoreColor(Color.parseColor("#d81e06"));//设置中心圆的颜色
        breatheView.setCoreRadius(20f)//设置中心圆半径
                .setDiffusMaxWidth(5f);//设置闪烁圆的最大半径
        circularView.setImageResource(R.mipmap.circulargrey);
        circularView.setVisibility(VISIBLE);
        //获取计时器组件
        ch = (Chronometer) findViewById(R.id.test);
        TimeDifference = (TextView) findViewById(R.id.timedifference);

        //摄像头重连
        QMUIRadiusImageView qmuiRadiusCarmerconnet = (QMUIRadiusImageView) findViewById(R.id.btn_carmerconnet);
        qmuiRadiusCarmerconnet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlay();
            }
        });
        //摄像头初始化
        handler=new Handler();
        Runnable runnable=new Runnable(){
            @Override
            public void run() {
                startPlay();
            }
        };
        handler.postDelayed(runnable, 5000);

        //更新页面接收数据
        ui_handler.postDelayed(ui_task, ReceiveTimeinterval);
//        //获取陀螺仪数据的任务
//        getsenddate_timer.schedule(getsenddate_task,1000,DirectionTimeinterval);
        // 发送陀螺仪数据的任务
        // 循环任务，按照上一次任务的发起时间计算下一次任务的开始时间
        mScheduledExecutorService.scheduleAtFixedRate(send_task, 1000, DirectionTimeinterval, TimeUnit.MILLISECONDS);
        //发送定时任务
        run_timer.schedule(run_task,1000,60000);
        // 停止运动
        qmuiRoundButton1 = (QMUIRoundButton) findViewById(R.id.button1);
        qmuiRoundButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DefaultSendBean bean = new DefaultSendBean();
                bean.setThreebyte((byte) 0x06);
                bean.setFourbyte((byte) 0xA5);
                SendData_ByteOnce(bean);
                LogTool.d("停止运动",StringTool.byteToString(bean.parse()));
            }
        });

        //恢复运动 禁止运动
        qmuiRoundButton2 = (QMUIRoundButton) findViewById(R.id.button2);
        qmuiRoundButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DefaultSendBean bean = new DefaultSendBean();
//                bean.setThreebyte((byte) 0x60);
//                bean.setFourbyte((byte) 0xA5);
//                SendData_ByteOnce(bean);
//                LogTool.d("恢复运动",StringTool.byteToString(bean.parse()));
                setButtonEnble(false);
            }
        });

        //查看接收数据
        textView = new TextView(CarControlActivity.this);
        textView.setLineSpacing(QMUIDisplayHelper.dp2px(CarControlActivity.this, 4), 1.0f);
        int padding = QMUIDisplayHelper.dp2px(CarControlActivity.this, 20);
        textView.setPadding(padding, padding, padding, padding);
        textView.setText("数据接收中...");
        textView.setTextColor(ContextCompat.getColor(CarControlActivity.this, R.color.app_color_description));
        qmuiRoundButton3 = (QMUIRoundButton) findViewById(R.id.button3);
        qmuiRoundButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatetextView=true;
                QMUIPopup mNormalPopup = QMUIPopups.popup(CarControlActivity.this, QMUIDisplayHelper.dp2px(CarControlActivity.this, 350), QMUIDisplayHelper.dp2px(CarControlActivity.this, 120))
                        .preferredDirection(QMUIPopup.DIRECTION_BOTTOM)
                        .view(textView)
                        .edgeProtection(QMUIDisplayHelper.dp2px(CarControlActivity.this, 20))
                        .dimAmount(0.6f)
                        .skinManager(QMUISkinManager.defaultInstance(CarControlActivity.this))
                        .animStyle(QMUIPopup.ANIM_GROW_FROM_CENTER)
                        .onDismiss(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                updatetextView=false;
                            }
                        })
                        .show(v);
            }
        });

        //边界设置
        qmuiRoundButton4 = (QMUIRoundButton) findViewById(R.id.button4);
        qmuiRoundButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置密码
                final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(CarControlActivity.this);
                builder.setTitle("密码")
                        .setPlaceholder("请输入管理员密码")
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
                                if (text.equals(getResources().getString(R.string.admin_password))) {
                                    dialog.dismiss();
                                    final String[] itemstitle = new String[]{"左端停止X坐标", "左端停止Y坐标", "右端停止X坐标","右端停止Y坐标", "中间停止X坐标", "中间停止Y坐标"};
                                    final String[] itemstag = new String[]{"Set_LeftX", "Set_LeftY", "Set_RightX","Set_RightY", "Set_MiddleX", "Set_MiddleY"};
                                    final byte[] itemsbyte = new byte[]{(byte) 0x0F, (byte) 0x10, (byte) 0x12,(byte) 0x13, (byte) 0x14, (byte) 0x15};
                                    final String[] itemsshow = new String[]{"左端停止X坐标 "+String.valueOf(LeftX/10.0)+" m", "左端停止Y坐标 "+String.valueOf(LeftY/10.0)+" m", "右端停止X坐标 "+String.valueOf(RightX/10.0)+" m","右端停止Y坐标 "+String.valueOf(RightY/10.0)+" m", "中间停止X坐标 "+String.valueOf(MiddleX/10.0)+" m", "中间停止Y坐标 "+String.valueOf(MiddleY/10.0)+" m"};
                                    new QMUIDialog.MenuDialogBuilder(CarControlActivity.this)
                                            .setSkinManager(QMUISkinManager.defaultInstance(CarControlActivity.this))
                                            .addItems(itemsshow, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, final int which) {
                                                    dialog.dismiss();
                                                    final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(CarControlActivity.this);
                                                    builder.setTitle(itemstitle[which])
                                                            .setPlaceholder("请输入您的坐标值(m)")
                                                            .setInputType(InputType.TYPE_CLASS_TEXT)
                                                            .setDefaultText(itemsshow[which].split(" ")[1])
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
                                                                        DefaultSendBean bean = new DefaultSendBean();
                                                                        bean.setThreebyte(itemsbyte[which]);
                                                                        bean.setFourbyte((byte)( Integer.parseInt(new java.text.DecimalFormat("0").format((Float.valueOf(text)*10.0))) & 0xFF));
                                                                        SendData_ByteOnce(bean);
                                                                        LogTool.d("边界设置",StringTool.byteToString(bean.parse()));
                                                                        SaveSharedPreferencesInt(itemstag[which],Integer.parseInt(new java.text.DecimalFormat("0").format((Float.valueOf(text)*10.0))));
                                                                        initSetting();
                                                                    }else {
                                                                        Toast.makeText(CarControlActivity.this, "请输入有效的坐标值,精度保留一位小数" , Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }
                                                            })
                                                            .create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show();
                                                }
                                            })
                                            .create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show();
                                } else {
                                    Toast.makeText(CarControlActivity.this, "请输入正确的密码", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show();
            }
        });

        //急停
        qmuiRoundButtonrz = (QMUIRadiusImageView) findViewById(R.id.btn_rz);
//        qmuiRoundButtonrz.setBorderColor(
//                ContextCompat.getColor(CarControlActivity.this, R.color.radiusImageView_border_color));
//        qmuiRoundButtonrz.setBorderWidth(QMUIDisplayHelper.dp2px(CarControlActivity.this, 2));
        qmuiRoundButtonrz.setCornerRadius(QMUIDisplayHelper.dp2px(CarControlActivity.this, 10));
        qmuiRoundButtonrz.setSelectedMaskColor(
                ContextCompat.getColor(CarControlActivity.this, R.color.radiusImageView_selected_mask_color));
        qmuiRoundButtonrz.setSelectedBorderColor(
                ContextCompat.getColor(CarControlActivity.this, R.color.radiusImageView_selected_border_color));
        qmuiRoundButtonrz.setSelectedBorderWidth(QMUIDisplayHelper.dp2px(CarControlActivity.this, 3));
        qmuiRoundButtonrz.setTouchSelectModeEnabled(false);
        qmuiRoundButtonrz.setCircle(true);
        qmuiRoundButtonrz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DefaultSendBean bean = new DefaultSendBean();
                bean.setThreebyte((byte) 0x04);
                bean.setFourbyte((byte) 0xA5);
                SendData_ByteOnce(bean);
                setButtonEnble(false);
                LogTool.d("急停",StringTool.byteToString(bean.parse()));
            }
        });
        qmuiRoundButtonrz.setClickable(false);

        //使能运动
        QMUIRoundButton qmuiRoundButtonsn = (QMUIRoundButton) findViewById(R.id.btn_sn);
        qmuiRoundButtonsn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DefaultSendBean bean = new DefaultSendBean();
                bean.setThreebyte((byte) 0x07);
                bean.setFourbyte((byte) 0xA5);
                SendData_ByteOnce(bean);
                setButtonEnble(true);
                if(mTabSegment.getSelectedIndex()==Pager.Manual.ordinal()){
                    Start_Chronometer();
                }
                LogTool.d("使能运动",StringTool.byteToString(bean.parse()));
            }
        });

        //速度固化
        qmuiRoundButtonsugh = (QMUIRoundButton) findViewById(R.id.btn_sugh);
        qmuiRoundButtonsugh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //当前油门
                int new_VAL_THR = ThrottleView.Value;
                int VAL_THR=new_VAL_THR>=5?5:new_VAL_THR;
                SaveSharedPreferencesInt("Set_CarvalTHR",VAL_THR);
                initSetting();
            }
        });
    }

    private void initTabs() {
        QMUITabBuilder builder = mTabSegment.tabBuilder();

        builder.setTypeface(null, Typeface.DEFAULT_BOLD);
        builder.setSelectedIconScale(1.2f)
                .setTextSize(QMUIDisplayHelper.sp2px(this, 13), QMUIDisplayHelper.sp2px(this, 13))
                .setDynamicChangeIconColor(false);

        QMUITab manual = builder
                .setNormalDrawable(androidx.core.content.ContextCompat.getDrawable(this, R.mipmap.icon_tabbar_manual))
                .setSelectedDrawable(androidx.core.content.ContextCompat.getDrawable(this, R.mipmap.icon_tabbar_manual_selected))
                .setText("手动模式")
                .build(this);
        QMUITab show = builder
                .setNormalDrawable(androidx.core.content.ContextCompat.getDrawable(this, R.mipmap.icon_tabbar_show))
                .setSelectedDrawable(androidx.core.content.ContextCompat.getDrawable(this, R.mipmap.icon_tabbar_show_selected))
                .setText("演示模式")
                .build(this);

        mTabSegment.addTab(show)
                .addTab(manual);
        mTabSegment.addOnTabSelectedListener(new QMUITabSegment.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int index) {
                //演示模式
                if(index==0){
                    DefaultSendBean bean = new DefaultSendBean();
                    bean.setThreebyte((byte) 0x0B);
                    bean.setFourbyte((byte) 0x02);
                    SendData_ByteOnce(bean);
                    //发送演示速度
                    handcount=0;
                    sendhandler.postDelayed(runnable,delayMillis);//定期执行
                    LogTool.d("演示模式",StringTool.byteToString(bean.parse()));
                }
                //手动模式
                if(index==1){
                    DefaultSendBean bean = new DefaultSendBean();
                    bean.setThreebyte((byte) 0x0B);
                    bean.setFourbyte((byte) 0x01);
                    SendData_ByteOnce(bean);
                    LogTool.d("手动模式",StringTool.byteToString(bean.parse()));
                }
            }

            @Override
            public void onTabUnselected(int index) {
            }

            @Override
            public void onTabReselected(int index) {
                //演示模式
                if(index==0){
                    DefaultSendBean bean = new DefaultSendBean();
                    bean.setThreebyte((byte) 0x0B);
                    bean.setFourbyte((byte) 0x02);
                    SendData_ByteOnce(bean);
                    //发送演示速度
                    handcount=0;
                    sendhandler.postDelayed(runnable,delayMillis);//定期执行
                    LogTool.d("演示模式",StringTool.byteToString(bean.parse()));
                }
                //手动模式
                if(index==1){
                    DefaultSendBean bean = new DefaultSendBean();
                    bean.setThreebyte((byte) 0x0B);
                    bean.setFourbyte((byte) 0x01);
                    SendData_ByteOnce(bean);
                    LogTool.d("手动模式",StringTool.byteToString(bean.parse()));
                }
            }

            @Override
            public void onDoubleTap(int index) {

            }
        });
    }

    private int handcount=0;
    private final int delayMillis = 100;
    private Handler sendhandler = new Handler();
    private Runnable runnable  = new Runnable(){//推送runnable，定期2s执行一次
        @Override
        public void run() {
            if (handcount==3){
                sendhandler.removeCallbacks(runnable);
            }else {
                DefaultSendBean bean = new DefaultSendBean();
                bean.setThreebyte((byte) 0x1D);
                bean.setFourbyte((byte)(carvalTHR & 0xFF));
                SendData_ByteOnce(bean);
                LogTool.d("演示速度",StringTool.byteToString(bean.parse()));
                //边界设置
                final byte[] itemsbyte = new byte[]{(byte) 0x0F, (byte) 0x10, (byte) 0x12,(byte) 0x13, (byte) 0x14, (byte) 0x15};

                for(int i = 0; i<6; i++){
                    try {
                        Thread.sleep(delayMillis);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    DefaultSendBean beanset = new DefaultSendBean();
                    beanset.setThreebyte(itemsbyte[i]);
                    switch (i){
                        case 0:
                            beanset.setFourbyte((byte)( LeftX & 0xFF));
                            break;
                        case 1:
                            beanset.setFourbyte((byte)( LeftY & 0xFF));
                            break;
                        case 2:
                            beanset.setFourbyte((byte)( RightX & 0xFF));
                            break;
                        case 3:
                            beanset.setFourbyte((byte)( RightY & 0xFF));
                            break;
                        case 4:
                            beanset.setFourbyte((byte)( MiddleX & 0xFF));
                            break;
                        case 5:
                            beanset.setFourbyte((byte)( MiddleY & 0xFF));
                            break;
                    }
                    SendData_ByteOnce(beanset);
                    LogTool.d("边界设置",StringTool.byteToString(beanset.parse()));

                }
                //发送磁场角度
                List<Integer> MagneticFieldlist=new ArrayList<Integer>();
                MagneticFieldlist.add(MagneticFieldA);
                MagneticFieldlist.add(MagneticFieldB);
                MagneticFieldlist.add(MagneticFieldY1);
                MagneticFieldlist.add(MagneticFieldY2);
                for(int i=0;i<MagneticFieldlist.size();i++){
                    byte[] bytes=StringTool.toLH(MagneticFieldlist.get(i));
                    DefaultSendBean bean1 = new DefaultSendBean();
                    DefaultSendBean bean2 = new DefaultSendBean();
                    switch (i){
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
                    try {
                        Thread.sleep(delayMillis);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    SendData_ByteOnce(bean1);
                    try {
                        Thread.sleep(delayMillis);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    SendData_ByteOnce(bean2);
                    LogTool.d("角度设置",StringTool.byteToString(bean1.parse()));
                    LogTool.d("角度设置",StringTool.byteToString(bean2.parse()));
                }
                handcount++;
                sendhandler.postDelayed(runnable, delayMillis);
            }
        }
    };

    private void initPagers() {
        HomeController.HomeControlListener listener = new HomeController.HomeControlListener() {
            @Override
            public void sendDataByteOnce(DefaultSendBean bean) {
                SendData_ByteOnce(bean);
            }

            @Override
            public void startChronometer() {
                Start_Chronometer();
            }
        };

        mPages = new HashMap<>();

        homeUtilController = new ShowController(this);
        homeUtilController.setHomeControlListener(listener);
        mPages.put(Pager.Show, homeUtilController);

        homeComponentsController = new ManualController(this);
        homeComponentsController.setHomeControlListener(listener);
        mPages.put(Pager.Manual, homeComponentsController);

        mViewPager.setAdapter(mPagerAdapter);
        mTabSegment.setupWithViewPager(mViewPager, false);
    }

    private void initSetting() {
        // 得到SP对象   
        SharedPreferences sp = getSharedPreferences(SET_FILENAME, MODE_PRIVATE);
        // 从存储的XML文件中根据相应的键获取数据，没有数据就返回默认值    
        HostIP = sp.getString("Set_HostIP", getResources().getString(R.string.set_hostip));
        HostPort = sp.getInt("Set_HostPort",getResources().getInteger(R.integer.set_hostport));
        SendTimeinterval = sp.getInt("Set_SendTimeinterval",50);
        ReceiveTimeinterval = sp.getInt("Set_ReceiveTimeinterval",50);
        DirectionTimeinterval = sp.getInt("Set_DirectionTimeinterval",50);
        TimerSetList = sp.getStringSet("Set_TimerSet1",new HashSet<String>() );
        // 从存储的XML文件中根据相应的键获取数据，没有数据就返回默认值    
        LeftX = sp.getInt("Set_LeftX", 0);
        LeftY = sp.getInt("Set_LeftY",0);
        RightX = sp.getInt("Set_RightX",0);
        RightY = sp.getInt("Set_RightY",0);
        MiddleX = sp.getInt("Set_MiddleX",0);
        MiddleY = sp.getInt("Set_MiddleY",0);
        MagneticFieldA = sp.getInt("Set_MagneticFieldA",getResources().getInteger(R.integer.set_magneticfielda));
        MagneticFieldB = sp.getInt("Set_MagneticFieldB",getResources().getInteger(R.integer.set_magneticfieldb));
        MagneticFieldY1 = sp.getInt("Set_MagneticFieldY1",getResources().getInteger(R.integer.set_magneticfieldy1));
        MagneticFieldY2 = sp.getInt("Set_MagneticFieldY2",getResources().getInteger(R.integer.set_magneticfieldy2));
        ipaddr = sp.getString("Set_CameraIP", getResources().getString(R.string.set_cameraip));
        ipaddrport = sp.getInt("Set_CameraPort",getResources().getInteger(R.integer.set_cameraport));
        username = sp.getString("Set_CameraUserName",getResources().getString(R.string.set_camerausername));
        password = sp.getString("Set_CameraPassWord",getResources().getString(R.string.set_camerapassword));
        carvalTHR=sp.getInt("Set_CarvalTHR", 5);
        realtimecarvalTHR=sp.getInt("Set_RealtimeCarvalTHR", 0);
    }

    private static  byte heard1;
    private static  boolean socketstatus=true;

    private void initTcp() {
        final Handler handler = new Handler();
        mInfo = new ConnectionInfo(HostIP, HostPort);
        mOkOptions = new OkSocketOptions.Builder()
                .setReconnectionManager(OkSocketOptions.getDefault().getReconnectionManager())
//                .setReconnectionManager(new NoneReconnect())
                .setConnectTimeoutSecond(10)
                .setCallbackThreadModeToken(new OkSocketOptions.ThreadModeToken() {
                    @Override
                    public void handleCallbackEvent(ActionDispatcher.ActionRunnable runnable) {
                        handler.post(runnable);
                    }
                })
                .setReaderProtocol(new IReaderProtocol() {
                    @Override
                    public int getHeaderLength() {
                        if(socketstatus){
                            return 2;
                        }else {
                            return 1;
                        }
                    }

                    @Override
                    public int getBodyLength(byte[] header, ByteOrder byteOrder) {
                        if(socketstatus){
                            if(header[0]==(byte) 0xAA&&header[1]==(byte) 0x55){
                                return BodyLength;
                            }else {
                                socketstatus=false;
                                heard1=header[0];
                                return 0;
                            }
                        }else {
                            if(heard1==(byte) 0xAA&&header[0]==(byte) 0x55){
                                socketstatus=true;
                                return BodyLength;
                            }else {
                                heard1=header[0];
                                return 0;
                            }
                        }
                    }
                })
                .build();
        mManager = OkSocket.open(mInfo).option(mOkOptions);
        mManager.registerReceiver(adapter);
        mManager.connect();
    }

    private DeviceBean getDeviceBean() {
        DeviceBean bean = new DeviceBean();
        bean.setIP(ipaddr);
        bean.setPort(String.valueOf(ipaddrport));
        bean.setUserName(username);
        bean.setPassWord(password);
        bean.setChannel("0");
        return bean;
    }

    protected void startPlay() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(HC_DVRManager.ACTION_START_RENDERING);
        filter.addAction(HC_DVRManager.ACTION_DVR_OUTLINE);
        registerReceiver(receiver, filter);

        mTextViewCarmerStatus.setText("连接中……");
        mTextViewCarmerStatus.setTextColor(getResources().getColor(R.color.app_color_theme_2));
        if (backflag) {
            backflag = false;
            new Thread() {
                @Override
                public void run() {
                    HC_DVRManager.getInstance().setSurfaceHolder(
                            m_osurfaceView.getHolder());
                    HC_DVRManager.getInstance().realPlay();
                }
            }.start();
        } else {
            new Thread() {
                @Override
                public void run() {
                    HC_DVRManager.getInstance().setDeviceBean(getDeviceBean());
                    HC_DVRManager.getInstance().setSurfaceHolder(
                            m_osurfaceView.getHolder());
                    HC_DVRManager.getInstance().initSDK();
                    HC_DVRManager.getInstance().loginDevice();
                    HC_DVRManager.getInstance().realPlay();
                }
            }.start();
        }
    }

    public void SendData_ByteOnce(DefaultSendBean bean) {
        if(mManager.isConnect()){
            mManager.send(bean);
        }else {
            CarControlActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    mTextViewTcpStatus.setText("未连接");
                    mTextViewTcpStatus.setTextColor(getResources().getColor(R.color.app_color_theme_2));
                }
            });
        }
    }

    private int uitime;
    private final Runnable ui_task = new Runnable() {
        @Override
        public void run() {
            //电池显示
            batteryView.setPower(VAL_Voltage_24V_Int);
            //小车数据
            mTextView11 .setText(VAL_X_Ordinate);
            mTextView12.setText(VAL_Y_Ordinate);
            mTextView13.setText(VAL_Z_Ordinate);
            mTextView14.setText(VAL_SelfDetection_Status);
            mTextView15.setText(VAL_Sport_Mode);
            if(VAL_Sport_Sign.equals("正常")){
                mTextView16.setTextColor(getResources().getColor(R.color.app_color_theme_4));
                mTextView16.setText(VAL_Sport_Sign);
            }else {
                mTextView16.setTextColor(getResources().getColor(R.color.app_color_theme_1));
                mTextView16.setText(VAL_Sport_Sign);
            }
            //    状态数据
            mTextView21.setText(VAL_TurnWheel_A_Angle);
            mTextView22.setText(VAL_TurnWheel_B_Angle);
            mTextView23.setText(VAL_TurnWheel_C_Angle);
            mTextView24.setText(VAL_TurnWheel_D_Angle);
            mTextView25.setText(VAL_Updown_Mast_Angle);
            mTextView26.setText(VAL_TurnWheel_Mast_Angle);
            mTextView27.setText(VAL_TurnWheel_Sailboard_Angle);
            //    控制数据
            mTextView31.setText(VAL_Voltage_24V);
            mTextView32.setText(VAL_Voltage_12V);
            mTextView33.setText(VAL_Voltage_5V);
            mTextView34.setText(VAL_Wheel_Status);
            mTextView35.setText(VAL_TurnWheel_Status);
            //九轴传感器
            mTextView18.setText(VAL_TOF_Front1);
            mTextView41.setText(VAL_TOF_Front2);
            mTextView42.setText(VAL_TOF_LeftFront);
            mTextView43.setText(VAL_TOF_RightFront);
            mTextView44.setText(VAL_TOF_Back1);
            mTextView45.setText(VAL_TOF_Back2);
            mTextView46.setText(VAL_TOF_LeftBack);
            mTextView47.setText(VAL_TOF_RightBack);
            mTextView48.setText(VAL_Yuntai_Angle);
            //磁场位置
            mTextView51.setText(VAL_AttitudeSensor_X);
            mTextView52.setText(VAL_AttitudeSensor_Y);
            mTextView53.setText(VAL_AttitudeSensor_Z);
            mTextView54.setText(VAL_Reserved_Fields1);
            mTextView56.setText(VAL_Reserved_Fields2);

            //判断桅杆起立的按钮是否生效
            if( !CarControlActivity.VAL_TurnWheel_Sailboard_Angle.equals("")){
                if(CarStatus){
                    if (Float.valueOf(CarControlActivity.VAL_TurnWheel_Sailboard_Angle.split(" ")[0]) >=60.0f){
                        homeComponentsController.setButtonEnble(false,"桅杆起立");
                        homeComponentsController.setButtonEnble(false,"云台向左");
                        homeComponentsController.setButtonEnble(false,"云台向右");
                    }else {
                        if(CarStatus){
                            homeComponentsController.setButtonEnble(true,"桅杆起立");
                            homeComponentsController.setButtonEnble(true,"云台向左");
                            homeComponentsController.setButtonEnble(true,"云台向右");
                        }
                    }
                }
            }
            // TODO Auto-generated method stub
            int uidelayMillis = 30;
            if(uitime>1000){
                breatheView.onStop();
                circularView.setImageResource(R.mipmap.circulargrey);
                circularView.setVisibility(VISIBLE);
            }else {
                if(VAL_Sport_Mode.equals("低功耗模式")){
                    if(circularView.getVisibility()==VISIBLE){
                        circularView.setVisibility(INVISIBLE);
                        breatheView.setInterval(2000l);
                        breatheView.onStart();
                    }
                    if(breatheView.getInterval()!=2000l){
                        breatheView.setInterval(2000l);
                        breatheView.onStart();
                    }
                }
                else if(VAL_Sport_Mode.equals("")){
                    breatheView.onStop();
                    circularView.setImageResource(R.mipmap.circulargrey);
                    circularView.setVisibility(VISIBLE);
                }else {
                    if(circularView.getVisibility()==VISIBLE){
                        circularView.setVisibility(INVISIBLE);
                        breatheView.setInterval(1000l);
                        breatheView.onStart();
                    }
                    if(breatheView.getInterval()!=1000l){
                        breatheView.setInterval(1000l);
                        breatheView.onStart();
                    }
                }
                uitime=uitime+uidelayMillis;
            }
            ui_handler.postDelayed(this, uidelayMillis);
        }
    };


    Runnable send_task = new Runnable( ) {
        public void run ( )
        {
            //判断车子状态为禁止直接返回
            if(!CarStatus){
                return;
            }
            //是否发送速度
            int new_VAL_THR = ThrottleView.Value;
            if(new_VAL_THR!=VAL_THR){
                VAL_THR=new_VAL_THR;
                SaveSharedPreferencesInt("Set_RealtimeCarvalTHR",VAL_THR);
                DefaultSendBean bean=new DefaultSendBean();
                bean.setThreebyte((byte) 0x03);
                bean.setFourbyte((byte)(VAL_THR & 0xFF));
                SendData_ByteOnce(bean);
                LogTool.d("速度",StringTool.byteToString(bean.parse()));
            }
            int[] controllerangle=MathTool.getRad(SingleRockerView.Value_X,SingleRockerView.Value_Y);
            if(MathTool.isDouble(controllerangle)){
                if(sendtype==1){
                    if(controllerangle[0]==1){
                        DefaultSendBean bean=new DefaultSendBean();
                        bean.setThreebyte((byte) 0x0C);
                        bean.setFourbyte((byte) 0x01);
                        SendData_ByteOnce(bean);
                        LogTool.d("向前",StringTool.byteToString(bean.parse()));
                    }
                    if(controllerangle[1]==1){
                        DefaultSendBean bean=new DefaultSendBean();
                        bean.setThreebyte((byte) 0x0C);
                        bean.setFourbyte((byte) 0x02);
                        SendData_ByteOnce(bean);
                        LogTool.d("向后",StringTool.byteToString(bean.parse()));
                    }
                    sendtype=2;
                }else {
                    if(controllerangle[2]==1){
                        DefaultSendBean bean=new DefaultSendBean();
                        bean.setThreebyte((byte) 0x0D);
                        bean.setFourbyte((byte)(controllerangle[4] & 0xFF));
                        SendData_ByteOnce(bean);
                        LogTool.d("向左",StringTool.byteToString(bean.parse()));
                    }
                    if(controllerangle[3]==1){
                        DefaultSendBean bean=new DefaultSendBean();
                        bean.setThreebyte((byte) 0x0E);
                        bean.setFourbyte((byte)(controllerangle[4] & 0xFF));
                        SendData_ByteOnce(bean);
                        LogTool.d("向右",StringTool.byteToString(bean.parse()));
                    }
                    sendtype=1;
                }
            }else {
                if(controllerangle[0]==1){
                    DefaultSendBean bean=new DefaultSendBean();
                    bean.setThreebyte((byte) 0x0C);
                    bean.setFourbyte((byte) 0x01);
                    SendData_ByteOnce(bean);
                    LogTool.d("向前",StringTool.byteToString(bean.parse()));
                }
                if(controllerangle[1]==1){
                    DefaultSendBean bean=new DefaultSendBean();
                    bean.setThreebyte((byte) 0x0C);
                    bean.setFourbyte((byte) 0x02);
                    SendData_ByteOnce(bean);
                    LogTool.d("向后",StringTool.byteToString(bean.parse()));
                }
                if(controllerangle[2]==1){
                    DefaultSendBean bean=new DefaultSendBean();
                    bean.setThreebyte((byte) 0x0D);
                    bean.setFourbyte((byte)(controllerangle[4] & 0xFF));
                    SendData_ByteOnce(bean);
                    LogTool.d("向左",StringTool.byteToString(bean.parse()));
                }
                if(controllerangle[3]==1){
                    DefaultSendBean bean=new DefaultSendBean();
                    bean.setThreebyte((byte) 0x0E);
                    bean.setFourbyte((byte)(controllerangle[4] & 0xFF));
                    SendData_ByteOnce(bean);
                    LogTool.d("向右",StringTool.byteToString(bean.parse()));
                }
            }
        }
    };

    TimerTask run_task = new TimerTask( ) {
        public void run ( )
        {
            if(mTabSegment.getSelectedIndex()==0){
                //是否发送速度
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf= new SimpleDateFormat("HH:mm");
                String dateStr = sdf.format(calendar.getTime());
                if(TimerSetList.contains(dateStr)){
                    DefaultSendBean bean=new DefaultSendBean();
                    bean.setThreebyte((byte) 0x02);
                    bean.setFourbyte((byte)0xA5);
                    SendData_ByteOnce(bean);
                    LogTool.d("倒计时演示",StringTool.byteToString(bean.parse()));
                }
            }
            TimeDifference.post(new Runnable() {
                @Override
                public void run() {
                    TimeDifference.setText(StringTool.TimeDifference(TimerSetList));
                }
            });
        }
    };

    private SocketActionAdapter adapter = new SocketActionAdapter() {
        @Override
        public void onSocketConnectionSuccess(ConnectionInfo info, String action) {
            mTextViewTcpStatus.setText("已连接");
            mTextViewTcpStatus.setTextColor(getResources().getColor(R.color.app_color_theme_4));
//            OkSocket.open(info)
//                    .getPulseManager()
//                    .pulse();//开始心跳,开始心跳后,心跳管理器会自动进行心跳触发
            DefaultSendBean bean=new DefaultSendBean();
            bean.setThreebyte((byte) 0x03);
            bean.setFourbyte((byte)(VAL_THR & 0xFF));
            SendData_ByteOnce(bean);
            LogTool.d("速度",StringTool.byteToString(bean.parse()));
        }

        @Override
        public void onSocketDisconnection(ConnectionInfo info, String action, Exception e) {
            if (e != null) {
                if (e instanceof RedirectException) {
//                    logSend("正在重定向连接(Redirect Connecting)...");
                    mManager.switchConnectionInfo(((RedirectException) e).redirectInfo);
                    mManager.connect();
                } else {
//                    logSend("异常断开(Disconnected with exception):" + e.getMessage());
                    mTextViewTcpStatus.setText("异常断开");
                    mTextViewTcpStatus.setTextColor(getResources().getColor(R.color.app_color_theme_2));
                }
            } else {
//                logSend("正常断开(Disconnect Manually)");
                mTextViewTcpStatus.setText("连接断开");
                mTextViewTcpStatus.setTextColor(getResources().getColor(R.color.app_color_theme_2));
            }
        }

        @Override
        public void onSocketConnectionFailed(ConnectionInfo info, String action, Exception e) {
            mTextViewTcpStatus.setText("连接失败");
            mTextViewTcpStatus.setTextColor(getResources().getColor(R.color.app_color_theme_2));
        }

        @Override
        public void onSocketReadResponse(ConnectionInfo info, String action, OriginalData data) {
//            if(mManager != null ){//是否是心跳返回包,需要解析服务器返回的数据才可知道
//                //喂狗操作
//                mManager.getPulseManager().feed();
//            }
            uitime=0;
            if(updatetextView){
                String responseString=StringTool.byteToString(data.getHeadBytes())+StringTool.byteToString(data.getBodyBytes());
                textView.setText(responseString);
                Log.d(TAG,responseString);
            }
            if(data.getBodyBytes().length==BodyLength){
                StringTool.getReadString(data.getHeadBytes(),data.getBodyBytes());
            }
        }

    };

    //标签页适配器
    private PagerAdapter mPagerAdapter = new PagerAdapter() {
        private int mChildCount = 0;
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
        @Override
        public int getCount() {
            return mPages.size();
        }
        @Override
        public Object instantiateItem(final ViewGroup container, int position) {
            HomeController page = mPages.get(Pager.getPagerFromPositon(position));
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            container.addView(page, params);
            return page;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
        @Override
        public int getItemPosition(Object object) {
            if (mChildCount == 0) {
                return POSITION_NONE;
            }
            return super.getItemPosition(object);
        }
        @Override
        public void notifyDataSetChanged() {
            mChildCount = getCount();
            super.notifyDataSetChanged();
        }
    };

    private void SaveSharedPreferencesInt(String tag,int value ) {
        // 得到编辑器对象   
        SharedPreferences.Editor editor = this.getSharedPreferences(SET_FILENAME, MODE_PRIVATE).edit();
        // 存入键值对   
        editor.putInt(tag,value);
        // 将内存中的数据写到XML文件中去   
        editor.commit();
    }


    private boolean CarStatus=false;
    private void setButtonEnble(boolean enble){
        CarStatus=enble;
        homeComponentsController.setButtonEnble(enble);
        homeUtilController.setButtonEnble(enble);
        qmuiRoundButton1.setEnabled(enble);
        qmuiRoundButton1.setTextColor(enble? getResources().getColor(R.color.white):getResources().getColor(R.color.app_color_description));
        qmuiRoundButton2.setEnabled(enble);
        qmuiRoundButton2.setTextColor(enble? getResources().getColor(R.color.white):getResources().getColor(R.color.app_color_description));
        qmuiRoundButton3.setEnabled(enble);
        qmuiRoundButton3.setTextColor(enble? getResources().getColor(R.color.white):getResources().getColor(R.color.app_color_description));
        qmuiRoundButton4.setEnabled(enble);
        qmuiRoundButton4.setTextColor(enble? getResources().getColor(R.color.white):getResources().getColor(R.color.app_color_description));
        qmuiRoundButtonrz.setClickable(enble);
        qmuiRoundButtonrz.setTouchSelectModeEnabled(enble);
        qmuiRoundButtonsugh.setEnabled(enble);
        qmuiRoundButtonsugh.setTextColor(enble? getResources().getColor(R.color.white):getResources().getColor(R.color.app_color_description));
    }

    private void Start_Chronometer(){
        //设置开始计时时间
        ch.setBase(SystemClock.elapsedRealtime());
        int hour = (int) ((SystemClock.elapsedRealtime() - ch.getBase()) / 1000 / 60);
        ch.setFormat("0"+String.valueOf(hour)+":%s");
        //启动计时器
        ch.start();
    }


    enum Pager {
        Show, Manual;
        public static Pager getPagerFromPositon(int position) {
            switch (position) {
                case 0:
                    return Show;
                case 1:
                    return Manual;
                default:
                    return Show;
            }
        }
    }
    //退出提醒
    @Override
    public void onBackPressed() {
        new QMUIDialog.MessageDialogBuilder(CarControlActivity.this)
                .setTitle("退出提醒")
                .setMessage("确定退出控制器？")
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction(0, "确定", QMUIDialogAction.ACTION_PROP_POSITIVE, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                        new Thread() {
                            @Override
                            public void run() {
                                HC_DVRManager.getInstance().stopPlay();
                            }
                        }.start();
                        finish();
                    }
                })
                .create().show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mScheduledExecutorService.shutdown();
        if (run_timer != null)
        {
            run_timer.cancel( );
            run_timer = null;
        }
        if (mManager != null) {
            mManager.disconnect();
            mManager.unRegisterReceiver(adapter);
        }

        new Thread() {
            @Override
            public void run() {
                HC_DVRManager.getInstance().logoutDevice();
                HC_DVRManager.getInstance().freeSDK();
            }
        }.start();

    }

    // 广播接收器
    private class StartRenderingReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (HC_DVRManager.ACTION_START_RENDERING.equals(intent.getAction())) {
                mTextViewCarmerStatus.setText("已连接");
                mTextViewCarmerStatus.setTextColor(getResources().getColor(R.color.app_color_theme_4));
            }
            if (HC_DVRManager.ACTION_DVR_OUTLINE.equals(intent.getAction())) {
                mTextViewCarmerStatus.setText("连接失败");
                mTextViewCarmerStatus.setTextColor(getResources().getColor(R.color.app_color_theme_2));
            }
        }
    }
}

