package com.example.yqc.activity.carcontrol;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.yqc.R;
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
    public ShowController(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.fragment_show, this);
        initView();
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
                LogTool.d("往复演示",StringTool.byteToString(bean.parse()));
            }
        });

        int textViewSize = QMUIDisplayHelper.dpToPx(60);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(textViewSize, textViewSize);
        mFloatLayout.addView(MyRoundButton1, lp);
        mFloatLayout.addView(MyRoundButton2, lp);

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
