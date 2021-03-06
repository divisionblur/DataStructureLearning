class Solution {

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int len = m + n; //从后往前移, 需要合并后的总长度
        for(int i = len - 1; i >=0; i--){
            if(m>0 && n>0 && nums1[m-1] > nums2[n-1]  || n==0){
                nums1[i] = nums1[--m];
            }else{
                nums1[i] = nums2[--n];
            }
        }
    }
}
