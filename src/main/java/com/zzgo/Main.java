package com.zzgo;

import com.google.gson.Gson;
import com.zzgo.bean.*;
import com.zzgo.contants.ConfigConstants;
import com.zzgo.contants.URLContants;
import com.zzgo.util.excel.ExcelUtils;
import org.apache.commons.io.IOUtils;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by 9527 on 2017/11/1.
 */
public class Main {

    public static void main(String[] args) {
        Gson gson = new Gson();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        String time = sdf.format(new Date());
        ExcelUtils excelUtils = new ExcelUtils();
        ReadExcelBean read = new ReadExcelBean();
        read.setSheetIndex(0);
        read.setFrom(100);
        read.setSize(ConfigConstants.ROW_SIZE);
        read.setExcelPath(ConfigConstants.IN_PATH);
        read.setContainTitle(false);
        read.setCols(new int[]{0});
        List<Map<Integer, String>> mapList = excelUtils.read(read.getExcelPath(), read
                .getSheetIndex(), read.isContainTitle(), read.getSize(), read.getFrom
                (), read.getCols());
        System.out.println(("读取条数=" + mapList.size()));
        WriteExcelBean write = new WriteExcelBean();
        write.setAppend(true);
        write.setAppendSize(ConfigConstants.OUT_SPACING_SIZE);
        write.setExcelPath(ConfigConstants.OUT_PATH.replaceAll("time", time));
        write.setSheetName("180000");
        write.setTitles(ConfigConstants.OUT_TITLE.split(","));
        List<Map<Integer, String>> data = new ArrayList<Map<Integer, String>>();
        try {
            for (int i = 0; i < mapList.size(); i++) {
                Map<Integer, String> mapAddress = mapList.get(i);
                Map<Integer, String> map = new HashMap<Integer, String>();
                List<String> list = new ArrayList<String>();
                //在这里操作数据
                String address = mapAddress.get(0);
                System.out.println("第[\t" + i + "\t]\t地址=" + address);
                String httpUrl = URLContants.DITUHUI_ONLINE_URL.replace("${address}", URLEncoder.encode(address +
                        "", "utf-8"));
                //String httpUrl = URLContants.GUAN_URL.replace("${address}", URLEncoder.encode(address +
                //        "", "utf-8"));
                String content = IOUtils.toString(new URL(httpUrl), "utf-8");
                Common common = gson.fromJson(content, Common.class);
                Result result = common.getResult();
                //Address,Standard,Combine,ES-Combine,ES-Poi,ES-Address,Provice,City,County,Town,XY,ResultType
                list.add(address);
                if (result != null) {
                    list.add(result.getStAddress());
                    list.add(result.getCombine());
                    list.add(result.getEsCombine());
                    list.add(result.getPoi());
                    list.add(result.getEsAddress());
                    list.add(result.getProvince());
                    list.add(result.getCity());
                    list.add(result.getCounty());
                    list.add(result.getTown());
                    list.add(result.getX() + "," + result.getY());
                    list.add(result.getResultType());
                    for (int l = 0; l < list.size(); l++) {
                        map.put(l, list.get(l));
                    }
                } else {
                    map.put(0, list.get(0));
                    for (int l = 1; l < list.size(); l++) {
                        map.put(l, "");
                    }
                }
                data.add(map);
                list.clear();
                if (i > 0 && i % 5000 == 0) {
                    write.setValues(data);
                    excelUtils.write(write);
                    data.clear();
                }
            }
            write.setValues(data);
            excelUtils.write(write);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main2(String[] args) {
        Gson gson = new Gson();
        ExcelUtils excelUtils = new ExcelUtils();
        ReadExcelBean read = new ReadExcelBean();
        read.setSheetIndex(0);
        read.setSize(100);
        read.setExcelPath(ConfigConstants.IN_PATH);
        read.setContainTitle(false);
        read.setCols(new int[]{4});
        List<Map<Integer, String>> mapList = excelUtils.read(read.getExcelPath(), read
                .getSheetIndex(), read.isContainTitle(), read.getSize(), read.getFrom
                (), read.getCols());
        System.out.println(("读取条数=" + mapList.size()));
        WriteExcelBean write = new WriteExcelBean();
        write.setAppend(true);
        write.setAppendSize(ConfigConstants.OUT_SPACING_SIZE);
        write.setExcelPath(ConfigConstants.OUT_PATH);
        write.setSheetName("demo");
        write.setTitles(ConfigConstants.OUT_TITLE.split(","));
        List<Map<Integer, String>> data = new ArrayList<Map<Integer, String>>();
        try {
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
                list.add(result.getX());
                list.add(result.getY());
                for (int l = 0; l < list.size(); l++) {
                    map.put(l, list.get(l));
                }
                data.add(map);
                list.clear();
            }
            write.setValues(data);
            excelUtils.write(write);
        } catch (Exception e) {

        }
    }

    public static void main4(String[] args) {
        String address = "";
        Gson gson = new Gson();
        List<BatchAddress> batchAddresses = new ArrayList<BatchAddress>();
        for (int i = 0; i < 5; i++) {
            BatchAddress batchAddress = new BatchAddress(i + "", "浙江省嘉兴市海宁市长安镇盐仓村连杭新区新兴路19号胜百公司办公楼");
            batchAddresses.add(batchAddress);
        }
        address = gson.toJson(batchAddresses);
        try {
            String httpUrl = URLContants.BATCH_DITUHUI_ONLINE_URL.replace("${addresses}", URLEncoder.encode(address +
                    "", "utf-8"));
            System.out.println(httpUrl);

            try {
                String content = IOUtils.toString(new URL(httpUrl), "utf-8");
                Bean bean = gson.fromJson(content, Bean.class);
                System.out.println();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
