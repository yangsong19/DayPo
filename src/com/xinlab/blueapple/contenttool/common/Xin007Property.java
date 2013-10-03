package com.xinlab.blueapple.contenttool.common;

import java.util.Properties;

public class Xin007Property {

	private static final String OO7_PROPERTY_NAME = "com.xinlab.blueapple.contenttool.oo7.properties";
	
	public static String getProperty(String key) {
		Properties props = XinPropertyManager.getProperties(OO7_PROPERTY_NAME);
		if (props == null) {
			props = PropertyLoader.loadProperties(OO7_PROPERTY_NAME);
			XinPropertyManager.putProperties(OO7_PROPERTY_NAME,props);
		}
		return props.getProperty(key);
	}

}
