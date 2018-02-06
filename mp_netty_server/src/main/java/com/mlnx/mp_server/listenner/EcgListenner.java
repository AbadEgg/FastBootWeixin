package com.mlnx.mp_server.listenner;

import com.mlnx.analysis.domain.ReadEcgAnalysResult;
import com.mlnx.mptp.model.Ecg;
import com.mlnx.mptp.mptp.body.Topic;

public interface EcgListenner extends DeviceListenner {

	/**
	 * 收到心电body
	 *
	 * @param topic
	 */
	public void reciveEcgBody(Topic topic, Ecg ecg);

	/**
	 * 收到分析完成的数据
	 * @param topic
	 * @param result
	 */
	public void reciveReadEcgAnalysResult(Topic topic, ReadEcgAnalysResult result);

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
