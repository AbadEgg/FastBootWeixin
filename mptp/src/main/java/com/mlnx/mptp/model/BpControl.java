package com.mlnx.mptp.model;

public class BpControl {

	private Boolean detecte;
	private Boolean runPump;
	private Boolean openValveM;
	private Boolean openValveR;

	public Boolean getDetecte() {
		return detecte;
	}

	public void setDetecte(Boolean detecte) {
		this.detecte = detecte;
	}

	public Boolean getRunPump() {
		return runPump;
	}

	public void setRunPump(Boolean runPump) {
		this.runPump = runPump;
	}

	public Boolean getOpenValveM() {
		return openValveM;
	}

	public void setOpenValveM(Boolean openValveM) {
		this.openValveM = openValveM;
	}

	public Boolean getOpenValveR() {
		return openValveR;
	}

	public void setOpenValveR(Boolean openValveR) {
		this.openValveR = openValveR;
	}

	public byte getCode() {
		byte b = 0;
		if (detecte != null) {
			if (detecte)
				b |= 0x02;
			else
				b |= 0x01;
		}
		if (runPump != null) {
			if (runPump)
				b |= 0x04;
			else
				b |= 0x08;
		}
		if (openValveM != null) {
			if (openValveM)
				b |= 0x20;
			else
				b |= 0x10;
		}
		if (openValveR != null) {
			if (openValveR)
				b |= 0x80;
			else
				b |= 0x40;
		}

		return b;
	}

	public boolean isControl() {
		return detecte != null || runPump != null || openValveM != null || openValveR != null;
	}

	@Override
	public String toString() {
		return "BpControl [detecte=" + detecte + ", runPump=" + runPump + ", openValveM=" + openValveM + ", openValveR="
				+ openValveR + "]";
	}

}
