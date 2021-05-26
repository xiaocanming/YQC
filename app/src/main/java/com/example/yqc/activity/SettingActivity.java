package com.example.yqc.activity;

import android.content.DialogInterface;
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
import com.example.yqc.bean.DefaultSendBean;
import com.example.yqc.customview.BerNpickerView;
import com.example.yqc.util.LogTool;
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
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class SettingActivity extends AppCompatActivity {
    private QMUITopBarLayout mTopBar;
    private QMUIGroupListView mGroupListView;
    //用于存储app参数
    private static final String SET_FILENAME = "ano_set_filename";
    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;
    private TimePickerView pvTime;
    private BerNpickerView nvTime;
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
                        .setDefaultText(HostIP)
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
                        .setDefaultText(String.valueOf(HostPort))
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
                        .setDefaultText(CameraIP)
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
                        .setDefaultText(String.valueOf(CameraPort))
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
                        .setDefaultText(CameraUserName)
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
                        .setDefaultText(CameraPassWord)
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


    //A点磁场角度
    private QMUICommonListItemView itemWithMagneticFieldA;
    View.OnClickListener itemWithMagneticFieldAOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v instanceof QMUICommonListItemView) {
                final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(SettingActivity.this);
                builder.setTitle("A点磁场角度")
                        .setPlaceholder("请输入您的磁场角度")
                        .setInputType(InputType.TYPE_CLASS_TEXT)
                        .setDefaultText(String.valueOf(MagneticFieldA/10.0))
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
                                if (StringTool.StringIsNumber(text)) {
                                    int numpares = Integer.parseInt(new java.text.DecimalFormat("0").format((Float.valueOf(text) * 10.0)));
                                    if (numpares > 3600) {
                                        Toast.makeText(SettingActivity.this, "角度不能大于360 °", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    SaveSharedPreferencesInt("Set_MagneticFieldA", numpares);
                                    initGroupListView();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(SettingActivity.this, "请输入有效的坐标值,精度保留一位小数", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .create(mCurrentDialogStyle).show();
            }
        }
    };

    //B点磁场角度
    private QMUICommonListItemView itemWithMagneticFieldB;
    View.OnClickListener itemWithMagneticFieldBOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v instanceof QMUICommonListItemView) {
                final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(SettingActivity.this);
                builder.setTitle("B点磁场角度")
                        .setPlaceholder("请输入您的磁场角度")
                        .setInputType(InputType.TYPE_CLASS_TEXT)
                        .setDefaultText(String.valueOf(MagneticFieldB/10.0))
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
                                if (StringTool.StringIsNumber(text)) {
                                    int numpares = Integer.parseInt(new java.text.DecimalFormat("0").format((Float.valueOf(text) * 10.0)));
                                    if (numpares > 3600) {
                                        Toast.makeText(SettingActivity.this, "角度不能大于360 °", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    SaveSharedPreferencesInt("Set_MagneticFieldB", numpares);
                                    initGroupListView();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(SettingActivity.this, "请输入有效的坐标值,精度保留一位小数", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .create(mCurrentDialogStyle).show();
            }
        }
    };

    //Y轴正方向磁场角度
    private QMUICommonListItemView itemWithMagneticFieldY1;
    View.OnClickListener itemWithMagneticFieldY1OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v instanceof QMUICommonListItemView) {
                final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(SettingActivity.this);
                builder.setTitle("Y轴正方向磁场角度")
                        .setPlaceholder("请输入您的磁场角度")
                        .setInputType(InputType.TYPE_CLASS_TEXT)
                        .setDefaultText(String.valueOf(MagneticFieldY1/10.0))
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
                                if (StringTool.StringIsNumber(text)) {
                                    int numpares = Integer.parseInt(new java.text.DecimalFormat("0").format((Float.valueOf(text) * 10.0)));
                                    if (numpares > 3600) {
                                        Toast.makeText(SettingActivity.this, "角度不能大于360 °", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    SaveSharedPreferencesInt("Set_MagneticFieldY1", numpares);
                                    initGroupListView();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(SettingActivity.this, "请输入有效的坐标值,精度保留一位小数", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .create(mCurrentDialogStyle).show();
            }
        }
    };

    //Y轴负方向磁场角度
    private QMUICommonListItemView itemWithMagneticFieldY2;
    View.OnClickListener itemWithMagneticFieldY2OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v instanceof QMUICommonListItemView) {
                final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(SettingActivity.this);
                builder.setTitle("Y轴负方向磁场角度")
                        .setPlaceholder("请输入您的磁场角度")
                        .setInputType(InputType.TYPE_CLASS_TEXT)
                        .setDefaultText(String.valueOf(MagneticFieldY2/10.0))
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
                                if (StringTool.StringIsNumber(text)) {
                                    int numpares = Integer.parseInt(new java.text.DecimalFormat("0").format((Float.valueOf(text) * 10.0)));
                                    if (numpares > 3600) {
                                        Toast.makeText(SettingActivity.this, "角度不能大于360 °", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    SaveSharedPreferencesInt("Set_MagneticFieldY2", numpares);
                                    initGroupListView();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(SettingActivity.this, "请输入有效的坐标值,精度保留一位小数", Toast.LENGTH_SHORT).show();
                                }
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
                        .addAction("删除", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                                new QMUIDialog.MessageDialogBuilder(SettingActivity.this)
                                        .setTitle("提示")
                                        .setMessage("删除定时任务")
                                        .setSkinManager(QMUISkinManager.defaultInstance(SettingActivity.this))
                                        .addAction("全部", new QMUIDialogAction.ActionListener() {
                                            @Override
                                            public void onClick(QMUIDialog dialog, int index) {
                                                ClearSharedPreferencesSet("Set_TimerSet1");
                                                initGroupListView();
                                                dialog.dismiss();
                                            }
                                        })
                                        .addAction("当前", new QMUIDialogAction.ActionListener() {
                                            @Override
                                            public void onClick(QMUIDialog dialog, int index) {
                                                AddSharedPreferencesSet("Set_TimerSet1","",item.getDetailText().toString());
                                                initGroupListView();
                                                dialog.dismiss();
                                            }
                                        })
                                        .addAction("取消", new QMUIDialogAction.ActionListener() {
                                            @Override
                                            public void onClick(QMUIDialog dialog, int index) {
                                                dialog.dismiss();
                                            }
                                        })
                                        .create(mCurrentDialogStyle).show();
                            }
                        })
                        .addAction("修改",  new QMUIDialogAction.ActionListener() {
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
                        .addAction("取消", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
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
        //单个时间选择器
        pvTime = new TimePickerView.Builder(SettingActivity.this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (v instanceof QMUICommonListItemView){
                    QMUICommonListItemView item=(QMUICommonListItemView)v;
                    AddSharedPreferencesSet("Set_TimerSet1", getTime(date),item.getDetailText().toString());
                    initGroupListView();
                }else {
                    AddSharedPreferencesSet("Set_TimerSet1",getTime(date),"");
                    initGroupListView();
                }
            }
        })
                .setType(new boolean[]{false, false, false, true, true, false})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .setLineSpacingMultiplier(2.0f)
                .build();
        //时间间隔选择器
        nvTime = new BerNpickerView.Builder(SettingActivity.this, new BerNpickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(List datalist, View v) {
                List<String> timeList = getTimeLag(datalist.get(0).toString(),datalist.get(1).toString(),datalist.get(2).toString(),datalist.get(3).toString(),datalist.get(4).toString());
                if(timeList.size()>0){
                    AddSharedPreferencesSet("Set_TimerSet1", timeList);
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
        for (int i = 15; i <= 60; i = i+5) {
            iv.add(i + "分");
        }
        List<List<String>> timelist = new ArrayList<>();
        timelist.add(sH);
        timelist.add(sM);
        timelist.add(eH);
        timelist.add(eM);
        timelist.add(iv);
        nvTime.setNPicker(timelist);
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
            public void onClick(final View view) {
                final String[] itemsshow = new String[]{"时间点选择", "时间间隔选择"};
                new QMUIDialog.MenuDialogBuilder(SettingActivity.this)
                        .setSkinManager(QMUISkinManager.defaultInstance(SettingActivity.this))
                        .addItems(itemsshow, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, final int which) {
                                dialog.dismiss();
                                switch (which){
                                    case 0:
                                        Calendar selectedDate = Calendar.getInstance();//系统当前时间
                                        pvTime.setDate(selectedDate);
                                        pvTime.show(view);
                                        break;
                                    case 1:
                                        nvTime.show(view);
                                        break;
                                }
                            }
                        })
                        .create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show();
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

        //磁场设置
        itemWithMagneticFieldA = mGroupListView.createItemView(
                ContextCompat.getDrawable(SettingActivity.this, R.mipmap.icon_listitem_magneticfield),
                "A点磁场角度",
                "",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        itemWithMagneticFieldB = mGroupListView.createItemView(
                ContextCompat.getDrawable(SettingActivity.this, R.mipmap.icon_listitem_magneticfield),
                "B点磁场角度",
                "",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        itemWithMagneticFieldY1 = mGroupListView.createItemView(
                ContextCompat.getDrawable(SettingActivity.this, R.mipmap.icon_listitem_magneticfield),
                "Y轴正方向磁场角度",
                "",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        itemWithMagneticFieldY2 = mGroupListView.createItemView(
                ContextCompat.getDrawable(SettingActivity.this, R.mipmap.icon_listitem_magneticfield),
                "Y轴负方向磁场角度",
                "",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_NONE);

        QMUIGroupListView.newSection(SettingActivity.this)
                .setTitle("磁场设置")
                .setLeftIconSize(size, ViewGroup.LayoutParams.WRAP_CONTENT)
                .addItemView(itemWithMagneticFieldA, itemWithMagneticFieldAOnClickListener)
                .addItemView(itemWithMagneticFieldB, itemWithMagneticFieldBOnClickListener)
                .addItemView(itemWithMagneticFieldY1, itemWithMagneticFieldY1OnClickListener)
                .addItemView(itemWithMagneticFieldY2, itemWithMagneticFieldY2OnClickListener)
                .addTo(mGroupListView);
    }

    private  String HostIP;
    private  int HostPort;
    private  String CameraIP;
    private  int CameraPort;
    private  String CameraUserName;
    private  String CameraPassWord;
    private  int MagneticFieldA;
    private  int MagneticFieldB;
    private  int MagneticFieldY1;
    private  int MagneticFieldY2;
    private void initGroupListView() {
        // 得到SP对象 
        SharedPreferences sp = getSharedPreferences(SET_FILENAME, MODE_PRIVATE);
        // 从存储的XML文件中根据相应的键获取数据，没有数据就返回默认值  
        HostIP = sp.getString("Set_HostIP", getResources().getString(R.string.set_hostip));
        HostPort = sp.getInt("Set_HostPort", getResources().getInteger(R.integer.set_hostport));
        itemWithIP.setDetailText(HostIP);
        itemWithPort.setDetailText(String.valueOf(HostPort));
        //获取摄像头相关设置
        CameraIP = sp.getString("Set_CameraIP", getResources().getString(R.string.set_cameraip));
        CameraPort = sp.getInt("Set_CameraPort", getResources().getInteger(R.integer.set_cameraport));
        CameraUserName = sp.getString("Set_CameraUserName", getResources().getString(R.string.set_camerausername));
        CameraPassWord = sp.getString("Set_CameraPassWord", getResources().getString(R.string.set_camerapassword));
        MagneticFieldA = sp.getInt("Set_MagneticFieldA",getResources().getInteger(R.integer.set_magneticfielda));
        MagneticFieldB = sp.getInt("Set_MagneticFieldB",getResources().getInteger(R.integer.set_magneticfieldb));
        MagneticFieldY1 = sp.getInt("Set_MagneticFieldY1",getResources().getInteger(R.integer.set_magneticfieldy1));
        MagneticFieldY2 = sp.getInt("Set_MagneticFieldY2",getResources().getInteger(R.integer.set_magneticfieldy2));

        itemWithCameraIP.setDetailText(CameraIP);
        itemWithCameraPort.setDetailText(String.valueOf(CameraPort));
        itemWithCameraUserName.setDetailText(CameraUserName);
        itemWithCameraPassWord.setDetailText(CameraPassWord);
        itemWithMagneticFieldA.setDetailText(String.valueOf(MagneticFieldA/10.0)+" °");
        itemWithMagneticFieldB.setDetailText(String.valueOf(MagneticFieldB/10.0)+" °");
        itemWithMagneticFieldY1.setDetailText(String.valueOf(MagneticFieldY1/10.0)+" °");
        itemWithMagneticFieldY2.setDetailText(String.valueOf(MagneticFieldY2/10.0)+" °");

        Set<String> SetList =sp.getStringSet("Set_TimerSet1",new HashSet<String>() );
        Set<String> sortSet = new TreeSet<String>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1);//降序排列
            }
        });
        sortSet.addAll(SetList);
        int size = QMUIDisplayHelper.dp2px(SettingActivity.this, 20);
        if(sectionTimer!=null){
            sectionTimer.removeFrom(mGroupListView);
        }
        sectionTimer=QMUIGroupListView.newSection(SettingActivity.this);
        sectionTimer.setTitle("定时器设置");
        sectionTimer.setLeftIconSize(size, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (String str : sortSet) {
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

    private void SaveSharedPreferencesString(String tag, String value) {
        // 得到编辑器对象   
        SharedPreferences.Editor editor = getSharedPreferences(SET_FILENAME, MODE_PRIVATE).edit();
        // 存入键值对   
        editor.putString(tag, value);
        // 将内存中的数据写到XML文件中去   
        editor.commit();
    }

    private void AddSharedPreferencesSet(String tag,String value,String oldvalue) {
        SharedPreferences sp = getSharedPreferences(SET_FILENAME, MODE_PRIVATE);
        Set<String> SetList=new HashSet<String>(sp.getStringSet(tag, new HashSet<String>()));
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

    private void AddSharedPreferencesSet(String tag,List<String> values) {
        SharedPreferences sp = getSharedPreferences(SET_FILENAME, MODE_PRIVATE);
        Set<String> SetList=new HashSet<String>(sp.getStringSet(tag, new HashSet<String>()));
        // 得到编辑器对象   
        SharedPreferences.Editor editor = getSharedPreferences(SET_FILENAME, MODE_PRIVATE).edit();
        for (String value:values) {
            SetList.add(value);
        }
        editor.putStringSet(tag,SetList);
        // 将内存中的数据写到XML文件中去   
        editor.commit();
    }

    private void ClearSharedPreferencesSet(String tag) {
        // 得到编辑器对象   
        SharedPreferences.Editor editor = getSharedPreferences(SET_FILENAME, MODE_PRIVATE).edit();
        editor.remove(tag);
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