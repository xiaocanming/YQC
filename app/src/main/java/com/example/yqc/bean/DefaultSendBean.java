package com.example.yqc.bean;

import com.xuhao.didi.core.iocore.interfaces.ISendable;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

public class DefaultSendBean implements ISendable {
    protected Byte threebyte ;
    protected Byte fourbyte ;

    @Override
    public final byte[] parse() {
        byte[] bytes = new byte[5];
        byte sum=0;
        bytes[0] = (byte) 0xEB;
        bytes[1] = (byte) 0x90;
        bytes[2] = threebyte;
        bytes[3] = fourbyte;
        for(int i=0;i<4;i++) sum += bytes[i];
        bytes[4] = sum;
        return bytes;
    }

    public Byte getThreebyte() {
        return threebyte;
    }

    public void setThreebyte(Byte threebyte) {
        this.threebyte = threebyte;
    }

    public Byte getFourbyte() {
        return fourbyte;
    }

    public void setFourbyte(Byte fourbyte) {
        this.fourbyte = fourbyte;
    }
}
