package com.mlnx.mp_session.core;

import com.mlnx.core.DeviceShare;
import com.mlnx.mp_session.config.ConfigService;
import com.mlnx.mp_session.listenner.BroadCast;
import com.mlnx.mptp.DeviceType;
import com.mlnx.mptp.mptp.MpPacket;
import com.mlnx.mptp.mptp.body.Topic;
import com.mlnx.mptp.mptp.body.TopicType;
import com.mlnx.mptp.utils.MptpLogUtils;
import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {

    private static Map<Channel, Session> sessionMap = new ConcurrentHashMap<Channel, Session>();
    // private static Map<Session, Channel> sessionKeyMap = new
    // ConcurrentHashMap<Session, Channel>();

    private static Map<String, Channel> channelMap = new ConcurrentHashMap<String, Channel>();
    private static List<Session> ecgSessions = new ArrayList<>();

    private static List<Session> configSessions = new ArrayList<>();

    private static boolean chageFlag = false;

    private static DeviceShare deviceShare;

    public static void setDeviceShare(DeviceShare deviceShare) {
        SessionManager.deviceShare = deviceShare;
    }

    public static List<Session> getEcgSessions() {
        return ecgSessions;
    }

    public static List<Session> getConfigSessions() {
        return configSessions;
    }

    public static void add(Channel channel, Session session) {

        Channel ch = get(session.getKey());
        if (ch != null) {
            MptpLogUtils.w("重复注册");
        }
        sessionMap.put(channel, session);
        channelMap.put(session.getKey(), channel);

        String deviceId = null;

        if (session instanceof DeviceSession) {
            if (session.getDeviceType().equals(DeviceType.ECG_DEVICE) || session.getDeviceType().equals(DeviceType.MP_DEVICE)) {
                deviceId = ((DeviceSession) session).getDeviceId();
                deviceShare.saveDevice(deviceId);
                BroadCast.ecgBroadCast.deviceOnline(new Topic(TopicType.U_DEVICE_ONLINE_TOPIC,deviceId),deviceId);
            }
        }

        chageFlag = true;
    }

    public static void addConfig(Session session) {
        if (!configSessions.contains(session)) {
            configSessions.add(session);
        }
    }

    public static Session getConfig(String key) {
        Channel channel = get(key);
        if (channel != null) {
            Session session = get(channel);
            if (configSessions.contains(session)) {
                return session;
            }
        }
        return null;
    }

    public static Session get(Channel channel) {
        return sessionMap.get(channel);
    }

    public static Channel get(String key) {
        return channelMap.get(key);
    }

    public static void refreshLastTime(Channel channel) {
        Session session = sessionMap.get(channel);
        if (session != null) {
            session.setLastPacketTime(new Date());
        }
    }

    public static void remove(Channel channel) {
        remove(channel, false);
    }

    public static void remove(Channel channel, boolean closeChannel) {
        Session session = sessionMap.get(channel);

        if (session == null) {
            channel.close();
            return;
        }

        channelMap.remove(session.getKey());
        sessionMap.remove(channel);
        session.removeLis();

        if(session instanceof DeviceSession){
            deviceShare.deleteDevice(((DeviceSession) session).getDeviceId());
        }

        ecgSessions.remove(session);
        configSessions.remove(session);

        if (closeChannel) {
            channel.close();
        }
        chageFlag = true;
    }

    public static void removeAllLis() {
        for (Session session : ecgSessions) {
            session.removeLis();
        }
    }

    public static int refreshSessions() {

        int sleepTime = ConfigService.DeviceReaderIdleMaxTimeSeconds;
        boolean change = false;

        for (Channel channel : sessionMap.keySet()) {
            Session session = sessionMap.get(channel);
            long newTime = System.currentTimeMillis();

            int second = (int) ((newTime - session.getLastPacketTime().getTime()) / 1000);
            if (second >= session.getReaderIdleTimeSeconds()) {
                if (session.isTimeOut()
                        && second >= ConfigService.RE_DETECT_SECONDS) {
                    remove(channel, true);
                    MptpLogUtils.e("接收超时 关闭channel:" + session);
                    change = true;
                } else if (!session.isTimeOut()) {
                    session.timeOut = true;
                    channel.writeAndFlush(new MpPacket().ping(DeviceType.SERVER));
                    if (!session.getDeviceType().equals(DeviceType.USR)) {
                        MptpLogUtils.e("接收超时 检测...:" + session);
                    }
                } else if (ConfigService.RE_DETECT_SECONDS - second < sleepTime) {
                    sleepTime = ConfigService.RE_DETECT_SECONDS - second;
                }
            } else if (session.getReaderIdleTimeSeconds() - second < sleepTime) {
                sleepTime = session.getReaderIdleTimeSeconds() - second;
            }
        }

        // printOnline();
        if (chageFlag) {
            printOnlineInfo();
        }
        chageFlag = false;

        return sleepTime;
    }

    private static void printOnline() {

        StringBuilder builder = new StringBuilder();
        builder.append("在线客户端:\n");

        for (Channel channel : sessionMap.keySet()) {
            builder.append(sessionMap.get(channel).toString());
            builder.append("\n");
        }
        MptpLogUtils.e(builder.toString());
    }

    public static void printOnlineInfo() {
        Integer userClient = 0;
        Integer deviceClient = 0;
        for (Channel channel : sessionMap.keySet()) {
            if (sessionMap.get(channel) instanceof UsrSession) {
                userClient++;
            } else if (sessionMap.get(channel) instanceof DeviceSession) {
                deviceClient++;
            }
        }

        MptpLogUtils.i("online device: " + deviceClient);
        MptpLogUtils.i("online ui: " + userClient);
    }

    public static void main(String[] args) {
        Integer integer = 1;
        List<Integer> integers = new ArrayList<>();
        integers.add(integer);
        integers.add(integer);
        integers.add(integer);

        System.out.println(integers.size());

        integers.remove(integer);
        System.out.println(integers.size());

    }
}
