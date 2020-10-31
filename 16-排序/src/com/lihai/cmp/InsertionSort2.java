package com.lihai.cmp;

import com.lihai.Sort;

/**
 * 插入排序的一种优化，把交换(三行代码)变为挪动！
 * 先把待插入元素备份,头部有序数据中比待插入元素大的,都朝尾部方向挪动1个位置。
 * @param <T>
 */
public class InsertionSort2<T extends Comparable<T>> extends Sort<T> {
    @Override
    protected void sort() {
        for (int begin = 1;begin < array.length;begin++){
            int cur = begin;
            T v = array[begin];
            while (cur > 0 && cmp(cur,cur-1) < 0){
                array[cur] = array[cur - 1];
                cur--;
            }
            array[cur] = v;// 将待插入元素放到最终的合适位置
        }
    }
}
