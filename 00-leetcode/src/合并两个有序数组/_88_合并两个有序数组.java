package 合并两个有序数组;

public class _88_合并两个有序数组 {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int len =m + n;
        for (int i = len - 1;i >= 0 ;i--){
            if (m > 0 && n > 0 && nums1[i] > nums2[i] || n==0) {
                nums1[i] = nums1[--m];
            }else {
                nums1[i] = nums2[--n];
            }
        }
    }
}
