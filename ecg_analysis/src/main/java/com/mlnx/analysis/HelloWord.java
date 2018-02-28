package com.mlnx.analysis;

import com.sun.jna.Library;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.ShortByReference;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Date;

public class HelloWord {
	private TestDLL INSTANCE;

	private byte[] gu32EncryptEcgOutData;

	private byte[] gpu8AcId = { (byte) 0x86, (byte) 0xda, 0x26, (byte) 0xc6,
			0x25, (byte) 0x99, (byte) 0xe2, (byte) 0xc0, (byte) 0xe5,
			(byte) 0xb8, 0x6e, 0x50 };

	private boolean reading;

	public HelloWord(TestDLL INSTANCE) {
		this.INSTANCE = INSTANCE;
	}

	public interface TestDLL extends Library {

		// 算法初始化
		void InitEcgAna();

		void InitECGFilter();

		// 数据解析
		void GetProcData(byte[] iEcgdata, byte[] gpu8AcId,
                         byte[] gu32EncryptEcgOutData);

		// 算法调用
		// 获取算法调用标志
		byte CheckMonitorAnalysisStartFlag();

		void AlielseEcgAnalysis();

		// 结果获取
		// 结果获取标志
		byte MonitorAnalysisStopFlag();

		void ClearEcgAnalysisFlag();

		// (ECG_ANA_PARAM_LIST)
		byte EcgGetParam(Integer tIndex, ShortByReference pi16Value);

		// (ECG_ANA_ARR_CODE_LIST)
		byte EcgGetArrhythmia(IntByReference type, ShortByReference last_time,
                              ShortByReference currentPos);

		// 诊断算法分析
		void SatrtEcgAnalysisFlag();

		void DiagnosisAnalysis();

		// 分析状态监测
		byte GetFinishEcgAnalysisFlag();

		// 结果获取
		void GetResult(int[] ResultBuff, int[] TempBuff);

	}

	private long t = 0;
	private boolean diagnosis = false;

	private Thread readFile = new Thread(new Runnable() {
		int count = 0;

		@Override
		public void run() {
			File file = new File("C:\\SIM_AD_1.txt");
			try {

				RandomAccessFile raf = new RandomAccessFile(file, "rw");
				String line = raf.readLine();
				while (line != null && !line.isEmpty()) {
					count++;
					String[] strs = line.split("\t");
					byte[] iEcgdata = new byte[24];
					gu32EncryptEcgOutData = new byte[24];

					for (int i = 0; i < iEcgdata.length; i++) {
						iEcgdata[i] = (byte) Integer.parseInt(strs[i]);
					}

					// 读取一行并解析数据
					INSTANCE.GetProcData(iEcgdata, gpu8AcId,
							gu32EncryptEcgOutData);

					// 判断是否可分析标志
					byte monitorFlag = INSTANCE.CheckMonitorAnalysisStartFlag();

					if (monitorFlag == 1) {
						t = new Date().getTime();
						count++;
						// 调用分析函数
						INSTANCE.AlielseEcgAnalysis();
						// System.out.println("调用分析函数:" + monitorFlag + "--"
						// + new Date() + "--次数：" + count);
					}

					Thread.sleep(2);
					line = raf.readLine();
				}

				// if (!diagnosis) {
				// System.out.println("xxxxxxxxxxx");
				// INSTANCE.SatrtEcgAnalysisFlag();
				// diagnosis = true;
				// }

				reading = false;
				raf.close();

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	});

	// 获取分析结果
	private Thread getMonitorResult = new Thread(new Runnable() {
		int count = 0;

		@Override
		public void run() {
			while (reading) {
				// 判断是否可获取分析结果标志
				byte monitorResultFlag = INSTANCE.MonitorAnalysisStopFlag();
				if (monitorResultFlag == 1) {
					t = new Date().getTime() - t;
					System.out.println("时间差：" + t);
					count++;
					System.out.println("第 " + count + " 次获取分析结果");
					// 获取结果
					ShortByReference shortByReference = new ShortByReference();
					INSTANCE.EcgGetParam(0, shortByReference);
					System.out.println("心率：" + shortByReference.getValue());
					INSTANCE.EcgGetParam(1, shortByReference);
					System.out.println("早搏个数：" + shortByReference.getValue());
					INSTANCE.EcgGetParam(2, shortByReference);
					System.out.println("st1：" + shortByReference.getValue());
					INSTANCE.EcgGetParam(3, shortByReference);
					System.out.println("st2：" + shortByReference.getValue());
					INSTANCE.EcgGetParam(4, shortByReference);
					System.out.println("st3：" + shortByReference.getValue());
					INSTANCE.EcgGetParam(5, shortByReference);
					System.out.println("st4：" + shortByReference.getValue());
					INSTANCE.EcgGetParam(6, shortByReference);
					System.out.println("st5：" + shortByReference.getValue());
					INSTANCE.EcgGetParam(7, shortByReference);
					System.out.println("st6：" + shortByReference.getValue());
					INSTANCE.EcgGetParam(8, shortByReference);
					System.out.println("st7：" + shortByReference.getValue());
					INSTANCE.EcgGetParam(9, shortByReference);
					System.out.println("st8：" + shortByReference.getValue());

					IntByReference type = new IntByReference();
					ShortByReference last_time = new ShortByReference();
					ShortByReference currentPos = new ShortByReference();
					INSTANCE.EcgGetArrhythmia(type, last_time, currentPos);

					System.out.println("type:" + (type.getValue() & 0x7F)
							+ "--last_time:" + last_time.getValue()
							+ "--currentPos:" + currentPos.getValue());

					INSTANCE.ClearEcgAnalysisFlag();
				}

				try {
					Thread.sleep(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	});

	// 获取诊断结果
	private Thread getDiagnosisResult = new Thread(new Runnable() {

		@Override
		public void run() {
			while (true) {
				byte finishFlag = INSTANCE.GetFinishEcgAnalysisFlag();
				if (finishFlag == 1) {
					System.out.println("诊断结束");
					int[] resultLead = new int[12 * 250];
					int[] result = new int[50];

					INSTANCE.GetResult(result, resultLead);

					for (int i = 0; i < result.length; i++) {
						System.out.println(i + ": " + result[i]);
					}

					break;
				}

				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	});

	public void start() {
		INSTANCE.InitEcgAna();
		INSTANCE.InitECGFilter();
		reading = true;
		readFile.start();
		getMonitorResult.start();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("开始诊断");
		INSTANCE.DiagnosisAnalysis();
		System.out.println("结束诊断");

		// try {
		// Thread.sleep(15000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }

		System.out.println("开始检测诊断结束标志");
		getDiagnosisResult.start();
	}
//
//	public static void main(String[] args) {
//		final TestDLL INSTANCE = (TestDLL) Native.loadLibrary(
//				"C:\\libecg12(10)", TestDLL.class);
//		HelloWord helloWord = new HelloWord(INSTANCE);
//		helloWord.start();
//
//	}
}
