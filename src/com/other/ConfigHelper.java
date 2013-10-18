package com.other;

import java.util.HashMap;
import java.util.Properties;

public class ConfigHelper {

	private static HashMap<String, Properties> propertyMap = new HashMap<String, Properties>();
	
	public static void putProperties(String namespace, Properties properties) {
		propertyMap.put(namespace, properties);
	}

	public static Properties getProperties(String namespace) {
		Properties properties = propertyMap.get(namespace);
		if(properties == null || properties.isEmpty()) {
			properties = PropertyLoader.loadProperties(namespace);
		}
		return properties;
	}

	public static String getStringProperty(String namespace, String key) {
		return getProperties(namespace).getProperty(key);
	}

	public static Integer getNumberProperty(String namespace, String key) {
		return Integer.valueOf(getProperties(namespace).getProperty(key));
	}

	public static void main(String[] args) {
		System.out.println(getStringProperty("conf.properties", "username"));
	}
}
