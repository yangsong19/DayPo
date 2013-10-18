package com.framework.helper;


import java.util.Properties;

import com.framework.util.FileUtil;
import com.framework.util.StringUtil;

public class ConfigHelper {

    private static final Properties configProperties = FileUtil.loadPropFile("config.properties");

    public static String getStringProperty(String key) {
        String value = "";
        if (configProperties.containsKey(key)) {
            value = configProperties.getProperty(key);
        } else {
            System.err.println("Can not get property [" + key + "] in config.properties file.");
        }
        return value;
    }

    public static int getNumberProperty(String key) {
        int value = 0;
        String sValue = getStringProperty(key);
        if (StringUtil.isNumber(sValue)) {
            value = Integer.parseInt(sValue);
        }
        return value;
    }
}
