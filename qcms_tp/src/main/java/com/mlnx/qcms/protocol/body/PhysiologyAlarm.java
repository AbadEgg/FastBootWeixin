package com.mlnx.qcms.protocol.body;

import com.mlnx.qcms.utils.ByteUtils;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fzh
 * @create 2018/1/24 10:20
 */
public class PhysiologyAlarm extends DataHeader{

    List<AlarmElement> alarmArray;	//这里减6,是为了和之前的版本兼容

    public List<AlarmElement> getAlarmArray() {
        return alarmArray;
    }

    public void setAlarmArray(List<AlarmElement> alarmArray) {
        this.alarmArray = alarmArray;
    }

    public PhysiologyAlarm() {
        alarmArray = new ArrayList<>(PhysiologyAlarmEnum.PALARM_DEFINE_END.getCode());
        packageType = PackageType.PACKAGE_PHYSIOLOGY_ALRAM;
    }

    @Override
    public void decodeData(ByteBuffer buf) {
        for (int i = 0; i < PhysiologyAlarmEnum.PALARM_DEFINE_END.getCode(); i++) {
            AlarmElement alarmElement = new AlarmElement();
            alarmElement.setAlarmSwith(AlarmSwith.decode(buf.get()));
            alarmElement.setAlarmLevel(AlarmLevel.decode(buf.get()));
            byte[] b4 = new byte[4];
            buf.get(b4);
            alarmElement.setAlarmOccurTime(ByteUtils.bytesToInt(b4));
            alarmElement.setNewOccure(buf.get()==0x01?true:false);
            alarmArray.add(alarmElement);
        }
    }
}
