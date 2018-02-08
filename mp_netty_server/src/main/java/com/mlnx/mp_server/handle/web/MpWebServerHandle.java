package com.mlnx.mp_server.handle.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.mlnx.mp_server.core.Session;
import com.mlnx.mp_server.core.SessionManager;
import com.mlnx.mptp.mptp.MpPacket;
import com.mlnx.mptp.utils.MptpLogUtils;

import java.util.List;
import java.util.Map;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;

public class MpWebServerHandle extends SimpleChannelInboundHandler {

//	@Override
//	public void channelActive(ChannelHandlerContext ctx) throws Exception {
//		MptpLogUtils.d("建立tcp连接 :" + ctx.channel().remoteAddress());
//		MptpLogUtils.d(ctx.channel() + "");
//	}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        Channel ch = ctx.channel();

        if (msg instanceof HttpRequest) {
//			MptpLogUtils.i("http 请求");
            processHttpRequest(ch, (HttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) {
//			MptpLogUtils.i("websocket 请求");
            processWebsocketRequest(ctx, (WebSocketFrame) msg);
        } else {
            // 未处理的请求类型
        }
    }

    /**
     * 完成websocket前的http握手 屏蔽掉非websocket握手请求
     *
     * @param channel
     * @param request
     */
    private void processHttpRequest(Channel channel, HttpRequest request) {
        HttpHeaders headers = request.headers();
        List<Map.Entry<String, String>> ls = headers.entries();
//		for (Map.Entry<String, String> i : ls) {
//			MptpLogUtils.i("header  " + i.getKey() + ":" + i.getValue());
//		}

        // 屏蔽掉非websocket握手请求
        // 只接受http GET和headers['Upgrade']为'websocket'的http请求
        if (!HttpMethod.GET.equals(request.getMethod())
                || !"websocket".equalsIgnoreCase(headers.get("Upgrade"))) {
            DefaultHttpResponse resp = new DefaultHttpResponse(
                    HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST);
            channel.write(resp);
            channel.close();

            MptpLogUtils.e("非websocket握手请求  url:" + request.getUri());
            return;
        }

        WebSocketServerHandshakerFactory wsShakerFactory = new WebSocketServerHandshakerFactory(
                "ws://" + request.headers().get(HttpHeaders.Names.HOST), null,
                false);
        WebSocketServerHandshaker wsShakerHandler = wsShakerFactory
                .newHandshaker(request);
        if (null == wsShakerHandler) {
            // 无法处理的websocket版本
            wsShakerFactory.sendUnsupportedVersionResponse(channel);
        } else {
            // 向客户端发送websocket握手,完成握手
            // 客户端收到的状态是101 sitching protocol
            wsShakerHandler.handshake(channel, request);
        }
    }

    /**
     * websocket通信
     *
     * @param request
     */
    private void processWebsocketRequest(ChannelHandlerContext ctx,
                                         WebSocketFrame request) {
        if (request instanceof CloseWebSocketFrame) {
             MptpLogUtils.i("远程UI关闭websocket");
            SessionManager.remove(ctx.channel());
        } else if (request instanceof PongWebSocketFrame) {
            Session clinet = SessionManager.get(ctx.channel());
            if (clinet != null)
                clinet.setTimeOut(false);
        } else if (request instanceof TextWebSocketFrame) {

            TextWebSocketFrame textWebSocketFrame = (TextWebSocketFrame) request;
            String text = textWebSocketFrame.text();

//             MptpLogUtils.i("收到设备内容:" + text);

            try {
                MpPacket mpPacket = JSON.parseObject(text, MpPacket.class);
                ctx.fireChannelRead(mpPacket);
            } catch (JSONException e) {
                 MptpLogUtils.e("非法json数据:" + text);
            }
        }
    }

}
