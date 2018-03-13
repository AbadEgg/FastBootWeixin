package com.mlnx.qcms.protocol.body;

import com.mlnx.qcms.protocol.Codec;
import com.mlnx.qcms.protocol.DataHeaderI;
import com.mlnx.qcms.protocol.config.TpConfig;
import com.mlnx.qcms.utils.ByteUtils;
import com.mlnx.qcms.utils.InvalidPacketException;
import com.mlnx.qcms.utils.QcmsLogUtils;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 * 协议内容
 *
 * @author fzh
 * @create 2018/1/23 9:17
 */
public class Body implements Codec {

    private static final ByteBuffer buffer = ByteBuffer
            .allocate(TpConfig.BODY_CAPABILITY);

    private static final int BODY_HEAD_SIZE = 4;

    private PatientInfo patientInfo;

    private UserInfoV1 userInfoV1;

    private ModuleInfo moduleInfo;

    private Command command;

    private EcgData ecgData;

    private Spo2Data spo2Data;

    private RespData respData;

    private TempData tempData;

    private NibpData nibpData;

    private CO2Data co2Data;

    private AgData agData;

    private COData coData;

    private IBP1Data ibp1Data;

    private IBP2Data ibp2Data;

    private IBP3Data ibp3Data;

    private IBP4Data ibp4Data;

    private BisData bisData;

    private TechnologyAlarm technologyAlarm;

    private PhysiologyAlarm physiologyAlarm;

    private AlarmSetting alarmSetting;

    private ConnectionInfo connectionInfo;

    public PatientInfo getPatientInfo() {
        return patientInfo;
    }

    public void setPatientInfo(PatientInfo patientInfo) {
        this.patientInfo = patientInfo;
    }

    public UserInfoV1 getUserInfoV1() {
        return userInfoV1;
    }

    public void setUserInfoV1(UserInfoV1 userInfoV1) {
        this.userInfoV1 = userInfoV1;
    }

    public ModuleInfo getModuleInfo() {
        return moduleInfo;
    }

