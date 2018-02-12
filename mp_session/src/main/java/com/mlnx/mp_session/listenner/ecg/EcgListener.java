package com.mlnx.mp_session.listenner.ecg;

import com.mlnx.mp_session.domain.EcgInfo;
import com.mlnx.mp_session.listenner.DeviceListenner;
import com.mlnx.mptp.mptp.body.Topic;

import java.util.List;

public interface EcgListener extends DeviceListenner {

	/**
	 * 收到心电body
	 *
	 * @param topics
	 */
	public void reciveEcgInfo(List<Topic> topics, EcgInfo ecgInfo);


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
