package com.mlnx.mptp.mptp.body;

import com.mlnx.mptp.ResponseCode;
import com.mlnx.mptp.mptp.Codec;
import com.mlnx.mptp.mptp.InvalidPacketException;
import com.mlnx.mptp.mptp.body.bp.BpBody;
import com.mlnx.mptp.mptp.body.config.ConfigBody;
import com.mlnx.mptp.mptp.body.ecg.EcgBody;
import com.mlnx.mptp.mptp.body.spo.SPOBody;
import com.mlnx.mptp.mptp.config.MptpConfig;
import com.mlnx.mptp.utils.ByteUtils;
import com.mlnx.mptp.utils.MptpLogUtils;
import com.mlnx.mptp.utils.ReadUtils;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;

public class Body implements Codec {

    private static final ByteBuffer buffer = ByteBuffer
            .allocate(MptpConfig.BODY_CAPABILITY);

    private Long packetTime;
    private String deviceId;
    private Integer patientId;
    private Float temp;
    private ResponseCode responseCode;
    private Command command;
    private String topic;
    private DeviceState deviceState;

    private Integer keepAliveTimer;
    private Integer messageId;

    private String name;
    private String passWord;

    private BpBody bpBody;
    private SPOBody spoBody;
    private EcgBody ecgBody;
    private ConfigBody configBody;

    public EcgBody buildEcgBody(){
        if (ecgBody == null){
            ecgBody = new EcgBody();
        }

        return ecgBody;
    }

    @Override
    public void init() {
        bpBody = new BpBody();
        bpBody.init();
        spoBody = new SPOBody();
        spoBody.init();
        ecgBody = new EcgBody();
        ecgBody.init();
        configBody = new ConfigBody();
    }

    public Float getTemp() {
        return temp;
    }

