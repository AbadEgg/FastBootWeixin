package com.mlnx.device_server.mybatis.type_handle;


import com.mlnx.device.ecg.ECGChannelType;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by amanda.shan on 2017/11/24.
 */
public class ECGChannelTypeHandler extends BaseTypeHandler<ECGChannelType> {

    private Class<ECGChannelType> type;

    private ECGChannelType[] enums;

    /**
     * 设置配置文件设置的转换类以及枚举类内容，供其他方法更便捷高效的实现
     *
     * @param type 配置文件中设置的转换类
     */
    public ECGChannelTypeHandler(Class<ECGChannelType> type) {
        if (type == null)
            throw new IllegalArgumentException("Type argument cannot be null");
        this.type = type;
        this.enums = type.getEnumConstants();
        if (this.enums == null)
            throw new IllegalArgumentException(type.getSimpleName()
                    + " does not represent an enum type.");
    }


    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, ECGChannelType ecgDeviceRunMode, JdbcType
            jdbcType)
            throws SQLException {
        preparedStatement.setString(i, ecgDeviceRunMode.toString());
    }

    @Override
    public ECGChannelType getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        String value = resultSet.getString(columnName);

//        System.out.println(String.format("getNullableResult columnName:%s  value:%s", columnName, value));

        if (resultSet.wasNull()) {
            return null;
        } else {
            // 根据数据库中的value值，定位PersonType子类
            return ECGChannelType.decode(value);
        }
    }

    @Override
    public ECGChannelType getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String value = resultSet.getString(i);

//        System.out.println(String.format("getNullableResult i:%s  value:%s", i, value));
        if (resultSet.wasNull()) {
            return null;
        } else {
            // 根据数据库中的value值，定位PersonType子类
            return ECGChannelType.decode(value);
        }
    }

    @Override
    public ECGChannelType getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String value = callableStatement.getString(i);

//        System.out.println(String.format("CallableStatement getNullableResult i:%s  value:%s", i, value));
        if (callableStatement.wasNull()) {
            return null;
        } else {
            // 根据数据库中的value值，定位PersonType子类
            return ECGChannelType.decode(value);
        }
    }
}
