package com.example.yqc.util;

import com.xuhao.didi.socket.client.sdk.client.ConnectionInfo;

public class RedirectException extends RuntimeException {
    public ConnectionInfo redirectInfo;

    public RedirectException(ConnectionInfo redirectInfo) {
        this.redirectInfo = redirectInfo;
    }
}