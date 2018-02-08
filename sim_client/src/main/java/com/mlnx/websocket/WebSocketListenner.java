package com.mlnx.websocket;

public interface WebSocketListenner {

    // public void onOpne();

    public void onMessage(String message);

    public void onMessage(byte[] bs);

    // public void onError(Exception ex);

    public void onClose(int code, String reason, boolean remote);
}
