package com.mlnx.mp_session.listenner.bp;

import com.mlnx.mp_session.domain.BpInfo;
import com.mlnx.mp_session.listenner.DeviceListenner;
import com.mlnx.mptp.mptp.body.Topic;

import java.util.List;

public interface BpListener extends DeviceListenner {
	/**
	 * 收到血压body
	 * 
	 * @param topics
	 * @param bpInfo
	 */
	public void reciveBpInfo(List<Topic> topics, BpInfo bpInfo);
}
