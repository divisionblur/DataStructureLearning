package com.lihai.cmp;

import com.lihai.Sort;

public class HeapSort<T extends Comparable<T>> extends Sort<T> {

    private int heapSize;
    @Override
    protected void sort() {
        int heapSize = array.length-1;
        for (int i = (heapSize >> 1) - 1;i >= 0;i--){
            siftDown(i);
        }

        while (heapSize > 1){
            swap(0,--heapSize);
            siftDown(0);
        }
    }

    private void siftDown(int index){
        T element = array[index];
        int half = (heapSize >> 1);
        while (index < half){
            int childIndex = (heapSize << 1) + 1;
            T child = array[childIndex];
            int rightIndex = childIndex + 1;
            if (rightIndex < heapSize && cmp(array[rightIndex],child) > 0){
                child = array[childIndex = rightIndex];
            }

            if (cmp(array[index],child) >= 0) break;
            array[index] = child;
            index = childIndex;
        }

        array[index] = element;
    }
}
