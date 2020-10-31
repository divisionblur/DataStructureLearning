package com.lihai.cmp;

import com.lihai.Sort;

public class QuickSort<T extends Comparable<T>> extends Sort<T> {

    @Override
    protected void sort() {
        sort(0,array.length);
    }

    private void sort(int begin, int end) {
        if ((end - begin) < 2) return; //递归出口！

        int mid = pivotIndex(begin, end);
        sort(begin, mid);
        sort(mid + 1, end);
    }

    private int pivotIndex(int begin, int end) {
        T pivot = array[begin];

        end--;
        while (begin < end) {
            while (begin < end) {
                if (cmp(array[end],pivot) > 0) {
                    end--;
                }else {
                    array[begin++] = array[end];
                    break;  //因为下一次要从左边开始比较了
                }
            }

            while (begin < end) {
                if (cmp(array[begin],pivot) < 0) {
                    begin++;
                }else {
                    array[end--] = array[begin];
                    break;
                }
            }
        }
        array[begin] = pivot;
        return begin;
    }
}


