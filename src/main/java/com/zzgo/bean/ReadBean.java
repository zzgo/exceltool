package com.zzgo.bean;

/**
 * Created by 9527 on 2017/11/1.
 */
public class ReadBean {
    private String excelPath;//路径
    private int sheetIndex;//第几个sheet
    private boolean isContainTitle;//是否包含标题
    private int size;//读取数量
    private int from;//从第行开始
    private int[] cols;//指定解析哪一列

    public ReadBean defaultPara(String excelPath) {
        this.excelPath = excelPath;
        return this;
    }

    public ReadBean addIsContainTitle(boolean isContainTitle) {
        this.isContainTitle = isContainTitle;
        return this;
    }

    public ReadBean addSize(int size) {
        this.size = size;
        return this;
    }

    public ReadBean addFrom(int from) {
        this.from = from;
        return this;
    }

    public ReadBean addCols(int[] cols) {
        this.cols = cols;
        return this;
    }

    public String getExcelPath() {
        return excelPath;
    }

    public void setExcelPath(String excelPath) {
        this.excelPath = excelPath;
    }

    public int getSheetIndex() {
        return sheetIndex;
    }

    public void setSheetIndex(int sheetIndex) {
        this.sheetIndex = sheetIndex;
    }

    public boolean isContainTitle() {
        return isContainTitle;
    }

    public void setContainTitle(boolean containTitle) {
        isContainTitle = containTitle;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int[] getCols() {
        return cols;
    }

    public void setCols(int[] cols) {
        this.cols = cols;
    }
}
