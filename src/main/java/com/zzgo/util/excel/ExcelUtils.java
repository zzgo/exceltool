package com.zzgo.util.excel;

import com.zzgo.bean.WriteExcelBean;
import com.zzgo.excel.ExcelApi;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 9527 on 2017/11/1.
 */
public class ExcelUtils {
    private Logger logger = Logger.getLogger(ExcelUtils.class);

    public void write(WriteExcelBean write) {
        try {
            ExcelApi excelApi = new ExcelApi();
            List<Map<Integer, String>> values = write.getValues();
            boolean append = write.isAppend();
            if (!append) {
                excelApi.write(write.getExcelPath(), write.getSheetName(), write.getTitles(),
                        values);
                return;
            }
            int appendSize = write.getAppendSize();
            List<Map<Integer, String>> appendValues = new ArrayList<Map<Integer, String>>();
            int mapSize = values.size();
            for (int i = 0; i < mapSize; i++) {
                appendValues.add(values.get(i));
                if (i > 0 && i % appendSize == 0) {
                    logger.info("写入条数共计=" + i);
                    excelApi.write(write.getExcelPath(), write.getSheetName(), write
                                    .getTitles(),
                            appendValues, append);
                    appendValues.clear();
                }
            }
            logger.info("写入条数共计=" + mapSize);
            excelApi.write(write.getExcelPath(), write.getSheetName(), write
                            .getTitles(),
                    appendValues, append);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Map<Integer, String>> read(String excelPath, int sheetIndex, boolean isContainTitle, int size, int
            from, int[] cols) {
        ExcelApi excelApi = new ExcelApi();
        return excelApi.read(excelPath, sheetIndex, isContainTitle, size,
                from, cols);
    }
}
