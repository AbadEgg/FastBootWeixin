package com.mlnx.websocket;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.ByteBuffer;


public class WebSocketUtils {

    private WebSocketListenner webSocketListenner;

    private WWebSocketClient client;
    private URI uri;

    class WWebSocketClient extends WebSocketClient {

        public WWebSocketClient(URI uri) {
            super(uri);
        }

        @Override
        public void onMessage(String message) {
            webSocketListenner.onMessage(message);
        }

        @Override
        public void onMessage(ByteBuffer blob) {

            byte[] bs = new byte[blob.remaining()];
            blob.get(bs);
            webSocketListenner.onMessage(bs);
        }

        @Override
        public void onError(Exception ex) {
            ex.printStackTrace();
        }

        @Override
        public void onOpen(ServerHandshake handshake) {
            // webSocketListenner.onOpne();
        }

        @Override
        public void onClose(int code, String reason, boolean remote) {

            webSocketListenner.onClose(code, reason, remote);
        }
    }

    public WebSocketUtils(URI uri, WebSocketListenner webSocketListenner) {
        super();
        this.webSocketListenner = webSocketListenner;
        this.uri = uri;
    }

    public synchronized void sendString(String text) throws Exception {

        if (client == null) {
            client = new WWebSocketClient(uri);
            client.connectBlocking();
        }
        try {
            client.send(text);
        } catch (Exception e) {
            e.printStackTrace();

            client = new WWebSocketClient(uri);
            client.connectBlocking();
            client.send(text);
        }

    }

    public synchronized void sendBytes(byte[] bs) throws Exception {

        if (client == null) {
            client = new WWebSocketClient(uri);
            client.connectBlocking();
        }
        try {
            client.send(bs);
        } catch (Exception e) {
            client = new WWebSocketClient(uri);
            client.connectBlocking();
            client.send(bs);
        }

    }

    public synchronized void disConnect() {
        if (client != null)
            client.close();
        client = null;
    }

    public static void main(String[] args) {
        String s = "";
        System.out.println(s instanceof String);
    }
}
