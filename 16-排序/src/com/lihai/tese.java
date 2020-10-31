package com.lihai;

public class tese {
    private static Integer[] array = {9,8,7,6,5,4,3,2,1,0};;
    public static void main(String[] args) {

        sort(0,array.length);
        for (Integer date : array) {
            System.out.print(date+"-");
        }
    }



    private static void sort(int begin, int end) {
        if ((end - begin) < 2) return; //递归出口！

        int mid = pivotIndex(begin, end);
        sort(begin, mid);
        sort(mid + 1, end);
    }

    private static int pivotIndex(int begin, int end) {
        Integer pivot = array[begin];

        end--;
        while (begin < end) {
            while (begin < end) {
                if (array[end] > pivot) {
                    end--;
                }else {
                    array[begin++] = array[end];
                    break;  //因为下一次要从左边开始比较了
                }
            }


            while (begin < end) {
                if (array[begin] < pivot) {
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
