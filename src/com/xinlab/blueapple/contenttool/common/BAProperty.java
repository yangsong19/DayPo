package com.xinlab.blueapple.contenttool.common;

import java.util.Properties;

public class BAProperty {

	private static final String BA_PROPERTY_NAME = "ba.properties";
	
	public static String getProperty(String key) {
		Properties props = XinPropertyManager.getProperties(BA_PROPERTY_NAME);
		if (props == null) {
			props = PropertyLoader.loadProperties(BA_PROPERTY_NAME);
			XinPropertyManager.putProperties(BA_PROPERTY_NAME,props);
		}
		return props.getProperty(key);
	}
}
