package com.lihai.notcmp;
import com.lihai.Sort;

/**
 * @author joy division
 * @date 2020/10/23 18:10
 */
public class CountingSort extends Sort<Integer> {

    //计数排序优化写法，可以比较负数。
    @Override
    protected void sort() {
        int max = array[0];
        int min = array[0];
        for (int i = 0;i < array.length;i++) {
            if (array[i] > max) {
                max = array[i];
            }

            if (array[i] < min) {
                min = array[i];
            }
        }

        // 开辟内存空间,存储次数。
        int[] counts = new int[max - min + 1];
        for (int i = 0;i < array.length;i++) {
            counts[array[i] - min]++;
        }

        //累加次数
        for (int i = 1;i < counts.length;i++) {
            counts[i] = counts[i] + counts[i - 1];
        }

        // 从后往前遍历元素(这样操作是稳定排序!)，将它放到有序数组中的合适位置
        int[] newArray = new int[array.length];
        for (int i = array.length;i >= 0;i--) {
            newArray[--counts[array[i] - min]] = array[i];
        }

        // 将有序数组赋值到array
        for (int i = 0;i < array.length;i++) {
            array[i] = newArray[i];
        }
    }

    protected void sort0() {
        //找出最大值!
        int max = array[0];
        for (int i = 1;i < array.length;i++){
            if (array[i] > max) {
                max = array[i];
            }
        }

        int[] counts = new int[max + 1];
        for (int i = 0;i < array.length;i++) {
            counts[array[i]]++;
        }

        int index = 0;
        for (int i = 0;i < counts.length;i++) {
            while (counts[i]-- > 0) {  //会先检查i的值是否是大于0的，如果是的话就进入While循环，并且i的值立即减一
                array[index++] = i;
            }
        }
    }
}
