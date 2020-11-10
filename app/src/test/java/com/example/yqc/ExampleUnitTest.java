package com.example.yqc;

import android.renderscript.Int2;
import android.util.Log;

import com.example.yqc.util.BitConverter;

import org.junit.Test;

import java.io.Console;

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
        bytes[0] = (byte) 0xFA;
        bytes[1] = (byte) 0xDE;

        bytes[2] = (byte) 0xFF;
        bytes[3] = (byte) 0x84;

        short a= BitConverter.toShort(bytes,0);
        short b= BitConverter.toShort(bytes,2);

        String abbb=String.valueOf(a/10.0);
        String acccc=String.valueOf(b/10.0);

    }
}