package com.example.yqc.activity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.FrameLayout;
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
import com.example.yqc.customview.MyRoundButton;
import com.example.yqc.customview.NoScrollViewPager;
import com.example.yqc.customview.PlaySurfaceView;
import com.example.yqc.customview.SingleRockerView;
import com.example.yqc.customview.ThrottleView;
import com.example.yqc.jna.HCNetSDKJNAInstance;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;


public class CarControlActivity extends AppCompatActivity  implements SurfaceHolder.Callback {
    private BatteryView batteryView;
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
    private QMUIRoundButton qmuiRoundButtonrz;

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
    private String ipaddr = "";
    private static int ipaddrport ;
    private String username = "";
    private String password = ""; // fill in with appkey

    //摄像头
    private int m_iLogID = -1; // return by NET_DVR_Login_v30
    private int m_iPlayID = -1; // return by NET_DVR_RealPlay_V40
    private int m_iPlaybackID = -1; // return by NET_DVR_PlayBackByTime
    private NET_DVR_DEVICEINFO_V30 m_oNetDvrDeviceInfoV30 = null;
    private int m_iStartChan = 0; // start channel number
    private int m_iChanNum = 0; // channel number
    private static PlaySurfaceView[] playView = new PlaySurfaceView[4];

    private final String TAG = "CarControlActivity";

    //数据传输参数
    private static long DirectionTimeinterval ;
    private static long ReceiveTimeinterval ;
    private static long SendTimeinterval ;

    private Timer send_timer = new Timer( );
    private int sendtype = 1;
//    private Timer getsenddate_timer = new Timer( );
    private Timer run_timer = new Timer( );
    private final Handler ui_handler = new Handler();

    //oksocket
    private  IConnectionManager mManager;
    private ConnectionInfo mInfo;
    private OkSocketOptions mOkOptions;

    //回传数据
    public static int VAL_Voltage_24V_Int=0;
    public static String VAL_Voltage_5V="",VAL_Voltage_12V="",VAL_Voltage_24V="",VAL_Wheel_Status="",VAL_TurnWheel_Status="";
    public static String VAL_TurnWheel_A_Angle="",VAL_TurnWheel_B_Angle="",VAL_TurnWheel_C_Angle="",VAL_TurnWheel_D_Angle="",VAL_Updown_Mast_Angle="",VAL_TurnWheel_Mast_Angle="", VAL_TurnWheel_Sailboard_Angle="",VAL_SelfDetection_Status="",VAL_Sport_Mode="",VAL_Sport_Sign="",VAL_X_Ordinate="",VAL_Y_Ordinate="",VAL_Z_Ordinate="";
    public static String VAL_TOF_Front1="",VAL_TOF_Front2="",VAL_TOF_LeftFront="",VAL_TOF_RightFront="",VAL_TOF_Back1="",VAL_TOF_Back2="",VAL_TOF_LeftBack="",VAL_TOF_RightBack="",VAL_Yuntai_Angle="";
    public static String VAL_AttitudeSensor_X="",VAL_AttitudeSensor_Y="",VAL_AttitudeSensor_Z="";
    public static String VAL_Reserved_Fields1="";
    //TCP的body长度
    public static final int  BodyLength= 49;

    //调节阶段用于查看接收的数据
    private TextView textView;
    private boolean updatetextView=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//禁止屏幕变暗
        setContentView(R.layout.activity_carcontrolpad);

        if (!initeSdk()) {
            this.finish();
            return;
        }
        initSetting();
        initView();
        initTcp();
        initTabs();
        initPagers();

    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        m_osurfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        Log.i(TAG, "surface is created");


