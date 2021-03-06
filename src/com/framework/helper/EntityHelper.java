package com.framework.helper;


import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.framework.annotation.Column;
import com.framework.base.BaseEntity;
import com.framework.util.ArrayUtil;
import com.framework.util.MapUtil;
import com.framework.util.StringUtil;

public class EntityHelper {

    private static final Logger logger = Logger.getLogger(EntityHelper.class);

    private static final Map<Class<?>, Map<String, String>> entityMap = new HashMap<Class<?>, Map<String, String>>(); // Entity 类 => (列名 => 字段名)

    static {
        if (logger.isInfoEnabled()) {
            logger.info("Init EntityHelper...");
        }

        // 获取并遍历所有 Entity 类
        List<Class<?>> entityClassList = ClassHelper.getClassListBySuper(BaseEntity.class);
        for (Class<?> entityClass : entityClassList) {
            // 获取并遍历该 Entity 类中所有的字段（不包括父类中的方法）
            Field[] fields = entityClass.getDeclaredFields();
            if (ArrayUtil.isNotEmpty(fields)) {
                // 创建一个 Field Map（用于存放列名与字段名的映射关系）
                Map<String, String> fieldMap = new HashMap<String, String>();
                for (Field field : fields) {
                    String fieldName = field.getName();
                    String columnName;
                    // 若该字段上存在 @Column 注解，则优先获取注解中的列名
                    if (field.isAnnotationPresent(Column.class)) {
                        columnName = field.getAnnotation(Column.class).value();
                    } else {
                        columnName = StringUtil.toUnderline(fieldName); // 将驼峰风格替换为下划线风格
                    }
                    // 若字段名与列名不同，则需要进行映射
                    if (!fieldName.equals(columnName)) {
                        fieldMap.put(columnName, fieldName);
                    }
                }
                // 将 Entity 类与 Field Map 放入 Entity Map 中
                if (MapUtil.isNotEmpty(fieldMap)) {
                    entityMap.put(entityClass, fieldMap);
                }
            }
        }
    }

    public static Map<Class<?>, Map<String, String>> getEntityMap() {
        return entityMap;
    }
}
