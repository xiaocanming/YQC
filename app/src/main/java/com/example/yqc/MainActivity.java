package com.example.yqc;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.yqc.activity.CarControlActivity;
import com.example.yqc.activity.ParameterSettingActivity;
import com.example.yqc.activity.SettingActivity;
import com.qmuiteam.qmui.util.QMUIViewHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;


public class MainActivity extends AppCompatActivity {
    private QMUIGroupListView mAboutGroupListView;
    private QMUITopBarLayout mTopBar;
    private TextView versionNameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initTopBar();
        initGroupList();

    }

    private void initView() {
        mAboutGroupListView = findViewById(R.id.about_list);
        mTopBar = findViewById(R.id.topbar);
        versionNameView=findViewById(R.id.copyright);
        String versionName = "";
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            versionName = info.versionName;
        }catch (Exception e){
            versionName="0.0";
        }
        versionNameView.setText("月球车 V"+ versionName);
    }

    private void initTopBar() {
        mTopBar.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.app_color_theme_6));
        mTopBar.setTitle("主页");
        mTopBar.addRightImageButton(R.mipmap.icon_topbar_setting, QMUIViewHelper.generateViewId()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initGroupList() {
        QMUIGroupListView.newSection(this)
                .addItemView(mAboutGroupListView.createItemView("月球车控制器"), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, CarControlActivity.class);
                        startActivity(intent);
                    }
                })
                .addItemView(mAboutGroupListView.createItemView("调试参数设置"), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, ParameterSettingActivity.class);
                        startActivity(intent);
                    }
                })
                .addTo(mAboutGroupListView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
