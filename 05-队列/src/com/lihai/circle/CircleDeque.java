package com.lihai.circle;

/**用动态数组实现循环双端队列
 * @author lihai
 * @date 2020/9/21-20:30
 */
public class CircleDeque<E> {
    private int size;
    private int front;
    private E elements[];
    private static final int DEFAULT_CAPACITY=10;

    public CircleDeque(){
        E[] elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size==0;
    }

    public void clear(){   //从队头开始删
        for (int i = 0; i <size ; i++) {
            elements[index(i)]=null;
        }
        size=0;
        front=0;
    }

    /**
     * 从双端队列尾部入队
     * @param element
     */
    public void enQueueRear(E element){
        ensureCapacity(size+1);
        elements[index(size)]=element;
        size++;
    }


    /**
     * 从双端队列头部入队
     */
    public void enQueueFront(E element){
        ensureCapacity(size+1);
        front=index(-1);
        elements[front]=element;
        size++;
    }

    /**
     * 从双端队列头部出队
     * @return
     */

    public E deQueueFront(){
        E returnValue = elements[front];
        elements[front]=null;
        front=index(1);
        size--;
        return returnValue;
    }

    /**
     * 从双端队列尾部出队
     * @return
     */
    public E deQueueRear(){
        E returnValue = elements[index(size - 1)];
        elements[index(size-1)]=null;
        size--;
        return returnValue;
    }

    private int index(int index){ //双端队列从头部入队会将 front=index(-1);
        index=front+index;
        if(index<0){
            return index+elements.length;
        }

        return index-(index>=elements.length ? elements.length : 0);
    }


    public E front(){
        return elements[front];
    }

    public E rear(){
        return elements[index(size-1)];
    }

    private void ensureCapacity(int capacity) {
        int oldCapacity=elements.length;
        if(oldCapacity>=capacity) return;
        int newCapacity=oldCapacity+(oldCapacity>>1);
        E[] newElements = (E[]) new Object[newCapacity];
        for (int i = 0; i <size; i++) {
            newElements[i]=elements[i];
        }
        elements=newElements;
        front=0;
    }


    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("capcacity=").append(elements.length)
                .append(" size=").append(size)
                .append(" front=").append(front)
                .append(", [");
        for (int i = 0; i < elements.length; i++) {
            if (i != 0) {
                string.append(", ");
            }

            string.append(elements[i]);
        }
        string.append("]");
        return string.toString();
    }

}
