package com.mlnx.qcms.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 波形图绘制工具
 *
 * @author fzh
 * @create 2018/3/8 11:23
 */
public class WaveformUtils{

    //模拟上一秒最后一个数据,
    private static int lastData = 128;

    public static int[] byte2int(byte[] waveData){
        int length = waveData.length/2;
        int[] result = new int[length];
        byte[] b2 = new byte[2];
        for (int i = 0; i < length ; i++) {
            b2[0]=waveData[i*2];
            b2[1]=waveData[i*2+1];
            result[i] = ByteUtils.bytesToSignInt(b2 ,2);
        }
        return result;
    }

    /**
     *
     * @param waveData ecg波形数据
     * @param dots 像素点
     * @return
     */
    public static List<int[]> getDots(byte[] waveData,int dots)  {
        int[] data = byte2int(waveData);
        int length = data.length;
        List<int[]> groups = new ArrayList<>();
        int dotsEach = length/dots;
        int remaider = length%dots;
        if(dotsEach<2) {
            System.out.println("取样异常");
        }
        //没有余数
        if(remaider==0){
            for (int i = 0; i < dots; i++) {
                int[] group = new int[dotsEach+1];
                if(i==0){
                    group[0] = lastData;
                }else {
                    group[0]=(data[i*dotsEach-1]);
                }
                for (int j = 0; j < dotsEach; j++) {
                    group[j+1]= data[i*dotsEach+j];
                }
                groups.add(group);
            }
        }else {
            int step = length/remaider;
            int flag = 0;
            for (int i = 0; i < dots ; i++) {
                int[] group;
                if(i%step==0){
                    group = new int[dotsEach+2];
                    if(i==0){
                        group[0] = lastData;
                    }else {
                        group[0] = data[i*dotsEach+flag-1];
                    }
                    for (int j = 0; j < dotsEach+1 ; j++) {
                        group[j+1] = data[i*dotsEach+j+flag];
                        if(flag==remaider){
                            break;
                        }
                    }
                    flag++;
                }else {
                    group = new int[dotsEach+1];
                    group[0] = data[i*dotsEach+flag-1];
                    for (int j = 0; j < dotsEach ; j++) {
                        group[j+1] = data[i*dotsEach+j+flag];
                    }
                }
                groups.add(group);
            }
        }
        lastData = data[length-1];
        return transferData(groups);
    }

    public static List<int[]> transferData(List<int[]> groups){
        for (int i = 0; i < groups.size(); i++) {
            int[] group = groups.get(i);
            group = handleArray(group);
        }
        return groups;
    }



    /**
     * 从数组中取最大值和最小值（最小值在前，最大值在后）
     * @param group
     * @return
     */
    public static int[] handleArray(int[] group){
        int min,max,i;
        min = max = group[0];
        for(i=0;i<group.length;i++) {
            // 判断最大值
            if(group[i]>max) {
                max = group[i];
            }
            // 判断最小值
            if(group[i]<min) {
                min = group[i];
            }
        }
        group[0] = min;
        group[1] = max;
        return group;
    }

    public static void printConsole(List<int[]> groups){
        for (int i = 0; i < groups.size(); i++) {
            System.out.println(String.format("像素%s最小值是%s,最大值是%s,大小是%s",i,groups.get(i)[0],groups.get(i)[1],groups.get(i).length));
        }
    }
}