    public void setTemp(Float temp) {
        this.temp = temp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Integer getKeepAliveTimer() {
        return keepAliveTimer;
    }

    public void setKeepAliveTimer(Integer keepAliveTimer) {
        this.keepAliveTimer = keepAliveTimer;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public DeviceState getDeviceState() {
        return deviceState;
    }

    public void setDeviceState(DeviceState deviceState) {
        this.deviceState = deviceState;
    }

    public BpBody getBpBody() {
        return bpBody;
    }

    public SPOBody getSpoBody() {
        return spoBody;
    }

    public EcgBody getEcgBody() {
        return ecgBody;
    }

    public void setEcgBody(EcgBody ecgBody) {
        this.ecgBody = ecgBody;
    }

    public void setBpBody(BpBody bpBody) {
        this.bpBody = bpBody;
    }

    public void setSpoBody(SPOBody spoBody) {
        this.spoBody = spoBody;
    }

    public ConfigBody getConfigBody() {
        return configBody;
    }

    public void setConfigBody(ConfigBody configBody) {
        this.configBody = configBody;
    }

    public Long getPacketTime() {
        return packetTime;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setPacketTime(Long packetTime) {
        this.packetTime = packetTime;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    @Override
    public void decode(ByteBuffer buf) {
        // 解析各个组
        while (buf.remaining() >= 5) {
            byte[] dst = new byte[4];
            buf.get(dst);

            int groupCode = 0;
            groupCode |= (dst[0] & 0x000000ff);
            groupCode <<= 8;
            groupCode |= (dst[1] & 0x000000ff);

            GroupType groupType = GroupType.decode(groupCode);
            String dataType = new String(dst, 2, 2);

            int dataLen = 0;

            if (groupType == null) {
                throw new InvalidPacketException("收到非法组编号："
                        + String.format("0x%x", groupCode));
            } else {
                if (groupType.equals(GroupType.ENCRY_SUCCESSION_DATA) || groupType.equals(GroupType.SUCCESSION_DATA)) {
                    dst = new byte[2];
                    buf.get(dst);
                    dataLen |= (dst[1] & 0x000000ff);
                    dataLen <<= 8;
                    dataLen |= (dst[0] & 0x000000ff);
                } else {
                    dataLen = buf.get() & 0x000000ff;
                }
                MptpLogUtils.mpDecode(groupType.toString());
            }

            // 读取内容
            if (buf.remaining() >= dataLen) {
                decodeData(groupType, buf, dataType, dataLen);
            } else {

                throw new InvalidPacketException(String.format(
                        "当前剩余内容长度:%d, 需要的内容长度：%d", buf.remaining(), dataLen));
            }
        }
    }

    /**
     * 解析数据
     *
     * @return
     */
    private void decodeData(GroupType groupType, ByteBuffer buffer,
                            String dataType, int dataLen) throws InvalidPacketException {
        switch (DataType.decode(groupType.getCode())) {
            case BP_DATA:
                bpBody.decodeData(groupType, buffer, dataType, dataLen);
                break;
            case SPO_DATA:
                spoBody.decodeData(groupType, buffer, dataType, dataLen);
                break;
            case ECG_DATA:
                ecgBody.decodeData(groupType, buffer, dataType, dataLen);
                break;
            case CONFIG_DATA:
                configBody.decodeData(groupType, buffer, dataType, dataLen);
                break;
            case PACKET_DATA:
                switch (groupType) {
                    case RESPONSE_CODE:
                        responseCode = ResponseCode.decode(ReadUtils.readData(buffer,
                                dataType, dataLen));
                        MptpLogUtils.mpDecode("返回码：" + responseCode);
                        break;
                    case DEVICE_TIME:
                        packetTime = ReadUtils.readTime(buffer, dataType, dataLen);
                        bpBody.getBpResult().setResultTime(packetTime);
                        MptpLogUtils.mpDecode("收到包时间：" + packetTime);
                        break;
                    case DEVICE_ID:
                        deviceId = ReadUtils.readString(buffer, dataType, dataLen);
                        MptpLogUtils.mpDecode("收到设备ID：" + deviceId);
                        break;
                    case COMMAND:
                        command = Command.decode(ReadUtils.readData(buffer, dataType,
                                dataLen));
                        MptpLogUtils.mpDecode("命令：" + command);
                        break;
                    case TOPIC:
                        topic = ReadUtils.readString(buffer, dataType, dataLen);
                        MptpLogUtils.mpDecode("主题：" + topic);
                        break;
                    case PATIENT_ID:
                        patientId = ReadUtils.readData(buffer, dataType, dataLen);
                        bpBody.getBpResult().setPatientID(patientId);
                        MptpLogUtils.mpDecode("收到病人ID：" + patientId);
                        break;
                    case TEMP:
                        temp = ReadUtils.readTemp(buffer, dataType, dataLen);
                        MptpLogUtils.mpDecode("收到病人提问：" + temp);
                        break;
                    case KEEP_ALIVE_TIMER:
                        keepAliveTimer = ReadUtils.readData(buffer, dataType, dataLen);
                        MptpLogUtils.mpDecode("设备连接保持连接的时间：" + keepAliveTimer);
                        break;
                    case MESSAGE_ID:
                        messageId = ReadUtils.readData(buffer, dataType, dataLen);
                        MptpLogUtils.mpDecode("消息ID：" + messageId);
                        break;
                    case USR_NAME:
                        name = ReadUtils.readString(buffer, dataType, dataLen);
                        MptpLogUtils.mpDecode("收到登入用户名：" + name);
                        break;
                    case PASSOWRD:
                        passWord = ReadUtils.readString(buffer, dataType, dataLen);
                        MptpLogUtils.mpDecode("收到登入密码：" + passWord);
                        break;
                    case DEVICE_STATE:
                        int code = ReadUtils.readData(buffer, dataType, dataLen);
                        deviceState = DeviceState.decode(code);
                        MptpLogUtils.mpDecode("设备状态 code ：" + code);
                        MptpLogUtils.mpDecode("设备状态：" + deviceState);
                        break;
                }
                break;
        }
    }

    @Override
    public byte[] encode() {

        buffer.clear();

        if (deviceId != null) {
            buffer.put(GroupType.DEVICE_ID.getEncodes());
            buffer.put(new String("CS").getBytes());
            byte[] bs = deviceId.getBytes(Charset.forName("US-ASCII"));
            buffer.put((byte) bs.length);
            buffer.put(bs);
        }
        if (packetTime != null) {
            buffer.put(GroupType.DEVICE_TIME.getEncodes());
            buffer.put(new String("DT").getBytes());

            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            byte[] bs = format.format(packetTime).getBytes();
            buffer.put((byte) bs.length);
            buffer.put(bs);
        }
        if (patientId != null) {
            buffer.put(GroupType.PATIENT_ID.getEncodes());
            buffer.put(new String("UL").getBytes());

            byte[] bs = ByteUtils.intToBytes(patientId);
            buffer.put((byte) bs.length);
            buffer.put(bs);
        }
        if (responseCode != null) {
            buffer.put(GroupType.RESPONSE_CODE.getEncodes());
            buffer.put(new String("BT").getBytes());

            buffer.put((byte) 1);
            buffer.put((byte) responseCode.getCode());
        }
        if (command != null) {
            buffer.put(GroupType.COMMAND.getEncodes());
            buffer.put(new String("BT").getBytes());

            buffer.put((byte) 1);
            buffer.put((byte) command.getCode());
        }
        if (topic != null) {
            buffer.put(GroupType.TOPIC.getEncodes());
            buffer.put(new String("CS").getBytes());
            byte[] bs = topic.getBytes(Charset.forName("US-ASCII"));
            buffer.put((byte) bs.length);
            buffer.put(bs);
        }
        if (deviceState != null) {
            buffer.put(GroupType.DEVICE_STATE.getEncodes());
            buffer.put(new String("BT").getBytes());
            buffer.put((byte) 1);
            buffer.put((byte) deviceState.getCode());
        }
        if (keepAliveTimer != null) {
            buffer.put(GroupType.KEEP_ALIVE_TIMER.getEncodes());
            buffer.put(new String("US").getBytes());
            byte[] bs = ByteUtils.intToBytes(keepAliveTimer, 2);
            buffer.put((byte) bs.length);
            buffer.put(bs);
        }
        if (messageId != null) {
            buffer.put(GroupType.MESSAGE_ID.getEncodes());
            buffer.put(new String("BT").getBytes());
            buffer.put((byte) 1);
            buffer.put((byte) messageId.intValue());
        }
        if (name != null) {
            buffer.put(GroupType.USR_NAME.getEncodes());
            buffer.put(new String("CS").getBytes());
            byte[] bs = name.getBytes(Charset.forName("US-ASCII"));
            buffer.put((byte) bs.length);
            buffer.put(bs);
        }
        if (passWord != null) {
            buffer.put(GroupType.PASSOWRD.getEncodes());
            buffer.put(new String("CS").getBytes());
            byte[] bs = passWord.getBytes(Charset.forName("US-ASCII"));
            buffer.put((byte) bs.length);
            buffer.put(bs);
        }

        buffer.put(bpBody.encode());
        buffer.put(spoBody.encode());
        buffer.put(ecgBody.encode());
        buffer.put(configBody.encode());
        buffer.flip();
        byte[] bs = new byte[buffer.remaining()];
        buffer.get(bs);
        return bs;
    }

}
