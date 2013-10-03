package com.xinlab.blueapple.contenttool.db;

import com.xinlab.blueapple.contenttool.common.XinConfig;

public class XinBADBAccessor2 extends XinDBAccessor2{

    public XinBADBAccessor2() {
        dburl = XinConfig.dbUrl;
    }
}
