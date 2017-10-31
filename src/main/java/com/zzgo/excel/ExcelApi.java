package com.zzgo.excel;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

/**
 * <p>excel的读写操作工具</p>
 */
public class ExcelApi {
    private Logger logger = Logger.getLogger(ExcelApi.class);
    private Workbook wb;

    private void writeInit(String excelPath) {
        try {
            if (excelPath.endsWith("xls")) {
                wb = new HSSFWorkbook();
            } else if (excelPath.endsWith("xlsx")) {
                wb = new SXSSFWorkbook();
            } else {
                throw new Exception("文件格式不正确！");
            }
        } catch (Exception e) {

        }
    }

    private void readInit(String excelPath) {
        try {
            InputStream inputStream = new FileInputStream(excelPath);
            if (excelPath.endsWith("xls")) {
                wb = new HSSFWorkbook(inputStream);
            } else if (excelPath.endsWith("xlsx")) {
                wb = new XSSFWorkbook(inputStream);
            } else {
                throw new Exception("文件格式不正确！");
            }
        } catch (Exception e) {

        }
    }

    public void write(String excelPath) {
        write(excelPath, "sheet1");
    }

    public void write(String excelPath, String sheetName) {
        write(excelPath, sheetName, null, null);
    }

    public void write(String excelPath, String sheetName, String[] titles, List<Map<Integer, String>> values) {
        write(excelPath, sheetName, titles, values, false);
    }

    public void write(String excelPath, String sheetName, String[] titles, List<Map<Integer, String>> values, boolean
            append) {
        logger.info("正在写入...请稍等...");
        long startTime = System.currentTimeMillis();
        if (sheetName == null)
            sheetName = "sheet1";
        try {
            Sheet sheet = null;
            int rowNum = 0;
            if (hasFile(excelPath)) {
                if (append) {
                    logger.info("正在追加写入操作...");
                    readInit(excelPath);
                    sheet = wb.getSheet(sheetName);
                    rowNum = sheet.getLastRowNum();
                    logger.info("rowNum=" + rowNum);
                }
            } else {
                writeInit(excelPath);
                sheet = (Sheet) wb.createSheet(sheetName);
            }
            //表头
            if (rowNum == 0) {
                Row titleRow = (Row) sheet.createRow(0);
                Cell cell = titleRow.createCell(0);
                for (int i = 0; i < titles.length; i++) {
                    cell = titleRow.createCell(i);
                    cell.setCellValue(titles[i]);
                    sheet.setColumnWidth(i, 20 * 256);
                }
                titleRow.setHeight((short) 400);
            }
            int r = rowNum + 1;
            for (int i = 0; i < values.size(); i++) {
                Row row = (Row) sheet.createRow(r++);
                Map<Integer, String> rowValues = values.get(i);
                for (int j = 0; j < rowValues.size(); j++) {
                    row.createCell(j).setCellValue(rowValues.get(j));
                }
            }
            //创建文件流
            OutputStream stream = new FileOutputStream(excelPath);
            //写入数据
            wb.write(stream);
            //关闭文件流
            stream.close();
            logger.info("##\t写入完成，用时=" + (System.currentTimeMillis() - startTime) + "ms");
        } catch (Exception e) {

        }
    }

    /**
     * <p>传入路径</p>
     *
     * @param excelPath
     * @return
     */
    public List<Map<Integer, String>> read(String excelPath) {
        return read(excelPath, 0);
    }

    public List<Map<Integer, String>> read(String excelPath, int sheetIndex) {
        return read(excelPath, sheetIndex, false);
    }

    public List<Map<Integer, String>> read(String excelPath, int sheetIndex, boolean isContainTitle) {
        return read(excelPath, sheetIndex, isContainTitle, 0);
    }

    public List<Map<Integer, String>> read(String excelPath, int sheetIndex, boolean isContainTitle, int size) {
        return read(excelPath, sheetIndex, isContainTitle, size, null);
    }

