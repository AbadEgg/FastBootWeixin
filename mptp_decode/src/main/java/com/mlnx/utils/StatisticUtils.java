package com.mlnx.utils;

import com.mlnx.domain.AnalysisDetail;
import com.mlnx.domain.EventStatistic;
import com.mlnx.domain.HeartStatistic;
import com.mlnx.domain.UnusualStatistic;
import com.mlnx.mptp.model.analysis.RealEcgAnalysResult;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 统计工具
 *
 * @author fzh
 * @create 2018/4/3 9:30
 */
public class StatisticUtils {

    public static AnalysisDetail ecgStatistic(List<RealEcgAnalysResult> realEcgAnalysResults) {
        //心率统计
        int sumHeart = 0;
        int sumCount = 0;
        int daySumHeart = 0;
        int dayCount = 0;
        int nightSumHeart = 0;
        int nightCount = 0;
        int maxHeart = Integer.MIN_VALUE;
        int minHeart = Integer.MAX_VALUE;
        long maxOccurTime = 0L;
        long minOccurTime = 0L;
        //事件统计
        int stopHeartCount = 0;
        int slowHeartCount = 0;
        int fastHeartCount = 0;
        //异常统计
        int vfCount = 0;
        int sxfastCount = 0;
        int mostPbCount = 0;
        int twoPbCount = 0;
        int accidentalPbCount = 0;
        int rontCount = 0;
        int pvcCount = 0;
        int twollCount = 0;
        int threellCount = 0;

        for (RealEcgAnalysResult realEcgAnalysResult : realEcgAnalysResults) {
            if (realEcgAnalysResult.getHeart() != null) {
                sumHeart += realEcgAnalysResult.getHeart();
                sumCount++;
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date(realEcgAnalysResult.getTime()));
                if (calendar.get(Calendar.HOUR_OF_DAY) > 7 && calendar.get(Calendar.HOUR_OF_DAY) < 22) {
                    daySumHeart += realEcgAnalysResult.getHeart();
                    dayCount++;
                } else {
                    nightSumHeart += realEcgAnalysResult.getHeart();
                    nightCount++;
                }
                if (realEcgAnalysResult.getHeart() > maxHeart) {
                    maxHeart = realEcgAnalysResult.getHeart();
                    maxOccurTime = realEcgAnalysResult.getTime();
                }
                if (realEcgAnalysResult.getHeart() < minHeart) {
                    minHeart = realEcgAnalysResult.getHeart();
                    minOccurTime = realEcgAnalysResult.getTime();
                }
            }
            if (realEcgAnalysResult.getHeartResult() != null) {
                switch (realEcgAnalysResult.getHeartResult()) {
                    case HEART_0:
                        stopHeartCount++;
                        break;
                    case HEART_1:
                        vfCount++;
                        break;
                    case HEART_2:
                        sxfastCount++;
                        break;
                    case HEART_3:
                        rontCount++;
                        break;
                    case HEART_4:
                        mostPbCount++;
                        break;
                    case HEART_5:
                        break;
                    case HEART_6:
                        twoPbCount++;
                        break;
                    case HEART_7:
                        accidentalPbCount++;
                        break;
                    case HEART_8:
                        twollCount++;
                        break;
                    case HEART_9:
                        threellCount++;
                        break;
                    case HEART_10:
                        fastHeartCount++;
                        break;
                    case HEART_11:
                        slowHeartCount++;
                        break;
                    case HEART_12:
                        pvcCount++;
                        break;
                    default:
                        break;
                }
            }
        }

        HeartStatistic heartStatistic = new HeartStatistic();
        heartStatistic.setAvgHeart(sumCount == 0?0:sumHeart / sumCount);
        heartStatistic.setAvgDayHeart(dayCount == 0?0:daySumHeart / dayCount);
        heartStatistic.setAvgNightHeart(nightCount == 0?0:nightSumHeart / nightCount);
        heartStatistic.setMaxHeart(maxHeart);
        heartStatistic.setMaxOccurTime(maxOccurTime);
        heartStatistic.setMinHeart(minHeart);
        heartStatistic.setMinOccurTime(minOccurTime);

        EventStatistic eventStatistic = new EventStatistic();
        eventStatistic.setFastHeartCount(fastHeartCount);
        eventStatistic.setSlowHeartCount(slowHeartCount);
        eventStatistic.setStopHeartCount(stopHeartCount);

        UnusualStatistic unusualStatistic = new UnusualStatistic();
        unusualStatistic.setAccidentalPbCount(accidentalPbCount);
        unusualStatistic.setMostPbCount(mostPbCount);
        unusualStatistic.setPvcCount(pvcCount);
        unusualStatistic.setRontCount(rontCount);
        unusualStatistic.setSxFastCount(sxfastCount);
        unusualStatistic.setThreellCount(threellCount);
        unusualStatistic.setTwollCount(twollCount);
        unusualStatistic.setVfCount(vfCount);

        AnalysisDetail analysisDetail = new AnalysisDetail();
        analysisDetail.setHeartStatistic(heartStatistic);
        analysisDetail.setEventStatistic(eventStatistic);
        analysisDetail.setUnusualStatistic(unusualStatistic);

        return analysisDetail;
    }
}
