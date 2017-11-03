package com.zzgo;

import com.google.gson.Gson;
import com.zzgo.bean.Common;
import com.zzgo.bean.Result;
import com.zzgo.contants.ConfigConstants;
import com.zzgo.contants.URLContants;
import com.zzgo.excel.ExcelApi;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 */
public class App {
    private Logger logger = Logger.getLogger(App.class);

    public static void main(String[] args) {
        new App().ttt();
    }

    public void run() {
        try {
            Gson gson = new Gson();
            //读取内容
            ExcelApi excelApi = new ExcelApi();
            System.out.println(new String(ConfigConstants.IN_PATH.getBytes("UTF-8")));
            List<Map<Integer, String>> mapList = excelApi.read(ConfigConstants.IN_PATH, 0, false, 1365, 1, new
                    int[]{0});
            logger.info("读取条数=" + mapList.size());
            //根据内容进行相应处理
            List<Map<Integer, String>> data = new ArrayList<Map<Integer, String>>();
            for (int i = 0; i < mapList.size(); i++) {
                Map<Integer, String> mapAddress = mapList.get(i);
                Map<Integer, String> map = new HashMap<Integer, String>();
                List<String> list = new ArrayList<String>();
                //在这里操作数据
                String address = mapAddress.get(0);
                String httpUrl = URLContants.NEW_URL.replace("${address}", URLEncoder.encode(address +
                        "", "utf-8"));
                String content = IOUtils.toString(new URL(httpUrl), "utf-8");
                Common common = gson.fromJson(content, Common.class);
                Result result = common.getResult();
                //addressOrign,stAddress,combine,province,city,county,town,esCombine,poi,esAddress
                list.add(address);
                //list.add(result.getStAddress());
                //list.add(result.getCombine());
                //list.add(result.getProvince());
                //list.add(result.getCity());
                //list.add(result.getCounty());
                //list.add(result.getTown());
                //list.add(result.getEsCombine());
                //list.add(result.getPoi());
                //list.add(result.getEsAddress());
                list.add(result.getX() + "," + result.getY());

                for (int l = 0; l < list.size(); l++) {
                    map.put(l, list.get(l));
                }
                data.add(map);
                list.clear();
            }
            excelApi.write(ConfigConstants.OUT_PATH, ConfigConstants.OUT_SHEET_NAME, ConfigConstants.OUT_TITLE.split
                            (","),
                    data);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void test2() {
        try {
            Gson gson = new Gson();
            //读取内容
            ExcelApi excelApi = new ExcelApi();
            System.out.println(new String(ConfigConstants.IN_PATH.getBytes("UTF-8")));
            List<Map<Integer, String>> mapList = excelApi.read(ConfigConstants.IN_PATH, 0, false, ConfigConstants
                    .ROW_SIZE, 2, new
                    int[]{0});
            //根据内容进行相应处理
            List<Map<Integer, String>> data = new ArrayList<Map<Integer, String>>();
            //计算
            int length = mapList.size();
            int mod = length % ConfigConstants.OUT_SPACING_SIZE;
            if (mod == length) mod = 0;
            for (int i = 0; i <= length - mod; i++) {
                Map<Integer, String> mapAddress = mapList.get(i);
                Map<Integer, String> map = new HashMap<Integer, String>();
                List<String> list = new ArrayList<String>();
                //在这里操作数据
                String address = mapAddress.get(0);
                String httpUrl = URLContants.NEW_URL.replace("${address}", URLEncoder.encode(address +
                        "", "utf-8"));
                String content = IOUtils.toString(new URL(httpUrl), "utf-8");
                Common common = gson.fromJson(content, Common.class);
                Result result = common.getResult();
                //addressOrign,stAddress,combine,province,city,county,town,esCombine,poi,esAddress
                list.add(address);
                //list.add(result.getStAddress());
                //list.add(result.getCombine());
                //list.add(result.getProvince());
                //list.add(result.getCity());
                //list.add(result.getCounty());
                //list.add(result.getTown());
                //list.add(result.getEsCombine());
                //list.add(result.getPoi());
                //list.add(result.getEsAddress());
                list.add(result.getX() + "," + result.getY());
                for (int l = 0; l < list.size(); l++) {
                    map.put(l, list.get(l));
                }
                data.add(map);
                if (i % ConfigConstants.OUT_SPACING_SIZE == 0 && i > 0) {
                    logger.info("条数=" + i);
                    excelApi.write(ConfigConstants.OUT_PATH, ConfigConstants.OUT_SHEET_NAME, ConfigConstants
                                    .OUT_TITLE.split(","),
                            data, true);
                    data.clear();
                }
                list.clear();
            }
            for (int i = length - mod + 1; i < length; i++) {
                Map<Integer, String> mapAddress = mapList.get(i);
                List<String> list = new ArrayList<String>();
                Map<Integer, String> map = new HashMap<Integer, String>();
                //在这里操作数据
                String address = mapAddress.get(0);
                String httpUrl = URLContants.NEW_URL.replace("${address}", URLEncoder.encode(address +
                        "", "utf-8"));
                String content = IOUtils.toString(new URL(httpUrl), "utf-8");
                Common common = gson.fromJson(content, Common.class);
                Result result = common.getResult();
                //addressOrign,stAddress,combine,province,city,county,town,esCombine,poi,esAddress
                list.add(address);
                //list.add(result.getStAddress());
                //list.add(result.getCombine());
                //list.add(result.getProvince());
                //list.add(result.getCity());
                //list.add(result.getCounty());
                //list.add(result.getTown());
                //list.add(result.getEsCombine());
                //list.add(result.getPoi());
                //list.add(result.getEsAddress());
                list.add(result.getX() + "," + result.getY());
                for (int l = 0; l < list.size(); l++) {
                    map.put(l, list.get(l));
                }
                data.add(map);
                list.clear();
            }
            logger.info("剩余条数=" + mod);
            excelApi.write(ConfigConstants.OUT_PATH, ConfigConstants.OUT_SHEET_NAME, ConfigConstants.OUT_TITLE.split
                            (","),
                    data, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object getObject(Map<Integer, String> map, int... args) {
        for (int i = 0; i < args.length; i++) {

        }
        return null;
    }


    public void ttt() {
        //ReadExcelBean readExcelBean = new ReadExcelBean().defaultPara("").addSize(1).addFrom(1).addIsContainTitle
        //        (false);
        //System.out.println(readExcelBean.getFrom());
    }

}
