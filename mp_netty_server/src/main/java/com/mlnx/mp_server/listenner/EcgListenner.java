package com.mlnx.mp_server.listenner;

import com.mlnx.mptp.model.Ecg;
import com.mlnx.mptp.utils.TopicUtils.DeviceTopic;

public interface EcgListenner extends DeviceListenner {

	/**
	 * 收到心电body
	 * 
	 * @param topic
	 */
	public void reciveEcgBody(DeviceTopic topic, Ecg ecg);

	/**
	 * 开始心电数据
	 * @param patientId
	 */
	public void startEcgPacket(Integer patientId);

	/**
	 * 停止心电数据
	 * @param patientId
	 */
	public void stopEcgPacket(Integer patientId);

}
