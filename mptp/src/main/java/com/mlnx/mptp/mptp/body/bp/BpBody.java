package com.mlnx.mptp.mptp.body.bp;

import com.mlnx.mptp.model.BpControl;
import com.mlnx.mptp.model.BpDeviceInfo;
import com.mlnx.mptp.model.BpResult;
import com.mlnx.mptp.mptp.InvalidPacketException;
import com.mlnx.mptp.mptp.body.GroupType;
import com.mlnx.mptp.mptp.config.MptpConfig;
import com.mlnx.mptp.utils.ByteUtils;
import com.mlnx.mptp.utils.MptpLogUtils;
import com.mlnx.mptp.utils.ReadUtils;

import java.nio.ByteBuffer;

public class BpBody {

	private static final ByteBuffer buffer = ByteBuffer
			.allocate(MptpConfig.BP_BODY_CAPABILITY);

	private BpControl bpControl;
	private BpDeviceInfo bpDeviceInfo;
	private BpResult bpResult;

	public BpBody() {
		bpDeviceInfo = new BpDeviceInfo();
		bpResult = new BpResult();
		bpControl = new BpControl();
	}

	public BpDeviceInfo getBpDeviceInfo() {
		return bpDeviceInfo;
	}

	public void setBpDeviceInfo(BpDeviceInfo bpDeviceInfo) {
		this.bpDeviceInfo = bpDeviceInfo;
	}

	public BpResult getBpResult() {
		return bpResult;
	}

	public void setBpResult(BpResult bpResult) {
		this.bpResult = bpResult;
	}

	public void setBpControl(BpControl bpControl) {
		this.bpControl = bpControl;
	}

	public void decodeData(GroupType groupType, ByteBuffer frame,
                           String dataType, int dataLen) throws InvalidPacketException {
		switch (groupType) {
		case BP_DEVICE_MODE:
			DeviceRunMode deviceRunMode = DeviceRunMode.decode(ReadUtils
					.readData(frame, dataType, dataLen));
			bpDeviceInfo.setDeviceRunMode(deviceRunMode);
			MptpLogUtils.mpDecode("收到设备模式：" + deviceRunMode);
			break;
		case DYNAMIC_MEASURE_INTERVAL:
			Integer dynamicMeasureInterval = ReadUtils.readData(frame,
					dataType, dataLen);
			bpDeviceInfo.setDynamicMeasureInterval(dynamicMeasureInterval);
			MptpLogUtils.mpDecode("收到设备时间间隔：" + dynamicMeasureInterval);
			break;
		case BP_BAT:
			Integer currentBat = ReadUtils.readData(frame, dataType, dataLen);
			bpDeviceInfo.setCurrentBat(currentBat);
			MptpLogUtils.mpDecode("收到设备电量：" + currentBat);
			break;
		case CURRENT_PRESSURE:
			Integer currentPressure = ReadUtils.readData(frame, dataType,
					dataLen);
			bpDeviceInfo.setCurrentPressure(currentPressure);
			MptpLogUtils.mpDecode("收到当前压力：" + currentPressure);
			break;
		case INSERT_SPO:
			Integer insertSpo = ReadUtils.readData(frame, dataType, dataLen);
			bpDeviceInfo.setInsertSpo(insertSpo);
			MptpLogUtils.mpDecode("是否插入血氧设备：" + insertSpo);
			break;
		case BP_AO_CONTROL:
			Integer bpControl = ReadUtils.readData(frame, dataType, dataLen);
			bpDeviceInfo.setBpControl(bpControl);
			MptpLogUtils.mpDecode("电机电磁阀控制：" + bpControl);
			break;

		case SBP:
			Integer resultSbp = ReadUtils.readData(frame, dataType, dataLen);
			bpResult.setResultSbp(resultSbp);
			MptpLogUtils.mpDecode("收到收缩压：" + resultSbp);
			break;
		case DBP:
			Integer resultDbp = ReadUtils.readData(frame, dataType, dataLen);
			bpResult.setResultDbp(resultDbp);
			MptpLogUtils.mpDecode("收到舒张压：" + resultDbp);
			break;
		case HEART:
			Integer resultHeart = ReadUtils.readData(frame, dataType, dataLen);
			bpResult.setResultHeart(resultHeart);
			MptpLogUtils.mpDecode("收到心率：" + resultHeart);
			break;

		case CREDIBILITY:
			int real = ReadUtils.readData(frame, dataType, dataLen);
			// 可信度
			Boolean isConfidence = (real == 0);
			Boolean isSucess = (real != 2);
			bpResult.setReal(real);
			bpResult.setConfidence(isConfidence);
			bpResult.setSucess(isSucess);
			MptpLogUtils.mpDecode("检测" + (isSucess ? "成功" : "失败"));
			MptpLogUtils.mpDecode("收到数据可信度：" + isConfidence);
			break;

		case WEAR:
			int wear = ReadUtils.readData(frame, dataType, dataLen);
			Boolean wearMode = (wear == 0);
			bpResult.setWear(wear);
			bpResult.setWearMode(wearMode);
			MptpLogUtils.mpDecode("佩戴方式" + (wearMode ? "正确" : "错误"));
		}
	}

