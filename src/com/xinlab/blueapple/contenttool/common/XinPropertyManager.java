package com.xinlab.blueapple.contenttool.common;

import java.util.HashMap;
import java.util.Properties;

public class XinPropertyManager {
	
	private static HashMap<String, Properties> PropertyMap = new HashMap<String, Properties>();
	
	public static void putProperties(String namespace, Properties properties) {
		PropertyMap.put(namespace, properties);
	}
	
	public static Properties getProperties(String namespace) {
		return PropertyMap.get(namespace);
	}
	
	public static String getProperty(String namespace, String key) {
		return getProperties(namespace).getProperty(key);
	}
}
