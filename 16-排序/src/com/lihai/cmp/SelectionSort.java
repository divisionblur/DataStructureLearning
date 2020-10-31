package com.lihai.cmp;

import com.lihai.Sort;

public class SelectionSort<T extends Comparable<T>> extends Sort<T> {
    //选择排序是将每一次最大的元素放到最后面

    @Override
    public void sort() {
        for (int end = array.length - 1;end > 0;end--){
            int max = 0;
            for (int begin = 1;begin <= end;begin++){
                if (cmp(max,begin) < 0){
                    max = begin;
                }
            }
            swap(max,end);
        }
    }
}
