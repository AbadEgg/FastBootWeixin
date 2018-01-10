package com.mlnx.ecg.store.utils;


import com.alicloud.openservices.tablestore.ClientException;
import com.alicloud.openservices.tablestore.SyncClient;
import com.alicloud.openservices.tablestore.model.CapacityUnit;
import com.alicloud.openservices.tablestore.model.DeleteTableRequest;
import com.alicloud.openservices.tablestore.model.ListTableResponse;
import com.alicloud.openservices.tablestore.model.PrimaryKeyType;
import com.alicloud.openservices.tablestore.model.ReservedThroughput;
import com.alicloud.openservices.tablestore.model.TableMeta;
import com.alicloud.openservices.tablestore.model.TableOptions;
import com.alicloud.openservices.tablestore.model.internal.CreateTableRequestEx;
import com.google.protobuf.ServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by amanda.shan on 2017/3/29.
 */
public class OTSUtils {

    private static final Logger logger = LoggerFactory.getLogger(OTSUtils.class);

    public static void createTable(SyncClient client, String tableName, List<PrimaryKey> primaryKeys) {
        TableMeta tableMeta = new TableMeta(tableName);

        for (int i = 0; i < primaryKeys.size(); i++) {
            PrimaryKey primaryKey = primaryKeys.get(i);
            tableMeta.addPrimaryKeyColumn(primaryKey.getName(), primaryKey.getType());
        }

        // 数据的过期时间, 单位秒, -1代表永不过期. 假如设置过期时间为一年, 即为 365 * 24 * 3600.
        int timeToLive = -1;
        // 保存的最大版本数, 设置为3即代表每列上最多保存3个最新的版本.
        int maxVersions = 3;
        TableOptions tableOptions = new TableOptions(timeToLive, maxVersions);
        CreateTableRequestEx request = new CreateTableRequestEx(tableMeta, tableOptions);
        // 设置读写预留值，容量型实例只能设置为0，高性能实例可以设置为非零值
        request.setReservedThroughput(new ReservedThroughput(new CapacityUnit(0, 0)));
        client.createTable(request);

        logger.info(tableName + ":表已创建");
    }

    /**
     * 删除阿里table表
     *
     * @param client
     * @param tableName
     * @throws ServiceException
     * @throws ClientException
     */
    public static void deleteTable(SyncClient client, String tableName)
            throws ServiceException, ClientException {
        DeleteTableRequest request = new DeleteTableRequest(tableName);
        client.deleteTable(request);

        logger.info(tableName + ":表已删除");
    }

    /**
     * 判断table是否存在
     *
     * @param client
     * @param compareTableName
     * @return
     */
    public static boolean tableExist(SyncClient client, String compareTableName) {
        boolean isExist = false;
        List<String> tableNameStrings = listTableWithFuture(client);
        for (String tableName : tableNameStrings) {
            if (compareTableName.equals(tableName))
                isExist = true;
        }
        return isExist;
    }

    /**
     * 列出所有table
     *
     * @param client
     */
    public static List<String> listTableWithFuture(SyncClient client) {

        ListTableResponse response = client.listTable();
        return response.getTableNames();
    }

    public static class PrimaryKey {
        private String name;
        private PrimaryKeyType type;

        public PrimaryKey(String name, PrimaryKeyType type) {
            this.name = name;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public PrimaryKeyType getType() {
            return type;
        }

        public void setType(PrimaryKeyType type) {
            this.type = type;
        }
    }

}
