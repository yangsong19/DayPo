package com.xinlab.blueapple.test;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang.ArrayUtils;

public class FindOddEvenNum {
    /*
     * 题目：一个int型数组，存放的数据中有两个重复的次数是奇数次，其他的重复的次数全是偶数次。
                   要求：找出这两个数据，时间复杂度是O(n) 空间复杂度是O(1)
     *
     * 假设数据是：55，44，33，44，44，44，22，33，55，55，22，22 
     */
    
    private static ArrayList<Integer> find(int[] data) {
//     第一步：将数据依次进行异或运算，即：55与44异或运算得到a，a再与33异或运算得到b，一直到最后得到一个数值为x.
        int temp = data[0];
        for (int i = 0; i < data.length - 1; i++) {
            temp ^= data[i + 1];
        }
        return select(data,temp);
    }
    private static ArrayList<Integer> select(int[] data,int temp) {
        ArrayList<Integer> target = new ArrayList<Integer>();
        for (int i = 0; i < data.length; i++) {
            if(ArrayUtils.contains(data, temp ^ data[i])) {
                target.add(data[i]);
            }
        }
        return target;
    }
    
    public static void main(String[] args) {
       /* System.out.println(1 | 2);    //逻辑或
        * System.out.println(1 & 2);    //逻辑与
        * System.out.println(~ 2);      //逻辑非
        * System.out.println(1 ^ 2);    //异或
        * System.out.println(~(1 ^ 2)); //同或==异或非
        *  0001
        *  0010
        * ^0011 = 3
        * ^0001
        *  0010 = 2
        */
       int[] data = {55,44,33,44,44,44,22,33,55,55,22,22};
       System.out.println(find(data).toString());
//       System.out.println(ArrayUtils.contains(data, 22));
    }
    
}
