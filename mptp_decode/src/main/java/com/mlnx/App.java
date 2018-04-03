package com.mlnx;

import com.mlnx.decode.MpDecode;
import com.mlnx.domain.AnalysisDetail;
import com.mlnx.mptp.model.analysis.RealEcgAnalysResult;
import com.mlnx.utils.ReadFileUtils;
import com.mlnx.utils.StatisticUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
        String fileDictionary = "U:\\04031202";
        List<String> files = new ArrayList<>();
        List<RealEcgAnalysResult> realEcgAnalysResults = new ArrayList<>();
        ReadFileUtils.readAllFile(files,fileDictionary);
        MpDecode mpDecode = new MpDecode();
        for (String file:files) {
            List<RealEcgAnalysResult> list = mpDecode.decode(file);
            realEcgAnalysResults.addAll(list);
        }
        AnalysisDetail analysisDetail = StatisticUtils.ecgStatistic(realEcgAnalysResults);
        System.out.println(analysisDetail.toString());
    }
}
