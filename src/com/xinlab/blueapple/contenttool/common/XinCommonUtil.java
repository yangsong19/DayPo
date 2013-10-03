package com.xinlab.blueapple.contenttool.common;

import java.io.InputStream;

public class XinCommonUtil {

	public static int getIntFromString(String value, int defaultValue) {
		int ivalue = defaultValue;
		try {
			ivalue = Integer.parseInt(value);
		}
		catch (NumberFormatException e) {
			// NOOP, return default
		}
		return ivalue;
	}

	public static long getLongFromString(String value, long defaultValue) {
		long lvalue = defaultValue;
		try {
			lvalue = Long.parseLong(value);
		}
		catch (NumberFormatException e) {
			// NOOP, return default
		}
		return lvalue;
	}

    public static InputStream loadResourceAsStream(String name,
			ClassLoader loader) {
		if (name == null)
			throw new IllegalArgumentException("null input: name");

		if (name.startsWith("/"))
			name = name.substring(1);
		try {
			if (loader == null)
				loader = ClassLoader.getSystemClassLoader();

			name = name.replace('.', '/');

			return loader.getResourceAsStream(name);
		} catch (Exception e) {
			return null;
		}
	}

	public static InputStream loadResourceAsStream(final String name) {
		return loadResourceAsStream(name, Thread.currentThread()
				.getContextClassLoader());
	}
}
