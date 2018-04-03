package com.mlnx.domain;

/**
 * 分析详情
 *
 * @author fzh
 * @create 2018/4/3 11:42
 */
public class AnalysisDetail {

    private HeartStatistic heartStatistic;

    private EventStatistic eventStatistic;

    private UnusualStatistic unusualStatistic;

    public HeartStatistic getHeartStatistic() {
        return heartStatistic;
    }

    public void setHeartStatistic(HeartStatistic heartStatistic) {
        this.heartStatistic = heartStatistic;
    }

    public EventStatistic getEventStatistic() {
        return eventStatistic;
    }

    public void setEventStatistic(EventStatistic eventStatistic) {
        this.eventStatistic = eventStatistic;
    }

    public UnusualStatistic getUnusualStatistic() {
        return unusualStatistic;
    }

    public void setUnusualStatistic(UnusualStatistic unusualStatistic) {
        this.unusualStatistic = unusualStatistic;
    }
}
