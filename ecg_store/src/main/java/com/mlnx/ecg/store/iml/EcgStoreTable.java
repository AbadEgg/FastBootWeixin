package com.mlnx.ecg.store.iml;

import com.alicloud.openservices.tablestore.SyncClient;
import com.alicloud.openservices.tablestore.model.BatchWriteRowRequest;
import com.alicloud.openservices.tablestore.model.BatchWriteRowResponse;
import com.alicloud.openservices.tablestore.model.ColumnValue;
import com.alicloud.openservices.tablestore.model.GetRangeRequest;
import com.alicloud.openservices.tablestore.model.GetRangeResponse;
import com.alicloud.openservices.tablestore.model.PrimaryKeyBuilder;
import com.alicloud.openservices.tablestore.model.PrimaryKeyType;
import com.alicloud.openservices.tablestore.model.PrimaryKeyValue;
import com.alicloud.openservices.tablestore.model.RangeIteratorParameter;
import com.alicloud.openservices.tablestore.model.RangeRowQueryCriteria;
import com.alicloud.openservices.tablestore.model.Row;
import com.alicloud.openservices.tablestore.model.RowPutChange;
import com.mlnx.ecg.store.EcgStore;
import com.mlnx.ecg.store.utils.DatePrefix;
import com.mlnx.ecg.store.utils.OTSUtils;
import com.mlnx.mptp.model.Ecg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static com.mlnx.ecg.store.config.EcgTableConfig.COLUMN_KEY1_NAME;
import static com.mlnx.ecg.store.config.EcgTableConfig.COLUMN_KEY2_NAME;
import static com.mlnx.ecg.store.config.EcgTableConfig.TABLE_NAME;

/**
 * Created by amanda.shan on 2017/12/28.
 */
public class EcgStoreTable implements EcgStore {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private SyncClient client;

    public SyncClient getClient() {
        return client;
    }

    public void setClient(SyncClient client) {
        this.client = client;
    }

    public void init() {

        if (!OTSUtils.tableExist(client, TABLE_NAME)) {

            List<OTSUtils.PrimaryKey> primaryKeys = new ArrayList<>();
            primaryKeys.add(new OTSUtils.PrimaryKey(COLUMN_KEY1_NAME, PrimaryKeyType.STRING));
            primaryKeys.add(new OTSUtils.PrimaryKey(COLUMN_KEY2_NAME, PrimaryKeyType.INTEGER));
            OTSUtils.createTable(client, TABLE_NAME, primaryKeys);
        }
    }

    public boolean save(List<Ecg> ecgs) {

        int index = 0;
        while (ecgs.size() > index) {

            BatchWriteRowRequest request = new BatchWriteRowRequest();

            for (int j = 0; j < 150; j++) {
                if (ecgs.size() <= index)
                    break;

                Ecg ecg = ecgs.get(index);
                index++;

                PrimaryKeyBuilder primaryKey = PrimaryKeyBuilder.createPrimaryKeyBuilder();

                primaryKey.addPrimaryKeyColumn(COLUMN_KEY1_NAME, PrimaryKeyValue.fromString(DatePrefix.yyyyMM(
                        new Date(ecg.getStartTime())).toString() + ecg.getPatientId()));
                primaryKey.addPrimaryKeyColumn(COLUMN_KEY2_NAME, PrimaryKeyValue.fromLong(ecg.getStartTime()));

                RowPutChange rowChange = new RowPutChange(TABLE_NAME, primaryKey.build());

//                logger.debug(COLUMN_KEY2_NAME + ":" + format(ecg.getStartTime(), "HH:mm:ss:SSS"));

//                SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss:SSS");
//                logger.debug(format.format(new Date(ecg.getStartTime())));

                // 加入属性列，消息内容
                rowChange.addColumn("device_id", ColumnValue
                        .fromString(ecg.getDeivceId()));
                rowChange.addColumn("channel_num", ColumnValue
                        .fromString(ecg.getNumChannels() + ""));
                rowChange.addColumn("sampling_rate", ColumnValue
                        .fromString(ecg.getSamplingRate() + ""));
                rowChange.addColumn("amplification", ColumnValue
                        .fromString(ecg.getAmplification() + ""));
                rowChange.addColumn("heart_rate", ColumnValue
                        .fromString(ecg.getHeartRate() + ""));
                rowChange.addColumn("wear_pose", ColumnValue
                        .fromString(ecg.getPose() + ""));
                rowChange.addColumn("battery_remain", ColumnValue
                        .fromString(ecg.getBatteryLevel() + ""));
                rowChange.addColumn("signal_strength", ColumnValue
                        .fromString(ecg.getSignalStrength() + ""));
                rowChange.addColumn("probe_channel_bias", ColumnValue
                        .fromString(ecg.getProbeChannelBias() + ""));
                rowChange.addColumn("probe_electrode_impedance",
                        ColumnValue.fromString(ecg.getProbeElectrodeImpedance() + ""));
                rowChange.addColumn("data", ColumnValue.fromBinary(ecg
                        .getData()));

                request.addRowChange(rowChange);
            }

            BatchWriteRowResponse response = client.batchWriteRow(request);

            logger.debug("是否全部成功:" + response.isAllSucceed());
            if (!response.isAllSucceed()) {
                for (BatchWriteRowResponse.RowResult rowResult : response.getFailedRows()) {
                    logger.error("失败的行:" + request.getRowChange(rowResult.getTableName(), rowResult.getIndex())
                            .getPrimaryKey());
                    logger.error("失败原因:" + rowResult.getError());
                }
                /**
                 * 可以通过createRequestForRetry方法再构造一个请求对失败的行进行重试.这里只给出构造重试请求的部分.
                 * 推荐的重试方法是使用SDK的自定义重试策略功能, 支持对batch操作的部分行错误进行重试. 设定重试策略后, 调用接口处即不需要增加重试代码.
                 */
                BatchWriteRowRequest retryRequest = request.createRequestForRetry(response.getFailedRows());
                response = client.batchWriteRow(retryRequest);
                if (!response.isAllSucceed()) {
                    logger.info("重试保存心电成功");
                }
            }

        }

        return true;

    }

