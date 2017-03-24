package com.wangke.wkcore.http.temptest;

import java.util.Comparator;

/**
 * Map按 键名 比较类
 * Created by wk on 15/12/26.
 **/
public class MapKeyComparator implements Comparator<String> {
    @Override
    public int compare(String str1, String str2) {
        return str1.compareTo(str2);
    }
}
