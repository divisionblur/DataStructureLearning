package com.queue;
import com.lihai.BinaryHeap;
import java.util.Comparator;

/**
 * @author lihai
 * @date 2020/10/9-10:06
 */
public class PriorityQueue<E> {
    private BinaryHeap<E> heap;

    public PriorityQueue(Comparator comparator){
        heap=new BinaryHeap<>(comparator);
    }
    public PriorityQueue(){
        this(null);
    }

    public int size(){
        return heap.size();
    }

    public boolean isEmpty(){
        return heap.isEmpty();
    }

    public void clear(){
        heap.clear();
    }

    public void enQueue(E element){
        heap.add(element);
    }

    public void deQueue(){
        heap.remove();
    }

    public E front(){
        return heap.get();
    }


}
