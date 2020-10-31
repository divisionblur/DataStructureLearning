package com.lihai.circle;

/**
 * @author lihai
 * @date 2020/9/21-19:17
 */

/**
 * 用数组实现单向循环队列！
 * @param <E>
 */
public class CircleQueue<E> {

    private int size;
    private int front;
    private E elements[];

    private static final int DEFAULT_CAPACITY=10;

    public CircleQueue(){  //构造函数可不能用泛型。
       elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size==0;
    }

    public void clear(){
        for (int i = 0; i <size ; i++) {
            elements[index(i)]=null;
        }
        front=0;
        size=0;
    }

    public void enQueue(E element){
        ensureCapacity(size+1);    //确保数组容量够不够，在队尾添加元素肯定是不用管队头指针的咯
        elements[index(size)]=element;//循环队列入队在尾部入队而如何定位尾部就是front+size只不过这是循环的队列用index()方法进行了封装！
                                      // 将索引固定在了0~size-1;
        size++;
    }


    public E deQueue(){
        E returnValue = elements[front];//先保留队头元素
        elements[front]=null;  //将对头元素置空
        front=index(1);  //将front指针 向队尾方向移动一位  即进行加一！
        size--;
        return returnValue;
    }

    public E front(){
        return elements[front];
    }

    @Override
    public String toString(){
        StringBuilder string = new StringBuilder();
        string.append("capacity=").append(elements.length)
        .append("size=").append(size).append("front=")
        .append(front)
        .append(",[");
        for (int i = 0; i <elements.length; i++) {
            if(i!=0){
                string.append(',');
            }
            string.append(elements[i]);
        }
        string.append("]");

        return string.toString();
    }

    private void ensureCapacity(int capacity) {
        int oldCapacity=elements.length;
        if(oldCapacity>=capacity)  return;
        int newCapacity=oldCapacity+(oldCapacity>>1);
        E[] newElements = (E[]) new Object[newCapacity];
        for (int i = 0; i <size ; i++) {
            newElements[i]=elements[i];
        }
        elements=newElements;
        front=0;
    }

    public int index(int index){
        index=front+index;
        return index-(index>=elements.length ? elements.length : 0);
    }
}
