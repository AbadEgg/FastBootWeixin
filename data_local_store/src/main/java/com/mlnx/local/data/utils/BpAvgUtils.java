package com.mlnx.local.data.utils;

import com.mlnx.local.data.domain.BpAvg;
import com.mlnx.local.data.store.bp.BpAvgStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author fzh
 * @create 2018/3/14 13:46
 */

public class BpAvgUtils {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private BpAvgStore bpAvgStore;

    public void setBpAvgStore(BpAvgStore bpAvgStore) {
        this.bpAvgStore = bpAvgStore;
    }

    private BpAvg bpDataProcess(List<Map<String, Object>> list) throws ParseException {
        if (list != null && list.size() > 0) {
            BpAvg bpAvg = new BpAvg();

            int sumSbp = 0;
            int sumDbp = 0;
            int sumHeart = 0;

            int daySumSbp = 0;
            int daySumDbp = 0;
            int daySumHeart = 0;
            int dayCount = 0;

            int nightSumSbp = 0;
            int nightSumDbp = 0;
            int nightSumHeart = 0;
            int nightCount = 0;

            bpAvg.setPatientId((Integer) list.get(0).get("patientId"));

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            bpAvg.setDayTime(format.parse(format.format((Date) list.get(0).get("time"))));
            for (Map<String,Object> map :list) {
                if (map.get("sbp") == null || map.get("dbp") == null || map.get("heart") == null) {
                    logger.error("血压数据为null： " + map.toString());
                    continue;
                }
                sumSbp += (int)map.get("sbp");
                sumDbp += (int)map.get("dbp");
                sumHeart += (int)map.get("heart");

                Calendar calendar = Calendar.getInstance();
                calendar.setTime((Date) map.get("time"));
                if (calendar.get(Calendar.HOUR_OF_DAY) > 7 && calendar.get(Calendar.HOUR_OF_DAY) < 22) {
                    daySumSbp += (int)map.get("sbp");
                    daySumDbp += (int)map.get("dbp");
                    daySumHeart += (int)map.get("heart");
                    dayCount++;
                } else {
                    nightSumSbp += (int)map.get("sbp");
                    nightSumDbp += (int)map.get("dbp");
                    nightSumHeart += (int)map.get("heart");
                    nightCount++;
                }
            }
            bpAvg.setDiastolicAvg(sumDbp / list.size());
            bpAvg.setSystolicAvg(sumSbp / list.size());
            bpAvg.setHeartAvg(sumHeart / list.size());

            if (dayCount > 0) {
                daySumSbp /= dayCount;
                daySumDbp /= dayCount;
                daySumHeart /= dayCount;
            }

            if (nightCount > 0) {
                nightSumSbp /= nightCount;
                nightSumDbp /= nightCount;
                nightSumHeart /= nightCount;
            }

            bpAvg.setSystolicDayAvg(daySumSbp);
            bpAvg.setSystolicNightAvg(nightSumSbp);
            bpAvg.setDiastolicDayAvg(daySumDbp);
            bpAvg.setDiastolicNightAvg(nightSumDbp);
            bpAvg.setHeartDayAvg(daySumHeart);
            bpAvg.setHeartNightAvg(nightSumHeart);

            logger.debug("血压统计:" + bpAvg.toString());
            return bpAvg;
        } else {
            return null;
        }
    }

}
