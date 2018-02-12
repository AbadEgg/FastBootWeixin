package com.mlnx.qcms.protocol.body;

import com.mlnx.qcms.utils.ByteUtils;
import com.mlnx.qcms.utils.StringUtils;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fzh
 * @create 2018/1/24 10:50
 */
public class AlarmSetting extends DataHeader{

    private int paramValidNum;	//有效的个数
    private AlarmSwith[] alarmSwiths;			//模块报警开关
    private AlarmLevel[] alarmLevels;			//模块报警级别
    private AgModuleItem[] agModuleItems; //AG 报警级别0-3: CO2, N2O, O2, AA
    private List<AlarmSettingElement> paramSetting;

    public int getParamValidNum() {
        return paramValidNum;
    }

    public void setParamValidNum(int paramValidNum) {
        this.paramValidNum = paramValidNum;
    }

    public AlarmSwith[] getAlarmSwiths() {
        return alarmSwiths;
    }

    public void setAlarmSwiths(AlarmSwith[] alarmSwiths) {
        this.alarmSwiths = alarmSwiths;
    }

    public AlarmLevel[] getAlarmLevels() {
        return alarmLevels;
    }

    public void setAlarmLevels(AlarmLevel[] alarmLevels) {
        this.alarmLevels = alarmLevels;
    }

    public AgModuleItem[] getAgModuleItems() {
        return agModuleItems;
    }

    public void setAgModuleItems(AgModuleItem[] agModuleItems) {
        this.agModuleItems = agModuleItems;
    }

    public List<AlarmSettingElement> getParamSetting() {
        return paramSetting;
    }

    public void setParamSetting(List<AlarmSettingElement> paramSetting) {
        this.paramSetting = paramSetting;
    }

    public AlarmSetting() {
        alarmSwiths = new AlarmSwith[PackageType.PACKAGE_MODULE_END.getCode()];
        alarmLevels = new AlarmLevel[PackageType.PACKAGE_MODULE_END.getCode()];
        agModuleItems = new AgModuleItem[AgModuleItem.AG_ITEM_MAX.getCode()];
        paramSetting = new ArrayList<>(ParamName.PARAM_MAX.getCode());
        packageType = PackageType.PACKAGE_ALARM_SETTING;
    }

    @Override
    public void decodeData(ByteBuffer buf) throws UnsupportedEncodingException {
        byte[] b2 = new byte[2];
        buf.get(b2);
        paramValidNum = ByteUtils.bytesToInt(b2,2);
        for (int i = 0; i <PackageType.PACKAGE_MODULE_END.getCode(); i++) {
            alarmSwiths[i] = AlarmSwith.decode(buf.get());
        }
        for (int i = 0; i <PackageType.PACKAGE_MODULE_END.getCode(); i++) {
            alarmLevels[i] = AlarmLevel.decode(buf.get());
        }
        for (int i = 0; i <AgModuleItem.AG_ITEM_MAX.getCode(); i++) {
            agModuleItems[i] = AgModuleItem.decode(buf.get());
        }
        for (int i = 0; i <ParamName.PARAM_MAX.getCode(); i++) {
            AlarmSettingElement alarmSettingElement = new AlarmSettingElement();
            buf.get(b2);
            alarmSettingElement.setParamName(StringUtils.bytesToString(b2));
            buf.get(b2);
            alarmSettingElement.setAlarmHi(ByteUtils.bytesToInt(b2,2));
            buf.get(b2);
            alarmSettingElement.setAlarmLow(ByteUtils.bytesToInt(b2,2));
            buf.get(b2);
            alarmSettingElement.setAlarmLevel(ByteUtils.bytesToInt(b2,2));
            alarmSettingElement.setAlarmSwitch(AlarmSwith.decode(buf.get()));
            buf.get(b2);
            alarmSettingElement.setAlarmLimitZoomValue(ByteUtils.bytesToInt(b2,2));
            alarmSettingElement.setReserve(buf.get());
        }
    }
}
