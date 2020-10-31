package com.lihai.cmp;

import com.lihai.Sort;

public class BubbleSort1<T extends Comparable<T>> extends Sort<T> {

    //最简单，最普通，比较常见的一种冒泡排序的写法！
    @Override
    protected void sort() {
        for (int end = array.length - 1;end > 0;end--){
            for (int begin = 1;begin <= end;begin++){
                if (cmp(begin,begin - 1) < 0){
                    swap(begin,begin - 1);
                }
            }
        }
    }
}