    public void setModuleInfo(ModuleInfo moduleInfo) {
        this.moduleInfo = moduleInfo;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public EcgData getEcgData() {
        return ecgData;
    }

    public void setEcgData(EcgData ecgData) {
        this.ecgData = ecgData;
    }

    public Spo2Data getSpo2Data() {
        return spo2Data;
    }

    public void setSpo2Data(Spo2Data spo2Data) {
        this.spo2Data = spo2Data;
    }

    public RespData getRespData() {
        return respData;
    }

    public void setRespData(RespData respData) {
        this.respData = respData;
    }

    public TempData getTempData() {
        return tempData;
    }

    public void setTempData(TempData tempData) {
        this.tempData = tempData;
    }

    public NibpData getNibpData() {
        return nibpData;
    }

    public void setNibpData(NibpData nibpData) {
        this.nibpData = nibpData;
    }

    public CO2Data getCo2Data() {
        return co2Data;
    }

    public void setCo2Data(CO2Data co2Data) {
        this.co2Data = co2Data;
    }

    public AgData getAgData() {
        return agData;
    }

    public void setAgData(AgData agData) {
        this.agData = agData;
    }

    public COData getCoData() {
        return coData;
    }

    public void setCoData(COData coData) {
        this.coData = coData;
    }

    public IBP1Data getIbp1Data() {
        return ibp1Data;
    }

    public void setIbp1Data(IBP1Data ibp1Data) {
        this.ibp1Data = ibp1Data;
    }

    public IBP2Data getIbp2Data() {
        return ibp2Data;
    }

    public void setIbp2Data(IBP2Data ibp2Data) {
        this.ibp2Data = ibp2Data;
    }

    public IBP3Data getIbp3Data() {
        return ibp3Data;
    }

    public void setIbp3Data(IBP3Data ibp3Data) {
        this.ibp3Data = ibp3Data;
    }

    public IBP4Data getIbp4Data() {
        return ibp4Data;
    }

    public void setIbp4Data(IBP4Data ibp4Data) {
        this.ibp4Data = ibp4Data;
    }

    public BisData getBisData() {
        return bisData;
    }

    public void setBisData(BisData bisData) {
        this.bisData = bisData;
    }

    public TechnologyAlarm getTechnologyAlarm() {
        return technologyAlarm;
    }

    public void setTechnologyAlarm(TechnologyAlarm technologyAlarm) {
        this.technologyAlarm = technologyAlarm;
    }

    public PhysiologyAlarm getPhysiologyAlarm() {
        return physiologyAlarm;
    }

    public void setPhysiologyAlarm(PhysiologyAlarm physiologyAlarm) {
        this.physiologyAlarm = physiologyAlarm;
    }

    public AlarmSetting getAlarmSetting() {
        return alarmSetting;
    }

    public void setAlarmSetting(AlarmSetting alarmSetting) {
        this.alarmSetting = alarmSetting;
    }

    public ConnectionInfo getConnectionInfo() {
        return connectionInfo;
    }

    public void setConnectionInfo(ConnectionInfo connectionInfo) {
        this.connectionInfo = connectionInfo;
    }

    public Body() {

    }

    /**
     * 解析数据
     * @param byteBuf
     * @return
     */
    @Override
    public void decode(ByteBuffer byteBuf) throws UnsupportedEncodingException {
        while (byteBuf.remaining()>4){
            int packageTypeCode = 0;
            packageTypeCode |= (byteBuf.get() & 0x000000ff);
//            System.out.println(String.format("0x%x",packageTypeCode));
            PackageType packageType = PackageType.decode(packageTypeCode);
//            System.out.println(packageType);
            int checkSum = 0;
            int packageLength = 0;
            int contentLength = 0;
            if (packageType == null) {
                throw new InvalidPacketException("收到非法包类型："
                        + String.format("0x%x", packageTypeCode));
            } else {
                QcmsLogUtils.mpDecode(packageType.toString());
                checkSum |= byteBuf.get() & 0x000000ff ;
                byte[] dst = new byte[2];
                byteBuf.get(dst);
                packageLength = ByteUtils.bytesToInt(dst,2);
                contentLength = packageLength - BODY_HEAD_SIZE;
            }
            if(byteBuf.remaining() < contentLength){
                throw new InvalidPacketException(String.format(
                        "当前剩余内容长度:%d, 需要的内容长度：%d", byteBuf.remaining(), contentLength));
            }
            DataHeaderI dataHeaderI = null;
            switch (packageType){
//                case PACKAGE_MODULE_BEGIN:
//                    EcgData ecgData = EcgData.decode(byteBuf.get(new byte[171],0,171));
//                    ecgData.setPackageType(packageType);
//                    ecgData.setChecksum(checkSum);
//                    ecgData.setPackageLength(packageLength);
//                    body.setEcgData(ecgData);
//                    break;
                case PACKAGE_ECG:
                    ecgData = new EcgData();
                    dataHeaderI = ecgData;
                    break;
                case PACKAGE_SPO2:
                    spo2Data = new Spo2Data();
                    dataHeaderI = spo2Data;
                    break;
                case PACKAGE_TEMP:
                    tempData = new TempData();
                    dataHeaderI = tempData;
                    break;
                case PACKAGE_NIBP:
                    nibpData = new NibpData();
                    dataHeaderI = nibpData;
                    break;
                case PACKAGE_RESP:
                    respData = new RespData();
                    dataHeaderI = respData;
                    break;
                case PACKAGE_CO2:
                    co2Data = new CO2Data();
                    dataHeaderI = co2Data;
                    break;
                case PACKAGE_AG:
                    agData = new AgData();
                    dataHeaderI = agData;
                    break;
                case PACKAGE_CO:
                    coData = new COData();
                    dataHeaderI = coData;
                    break;
                case PACKAGE_IBP1:
                    ibp1Data = new IBP1Data();
                    dataHeaderI = ibp1Data;
                    break;
                case PACKAGE_IBP2:
                    ibp2Data = new IBP2Data();
                    dataHeaderI = ibp2Data;
                    break;
                case PACKAGE_IBP3:
                    ibp3Data = new IBP3Data();
                    dataHeaderI = ibp3Data;
                    break;
                case PACKAGE_IBP4:
                    ibp4Data = new IBP4Data();
                    dataHeaderI = ibp4Data;
                    break;
                case PACKAGE_BIS:
                    bisData = new BisData();
                    dataHeaderI = bisData;
                    break;
//                case PACKAGE_MODULE_END:
//                    break;
                case PACKAGE_USERINFO:
                    patientInfo = new PatientInfo();
                    dataHeaderI = patientInfo;
                    break;
                case PACKAGE_MODULE_INFO:
                    moduleInfo = new ModuleInfo();
                    dataHeaderI = moduleInfo;
                    break;
                case PACKAGE_CMD:
                    command = new Command();
                    dataHeaderI = command;
                    break;
                case PACKAGE_ALARM_SETTING:
                    alarmSetting = new AlarmSetting();
                    dataHeaderI = alarmSetting;
                    break;
                case PACKAGE_TECHNOLOGY_ALARM:
                    technologyAlarm = new TechnologyAlarm();
                    dataHeaderI = technologyAlarm;
                    break;
                case PACKAGE_PHYSIOLOGY_ALRAM:
                    physiologyAlarm = new PhysiologyAlarm();
                    dataHeaderI = physiologyAlarm;
                    break;
                case PACKAGE_USERINFOV1:
                    userInfoV1 = new UserInfoV1();
                    dataHeaderI = userInfoV1;
                    break;
                default:
                    break;
            }
            dataHeaderI.setPackageType(packageType);
            dataHeaderI.setChecksum(checkSum);
            dataHeaderI.setPackageLength(packageLength);
            dataHeaderI.decodeData(byteBuf);
        }
    }

    @Override
    public byte[] encode() {
        buffer.clear();

        if(patientInfo!=null){
            buffer.put(patientInfo.encodeData());
        }
        if(userInfoV1!=null){
            buffer.put(userInfoV1.encodeData());
        }
        if(moduleInfo!=null){
            buffer.put(moduleInfo.encodeData());
        }
        if(command!=null){
            buffer.put(command.encodeData());
        }
        if(ecgData!=null){
            buffer.put(ecgData.encodeData());
        }
        if(spo2Data!=null){
            buffer.put(spo2Data.encodeData());
        }
        if(respData!=null){
            buffer.put(respData.encodeData());
        }
        if(tempData!=null){
            buffer.put(tempData.encodeData());
        }
        if(nibpData!=null){
            buffer.put(nibpData.encodeData());
        }
        if(co2Data!=null){
            buffer.put(co2Data.encodeData());
        }
        if(agData!=null){
            buffer.put(agData.encodeData());
        }
        if(coData!=null){
            buffer.put(coData.encodeData());
        }
        if(ibp1Data!=null){
            buffer.put(ibp1Data.encodeData());
        }
        if(ibp2Data!=null){
            buffer.put(ibp2Data.encodeData());
        }
        if(ibp3Data!=null){
            buffer.put(ibp3Data.encodeData());
        }
        if(ibp4Data!=null){
            buffer.put(ibp4Data.encodeData());
        }
        if(bisData!=null){
            buffer.put(bisData.encodeData());
        }
        if(technologyAlarm!=null){
            buffer.put(technologyAlarm.encodeData());
        }
        if(physiologyAlarm!=null){
            buffer.put(physiologyAlarm.encodeData());
        }
        if(alarmSetting!=null){
            buffer.put(alarmSetting.encodeData());
        }
        if(connectionInfo!=null){
            buffer.put(connectionInfo.encodeData());
        }
        buffer.flip();
        byte[] bs = new byte[buffer.remaining()];
        buffer.get(bs);
        return bs;
    }

    public int calPackageBytes(){
        int value = 16;
        if(patientInfo!=null){
            value += patientInfo.packageLength;
        }
        if(userInfoV1!=null){
            value += userInfoV1.packageLength;
        }
        if(moduleInfo!=null){
            value += moduleInfo.packageLength;
        }
        if(command!=null){
            value += command.packageLength;
        }
        if(ecgData!=null){
            value += ecgData.packageLength;
        }
        if(spo2Data!=null){
            value += spo2Data.packageLength;
        }
        if(respData!=null){
            value += respData.packageLength;
        }
        if(tempData!=null){
            value += tempData.packageLength;
        }
        if(nibpData!=null){
            value += nibpData.packageLength;
        }
        if(co2Data!=null){
            value += co2Data.packageLength;
        }
        if(agData!=null){
            value += agData.packageLength;
        }
        if(coData!=null){
            value += coData.packageLength;
        }
        if(ibp1Data!=null){
            value += ibp1Data.packageLength;
        }
        if(ibp2Data!=null){
            value += ibp2Data.packageLength;
        }
        if(ibp3Data!=null){
            value += ibp3Data.packageLength;
        }
        if(ibp4Data!=null){
            value += ibp4Data.packageLength;
        }
        if(bisData!=null){
            value += bisData.packageLength;
        }
        if(technologyAlarm!=null){
            value += technologyAlarm.packageLength;
        }
        if(physiologyAlarm!=null){
            value += physiologyAlarm.packageLength;
        }
        if(alarmSetting!=null){
            value += alarmSetting.packageLength;
        }
        if(connectionInfo!=null){
            value += connectionInfo.packageLength;
        }
        return value;
    }

    public int calPackageNum(){
        int value = 0;
        if(patientInfo!=null){
            value++;
        }
        if(userInfoV1!=null){
            value++;
        }
        if(moduleInfo!=null){
            value++;
        }
        if(command!=null){
            value++;
        }
        if(ecgData!=null){
            value++;
        }
        if(spo2Data!=null){
            value++;
        }
        if(respData!=null){
            value++;
        }
        if(tempData!=null){
            value++;
        }
        if(nibpData!=null){
            value++;
        }
        if(co2Data!=null){
            value++;
        }
        if(agData!=null){
            value++;
        }
        if(coData!=null){
            value++;
        }
        if(ibp1Data!=null){
            value++;
        }
        if(ibp2Data!=null){
            value++;
        }
        if(ibp3Data!=null){
            value++;
        }
        if(ibp4Data!=null){
            value++;
        }
        if(bisData!=null){
            value++;
        }
        if(technologyAlarm!=null){
            value++;
        }
        if(physiologyAlarm!=null){
            value++;
        }
        if(alarmSetting!=null){
            value++;
        }
        if(connectionInfo!=null){
            value++;
        }
        return value;
    }

}
