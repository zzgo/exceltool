package com.zzgo.bean;

import java.util.List;
import java.util.Map;

/**
 * Created by 9527 on 2017/11/1.
 */
public class WriteExcelBean {
    private String excelPath;//文件路径
    private String sheetName;//sheetName
    private String[] titles;//写入标题
    private List<Map<Integer, String>> values;//写入的值
    private boolean append;//是否追加操作
    private int appendSize;//追加条数


    public WriteExcelBean() {
    }

    public int getAppendSize() {
        return appendSize;
    }

    public void setAppendSize(int appendSize) {
        this.appendSize = appendSize;
    }

    public String getExcelPath() {
        return excelPath;
    }

    public void setExcelPath(String excelPath) {
        this.excelPath = excelPath;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String[] getTitles() {
        return titles;
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    public List<Map<Integer, String>> getValues() {
        return values;
    }

    public void setValues(List<Map<Integer, String>> values) {
        this.values = values;
    }

    public boolean isAppend() {
        return append;
    }

    public void setAppend(boolean append) {
        this.append = append;
    }
}
