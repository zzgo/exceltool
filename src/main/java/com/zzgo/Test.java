package com.zzgo;

import com.zzgo.util.txt.TxtUtils;

import java.util.List;

/**
 * Created by 9527 on 2017/11/7.
 */
public class Test {
    public static void main(String[] args) {
        try {
            List<String> list = TxtUtils.read("C:\\Users\\Administrator\\Desktop\\123.txt");
            int k = 0;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).startsWith("1"))
                    k++;
            }
            System.out.println(k);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

