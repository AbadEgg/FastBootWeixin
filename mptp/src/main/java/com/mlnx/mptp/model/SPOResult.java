package com.mlnx.mptp.model;

public class SPOResult {

	private Integer resultSPO;
	private Integer resultHeart;
	private byte[] spoWave;

	public Integer getResultSPO() {
		return resultSPO;
	}

	public void setResultSPO(Integer resultSPO) {
		this.resultSPO = resultSPO;
	}

	public Integer getResultHeart() {
		return resultHeart;
	}

	public void setResultHeart(Integer resultHeart) {
		this.resultHeart = resultHeart;
	}

	public byte[] getSpoWave() {
		return spoWave;
	}

	public void setSpoWave(byte[] spoWave) {
		this.spoWave = spoWave;
	}
}
