package com.zzgo.util;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;

/**
 * 读取配置文件工具
 *
 * @author liweigu
 */
public class AppPropertiesUtil {
    private static final Logger LOGGER = Logger.getLogger(AppPropertiesUtil.class);

    /**
     * 读取配置文件
     *
     * @param fileName 文件名
     * @param clazz    类名
     * @return 配置信息
     */
    public static Properties readPropertiesFile(String fileName, Class clazz) {
        InputStream inStream = readFromUsrDir(fileName);

        if (inStream == null) {
            inStream = readFromClassPath(fileName, clazz);
        }
        if (inStream == null) {
            inStream = readFromResource(fileName, clazz);
        }

        Properties prop = new Properties();
        try {
            if (inStream != null) {
                prop.load(inStream);
                inStream.close();
                LOGGER.info("## 加载配置" + fileName + "成功");
            } else {
                prop = null;
                LOGGER.error("## 加载配置文件" + fileName + "出错");
            }
        } catch (Exception e) {
            prop = null;
            LOGGER.error("## 加载配置文件失败");
        }
        return prop;
    }

    private static InputStream readFromUsrDir(String fileName) {
        String filePath = System.getProperty("user.dir") + File.separator + fileName;
        InputStream inStream = null;
        try {
            inStream = new FileInputStream(filePath);
        } catch (FileNotFoundException localFileNotFoundException) {
            LOGGER.warn("readFromUsrDir发生异常：" + localFileNotFoundException.getMessage());
        }
        return inStream;
    }

    private static InputStream readFromClassPath(String fileName, Class clazz) {
        return clazz.getClassLoader().getResourceAsStream(fileName);
    }

    private static InputStream readFromResource(String fileName, Class clazz) {
        return clazz.getClass().getResourceAsStream(fileName);
    }
}