        if(playView[0]==null){
            return;
        }
        playView[0].m_hHolder = surfaceHolder;
        Surface surface = surfaceHolder.getSurface();
        if (true == surface.isValid()) {
            if (m_iPlayID != -1) {
                if (-1 == HCNetSDK.getInstance().NET_DVR_RealPlaySurfaceChanged(m_iPlayID, 0, surfaceHolder)) {
                    Log.e(TAG, "Player setVideoWindow failed!");
                }
            } else {
                if (-1 == HCNetSDK.getInstance().NET_DVR_PlayBackSurfaceChanged(m_iPlaybackID, 0, surfaceHolder)) {
                    Log.e(TAG, "Player setVideoWindow failed!");
                }
            }

        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        Log.i(TAG, "surface changed");
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        Log.i(TAG, "Player setVideoWindow release!");
        if (-1 == m_iPlayID && -1 == m_iPlaybackID) {
            return;
        }
        if (true == surfaceHolder.getSurface().isValid()) {
            if (m_iPlayID != -1) {
                if (-1 == HCNetSDK.getInstance().NET_DVR_RealPlaySurfaceChanged(m_iPlayID, 0, null)) {
                    Log.e(TAG, "Player setVideoWindow failed!");
                }
            } else {
                if (-1 == HCNetSDK.getInstance().NET_DVR_PlayBackSurfaceChanged(m_iPlaybackID, 0, null)) {
                    Log.e(TAG, "Player setVideoWindow failed!");
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("m_iPlayID", m_iPlayID);
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        m_iPlayID = savedInstanceState.getInt("m_iPlayID");
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState");
    }

    private void initView() {
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
        //    连接状态
        mTextViewTcpStatus=findViewById(R.id.tcpstatus);
        mTextViewCarmerStatus=findViewById(R.id.carmerstatus);
        //分页
        mViewPager=findViewById(R.id.main_view_pager);
        mTabSegment=findViewById(R.id.tabs);
        //摄像头
        m_osurfaceView = (SurfaceView) findViewById(R.id.Sur_Player);
        m_osurfaceView.getHolder().addCallback(this);


        //摄像头初始化
        handler=new Handler();
        startStep=2;
        Runnable runnable=new Runnable(){
            @Override
            public void run() {
                // TODO Auto-generated method stub
                //要做的事情，这里再次调用此Runnable对象，以实现每两秒实现一次的定时器操作
                if(startStep==2){
                    login_sub();
                    startStep=3;
                    handler.postDelayed(this, 500);
                }
                else if(startStep==3){
                    start_preview_sub();
                }
            }
        };
        handler.postDelayed(runnable, 5000);

        //更新页面接收数据
        ui_handler.postDelayed(ui_task, ReceiveTimeinterval);
//        //获取陀螺仪数据的任务
//        getsenddate_timer.schedule(getsenddate_task,1000,DirectionTimeinterval);
        //发送陀螺仪数据的任务
        send_timer.schedule(send_task,1000,DirectionTimeinterval);
        //发送定时任务
        run_timer.schedule(run_task,60000,60000);
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

        //恢复运动
        qmuiRoundButton2 = (QMUIRoundButton) findViewById(R.id.button2);
        qmuiRoundButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DefaultSendBean bean = new DefaultSendBean();
                bean.setThreebyte((byte) 0x60);
                bean.setFourbyte((byte) 0xA5);
                SendData_ByteOnce(bean);
                LogTool.d("恢复运动",StringTool.byteToString(bean.parse()));
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
                QMUIPopup mNormalPopup = QMUIPopups.popup(CarControlActivity.this, QMUIDisplayHelper.dp2px(CarControlActivity.this, 250), QMUIDisplayHelper.dp2px(CarControlActivity.this, 120))
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
                final String[] itemstitle = new String[]{"左端停止X坐标", "左端停止Y坐标", "右端停止X坐标","右端停止Y坐标", "中间停止X坐标", "中间停止Y坐标"};
                final String[] itemstag = new String[]{"Set_LeftX", "Set_LeftY", "Set_RightX","Set_RightY", "Set_MiddleX", "Set_MiddleY"};
                final byte[] itemsbyte = new byte[]{(byte) 0x0F, (byte) 0x10, (byte) 0x12,(byte) 0x13, (byte) 0x14, (byte) 0x15};
                final String[] itemsshow = new String[]{"左端停止X坐标:"+String.valueOf(LeftX/10.0)+" m", "左端停止Y坐标:"+String.valueOf(LeftY/10.0)+" m", "右端停止X坐标:"+String.valueOf(RightX/10.0)+" m","右端停止Y坐标:"+String.valueOf(RightY/10.0)+" m", "中间停止X坐标:"+String.valueOf(MiddleX/10.0)+" m", "中间停止Y坐标:"+String.valueOf(MiddleY/10.0)+" m"};
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
            }
        });


        //急停
        qmuiRoundButtonrz = (QMUIRoundButton) findViewById(R.id.btn_rz);
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
                LogTool.d("使能运动",StringTool.byteToString(bean.parse()));
            }
        });
    }

    private boolean initeSdk() {
        // init net sdk
        if (!HCNetSDK.getInstance().NET_DVR_Init()) {
            Log.e(TAG, "HCNetSDK init is failed!");
            return false;
        }
        HCNetSDK.getInstance().NET_DVR_SetLogToFile(3, "/mnt/sdcard/sdklog/", true);
        return true;
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

    private void initPagers() {
        HomeController.HomeControlListener listener = new HomeController.HomeControlListener() {
            @Override
            public void sendDataByteOnce(DefaultSendBean bean) {
                SendData_ByteOnce(bean);
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
        TimerSetList = sp.getStringSet("Set_TimerSetInterval",new HashSet<String>() );
        // 从存储的XML文件中根据相应的键获取数据，没有数据就返回默认值    
        LeftX = sp.getInt("Set_LeftX", 0);
        LeftY = sp.getInt("Set_LeftY",0);
        RightX = sp.getInt("Set_RightX",0);
        RightY = sp.getInt("Set_RightY",0);
        MiddleX = sp.getInt("Set_MiddleX",0);
        MiddleY = sp.getInt("Set_MiddleY",0);
        ipaddr = sp.getString("Set_CameraIP", getResources().getString(R.string.set_cameraip));
        ipaddrport = sp.getInt("Set_CameraPort",getResources().getInteger(R.integer.set_cameraport));
        username = sp.getString("Set_CameraUserName",getResources().getString(R.string.set_camerausername));
        password = sp.getString("Set_CameraPassWord",getResources().getString(R.string.set_camerapassword));
    }

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
                        return 2;
                    }

                    @Override
                    public int getBodyLength(byte[] header, ByteOrder byteOrder) {
                        if(header[0]==(byte) 0xAA&&header[1]==(byte) 0x55){
                            return BodyLength;
                        }else {
                            if(BodyLength%2==0){
                                return 1;
                            }else {
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

    private void login_sub() {
        try {
            if (m_iLogID < 0) {
                // login on the device
                m_iLogID = loginDevice();
                if (m_iLogID < 0) {
                    Log.e(TAG, "This device logins failed!");
                    mTextViewCarmerStatus.setText("登录失败");
                    mTextViewCarmerStatus.setTextColor(getResources().getColor(R.color.app_color_theme_2));
                    Toast toast=Toast.makeText(getApplicationContext(), "无法连接设备,请重新登陆", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                } else {
                    Log.i(TAG, "m_iLogID=" + m_iLogID);
                }
                // get instance of exception callback and set
                ExceptionCallBack oexceptionCbf = getExceptiongCbf();
                if (oexceptionCbf == null) {
                    Log.e(TAG, "ExceptionCallBack object is failed!");
                    mTextViewCarmerStatus.setText("1设备出错");
                    mTextViewCarmerStatus.setTextColor(getResources().getColor(R.color.app_color_theme_2));
                    Toast toast=Toast.makeText(getApplicationContext(), "设备出错，请检查", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                if (!HCNetSDK.getInstance().NET_DVR_SetExceptionCallBack(oexceptionCbf)) {
                    Log.e(TAG, "NET_DVR_SetExceptionCallBack is failed!");
                    mTextViewCarmerStatus.setText("2设备出错");
                    mTextViewCarmerStatus.setTextColor(getResources().getColor(R.color.app_color_theme_2));
                    Toast toast=Toast.makeText(getApplicationContext(), "设备出错，请检查", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                //m_oLoginBtn.setText("Logout");
                Log.i(TAG, "Login sucess");
            } else {
                // whether we have logout
                if (!HCNetSDK.getInstance().NET_DVR_Logout_V30(m_iLogID)) {
                    Log.e(TAG, " NET_DVR_Logout is failed!");
                    mTextViewCarmerStatus.setText("3设备出错");
                    mTextViewCarmerStatus.setTextColor(getResources().getColor(R.color.app_color_theme_2));
                    //if (!HCNetSDKJNAInstance.getInstance().NET_DVR_DeleteOpenEzvizUser(m_iLogID)) {
                    //		Log.e(TAG, " NET_DVR_DeleteOpenEzvizUser is failed!");
                    return;
                }
                //m_oLoginBtn.setText("Login");
                m_iLogID = -1;
            }

        } catch (Exception err) {
            Log.e(TAG, "error: " + err.toString());
        }

    }

    private int loginDevice()
    {
        int iLogID = -1;
        iLogID = loginNormalDevice();
        // iLogID = JNATest.TEST_EzvizLogin();
        // iLogID = loginEzvizDevice();
        return iLogID;
    }

    private int loginNormalDevice()
    {
        // get instance
        m_oNetDvrDeviceInfoV30 = new NET_DVR_DEVICEINFO_V30();
        if (null == m_oNetDvrDeviceInfoV30)
        {
            Log.e(TAG, "HKNetDvrDeviceInfoV30 new is failed!");
            return -1;
        }
        String strIP = ipaddr;//m_oIPAddr.getText().toString();
        int nPort =ipaddrport; //Integer.parseInt(m_oPort.getText().toString());
        String strUser = username;//m_oUser.getText().toString();
        String strPsd =password;//"admin123";

        // call NET_DVR_Login_v30 to login on, port 8000 as default
        int iLogID = HCNetSDK.getInstance().NET_DVR_Login_V30(strIP, nPort, strUser, strPsd, m_oNetDvrDeviceInfoV30);
        if (iLogID < 0)
        {
            Log.e(TAG, "NET_DVR_Login is failed!Err:" + HCNetSDK.getInstance().NET_DVR_GetLastError());
            return -1;
        }

        if (m_oNetDvrDeviceInfoV30.byChanNum > 0)
        {
            m_iStartChan = m_oNetDvrDeviceInfoV30.byStartChan;
            m_iChanNum = m_oNetDvrDeviceInfoV30.byChanNum;
        }
        else if (m_oNetDvrDeviceInfoV30.byIPChanNum > 0)
        {
            m_iStartChan = m_oNetDvrDeviceInfoV30.byStartDChan;
            m_iChanNum = m_oNetDvrDeviceInfoV30.byIPChanNum + m_oNetDvrDeviceInfoV30.byHighDChanNum * 256;
        }

        if (m_iChanNum > 1)
        {
            ChangeSingleSurFace(false);
        }
        else
        {
            ChangeSingleSurFace(true);
        }
        Log.i(TAG, "NET_DVR_Login is Successful!");
        return iLogID;
    }

    private void start_preview_sub() {
        try {

            if (m_iLogID < 0) {
                Log.e(TAG, "please login on device first");
                return;
            }

            if (m_iPlaybackID >= 0) {
                Log.i(TAG, "Please stop palyback first");
                return;
            }


            if (m_iPlayID < 0) {
                startSinglePreview();

            } else {
                stopSinglePreview();
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }
    }

    private void stopSinglePreview()
    {
        if (m_iPlayID < 0)
        {
            Log.e(TAG, "m_iPlayID < 0");
            return;
        }

        if(HCNetSDKJNAInstance.getInstance().NET_DVR_CloseSound())
        {
            Log.e(TAG, "NET_DVR_CloseSound Succ!");
        }

        // net sdk stop preview
        if (!HCNetSDK.getInstance().NET_DVR_StopRealPlay(m_iPlayID))
        {
            Log.e(TAG, "StopRealPlay is failed!Err:" + HCNetSDK.getInstance().NET_DVR_GetLastError());
            return;
        }
        Log.i(TAG, "NET_DVR_StopRealPlay succ");
        m_iPlayID = -1;
    }
    private void startSinglePreview()
    {
        if (m_iPlaybackID >= 0)
        {
            Log.i(TAG, "Please stop palyback first");
            return;
        }

        Log.i(TAG, "m_iStartChan:" + m_iStartChan);

        NET_DVR_PREVIEWINFO previewInfo = new NET_DVR_PREVIEWINFO();
        previewInfo.lChannel = m_iStartChan;
        previewInfo.dwStreamType = 0; // main stream
        previewInfo.bBlocked = 1;
        previewInfo.hHwnd = playView[0].m_hHolder;

        m_iPlayID = HCNetSDK.getInstance().NET_DVR_RealPlay_V40(m_iLogID, previewInfo, null);
        if (m_iPlayID < 0)
        {
            mTextViewCarmerStatus.setText("RealPlay报错");
            mTextViewCarmerStatus.setTextColor(getResources().getColor(R.color.app_color_theme_2));
            Log.e(TAG, "NET_DVR_RealPlay is failed!Err:" + HCNetSDK.getInstance().NET_DVR_GetLastError());
            return;
        }
        mTextViewCarmerStatus.setText("已连接");
        mTextViewCarmerStatus.setTextColor(getResources().getColor(R.color.app_color_theme_4));
    }

    private void ChangeSingleSurFace(boolean bSingle) {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);

        for (int i = 0; i < 4; i++) {
            if (playView[i] == null) {
                playView[i] = new PlaySurfaceView(this);
                playView[i].setParam(QMUIDisplayHelper.dp2px(CarControlActivity.this, 450),QMUIDisplayHelper.dp2px(CarControlActivity.this, 300));
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.WRAP_CONTENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT);
                params.topMargin = QMUIDisplayHelper.dp2px(CarControlActivity.this, 10);//playView[i].getM_iHeight() - (i / 2) * playView[i].getM_iHeight();
                params.rightMargin = QMUIDisplayHelper.dp2px(CarControlActivity.this, 10);
                params.width=QMUIDisplayHelper.dp2px(CarControlActivity.this, 450);
                params.height=QMUIDisplayHelper.dp2px(CarControlActivity.this, 300);
                params.gravity = Gravity.TOP | Gravity.RIGHT;
                addContentView(playView[i], params);
                playView[i].setVisibility(View.INVISIBLE);

            }
        }

        if (bSingle) {
            for (int i = 0; i < 4; ++i) {
                playView[i].setVisibility(View.INVISIBLE);
            }
            playView[0].setParam(QMUIDisplayHelper.dp2px(CarControlActivity.this, 450) ,QMUIDisplayHelper.dp2px(CarControlActivity.this, 300));
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT);
            params.topMargin = QMUIDisplayHelper.dp2px(CarControlActivity.this, 10);//playView[3].getM_iHeight() - (3 / 2) * playView[3].getM_iHeight();
//            params.bottomMargin = 0;
            params.rightMargin = QMUIDisplayHelper.dp2px(CarControlActivity.this, 10);
            // params.
            params.gravity = Gravity.TOP | Gravity.RIGHT;
            playView[0].setLayoutParams(params);
            playView[0].setVisibility(View.VISIBLE);
        }
    }

    private ExceptionCallBack getExceptiongCbf()
    {
        ExceptionCallBack oExceptionCbf = new ExceptionCallBack()
        {
            public void fExceptionCallBack(int iType, int iUserID, int iHandle)
            {
                System.out.println("recv exception, type:" + iType);
            }
        };
        return oExceptionCbf;
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

    private final Runnable ui_task = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            ui_handler.postDelayed(this, 30);
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
        }
    };

    TimerTask send_task = new TimerTask( ) {
        public void run ( )
        {
            //是否发送速度
            int new_VAL_THR = ThrottleView.Value * 10 / ThrottleView.Value_max ;
            if(new_VAL_THR!=VAL_THR){
                VAL_THR=new_VAL_THR;
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
    };

    private SocketActionAdapter adapter = new SocketActionAdapter() {
        @Override
        public void onSocketConnectionSuccess(ConnectionInfo info, String action) {
            mTextViewTcpStatus.setText("已连接");
            mTextViewTcpStatus.setTextColor(getResources().getColor(R.color.app_color_theme_4));
//            OkSocket.open(info)
//                    .getPulseManager()
//                    .pulse();//开始心跳,开始心跳后,心跳管理器会自动进行心跳触发
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
            if(updatetextView){
                String responseString=StringTool.byteToString(data.getHeadBytes())+StringTool.byteToString(data.getBodyBytes());
                textView.setText(responseString);
                Log.d(TAG,responseString);
            }
            StringTool.getReadString(data.getHeadBytes(),data.getBodyBytes());
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

    private void setButtonEnble(boolean enble){
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
        qmuiRoundButtonrz.setEnabled(enble);
        qmuiRoundButtonrz.setTextColor(enble? getResources().getColor(R.color.white):getResources().getColor(R.color.app_color_description));
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
                        finish();
                    }
                })
                .create().show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (send_timer != null)
        {
            send_timer.cancel( );
            send_timer = null;
        }
        if (run_timer != null)
        {
            run_timer.cancel( );
            run_timer = null;
        }
        if (mManager != null) {
            mManager.disconnect();
            mManager.unRegisterReceiver(adapter);
        }

        stopSinglePreview();
        HCNetSDK.getInstance().NET_DVR_Logout_V30(m_iLogID);
        HCNetSDK.getInstance().NET_DVR_Cleanup();

    }
}

