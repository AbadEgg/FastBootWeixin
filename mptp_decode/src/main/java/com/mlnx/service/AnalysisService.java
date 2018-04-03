package com.mlnx.service;

import com.mlnx.decode.MpDecode;
import com.mlnx.domain.AnalysisDetail;
import com.mlnx.mptp.model.analysis.RealEcgAnalysResult;
import com.mlnx.utils.ReadFileUtils;
import com.mlnx.utils.StatisticUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fzh
 * @create 2018/4/3 13:28
 */
public class AnalysisService {

    private MpDecode mpDecode;

    public AnalysisService() {
        mpDecode = new MpDecode();
    }

    public AnalysisDetail getAnalysisDetail(String fileDictionary) throws Exception {
        List<String> files = new ArrayList<>();
        List<RealEcgAnalysResult> realEcgAnalysResults = new ArrayList<>();
        ReadFileUtils.readAllFile(files,fileDictionary);
        MpDecode mpDecode = new MpDecode();
        for (String file:files) {
            List<RealEcgAnalysResult> list = mpDecode.decode(file);
            realEcgAnalysResults.addAll(list);
        }
        return StatisticUtils.ecgStatistic(realEcgAnalysResults);
    }
}
