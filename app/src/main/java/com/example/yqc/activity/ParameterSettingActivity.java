package com.example.yqc.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.yqc.R;
import com.example.yqc.util.Log4jConfigure;
import com.example.yqc.util.StringTool;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import java.io.File;

public class ParameterSettingActivity extends AppCompatActivity {
    private QMUITopBarLayout mTopBar;
    private QMUIGroupListView mGroupListView;
    //用于存储app参数
    private static final String SET_FILENAME = "ano_set_filename";
    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;
    //演示左端停止点X坐标
    private QMUICommonListItemView itemWithDirectionTimeinterval;
    private QMUICommonListItemView itemWithReceiveTimeinterval;
    private QMUICommonListItemView itemWithSendTimeinterval;
    View.OnClickListener itemWithOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final String tag=v.getTag().toString();
            if (v instanceof QMUICommonListItemView) {
                final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(ParameterSettingActivity.this);
                builder.setTitle("时间间隔")
                        .setPlaceholder("单位（ms）")
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
                                    SaveSharedPreferencesInt(tag,Integer.valueOf(text));
                                    initGroupListView();
                                    dialog.dismiss();
                                }else {
                                    Toast.makeText(ParameterSettingActivity.this, "请输入有效的时间间隔" , Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .create(mCurrentDialogStyle).show();
            }
        }
    };

    private QMUICommonListItemView itemWithLogFile;
    View.OnClickListener itemWithLogFileOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v instanceof QMUICommonListItemView) {

                String path="";
                if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                    path= Environment.getExternalStorageDirectory()
                            + Log4jConfigure.DEFAULT_LOG_DIR + Log4jConfigure.DEFAULT_LOG_FILE_NAME;
                } else {
                    path= "//data//data//" + Log4jConfigure.PACKAGE_NAME + "//files"
                            + File.separator+ Log4jConfigure.DEFAULT_LOG_FILE_NAME;
                }
                final String finalPath = path;
                new QMUIDialog.MessageDialogBuilder(ParameterSettingActivity.this)
                        .setTitle("Log日志")
                        .setMessage(finalPath)
                        .addAction("取消", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                            }
                        })
                        .addAction(0, "打开", QMUIDialogAction.ACTION_PROP_POSITIVE, new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                                //调用系统文件管理器打开指定路径目录
                                File dir = new File(finalPath);
                                File parentFlie = new File(dir.getParent());
//                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                                intent.setDataAndType(Uri.fromFile(parentFlie), "*/*");
//                                intent.addCategory(Intent.CATEGORY_OPENABLE);
//                                startActivity(intent);

                            }
                        })
                        .create().show();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametersetting);
        initView();
        initTopBar();
        initGroupList();
        initGroupListView();
    }

    private void initView() {
        mTopBar = findViewById(R.id.topbar);
        mGroupListView = findViewById(R.id.groupListView);
    }

    private void initTopBar() {
        mTopBar.setBackgroundColor(ContextCompat.getColor(ParameterSettingActivity.this, R.color.app_color_theme_6));
        mTopBar.setTitle("调试参数设置");
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开启成功
                ParameterSettingActivity.this.finish();
            }
        });
    }

    private void initGroupList() {
        int size = QMUIDisplayHelper.dp2px(ParameterSettingActivity.this, 20);
        //两条发送数据的时间间隔
        itemWithSendTimeinterval = mGroupListView.createItemView(
                ContextCompat.getDrawable(ParameterSettingActivity.this, R.mipmap.icon_listitem_cycle),
                "两条发送数据的时间间隔",
                "",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        itemWithSendTimeinterval.setTag("Set_SendTimeinterval");
        //接收数据（更新UI数据）的时间间隔
        itemWithReceiveTimeinterval = mGroupListView.createItemView(
                ContextCompat.getDrawable(ParameterSettingActivity.this, R.mipmap.icon_listitem_cycle),
                "接收数据的时间间隔",
                "",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        itemWithReceiveTimeinterval.setTag("Set_ReceiveTimeinterval");
        //摇杆方向发送的时间间隔
        itemWithDirectionTimeinterval = mGroupListView.createItemView(
                ContextCompat.getDrawable(ParameterSettingActivity.this, R.mipmap.icon_listitem_cycle),
                "摇杆方向发送数据的时间间隔",
                "",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        itemWithDirectionTimeinterval.setTag("Set_DirectionTimeinterval");
        //打开日志LOG
        itemWithLogFile = mGroupListView.createItemView(
                ContextCompat.getDrawable(ParameterSettingActivity.this, R.mipmap.icon_listitem_file),
                "Log日志",
                "",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_NONE);

        QMUIGroupListView.newSection(ParameterSettingActivity.this)
                .setTitle("参数设置")
                .setLeftIconSize(size, ViewGroup.LayoutParams.WRAP_CONTENT)
//                .addItemView(itemWithSendTimeinterval, itemWithOnClickListener)
                .addItemView(itemWithReceiveTimeinterval, itemWithOnClickListener)
                .addItemView(itemWithDirectionTimeinterval, itemWithOnClickListener)
                .addItemView(itemWithLogFile, itemWithLogFileOnClickListener)
                .addTo(mGroupListView);

    }

    private void initGroupListView() {
        // 得到SP对象 
        SharedPreferences sp = getSharedPreferences(SET_FILENAME, MODE_PRIVATE);
        // 从存储的XML文件中根据相应的键获取数据，没有数据就返回默认值  
        int SendTimeinterval = sp.getInt("Set_SendTimeinterval",50);
        int ReceiveTimeinterval = sp.getInt("Set_ReceiveTimeinterval",50);
        int DirectionTimeinterval = sp.getInt("Set_DirectionTimeinterval",50);

        itemWithSendTimeinterval.setDetailText(String.valueOf(SendTimeinterval));
        itemWithReceiveTimeinterval.setDetailText(String.valueOf(ReceiveTimeinterval));
        itemWithDirectionTimeinterval.setDetailText(String.valueOf(DirectionTimeinterval));

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