package com.zzgo.util.txt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 9527 on 2017/11/7.
 */
public class TxtUtils {
    public static List<String> read(String fileName) throws Exception {
        List<String> list = new ArrayList<String>();
        /* 读入TXT文件 */
        File filename = new File(fileName); // 要读取以上路径的input。txt文件
        InputStreamReader reader = new InputStreamReader(
                new FileInputStream(filename)); // 建立一个输入流对象reader
        BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
        String line = "";
        line = br.readLine();
        while (line != null) {
            list.add(line);
            line = br.readLine(); // 一次读入一行数据
        }
        return list;
    }
}
