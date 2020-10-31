package com.lihai.list;


import com.lihai.list.util.LinkedList;
import com.lihai.list.util.List;

/**用双向链表实现的队列
 * @author lihai
 * @date 2020/9/21-18:39
 */
public class Queue<E> {

    private List<E> list= new LinkedList<>();

    public int size(){
       return list.size();
    }

    public boolean isEmpty(){
        return list.isEmpty();
    }

    public void clear(){
        list.clear();
    }


    public void enQueue(E element){
        list.add(element);   //只能从尾部入队
    }

    public E deQueue(){
        return list.remove(0);
    }

    public E front(){
        return list.get(0);
    }

}
