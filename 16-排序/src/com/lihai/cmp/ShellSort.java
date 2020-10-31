package com.lihai.cmp;

import com.lihai.Sort;

import java.util.ArrayList;
import java.util.List;

public class ShellSort<T extends Comparable<T>> extends Sort<T> {
    @Override
    protected void sort() {
        List<Integer> stepSequence = shellStepSequence();
        for (Integer step : stepSequence) {
             sort(step);
        }
    }

    private void sort(int step) {
        for (int col = 0;col < step;col = col++) { //每一列都要进行插入排序！
            for (int begin = col + step;begin < array.length;begin = begin + step) {
                int cur = begin;
                while (cur > 0 && cmp(cur,cur - step) < 0) {
                    swap(cur,cur - step);
                    cur = cur - step;
                }
            }
        }
    }

    private List<Integer> shellStepSequence() {
      List<Integer> stepSequence = new ArrayList<>();
      int step = array.length;
      while ((step >> 1) > 0) {
        stepSequence.add(step);
      }
      return stepSequence;
    }
}
