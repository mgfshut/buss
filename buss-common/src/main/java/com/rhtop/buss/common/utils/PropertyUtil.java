package com.rhtop.buss.common.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * 一个读取common.properties公用配置文件的工具类
 * @author MakeItHappen
 *
 */
public class PropertyUtil {

    private static Properties prop = new Properties();

    /**
     * 获取通用配置
     * @param key peoperties文件中配置的键
     * @return  返回该键对应的值
     * @throws IOException
     */
    public static String getCommonProp(String key) throws IOException {
        prop.load(PropertyUtil.class.getClassLoader().getResourceAsStream("properties/common.properties"));
        return prop.getProperty(key);
    }
}
