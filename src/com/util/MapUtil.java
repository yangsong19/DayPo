package com.util;

import java.util.Map;

import org.apache.commons.collections.MapUtils;

public class MapUtil {
	// 判断Map是否非空
	public static boolean isNotEmpty(Map<?, ?> map) {
		return MapUtils.isNotEmpty(map);
	}

	// 判断Map是否为空
	public static boolean isEmpty(Map<?, ?> map) {
		return MapUtils.isEmpty(map);
	}
}
