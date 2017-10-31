package com.zzgo.contants;

import com.zzgo.util.AppPropertiesUtil;

import java.util.Properties;

/**
 * Created by 9527 on 2017/10/30.
 */
public class ConfigConstants {
    /**
     * 加载配置文件
     */
    private static Properties prop = AppPropertiesUtil.readPropertiesFile("src\\main\\resources\\config.properties",
            ConfigConstants
                    .class);
    public static final String IN_PATH = (String) prop.get("IN.PATH");

    public static final String OUT_PATH = (String) prop.get("OUT.PATH");

    public static final String OUT_TITLE = (String) prop.get("OUT.TITLE");

    public static final Integer OUT_SPACING_SIZE = Integer.parseInt(prop.get("OUT.SPACING.SIZE").toString());

    public static final String OUT_SHEET_NAME = (String) prop.get("OUT.SHEET.NAME");

    public static final Integer ROW_SIZE = Integer.parseInt(prop.get("ROW.SIZE").toString());
}


