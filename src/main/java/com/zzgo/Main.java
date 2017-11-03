package com.zzgo;

import com.google.gson.Gson;
import com.zzgo.bean.Common;
import com.zzgo.bean.ReadExcelBean;
import com.zzgo.bean.Result;
import com.zzgo.bean.WriteExcelBean;
import com.zzgo.contants.ConfigConstants;
import com.zzgo.contants.URLContants;
import com.zzgo.util.excel.ExcelUtils;
import org.apache.commons.io.IOUtils;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 9527 on 2017/11/1.
 */
public class Main {
    public static void main(String[] args) {
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
}
