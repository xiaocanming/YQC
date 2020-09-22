package com.example.yqc.util;

import com.example.yqc.bean.DefaultSendBean;

import java.util.ArrayList;
import java.util.List;

public class DoubleBufferQueue {
    private List<DefaultSendBean> readList = new ArrayList<DefaultSendBean>();
    private List<DefaultSendBean> writeList = new ArrayList<DefaultSendBean>();
    private static DoubleBufferQueue queue = new DoubleBufferQueue();

    private DoubleBufferQueue() {

    }

    public static DoubleBufferQueue getInst() {
        return queue;
    }

    public void push(DefaultSendBean value) {
        synchronized (writeList) {
            writeList.add(value);
        }
    }

    public int getWriteListSize() {
        synchronized (writeList) {
            return writeList.size();
        }
    }

    public List<DefaultSendBean> getReadList() {
        return readList;
    }

    public void swap() {
        synchronized(writeList) {
            List<DefaultSendBean> temp = readList;
            readList = writeList;
            writeList = temp;

            writeList.clear();
        }
    }

}
