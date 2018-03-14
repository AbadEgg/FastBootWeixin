package com.mlnx.local.data.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by amanda.shan on 2018/2/5.
 */
public class BeanUtils {

    public static Map<String, Object> toMap(Object obj) {
        return toMap(obj, true);
    }

    public static Map<String, Object> toMap(Object obj, boolean all) {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                /*过滤class属性  */
                if (!key.equals("class")) {
                    //*得到property对应的getter方法*/
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);

                    if (all || value != null) {
                        map.put(key, value);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