	public byte[] encode() {

		buffer.clear();

		if (bpDeviceInfo.getDeviceRunMode() != null) {
			buffer.put(GroupType.BP_DEVICE_MODE.getEncodes());
			buffer.put(new String("BT").getBytes());
			buffer.put((byte) 1);
			buffer.put((byte) bpDeviceInfo.getDeviceRunMode().getCode());
		}
		if (bpDeviceInfo.getDynamicMeasureInterval() != null) {
			buffer.put(GroupType.DYNAMIC_MEASURE_INTERVAL.getEncodes());
			buffer.put(new String("BT").getBytes());

			buffer.put((byte) 1);
			buffer.put((byte) bpDeviceInfo.getDynamicMeasureInterval()
					.byteValue());
		}
		if (bpDeviceInfo.getCurrentBat() != null) {
			buffer.put(GroupType.BP_BAT.getEncodes());
			buffer.put(new String("BT").getBytes());

			buffer.put((byte) 1);
			buffer.put((byte) bpDeviceInfo.getCurrentBat().byteValue());
		}
		if (bpDeviceInfo.getCurrentPressure() != null) {
			buffer.put(GroupType.CURRENT_PRESSURE.getEncodes());
			buffer.put(new String("US").getBytes());
			byte[] bs = ByteUtils.intToBytes(bpDeviceInfo.getCurrentPressure(),
					2);
			buffer.put((byte) bs.length);
			buffer.put(bs);
		}
		if (bpDeviceInfo.getInsertSpo() != null) {
			buffer.put(GroupType.INSERT_SPO.getEncodes());
			buffer.put(new String("CS").getBytes());

			buffer.put((byte) 1);
			buffer.put((byte) bpDeviceInfo.getInsertSpo().byteValue());
		}

		if (bpDeviceInfo.getBpControl() != null) {
			buffer.put(GroupType.BP_AO_CONTROL.getEncodes());
			buffer.put(new String("BT").getBytes());

			buffer.put((byte) 1);
			buffer.put((byte) bpDeviceInfo.getBpControl().byteValue());
		}

		if (bpControl.isControl()) {
			buffer.put(GroupType.BP_AO_CONTROL.getEncodes());
			buffer.put(new String("CS").getBytes());

			buffer.put((byte) 1);
			buffer.put(bpControl.getCode());
		}

		if (bpResult.getResultSbp() != null) {
			buffer.put(GroupType.SBP.getEncodes());
			buffer.put(new String("US").getBytes());
			byte[] bs = ByteUtils.intToBytes(bpResult.getResultSbp(), 2);
			buffer.put((byte) bs.length);
			buffer.put(bs);
		}

		if (bpResult.getResultDbp() != null) {
			buffer.put(GroupType.DBP.getEncodes());
			buffer.put(new String("US").getBytes());
			byte[] bs = ByteUtils.intToBytes(bpResult.getResultDbp(), 2);
			buffer.put((byte) bs.length);
			buffer.put(bs);
		}

		if (bpResult.getResultHeart() != null) {
			buffer.put(GroupType.HEART.getEncodes());
			buffer.put(new String("US").getBytes());
			byte[] bs = ByteUtils.intToBytes(bpResult.getResultHeart(), 2);
			buffer.put((byte) bs.length);
			buffer.put(bs);
		}

		if (bpResult.getReal() != null) {
			buffer.put(GroupType.CREDIBILITY.getEncodes());
			buffer.put(new String("BT").getBytes());
			buffer.put((byte) 1);
			buffer.put(bpResult.getReal().byteValue());
		}

		if (bpResult.getWear() != null) {
			buffer.put(GroupType.WEAR.getEncodes());
			buffer.put(new String("BT").getBytes());
			buffer.put((byte) 1);
			buffer.put(bpResult.getWear().byteValue());
		}

		buffer.flip();
		byte[] bs = new byte[buffer.remaining()];
		buffer.get(bs);
		return bs;
	}

	public void openValveR(boolean open) {
		Integer bpControl;
		if (open) {
			bpControl = 0x80;

		} else {
			bpControl = 0x40;
		}
		bpDeviceInfo.setBpControl(bpControl);
	}

	public void openValveM(boolean open) {
		Integer bpControl;
		if (open) {
			bpControl = 0x20;

		} else {
			bpControl = 0x10;
		}
		bpDeviceInfo.setBpControl(bpControl);
	}

	public void runPump(boolean open) {
		Integer bpControl;
		if (open) {
			bpControl = 0x04;

		} else {
			bpControl = 0x08;
		}
		bpDeviceInfo.setBpControl(bpControl);
	}

}