    public List<Map<Integer, String>> read(String excelPath, int sheetIndex, boolean isContainTitle, int size, int[]
            cols) {
        return read(excelPath, sheetIndex, isContainTitle, size, 1, cols);
    }

    /**
     * @param excelPath
     * @param sheetIndex
     * @param isContainTitle
     * @param size
     * @param from
     * @return
     */
    public List<Map<Integer, String>> read(String excelPath, int sheetIndex, boolean isContainTitle, int size, int
            from, int[] cols) {
        logger.info("正在读取...请稍等...");
        long startTime = System.currentTimeMillis();
        readInit(excelPath);
        List<Map<Integer, String>> mapList = new ArrayList<Map<Integer, String>>();
        Sheet sheet = (Sheet) wb.getSheetAt(sheetIndex);
        int rowNum = sheet.getLastRowNum();
        if (from < 1)
            from = 1;
        if (from == 1 && !isContainTitle)
            from = 2;
        //没有指定度读哪一行
        size = size == 0 ? rowNum : size;
        from = from > rowNum ? rowNum : from;
        rowNum = size > rowNum ? rowNum : (from + size - 2);
        if (isContainTitle) {
            if (from > 1) {
                String[] titles = readTitles(excelPath, cols);
                Map<Integer, String> map = new HashMap<Integer, String>();
                for (int i = 0; i < titles.length; i++) {
                    map.put(i, titles[i]);
                }
                mapList.add(map);
            }
        }

        for (int i = from - 1; i <= rowNum; i++) {
            Row row = (Row) sheet.getRow(i);
            Map<Integer, String> map = new HashMap<Integer, String>();
            int colNum = row.getPhysicalNumberOfCells();
            colNum = cols != null ? cols.length : colNum;
            for (int j = 0; j < colNum; j++) {
                map.put(j, getCellFormatValue(row.getCell(cols != null ? cols[j] : j)).toString());
            }
            mapList.add(map);
        }
        logger.info("##\t读取Excel用时=" + (System.currentTimeMillis() - startTime) + "ms");
        return mapList;
    }


    /**
     * @param excelPath 读取表头
     * @return
     */
    public String[] readTitles(String excelPath) {
        return readTitles(excelPath, null);
    }

    public String[] readTitles(String excelPath, int[] cols) {
        readInit(excelPath);
        String[] titles;
        Sheet sheet = (Sheet) wb.getSheetAt(0);
        Row row = (Row) sheet.getRow(0);
        int length = cols != null ? cols.length : row.getPhysicalNumberOfCells();
        titles = new String[length];
        for (int i = 0; i < length; i++) {
            titles[i] = row.getCell(cols != null ? cols[i] : i).getStringCellValue();
        }
        return titles;
    }


    /**
     * 根据Cell类型设置数据
     *
     * @param cell
     * @return
     * @author
     */
    private Object getCellFormatValue(Cell cell) {
        Object cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:// 如果当前Cell的Type为NUMERIC
                case Cell.CELL_TYPE_FORMULA: {
                    // 判断当前的cell是否为Date
                    if (DateUtil.isCellDateFormatted(cell)) {
                        // 如果是Date类型则，转化为Data格式
                        // data格式是带时分秒的：2013-7-10 0:00:00
                        // cellvalue = cell.getDateCellValue().toLocaleString();
                        // data格式是不带带时分秒的：2013-7-10
                        Date date = cell.getDateCellValue();
                        cellvalue = date;
                    } else {// 如果是纯数字

                        // 取得当前Cell的数值
                        cellvalue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                case Cell.CELL_TYPE_STRING:// 如果当前Cell的Type为STRING
                    // 取得当前的Cell字符串
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                default:// 默认的Cell值
                    cellvalue = "";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;
    }

    public boolean hasFile(String path) {
        File file = new File(path);
        return file.exists();
    }
}
