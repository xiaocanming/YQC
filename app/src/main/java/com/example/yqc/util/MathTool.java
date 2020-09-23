package com.example.yqc.util;

public class MathTool {
    //死角角度
    public  static  int ignoreangle =20;
    public  static  int maxangle =50;
    //内圈半径大小
    public  static  double innerring =0.5;
    /**
     * 得到两点之间的弧度
     * from github sarlmoclen
     */
    public static int[] getRad(float px2, float py2) {
        int[] matharray=new int[5];

        float px1=1500;
        float py1=1500;

        //得到两点X的距离
        float x = px2 - px1;
        //得到两点Y的距离
        float y = py1 - py2;
        //算出斜边长
        float xie = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        //得到这个角度的余弦值（通过三角函数中的定理 ：邻边/斜边=角度余弦值）
        float cosAngle = y / xie;
        //通过反余弦定理获取到其角度的弧度
        float rad = (float) Math.acos(cosAngle);
        double angle = rad*(180/Math.PI);

        //长度半径小于一般不做任何操作
        if(xie<(634.0*innerring)){
            matharray[0]=0;
            matharray[1]=0;
            matharray[2]=0;
            matharray[3]=0;
            matharray[4]=0 ;
            return matharray;
        }

        if(py2>py1){
            double newrad=(180- angle);
            if(px2>px1){
                if(newrad<ignoreangle){
                    matharray[0]=1;
                    matharray[1]=0;
                    matharray[2]=0;
                    matharray[3]=0;
                    matharray[4]=0;
                }else {
                    if(newrad> 90-ignoreangle){
                        matharray[0]=0;
                        matharray[1]=0;
                        matharray[2]=0;
                        matharray[3]=1;
                        matharray[4]=maxangle;
                    }else {
                        matharray[0]=1;
                        matharray[1]=0;
                        matharray[2]=0;
                        matharray[3]=1;
                        matharray[4]=new Double(newrad-ignoreangle).intValue();
                    }
                }
            }else {
                if(px2==px1){
                    matharray[0]=1;
                    matharray[1]=0;
                    matharray[2]=0;
                    matharray[3]=0;
                    matharray[4]=0 ;
                }else {
                    if(newrad<ignoreangle){
                        matharray[0]=1;
                        matharray[1]=0;
                        matharray[2]=0;
                        matharray[3]=0;
                        matharray[4]=0;
                    }else {
                        if(newrad> 90-ignoreangle){
                            matharray[0]=0;
                            matharray[1]=0;
                            matharray[2]=1;
                            matharray[3]=0;
                            matharray[4]=maxangle;
                        }else {
                            matharray[0]=1;
                            matharray[1]=0;
                            matharray[2]=1;
                            matharray[3]=0;
                            matharray[4]=new Double(newrad-ignoreangle).intValue();
                        }
                    }
                }
            }
        }else {
            if(py2==py1){
                if(px2>px1){
                    matharray[0]=0;
                    matharray[1]=0;
                    matharray[2]=0;
                    matharray[3]=1;
                    matharray[4]=maxangle;
                }else {
                    if(px2==px1){
                        matharray[0]=0;
                        matharray[1]=0;
                        matharray[2]=0;
                        matharray[3]=0;
                        matharray[4]=0 ;
                    }else {
                        matharray[0]=0;
                        matharray[1]=0;
                        matharray[2]=1;
                        matharray[3]=0;
                        matharray[4]=maxangle;
                    }
                }
            }else {
                double newrad= angle;
                if(px2>px1){
                    if(newrad<ignoreangle){
                        matharray[0]=0;
                        matharray[1]=1;
                        matharray[2]=0;
                        matharray[3]=0;
                        matharray[4]=0;
                    }else {
                        if(newrad> 90-ignoreangle){
                            matharray[0]=0;
                            matharray[1]=0;
                            matharray[2]=0;
                            matharray[3]=1;
                            matharray[4]=maxangle;
                        }else {
                            matharray[0]=0;
                            matharray[1]=1;
                            matharray[2]=1;
                            matharray[3]=0;
                            matharray[4]=new Double(newrad-ignoreangle).intValue();
                        }
                    }
                }else {
                    if(px2==px1){
                        matharray[0]=0;
                        matharray[1]=1;
                        matharray[2]=0;
                        matharray[3]=0;
                        matharray[4]=0 ;
                    }else {
                        if(newrad<ignoreangle){
                            matharray[0]=0;
                            matharray[1]=1;
                            matharray[2]=0;
                            matharray[3]=0;
                            matharray[4]=0;
                        }else {
                            if(newrad> 90-ignoreangle){
                                matharray[0]=0;
                                matharray[1]=0;
                                matharray[2]=1;
                                matharray[3]=0;
                                matharray[4]=maxangle;
                            }else {
                                matharray[0]=0;
                                matharray[1]=1;
                                matharray[2]=0;
                                matharray[3]=1;
                                matharray[4]=new Double(newrad-ignoreangle).intValue();
                            }
                        }
                    }
                }
            }
        }
        return matharray;
    }
}
