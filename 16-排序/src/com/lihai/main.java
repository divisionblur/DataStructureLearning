package com.lihai;

import com.lihai.cmp.SelectionSort;

public class main {
    public static void main(String[] args) {
        Integer[] array = {10 ,9,8,7 ,6 ,5 ,4 ,3 ,2 ,1};
        for (int end = array.length-1;end > 0;end --) {
            for (int begin = 1;begin <= end;begin++){
                if(array[begin] < array[begin-1]){//大一次就要交换一次，太浪费性能了。
                    int temp = array[begin-1];
                    array[begin-1] = array[begin];
                    array[begin] = temp;
                }
            }
        }

        for (int i = 0; i < array.length-1 ; i++) {
            for (int j= 1;j <= array.length-1-i;j++){
                if(array[j] < array[j-1]){
                    int temp = array[j-1];
                    array[j-1] = array[j];
                    array[j] = temp;
                }
            }
        }

        for (int end = array.length-1; end > 0; end--){//n-1次
            int maxIndex = 0;//假设最大值的索引是0
            for (int begin = 1;begin <= end; begin++){
                if(array[maxIndex] <= array[begin]){ //如果不加等于的话当begin=1且此时end=1的话，就会交换位置此时就不是稳定性排序了
                    maxIndex = begin;
                }
            }//每一次都把待排序中最大的找出来，然后在下面进行交换放在最后面
            int temp = array[maxIndex];
            array[maxIndex] = array[end];
            array[end] = temp;
        }


        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]+"-");
        }
    }
}
