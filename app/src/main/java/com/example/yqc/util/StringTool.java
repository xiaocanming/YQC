package com.example.yqc.util;

import com.example.yqc.activity.CarControlActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringTool {
    /*Java 验证Ip是否合法*/
    public static boolean isIPAddress(String ipaddr) {
        boolean flag = false;
        Pattern pattern = Pattern.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
        Matcher m = pattern.matcher(ipaddr);
        flag = m.matches();
        return flag;
    }
    /*Java 验证int是否合法*/
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /*Java 验证数字是否合法包括小数*/
    public static boolean StringIsNumber(String str){
        Pattern pattern = Pattern.compile("[0-9]*.?[0-9]?");
        Matcher matcher = pattern.matcher((CharSequence) str);
        return  matcher.matches();
    }

    /*Java 解析接受到的数据*/
    public static boolean getReadString(byte[] headbetys,byte[] bodybetys) {
        //检验数据合法性
        if(headbetys[0]!=(byte) 0xAA||headbetys[1]!=(byte) 0x55){
            return false;
        }
        byte sum=0;
        for(int i=0;i<2;i++) sum += headbetys[i];
        for(int i=0;i<CarControlActivity.BodyLength-1;i++) sum += bodybetys[i];
        if(bodybetys[CarControlActivity.BodyLength-1]!=sum){
            return false;
        }
        //获取5V电压值
        CarControlActivity.VAL_Voltage_5V=String.valueOf(Bytes2Int_BE(bodybetys[0],bodybetys[1])/10.0) +" V";
        //获取12V电压值
        CarControlActivity.VAL_Voltage_12V=String.valueOf(Bytes2Int_BE(bodybetys[2],bodybetys[3])/10.0) +" V";
        //获取24V电压值
        CarControlActivity.VAL_Voltage_24V=String.valueOf(Bytes2Int_BE(bodybetys[4],bodybetys[5])/10.0) +" V";
        //显示电池
        int battery=0;
        float voltage24v=Bytes2Int_BE(bodybetys[4],bodybetys[5])/10;
        if(voltage24v<20){
            battery=0;
        }
        if(voltage24v>25){
            battery=100;
        }
        if(20<=voltage24v&&voltage24v<=25){
            battery=(int)Math.round((voltage24v-20.0)/(25.0-20.0)*100);
        }
        CarControlActivity.VAL_Voltage_24V_Int=battery;
        //获取六个驱动轮电机驱动控制器状态
        int wheelstatus= ByteInt_Single(bodybetys[6]);
        int wheelid= ByteInt_Single(bodybetys[7]);
        if(wheelstatus==Integer.parseInt("01",16)){
            CarControlActivity.VAL_Wheel_Status="正常";
        }else {
            String abnormal=String.valueOf(wheelid);
            CarControlActivity.VAL_Wheel_Status=abnormal;
        }
        //四个转向轮、桅杆、帆板电机驱动控制器状态
        int turnwheelstatus= ByteInt_Single(bodybetys[8]);
        int turnwheelid= ByteInt_Single(bodybetys[9]);
        if(turnwheelstatus==Integer.parseInt("01",16)){
            CarControlActivity.VAL_TurnWheel_Status="正常";
        }else {
            String abnormal=String.valueOf(turnwheelid);
            CarControlActivity.VAL_TurnWheel_Status=abnormal;
        }
        //前转向轮1角度传感器角度
        CarControlActivity.VAL_TurnWheel_A_Angle= String.valueOf(Bytes2Int_BE(bodybetys[10],bodybetys[11])/10.0) +" °";
        //前转向轮2角度传感器角度
        CarControlActivity.VAL_TurnWheel_B_Angle= String.valueOf(Bytes2Int_BE(bodybetys[12],bodybetys[13])/10.0) +" °";
        //后转向轮1角度传感器角度
        CarControlActivity.VAL_TurnWheel_C_Angle= String.valueOf(Bytes2Int_BE(bodybetys[14],bodybetys[15])/10.0) +" °";
        //后转向轮2角度传感器角度
        CarControlActivity.VAL_TurnWheel_D_Angle= String.valueOf(Bytes2Int_BE(bodybetys[16],bodybetys[17])/10.0) +" °";
        //桅杆起伏角度传感器角度
        CarControlActivity.VAL_Updown_Mast_Angle= String.valueOf(Bytes2Int_BE(bodybetys[18],bodybetys[19])/10.0) +" °";
        //云台偏航角度
        CarControlActivity.VAL_TurnWheel_Mast_Angle= String.valueOf(Bytes2Int_BE(bodybetys[20],bodybetys[21])/10.0) +" °";
        //帆板角度传感器角度
        CarControlActivity.VAL_TurnWheel_Sailboard_Angle= String.valueOf(Bytes2Int_BE(bodybetys[22],bodybetys[23])/10.0) +" °";
        //YQC自检状态
        int selfdetectionstatus= ByteInt_Single(bodybetys[24]);
        if(selfdetectionstatus==Integer.parseInt("01",16)){
            CarControlActivity.VAL_SelfDetection_Status="自检正常";
        }else {
            CarControlActivity.VAL_SelfDetection_Status="自检异常";
        }
        //运动模式
        int sportmode= ByteInt_Single(bodybetys[25]);
        switch (sportmode){
            case 0:
                CarControlActivity.VAL_Sport_Mode="停止状态";
                break;
            case 1:
                CarControlActivity.VAL_Sport_Mode="单程演示模式";
                break;
            case 2:
                CarControlActivity.VAL_Sport_Mode="往复演示模式";
                break;
            case 3:
                CarControlActivity.VAL_Sport_Mode="手动控制模式";
                break;
            default:
                CarControlActivity.VAL_Sport_Mode="数据异常";
        }
        //运行状态标志
        int sportsign= ByteInt_Single(bodybetys[26]);
        switch (sportsign){
            case 0:
                CarControlActivity.VAL_Sport_Sign="正常";
                break;
            case 1:
                CarControlActivity.VAL_Sport_Sign="帆板和桅杆运动顺序错误";
                break;
            case 2:
                CarControlActivity.VAL_Sport_Sign="遇到障碍物";
                break;
            default:
                CarControlActivity.VAL_Sport_Sign="数据异常";
        }
        //获取X坐标
        CarControlActivity.VAL_X_Ordinate= numToMString(ByteInt_Single(bodybetys[27]));
        //获取Y坐标
        CarControlActivity.VAL_Y_Ordinate= numToMString(ByteInt_Single(bodybetys[28]));
        //获取Z坐标
        CarControlActivity.VAL_Z_Ordinate= numToMString(ByteInt_Single(bodybetys[29]));
        //TOF前1距离
        CarControlActivity.VAL_TOF_Front1= numToMString(ByteInt_Single(bodybetys[30]));
        //TOF前2距离
        CarControlActivity.VAL_TOF_Front2= numToMString(ByteInt_Single(bodybetys[31]));
        //TOF左前距离
        CarControlActivity.VAL_TOF_LeftFront= numToMString(ByteInt_Single(bodybetys[32]));
        //TOF右前距离
        CarControlActivity.VAL_TOF_RightFront= numToMString(ByteInt_Single(bodybetys[33]));
        //TOF后1距离
        CarControlActivity.VAL_TOF_Back1= numToMString(ByteInt_Single(bodybetys[34]));
        //TOF后2距离
        CarControlActivity.VAL_TOF_Back2= numToMString(ByteInt_Single(bodybetys[35]));
        //TOF左后距离
        CarControlActivity.VAL_TOF_LeftBack= numToMString(ByteInt_Single(bodybetys[36]));
        //TOF右后距离
        CarControlActivity.VAL_TOF_RightBack= numToMString(ByteInt_Single(bodybetys[37]));
        //姿态传感器磁场X坐标
        CarControlActivity.VAL_AttitudeSensor_X= String.valueOf(BitConverter.toShort(bodybetys,38)/10.0);
        //姿态传感器磁场Y坐标
        CarControlActivity.VAL_AttitudeSensor_Y=  String.valueOf(BitConverter.toShort(bodybetys,40)/10.0);
        //姿态传感器磁场Z坐标
        CarControlActivity.VAL_AttitudeSensor_Z=  String.valueOf(BitConverter.toShort(bodybetys,42)/10.0);
        //云台俯仰
        CarControlActivity.VAL_Yuntai_Angle= String.valueOf(Bytes2Int_BE(bodybetys[44],bodybetys[45])/10.0) +" °";;
        //温度
        CarControlActivity.VAL_Reserved_Fields1= String.valueOf(ByteInt_Single(bodybetys[46])/10.0)+" ℃";
        return true;
    }


    /**
     * 转换byte数组为int（小端）
     * @return
     */
    public static int Bytes2Int_LE(byte byte1,byte byte2){
        int iRst = (byte1 & 0xFF);
        iRst |= (byte2 & 0xFF) << 8;
        return iRst;
    }

    /**
     * 转换byte数组为int（大端）
     * @return
     */
    public static int Bytes2Int_BE(byte byte1,byte byte2){
        int iRst = (byte1 & 0xFF) << 8 ;
        iRst |= byte2 & 0xFF;
        return iRst;
    }

    /**
     * 转换byte为int (单字节)
     * @return
     */
    public static int ByteInt_Single(byte byte1){
        int iRst = (byte1 & 0xFF);
        return iRst;
    }

    /**
     * 把数字转换为转换为m
     * @return
     */
    public static String numToMString(int num){
        double f = num/10.0;
        return String.format("%.1f", f)+" m";
    }

    public static String byteToString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        if (!(bytes == null || bytes.length == 0)) {
            for (int i = 0; i < bytes.length; i++) {
                sb.append(String.format("%02X", bytes[i]));
            }
        }
        return sb.toString();
    }

    //String转为16进制字节
    public static byte[] stringToBytes(String text) {
        int len = text.length();
        byte[] bytes = new byte[(len + 1) / 2];
        for (int i = 0; i < len; i += 2) {
            int size = Math.min(2, len - i);
            String sub = text.substring(i, i + size);
            bytes[i / 2] = (byte) Integer.parseInt(sub, 16);
        }
        return bytes;
    }


}
