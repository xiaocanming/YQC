package com.example.yqc;

import android.renderscript.Int2;
import android.util.Log;

import com.example.yqc.util.BitConverter;

import org.junit.Test;

import java.io.Console;

import static com.example.yqc.util.StringTool.Bytes2Int_BE;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
        byte[] bytes = new byte[4];
        bytes[0] = (byte) 0x0A;
        bytes[1] = (byte) 0x43;

        bytes[2] = (byte) 0xFF;
        bytes[3] = (byte) 0x84;

        short a= BitConverter.toShort(bytes,0);
        short b= BitConverter.toShort(bytes,2);

        String AAAAAA =  String.valueOf(Bytes2Int_BE(bytes[0],bytes[1])/10.0);
        String BBBBBB =  String.valueOf(BitConverter.toShort(bytes,0)/10.0);
        String CCCCCC =  String.valueOf(BitConverter.toInt(bytes,0)/10.0);
        String DDDDDD =  String.valueOf(BitConverter.toLong(bytes,0)/10.0);

        String abbb=String.valueOf(a/10.0);
        String acccc=String.valueOf(b/10.0);

    }
}