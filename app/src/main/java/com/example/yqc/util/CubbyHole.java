package com.example.yqc.util;

import com.example.yqc.bean.DefaultSendBean;

public class CubbyHole {
    private DefaultSendBean contents;
    private boolean available = false;
    private static CubbyHole cubbyhole = new CubbyHole();

    public static CubbyHole getInst() {
        return cubbyhole;
    }

    public synchronized DefaultSendBean get() {
        while (available == false) {
            try {
                wait();
            }
            catch (InterruptedException e) {
            }
        }
        available = false;
        notifyAll();
        return contents;
    }

    public synchronized void put(DefaultSendBean value) {
        while (available == true) {
            try {
                wait();
            }
            catch (InterruptedException e) {
            }
        }
        contents = value;
        available = true;
        notifyAll();
    }
}


