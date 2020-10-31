package com.lihai.cmp;

import com.lihai.Sort;

public class InsertionSort3<T extends Comparable<T>> extends Sort<T> {
    @Override
    protected void sort() {
        for (int begin = 1;begin < array.length;begin++){
            insert(begin,search(begin));
        }
    }

    private void insert(int source, int dest) {
        T v = array[source];
        for (int i = source; i > dest; i--){
            array[i] = array[i-1];
        }
        array[dest] = v;
    }


    private int search(int index) {
        int begin = 0;
        int end = index;
        while(begin < end){
            int mid = (begin + end) >> 1;
            if (cmp(array[index],array[mid]) < 0){
                end = mid;
            }else {
                begin = mid + 1;
            }
        }
        return begin;
    }
}