    public List<Ecg> getEcg(long startTime, long endTime, int patientId) {

        // 设置起始主键
        PrimaryKeyBuilder start = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        start.addPrimaryKeyColumn(COLUMN_KEY1_NAME, PrimaryKeyValue.fromString(DatePrefix.yyyyMM(
                new Date(startTime)).toString() + patientId));
        start.addPrimaryKeyColumn(COLUMN_KEY2_NAME, PrimaryKeyValue.fromLong(startTime));

        PrimaryKeyBuilder end = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        end.addPrimaryKeyColumn(COLUMN_KEY1_NAME, PrimaryKeyValue.fromString(DatePrefix.yyyyMM(
                new Date(endTime)).toString() + patientId));
        end.addPrimaryKeyColumn(COLUMN_KEY2_NAME, PrimaryKeyValue.fromLong(endTime));

        List<Row> rows = get(start, end);

        return null;
    }

    private List<Row> get(PrimaryKeyBuilder start, PrimaryKeyBuilder end) {

        List<Row> rows = new ArrayList<>();

        RangeRowQueryCriteria rangeRowQueryCriteria = new RangeRowQueryCriteria(TABLE_NAME);
        rangeRowQueryCriteria.setInclusiveStartPrimaryKey(start.build());
        rangeRowQueryCriteria.setExclusiveEndPrimaryKey(end.build());

        while (true) {
            GetRangeResponse getRangeResponse = client.getRange(new GetRangeRequest(rangeRowQueryCriteria));
            rows.addAll(getRangeResponse.getRows());

            // 若nextStartPrimaryKey不为null, 则继续读取.
            if (getRangeResponse.getNextStartPrimaryKey() != null) {
                rangeRowQueryCriteria.setInclusiveStartPrimaryKey(getRangeResponse.getNextStartPrimaryKey());
            } else {
                break;
            }
        }

        return rows;

    }

    /**
     * 迭代读取
     *
     * @param start
     * @param end
     * @param endPkValue
     */
    private void getRangeByIterator(PrimaryKeyBuilder start, PrimaryKeyBuilder end, String endPkValue) {
        RangeIteratorParameter rangeIteratorParameter = new RangeIteratorParameter(TABLE_NAME);
        rangeIteratorParameter.setInclusiveStartPrimaryKey(start.build());
        rangeIteratorParameter.setExclusiveEndPrimaryKey(end.build());

        Iterator<Row> iterator = client.createRangeIterator(rangeIteratorParameter);
        logger.debug("使用Iterator进行GetRange的结果为:");
        while (iterator.hasNext()) {
            Row row = iterator.next();
            logger.debug(row.toString());
        }
    }

}
