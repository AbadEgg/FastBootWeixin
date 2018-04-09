package com.mlnx.service;

import com.mlnx.decode.MpDecode;
import com.mlnx.domain.AnalysisDetail;
import com.mlnx.domain.DataTime;
import com.mlnx.mptp.model.analysis.RealEcgAnalysResult;
import com.mlnx.support.ProgressBar;
import com.mlnx.utils.StatisticUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author fzh
 * @create 2018/4/3 13:28
 */
public class AnalysisService {

    private static Logger logger = LoggerFactory.getLogger(AnalysisService.class);

    private MpDecode mpDecode;

    public AnalysisService() {
        mpDecode = new MpDecode();
    }

    public AnalysisDetail getAnalysisDetail(ProgressBar progressBar, String... fileNames) throws Exception {
        List<RealEcgAnalysResult> realEcgAnalysResults = new ArrayList<>();
        int i = 0;
        for (String file:fileNames) {
            String[] txts = new File(file).list();
            for(String txt:txts){
               if(!txt.contains("heartNum")){
                   List<RealEcgAnalysResult> list = mpDecode.decode(file+"\\"+txt);
                   realEcgAnalysResults.addAll(list);
               }
            }
            progressBar.progress(new BigDecimal(Integer.toString(++i)).divide(new BigDecimal(Integer.toString(fileNames.length)),2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100+"")).intValue());
        }
        return StatisticUtils.ecgStatistic(realEcgAnalysResults);
    }

    public Map<Integer,Map<String,Object>> getTimeAxis(String filePath) throws Exception {
        Map<Integer, Map<String, Object>> map = new HashMap<>();
        File file = new File(filePath);
        String[] patientIds = file.list();
        if (patientIds != null) {
            for (String patientId : patientIds) {
                Map<String, Object> m = new HashMap<>();
                String[] datas = new File(filePath + "\\"+patientId).list();
                if (datas != null) {
                    for (String data : datas) {
                        DataTime dataTime = new DataTime();
                        String dataFile = filePath + "\\" + patientId + "\\" + data;
                        String[] txts = new File(dataFile).list();
                        if(txts!=null){
                            List<String> txtfiles= new ArrayList<>();
                            for (String txt:txts) {
                                if(!txt.contains("heartNum")){
                                    txtfiles.add(txt);
                                }
                            }
                            Collections.sort(txtfiles);
                            DataTime dataTime1 = mpDecode.getDataTime(dataFile+"\\"+txtfiles.get(0));
                            dataTime.setStartTime(dataTime1.getStartTime());
                            if(txts.length!=1){
                                DataTime dataTime2 = mpDecode.getDataTime(dataFile+"\\"+txtfiles.get(txtfiles.size()-1));
                                dataTime.setEndTime(dataTime2.getEndTime());
                            }else {
                                dataTime.setEndTime(dataTime1.getEndTime());
                            }
                        }else {
                            logger.info("该数据文件下无txt文件");
                        }
                        m.put(dataFile, dataTime);
                    }
                } else {
                    logger.info("该病人下无数据文件");
                }
                map.put(Integer.parseInt(patientId), m);
            }
        } else {
            logger.info("该文件目录为空");
        }
        return map;
    }
}
