package com.example.yqc.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bigkoo.pickerview.TimePickerView;
import com.example.yqc.R;
import com.example.yqc.util.StringTool;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIViewHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SettingActivity extends AppCompatActivity {
    private QMUITopBarLayout mTopBar;
    private QMUIGroupListView mGroupListView;
    //用于存储app参数
    private static final String SET_FILENAME = "ano_set_filename";
    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;
    private TimePickerView pvTime;
    //Ip地址
    private QMUICommonListItemView itemWithIP;
    View.OnClickListener itemWithIPOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v instanceof QMUICommonListItemView) {
                final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(SettingActivity.this);
                builder.setTitle("IP地址")
                        .setPlaceholder("请输入您的IP地址")
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
                                if(StringTool.isIPAddress(text)){
                                    SaveSharedPreferencesString("Set_HostIP",text);
                                    initGroupListView();
                                    dialog.dismiss();
                                }else {
                                    Toast.makeText(SettingActivity.this, "请输入有效的IP地址", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .create(mCurrentDialogStyle).show();
            }
        }
    };
    //端口
    private QMUICommonListItemView itemWithPort;
    View.OnClickListener itemWithPortOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v instanceof QMUICommonListItemView) {
                final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(SettingActivity.this);
                builder.setTitle("端口号")
                        .setPlaceholder("请输入您的端口号")
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
                                if(StringTool.isInteger(text)){
                                    SaveSharedPreferencesInt("Set_HostPort",Integer.valueOf(text));
                                    initGroupListView();
                                    dialog.dismiss();
                                }else {
                                    Toast.makeText(SettingActivity.this, "请输入有效的端口号" , Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .create(mCurrentDialogStyle).show();
            }
        }
    };


    //摄像头Ip地址
    private QMUICommonListItemView itemWithCameraIP;
    View.OnClickListener itemWithCameraIPOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v instanceof QMUICommonListItemView) {
                final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(SettingActivity.this);
                builder.setTitle("摄像头Ip地址")
                        .setPlaceholder("请输入您的摄像头Ip地址")
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
                                if(StringTool.isIPAddress(text)){
                                    SaveSharedPreferencesString("Set_CameraIP",text);
                                    initGroupListView();
                                    dialog.dismiss();
                                }else {
                                    Toast.makeText(SettingActivity.this, "请输入有效的IP地址", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .create(mCurrentDialogStyle).show();
            }
        }
    };
    //摄像头端口
    private QMUICommonListItemView itemWithCameraPort;
    View.OnClickListener itemWithCameraPortOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v instanceof QMUICommonListItemView) {
                final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(SettingActivity.this);
                builder.setTitle("摄像头端口号")
                        .setPlaceholder("请输入您的摄像头端口号")
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
                                if(StringTool.isInteger(text)){
                                    SaveSharedPreferencesInt("Set_CameraPort",Integer.valueOf(text));
                                    initGroupListView();
                                    dialog.dismiss();
                                }else {
                                    Toast.makeText(SettingActivity.this, "请输入有效的端口号" , Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .create(mCurrentDialogStyle).show();
            }
        }
    };
    //摄像头用户名
    private QMUICommonListItemView itemWithCameraUserName;
    View.OnClickListener itemWithCameraUserNameOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v instanceof QMUICommonListItemView) {
                final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(SettingActivity.this);
                builder.setTitle("用户名")
                        .setPlaceholder("请输入摄像头的用户名")
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
                                SaveSharedPreferencesString("Set_CameraUserName",text);
                                initGroupListView();
                                dialog.dismiss();
                            }
                        })
                        .create(mCurrentDialogStyle).show();
            }
        }
    };
    //摄像头密码
    private QMUICommonListItemView itemWithCameraPassWord;
    View.OnClickListener itemWithCameraPassWordOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v instanceof QMUICommonListItemView) {
                final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(SettingActivity.this);
                builder.setTitle("密码")
                        .setPlaceholder("请输入摄像头的密码")
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
                                SaveSharedPreferencesString("Set_CameraPassWord",text);
                                initGroupListView();
                                dialog.dismiss();
                            }
                        })
                        .create(mCurrentDialogStyle).show();
            }
        }
    };


    //定时器
    private QMUIGroupListView.Section sectionTimer;
    View.OnClickListener itemWithTimerOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            if (v instanceof QMUICommonListItemView) {
                final QMUICommonListItemView item=(QMUICommonListItemView)v;
                new QMUIDialog.MessageDialogBuilder(SettingActivity.this)
                        .setTitle("定时器")
                        .setMessage("定时器修改和删除")
                        .setSkinManager(QMUISkinManager.defaultInstance(SettingActivity.this))
                        .addAction("取消", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                            }
                        })
                        .addAction("删除", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                AddSharedPreferencesSet("Set_TimerSet","",item.getDetailText().toString());
                                initGroupListView();
                                dialog.dismiss();
                            }
                        })
                        .addAction(0, "修改", QMUIDialogAction.ACTION_PROP_POSITIVE, new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                                String str=item.getDetailText().toString();
                                SimpleDateFormat sdf= new SimpleDateFormat("HH:mm");
                                Date date = null;
                                try {
                                    date = sdf.parse(str);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(date);
                                pvTime.setDate(calendar);
                                pvTime.show(v);
                            }
                        })
                        .create(mCurrentDialogStyle).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        initTopBar();
        initGroupList();
        initGroupListView();
    }

    private void initView() {
        mTopBar = findViewById(R.id.topbar);
        mGroupListView = findViewById(R.id.groupListView);
        pvTime = new TimePickerView.Builder(SettingActivity.this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (v instanceof QMUICommonListItemView){
                    QMUICommonListItemView item=(QMUICommonListItemView)v;
                    AddSharedPreferencesSet("Set_TimerSet", getTime(date),item.getDetailText().toString());
                    initGroupListView();
                }else {
                    AddSharedPreferencesSet("Set_TimerSet",getTime(date),"");
                    initGroupListView();
                }
            }
        })
                .setType(new boolean[]{false, false, false, true, true, false})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .setLineSpacingMultiplier(2.0f)
                .build();


    }

    private void initTopBar() {
        mTopBar.setBackgroundColor(ContextCompat.getColor(SettingActivity.this, R.color.app_color_theme_6));
        mTopBar.setTitle("设置");
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开启成功
                SettingActivity.this.finish();
            }
        });
        mTopBar.addRightImageButton(R.mipmap.icon_topbar_add, QMUIViewHelper.generateViewId()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar selectedDate = Calendar.getInstance();//系统当前时间
                pvTime.setDate(selectedDate);
                pvTime.show(view);
            }
        });
    }

    private void initGroupList() {
        int size = QMUIDisplayHelper.dp2px(SettingActivity.this, 20);
        //采集周期
        itemWithIP = mGroupListView.createItemView(
                ContextCompat.getDrawable(SettingActivity.this, R.mipmap.icon_listitem_ip),
                "IP地址",
                "",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        itemWithPort = mGroupListView.createItemView(
                ContextCompat.getDrawable(SettingActivity.this, R.mipmap.icon_listitem_port),
                "端口号",
                "",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_NONE);

        QMUIGroupListView.newSection(SettingActivity.this)
                .setTitle("服务器设置")
                .setLeftIconSize(size, ViewGroup.LayoutParams.WRAP_CONTENT)
                .addItemView(itemWithIP, itemWithIPOnClickListener)
                .addItemView(itemWithPort, itemWithPortOnClickListener)
                .addTo(mGroupListView);


        //采集周期
        itemWithCameraIP = mGroupListView.createItemView(
                ContextCompat.getDrawable(SettingActivity.this, R.mipmap.icon_listitem_ip),
                "IP地址",
                "",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        itemWithCameraPort = mGroupListView.createItemView(
                ContextCompat.getDrawable(SettingActivity.this, R.mipmap.icon_listitem_port),
                "端口号",
                "",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        itemWithCameraUserName = mGroupListView.createItemView(
                ContextCompat.getDrawable(SettingActivity.this, R.mipmap.icon_listitem_username),
                "用户名",
                "",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        itemWithCameraPassWord = mGroupListView.createItemView(
                ContextCompat.getDrawable(SettingActivity.this, R.mipmap.icon_listitem_password),
                "密码",
                "",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_NONE);

        QMUIGroupListView.newSection(SettingActivity.this)
                .setTitle("摄像头设置")
                .setLeftIconSize(size, ViewGroup.LayoutParams.WRAP_CONTENT)
                .addItemView(itemWithCameraIP, itemWithCameraIPOnClickListener)
                .addItemView(itemWithCameraPort, itemWithCameraPortOnClickListener)
                .addItemView(itemWithCameraUserName, itemWithCameraUserNameOnClickListener)
                .addItemView(itemWithCameraPassWord, itemWithCameraPassWordOnClickListener)
                .addTo(mGroupListView);

    }

    private void initGroupListView() {
        // 得到SP对象 
        SharedPreferences sp = getSharedPreferences(SET_FILENAME, MODE_PRIVATE);
        // 从存储的XML文件中根据相应的键获取数据，没有数据就返回默认值  
        String HostIP = sp.getString("Set_HostIP", getResources().getString(R.string.set_hostip));
        int HostPort = sp.getInt("Set_HostPort",getResources().getInteger(R.integer.set_hostport));
        Set<String> SetList = sp.getStringSet("Set_TimerSet",new HashSet<String>() );
        itemWithIP.setDetailText(HostIP);
        itemWithPort.setDetailText(String.valueOf(HostPort));
        //获取摄像头相关设置
        String CameraIP = sp.getString("Set_CameraIP", getResources().getString(R.string.set_cameraip));
        int CameraPort = sp.getInt("Set_CameraPort",getResources().getInteger(R.integer.set_cameraport));
        String CameraUserName = sp.getString("Set_CameraUserName",getResources().getString(R.string.set_camerausername));
        String CameraPassWord = sp.getString("Set_CameraPassWord",getResources().getString(R.string.set_camerapassword));
        itemWithCameraIP.setDetailText(CameraIP);
        itemWithCameraPort.setDetailText(String.valueOf(CameraPort));
        itemWithCameraUserName.setDetailText(CameraUserName);
        itemWithCameraPassWord.setDetailText(CameraPassWord);


        int size = QMUIDisplayHelper.dp2px(SettingActivity.this, 20);
        if(sectionTimer!=null){
            sectionTimer.removeFrom(mGroupListView);
        }
        sectionTimer=QMUIGroupListView.newSection(SettingActivity.this);
        sectionTimer.setTitle("定时器设置");
        sectionTimer.setLeftIconSize(size, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (String str : SetList) {
            //定时器
            QMUICommonListItemView itemWithTimer = mGroupListView.createItemView(
                    ContextCompat.getDrawable(SettingActivity.this, R.mipmap.icon_listitem_cycle),
                    "定时演示时间",
                    str,
                    QMUICommonListItemView.HORIZONTAL,
                    QMUICommonListItemView.ACCESSORY_TYPE_NONE);
            sectionTimer.addItemView(itemWithTimer, itemWithTimerOnClickListener);
        }
        sectionTimer.addTo(mGroupListView);
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }

    private void SaveSharedPreferencesString(String tag,String value) {
        // 得到编辑器对象   
        SharedPreferences.Editor editor = getSharedPreferences(SET_FILENAME, MODE_PRIVATE).edit();
        // 存入键值对   
        editor.putString(tag, value);
        // 将内存中的数据写到XML文件中去   
        editor.commit();
    }

    private void AddSharedPreferencesSet(String tag,String value,String oldvalue) {
        SharedPreferences sp = getSharedPreferences(SET_FILENAME, MODE_PRIVATE);
        Set<String> SetList = sp.getStringSet(tag,new HashSet<String>() );
        if(!oldvalue.equals("")){
            SetList.remove(oldvalue);
        }
        // 得到编辑器对象   
        SharedPreferences.Editor editor = getSharedPreferences(SET_FILENAME, MODE_PRIVATE).edit();
        if(!value.equals("")){
            SetList.add(value);
        }
        editor.putStringSet(tag,SetList);
        // 将内存中的数据写到XML文件中去   
        editor.commit();
    }

    private void SaveSharedPreferencesInt(String tag,int value ) {
        // 得到编辑器对象   
        SharedPreferences.Editor editor = getSharedPreferences(SET_FILENAME, MODE_PRIVATE).edit();
        // 存入键值对   
        editor.putInt(tag,value);
        // 将内存中的数据写到XML文件中去   
        editor.commit();
    }
}