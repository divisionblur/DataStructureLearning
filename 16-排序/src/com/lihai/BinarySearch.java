package com.lihai;

public class BinarySearch {
	
	/**
	 * 查找一个数v在有序数组array中的位置
	 */
	public static int indexOf(int[] array, int v) {
		if (array == null || array.length == 0) return -1;
		int begin = 0;
		int end = array.length;//不是最后一个元素，这个相当于数组长度位置的索引(这个设计很妙)
		while (begin < end) {//begin==end 是还没有找到就说明要找的元素不在数组中！
			int mid = (begin + end) >> 1;
			if (v < array[mid]) {
				end = mid;
			} else if (v > array[mid]) {
				begin = mid + 1;
			} else {
				return mid;
			}
		}
		return -1;
	}
	
	/**
	 * 查找v在有序数组array中待插入位置  (要将这个元素放在哪里)
	 */
	public static int search(int[] array, int v) {
		if (array == null || array.length == 0) return -1;
		int begin = 0;
		int end = array.length;
		while (begin < end) {
			int mid = (begin + end) >> 1;
			if (v < array[mid]) {
				end = mid;
			} else {
				begin = mid + 1;
			}
		}
		return begin;
	}
}
