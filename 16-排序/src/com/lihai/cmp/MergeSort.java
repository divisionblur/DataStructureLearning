package com.lihai.cmp;

import com.lihai.Sort;

public class MergeSort<T extends Comparable<T>> extends Sort<T> {
    private T[] leftArray;

    @Override
    protected void sort() {
        leftArray = (T[]) new Comparable[array.length >> 1];
        sort(0,array.length);
    }
    //要用到递归!
    private void sort(int begin ,int end) {
        if (end - begin < 2) return;
        int mid = (begin + end) >> 1;
        sort(begin,mid);
        sort(mid,end);
        merge(begin,mid,end);
    }

    private void merge(int begin,int mid,int end) {
        int li = begin;
        int le = mid - begin;
        int ri = mid;
        int re = end;
        int ai = begin;
        //先备份左边的数据
        for (int i = li;i < le;i++){
            leftArray[i] = array[begin + i];
        }

        // 如果左边还没有结束，左边结束的话就直接结束排序了！
        while (li < le) {
            if (ri < re && cmp(array[ri],array[li]) < 0){ //右边更小！
                array[ai++] = array[ri++];
            }else {
                array[ai++] = leftArray[li++];
            }
        }
    }
}
