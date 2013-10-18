package com.other;

import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Administrator
 * 			
 *   1 用于获取 sql.properties 配置文件中定义的 SQL 语句（模板）
 *   2 生成基于单表的 SQL 语句
 *
 *
 */
public class SQLHelper {

	private static final Properties sqlProperties = ConfigHelper
			.getProperties("sql.properties");

	public static String getSQL(String key) {
		String value = "";
		if (sqlProperties.containsKey(key)) {
			value = sqlProperties.getProperty(key);
		} else {
			System.err.println("Can not get property [" + key
					+ "] in sql.properties file.");
		}
		return value;
	}

	public static String generateSelectSQL(Class<?> cls, String condition,
			Object... params) {
		StringBuilder sql = new StringBuilder("select * from ");

		String tableName = getTableName(cls);
		if (StringUtils.isNotEmpty(tableName)) {
			sql.append(tableName).append(" ");
		}

		if (StringUtils.isNotEmpty(condition)) {
			if (ArrayUtils.isNotEmpty(params)) {
				condition = getWhereCondition(condition, params);
			}
			sql.append("where ").append(condition);
		}

		return sql.toString();
	}

	public static String generateInsertSQL(Class<?> cls,
			Map<String, Object> fieldMap) {
		StringBuilder sql = new StringBuilder("insert into ");

		String tableName = getTableName(cls);
		if (StringUtils.isNotEmpty(tableName)) {
			sql.append(tableName).append(" ");
		}

		if (MapUtil.isNotEmpty(fieldMap)) {
			int i = 0;
			StringBuilder columns = new StringBuilder();
			StringBuilder values = new StringBuilder(" values ");
			for (Map.Entry<String, ?> fieldEntry : fieldMap.entrySet()) {
				String columnName = HumpToUnderlineUtil.humpToUnderline(fieldEntry.getKey());
				Object columnValue = fieldEntry.getValue();
				if (i == 0) {
					columns.append("(").append(columnName);
					values.append("('").append(columnValue).append("'");
				} else if (i == fieldMap.size() - 1) {
					columns.append(", ").append(columnName).append(")");
					values.append(", '").append(columnValue).append("')");
				} else {
					columns.append(", ").append(columnName);
					values.append(", '").append(columnValue).append("'");
				}
				i++;
			}
			sql.append(columns).append(values);
		}

		return sql.toString();
	}

	public static String generateDeleteSQL(Class<?> cls, String condition,
			Object... params) {
		StringBuilder sql = new StringBuilder("delete from ");

		String tableName = getTableName(cls);
		if (StringUtils.isNotEmpty(tableName)) {
			sql.append(tableName).append(" ");
		}

		if (StringUtils.isNotEmpty(condition)) {
			if (ArrayUtils.isNotEmpty(params)) {
				condition = getWhereCondition(condition, params);
			}
			sql.append(" where ").append(condition);
		}

		return sql.toString();
	}

	public static String generateUpdateSQL(Class<?> cls,
			Map<String, Object> fieldMap, String condition, Object... params) {
		StringBuilder sql = new StringBuilder("update ");

		String tableName = getTableName(cls);
		if (StringUtils.isNotEmpty(tableName)) {
			sql.append(tableName).append(" ");
		}

		if (MapUtil.isNotEmpty(fieldMap)) {
			sql.append("set ");
			int i = 0;
			for (Map.Entry<String, ?> fieldEntry : fieldMap.entrySet()) {
				String columnName = HumpToUnderlineUtil.humpToUnderline(fieldEntry.getKey());
				Object columnValue = fieldEntry.getValue();
				if (i == 0) {
					sql.append(columnName).append(" = '").append(columnValue)
							.append("'");
				} else {
					sql.append(", ").append(columnName).append(" = '").append(
							columnValue).append("'");
				}
				i++;
			}
		}

		if (StringUtils.isNotEmpty(condition)) {
			if (ArrayUtils.isNotEmpty(params)) {
				condition = getWhereCondition(condition, params);
			}
			sql.append(" where ").append(condition);
		}

		return sql.toString();
	}

	private static String getTableName(Class<?> cls) {
		String tableName;
		if (cls.isAnnotationPresent(Table.class)) {
			tableName = cls.getAnnotation(Table.class).value();
		} else {
			tableName = cls.getSimpleName().toLowerCase();
		}
		return tableName;
	}

	private static String getWhereCondition(String condition, Object... params) {
		StringBuffer buffer = new StringBuffer();
		Matcher matcher = Pattern.compile("\\?").matcher(condition);
		for (int i = 0; matcher.find(); i++) {
			matcher.appendReplacement(buffer, "'" + params[i].toString() + "'");
		}
		matcher.appendTail(buffer);
		return buffer.toString();
	}
}
