package com.lihai.cmp;

import com.lihai.Sort;

public class InsertionSort1<T extends Comparable<T>> extends Sort<T> {
    @Override
    protected void sort() {
        for (int begin = 1;begin < array.length;begin++){
            int cur = begin;
            while (cur > 0 && cmp(cur,cur-1) < 0){
                swap(cur,cur-1);
                cur--;
            }
        }
    }
}
/**
 * 插入排序的思想是从1号索引位置开始(默认0号位置的元素是有序的) 如果比前面的数据小的话，就交换位置
 * 这样前两个就是有顺序的了！其余后面的也是这样与前一个元素就行比较，如果比前一个元素小的话，就交换位置
 * 直到0索引位置 或者比前一个元素大 就不用进行交换操作了！
 */
