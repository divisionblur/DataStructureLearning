package com.lihai.list;

import com.lihai.list.util.LinkedList;
import com.lihai.list.util.List;

/**
 * @author lihai
 * @date 2020/9/21-18:56
 */
public class Deque<E>{
    List<E> list=new LinkedList<E>();

    public int size(){
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void clear() {
        list.clear();
    }


    /**
     * 从队列头部入队！
     * @param element
     */
    public void enQueueFront(E element) {
        list.add(0, element);
    }

    /**
     * 从队列尾部入队
     * @param element
     */
    public void enQueueRear(E element) {
        list.add(element);
    }

    /**
     * 从队列头部出队！
     * @return
     */
    public E deQueueFront(){
        return list.remove(0);
    }

    /**
     * 从队列尾部出队
     * @return
     */
    public E deQueueRear() {
        return list.remove(list.size() - 1);
    }

    public E front() {
        return list.get(0);
    }

    public E rear() {
        return list.get(list.size() - 1);
    }
}
