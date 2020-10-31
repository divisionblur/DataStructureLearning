package com.lihai.cmp;
import com.lihai.Sort;
public class BubbleSort2<T extends Comparable<T>> extends Sort<T> {
    //对第一种冒泡排序的一种优化，这种优化主要是针对一种极端情况，就是当数组中的数据全部都有顺序时
    //可以提前终止排序，而不用傻乎乎的还一个一个的进行比较操作！因为第一次如果没有发生交换的话
    // 就说明是全部有序的
    @Override
    protected void sort() {
        for (int end = array.length - 1;end > 0;end--){
            boolean sorted = true;
            for (int begin = 1;begin <= end;begin++){
                if (cmp(begin,begin-1) < 0){
                    swap(begin,begin-1);
                    sorted = false;
                }
            }
            if (sorted) break;
        }
    }
}
