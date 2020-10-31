package lihai;

import java.io.InputStreamReader;
import java.util.Comparator;

/**
 * @author lihai
 * @date 2020/10/9-9:28
 */
public class main {
    static void test(){
        //新建一个小顶堆 只需要将比较规则改一下就行！
        BinaryHeap<Integer> heap = new BinaryHeap<>(new Comparator<Integer>(){
            @Override
            public int compare(Integer o1, Integer o2){
                return o2-o1;
            }
        });
        int k=3;
        Integer[] data={51, 30, 39, 92, 74, 25, 16, 93,
                91, 19, 54, 47, 73, 62, 76, 63, 35, 18,
                90, 6, 65, 49, 3, 26, 61, 21, 48};
        for (int i = 0; i <data.length ; i++) {
            if(heap.size<k){
                heap.add(data[i]);
            }else if(data[i]>heap.get()){
                heap.replace(data[i]);   //添加一个就将此时堆顶元素中最小的替换，最终留下的就是K个最大的！
            }
        }
    }

}
