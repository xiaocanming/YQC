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
import com.example.yqc.customview.BerNpickerView;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SettingActivity extends AppCompatActivity {
    private QMUITopBarLayout mTopBar;
    private QMUIGroupListView mGroupListView;
    //用于存储app参数
    private static final String SET_FILENAME = "ano_set_filename";
    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;
    private BerNpickerView pvTime;
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
                                if (StringTool.isIPAddress(text)) {
                                    SaveSharedPreferencesString("Set_HostIP", text);
                                    initGroupListView();
                                    dialog.dismiss();
                                } else {
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
                                if (StringTool.isInteger(text)) {
                                    SaveSharedPreferencesInt("Set_HostPort", Integer.valueOf(text));
                                    initGroupListView();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(SettingActivity.this, "请输入有效的端口号", Toast.LENGTH_SHORT).show();
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
                                if (StringTool.isIPAddress(text)) {
                                    SaveSharedPreferencesString("Set_CameraIP", text);
                                    initGroupListView();
                                    dialog.dismiss();
                                } else {
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
                                if (StringTool.isInteger(text)) {
                                    SaveSharedPreferencesInt("Set_CameraPort", Integer.valueOf(text));
                                    initGroupListView();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(SettingActivity.this, "请输入有效的端口号", Toast.LENGTH_SHORT).show();
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
                                SaveSharedPreferencesString("Set_CameraUserName", text);
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
                                SaveSharedPreferencesString("Set_CameraPassWord", text);
                                initGroupListView();
                                dialog.dismiss();
                            }
                        })
                        .create(mCurrentDialogStyle).show();
            }
        }
    };


    //定时器
    private QMUICommonListItemView itemWithTimer;
    View.OnClickListener itemWithTimerOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            if (v instanceof QMUICommonListItemView) {
                final QMUICommonListItemView item = (QMUICommonListItemView) v;
                new QMUIDialog.MessageDialogBuilder(SettingActivity.this)
                        .setTitle("定时器设置")
                        .setMessage("定时任务修改和关闭")
                        .setSkinManager(QMUISkinManager.defaultInstance(SettingActivity.this))
                        .addAction("取消", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                            }
                        })
                        .addAction("关闭", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                ClearSharedPreferencesSet("Set_TimerSetInterval");
                                SaveSharedPreferencesString("Set_TimerSetShow", getResources().getString(R.string.set_timersetshow));
                                initGroupListView();
                                dialog.dismiss();
                            }
                        })
                        .addAction(0, "修改", QMUIDialogAction.ACTION_PROP_POSITIVE, new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
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
        pvTime = new BerNpickerView.Builder(SettingActivity.this, new BerNpickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(List datalist, View v) {
                List<String> timeList = getTimeLag(datalist.get(0).toString(),datalist.get(1).toString(),datalist.get(2).toString(),datalist.get(3).toString(),datalist.get(4).toString());
                if(timeList.size()>0){
                    ClearSharedPreferencesSet("Set_TimerSetInterval");
                    AddSharedPreferencesSet("Set_TimerSetInterval", timeList);
                    String saveTimeListShow=datalist.get(0)+":"+datalist.get(1).toString()+"——"+datalist.get(2)+":"+datalist.get(3).toString()+"("+datalist.get(4).toString()+")";
                    SaveSharedPreferencesString("Set_TimerSetShow", saveTimeListShow);
                    initGroupListView();
                }else {
                    Toast.makeText(SettingActivity.this, "时间段不合法", Toast.LENGTH_SHORT).show();
                }

            }
        })
                .setTotal(5)
                .setTitleText("开始时间-结束时间-间隔时长")
                .build();
        List<String> sH = new ArrayList<>();
        List<String> eH = new ArrayList<>();
        List<String> sM = new ArrayList<>();
        List<String> eM = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            String hour = String.valueOf(i);
            /*判断如果为个位数则在前面拼接‘0’*/
            if (hour.length() < 2) {
                hour = "0" + hour;
            }
            sH.add(hour);
            eH.add(hour);
        }
        for (int i = 0; i < 60; i++) {
            String minute = String.valueOf(i);
            /*判断如果为个位数则在前面拼接‘0’*/
            if (minute.length() < 2) {
                minute = "0" + minute;
            }
            sM.add(minute);
            eM.add(minute);
        }
        List<String> iv = new ArrayList<>();
        for (int i = 10; i <= 120; i = i+10) {
            iv.add(i + "分");
        }
        List<List<String>> timelist = new ArrayList<>();
        timelist.add(sH);
        timelist.add(sM);
        timelist.add(eH);
        timelist.add(eM);
        timelist.add(iv);
        pvTime.setNPicker(timelist);
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

        itemWithTimer = mGroupListView.createItemView(
                ContextCompat.getDrawable(SettingActivity.this, R.mipmap.icon_listitem_cycle),
                "演示计划",
                "",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        QMUIGroupListView.newSection(SettingActivity.this)
                .setTitle("定时器设置")
                .setLeftIconSize(size, ViewGroup.LayoutParams.WRAP_CONTENT)
                .addItemView(itemWithTimer, itemWithTimerOnClickListener)
                .addTo(mGroupListView);

    }

    private void initGroupListView() {
        // 得到SP对象 
        SharedPreferences sp = getSharedPreferences(SET_FILENAME, MODE_PRIVATE);
        // 从存储的XML文件中根据相应的键获取数据，没有数据就返回默认值  
        String HostIP = sp.getString("Set_HostIP", getResources().getString(R.string.set_hostip));
        int HostPort = sp.getInt("Set_HostPort", getResources().getInteger(R.integer.set_hostport));
        itemWithIP.setDetailText(HostIP);
        itemWithPort.setDetailText(String.valueOf(HostPort));
        //获取摄像头相关设置
        String CameraIP = sp.getString("Set_CameraIP", getResources().getString(R.string.set_cameraip));
        int CameraPort = sp.getInt("Set_CameraPort", getResources().getInteger(R.integer.set_cameraport));
        String CameraUserName = sp.getString("Set_CameraUserName", getResources().getString(R.string.set_camerausername));
        String CameraPassWord = sp.getString("Set_CameraPassWord", getResources().getString(R.string.set_camerapassword));
        //获取定时器设置
        String TimerSetShow = sp.getString("Set_TimerSetShow", "");
        //如果未空添加默认值 10点到16点 30分钟间隔
        if(TimerSetShow.equals("")){
            List<String> timeList = getTimeLag("10","00","16","00","30");
            AddSharedPreferencesSet("Set_TimerSetInterval", timeList);
            TimerSetShow="10:00——16:00(30分)";
            SaveSharedPreferencesString("Set_TimerSetShow", TimerSetShow);
        }
        itemWithCameraIP.setDetailText(CameraIP);
        itemWithCameraPort.setDetailText(String.valueOf(CameraPort));
        itemWithCameraUserName.setDetailText(CameraUserName);
        itemWithCameraPassWord.setDetailText(CameraPassWord);
        itemWithTimer.setDetailText(TimerSetShow);
    }

    private void SaveSharedPreferencesString(String tag, String value) {
        // 得到编辑器对象   
        SharedPreferences.Editor editor = getSharedPreferences(SET_FILENAME, MODE_PRIVATE).edit();
        // 存入键值对   
        editor.putString(tag, value);
        // 将内存中的数据写到XML文件中去   
        editor.commit();
    }

    private void ClearSharedPreferencesSet(String tag) {
        SharedPreferences sp = getSharedPreferences(SET_FILENAME, MODE_PRIVATE);
        Set<String> SetList = sp.getStringSet(tag, new HashSet<String>());
        SetList.clear();
        // 得到编辑器对象   
        SharedPreferences.Editor editor = getSharedPreferences(SET_FILENAME, MODE_PRIVATE).edit();
        editor.putStringSet(tag, SetList);
        // 将内存中的数据写到XML文件中去   
        editor.commit();
    }

    private void AddSharedPreferencesSet(String tag, List<String> values) {
        SharedPreferences sp = getSharedPreferences(SET_FILENAME, MODE_PRIVATE);
        Set<String> SetList = sp.getStringSet(tag, new HashSet<String>());
        // 得到编辑器对象   
        SharedPreferences.Editor editor = getSharedPreferences(SET_FILENAME, MODE_PRIVATE).edit();
        for (String value : values
        ) {
            SetList.add(value);
        }
        editor.putStringSet(tag, SetList);
        // 将内存中的数据写到XML文件中去   
        editor.commit();
    }

    private void SaveSharedPreferencesInt(String tag, int value) {
        // 得到编辑器对象   
        SharedPreferences.Editor editor = getSharedPreferences(SET_FILENAME, MODE_PRIVATE).edit();
        // 存入键值对   
        editor.putInt(tag, value);
        // 将内存中的数据写到XML文件中去   
        editor.commit();
    }

    /**
     * 根据时间段和时间间隔获取时间
     *
     * @return
     */
    public List<String> getTimeLag(String startH, String startM, String endH, String endM, String interval) {
        //校验参数
        int sh = Integer.parseInt(startH);
        int sm = Integer.parseInt(startM);
        int eh = Integer.parseInt(endH);
        int em = Integer.parseInt(endM);
        int iv = Integer.parseInt(interval.substring(0, interval.length() - 1));
        ArrayList<String> list = new ArrayList<String>();//创建集合存储所有时间点
        if(sh==eh){
            if(sm>=em){
                return  list;
            }
        }
        if(sh>eh){
            return  list;
        }
        for (int h = sh, m = sm; h <= eh; m += iv) {//创建循环，指定间隔五分钟
            if (m >= 60) {//判断分钟累计到60时清零，小时+1
                h++;
                m = m-60;
            }
            if (h == eh && m > em) {//判断小时累计到24时跳出循环，不添加到集合
                break;
            }
            /*转换为字符串*/
            String hour = String.valueOf(h);
            String minute = String.valueOf(m);
            /*判断如果为个位数则在前面拼接‘0’*/
            if (hour.length() < 2) {
                hour = "0" + hour;
            }
            if (minute.length() < 2) {
                minute = "0" + minute;
            }
            list.add(hour + ":" + minute);//拼接为HH:mm格式，添加到集合
        }
        return list;
    }
}