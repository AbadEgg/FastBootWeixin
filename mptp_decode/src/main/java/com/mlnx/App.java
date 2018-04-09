package com.mlnx;

import com.alibaba.fastjson.JSON;
import com.mlnx.domain.AnalysisDetail;
import com.mlnx.service.AnalysisService;
import com.mlnx.support.ProgressBar;

import java.util.Map;
import java.util.Set;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
        String fileDictionary = "U:\\data";
        AnalysisService analysisService = new AnalysisService();
        Map map = analysisService.getTimeAxis(fileDictionary);
        System.out.println(JSON.toJSONString(map));

        Map m = (Map) map.get(110);
        Set<String> filenames = m.keySet();
        AnalysisDetail analysisDetail = analysisService.getAnalysisDetail(new ProgressBar() {
            @Override
            public void progress(int percent) {
                System.out.println(String.format("解析进度:%d",percent));
            }
        }, filenames.toArray(new String[filenames.size()]));
        System.out.println("分析结果:"+JSON.toJSONString(analysisDetail));


    }
}
