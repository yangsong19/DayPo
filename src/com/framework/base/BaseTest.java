package com.framework.base;


import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.runner.RunWith;

import com.framework.OrderedRunner;
import com.framework.helper.DBHelper;
import com.framework.helper.InitHelper;
import com.framework.util.ClassUtil;

@RunWith(OrderedRunner.class)
public abstract class BaseTest {

    private static final Logger logger = Logger.getLogger(BaseTest.class);

    protected BaseTest() {
        InitHelper.init();
    }

    protected static void initSQL(String sqlPath) {
        try {
            File sqlFile = new File(ClassUtil.getClassPath() + sqlPath);
            List<String> sqlList = FileUtils.readLines(sqlFile);
            for (String sql : sqlList) {
                DBHelper.update(sql);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
