package com.lihai.cmp;

import com.lihai.Sort;

public class BubbleSort3<T extends Comparable<T>> extends Sort<T> {

    //这种优化主要是对那种局部已经有序的优化，比如最后部分数据已经有序，记录下最后交换时的索引位置！
    @Override
    protected void sort() {
        for (int end = array.length - 1;end > 0;end--){
            //当数据全部有顺序时,不会进入if语句，比较完一轮之后，退出内循环将sortedIndex的值1赋值给
            //end = 1,end此时又会减一，那么就不满足循环条件了，退出循环！
            int sortedIndex = 1;
            for (int begin = 1;begin <= end; begin++){
                if (cmp(begin,begin - 1) < 0){
                    swap(begin,begin - 1);
                    //记录最后一次交换的位置，减少比较次数！
                    sortedIndex = begin;
                }
            }
            end = sortedIndex;
        }
    }
}
