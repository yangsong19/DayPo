package com.xinlab.blueapple.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Test {
    /**
     * 
     * @param inDateList
     * @param outDateList
     * @param sequenceList
     */
    public static void inList2OutList(List<List<String>> inDateList,
            List<List<List<String>>> outDateList, List<Integer> sequenceList) {

        int size = sequenceList.size();

        // sequenceList为空时不排序
        if (size == 0) {
            for (int i = 0; i < inDateList.size(); i++) {
                outDateList.add(new ArrayList(inDateList.get(i)));
            }
        }

        int[] value = new int[size];
        for (int i = 0; i < sequenceList.size(); i++) {
            value[i] = sequenceList.get(i);
        }

        // 若要对使用Collections.sort要申请为final才可以
        final int[] values = value;

        // 排序部分
        Collections.sort(inDateList, new Comparator<List<String>>() {
            public int compare(List<String> entity1, List<String> entity2) {
                int ret = entity1.get(values[0]).compareTo(
                        entity2.get(values[0]));

                for (int i = 1; i < values.length; i++) {
                    if (ret == 0) {
                        ret = entity1.get(values[i]).compareTo(
                                entity2.get(values[i]));
                    }
                }
                return ret;
            }
        });

        // 分堆部分
        String[] head = new String[size];
        for (int i = 0; i < head.length; i++) {
            head[i] = "";
        }

        // 分堆核心
        for (List<String> list : inDateList) {

            boolean flg = false;
            for (int i = 0; i < head.length; i++) {
                flg = flg || !head[i].equals(values[i]);
                if (flg) {
                    // 当换堆时添加新的List
                    outDateList.add(new ArrayList<List<String>>());
                    break;
                }
            }

            outDateList.get(outDateList.size() - 1).add(list);
            for (int j = 0; j < head.length; j++) {
                head[j] = list.get(values[j]);
            }
        }
    }
}
