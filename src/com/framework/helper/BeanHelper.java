package com.framework.helper;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.framework.annotation.Bean;

public class BeanHelper {

    private static final Logger logger = Logger.getLogger(BeanHelper.class);

    private static final Map<Class<?>, Object> beanMap = new HashMap<Class<?>, Object>(); // Bean 类 => Bean 实例

    static {
        if (logger.isInfoEnabled()) {
            logger.info("Init BeanHelper...");
        }

        try {
            // 获取并遍历所有的 Bean（带有 @Bean 注解的类）
            List<Class<?>> beanClassList = ClassHelper.getClassListByAnnotation(Bean.class);
            for (Class<?> beanClass : beanClassList) {
                // 创建 Bean 实例
                Object beanInstance = beanClass.newInstance();
                // 将 Bean 实例放入 Bean Map 中（键为 Bean 类，值为 Bean 实例）
                beanMap.put(beanClass, beanInstance);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static Map<Class<?>, Object> getBeanMap() {
        return beanMap;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> cls) {
        if (!beanMap.containsKey(cls)) {
            throw new RuntimeException("Can not get bean by class!");
        }
        return (T) beanMap.get(cls);
    }
}
