package com.lihai;

import java.util.Arrays;

/**考虑了缩容的动态数组
 * @author lihai
 * @date 2020/9/18-19:23 
 */
public class ArrayList<E> extends AbstractList<E> {//继承了AbstractList就相当于间接实现了List,所以需要重写这些方法！

    private E elements[];
    private static final int DEFAULT_CAPACITY=10;

    public ArrayList(int capacity) {
        capacity=(capacity<DEFAULT_CAPACITY) ? DEFAULT_CAPACITY  : capacity;
        elements= (E[]) new Object[capacity];
    }

    public ArrayList(){
        this(DEFAULT_CAPACITY);
    }

    @Override
    public void clear() {
        for (int i = 0; i <size ; i++) {
            elements[i]=null;
        }
        size=0; //到这里只是将数组的元素全部清空，但是数组的空间仍然没有变，如果这个数组很长的话也会很占用空间

        //所以可以把数组的空间变小。
        if(elements!=null && elements.length>DEFAULT_CAPACITY){
            elements= (E[]) new Object[DEFAULT_CAPACITY];
        }
    }


    /**
     * 获取index位置的元素
     * @param index
     * @return
     */
    @Override
    public E get(int index) {
        rangeCheck(index);
        return elements[index];
    }

    /**
     * 设置index位置的元素
     * @param index
     * @param element
     * @return 原来的元素ֵ
     */
    @Override
    public E set(int index, E element) {
        rangeCheck(index);
        E returnValue = elements[index];
        elements[index]=element;
        return returnValue;
    }

    /**
     * 在index位置插入一个元素
     * @param index
     * @param element
     */
    @Override
    public void add(int index, E element) {

        rangeCheckForAdd(index);

        ensureCapacity(size+1);

        for (int i = index; i < size; i++) {
            elements[i+1]=elements[i];
        }
        elements[index]=element;
        size++;
    }

    /**
     * 删除index位置的元素
     * @param index
     * @return index位置节点的值
     */
    @Override
    public E remove(int index) {
        rangeCheck(index);
        E oldValue = elements[index];
        for (int i = index; i <size ; i++) {
            elements[i]=elements[i+1];
        }

        elements[--size]=null;

        trim();
        return oldValue;
    }



    /**
     * 查看元素的索引
     * @param element
     * @return
     */
    @Override
    public int indexOf(E element) {

        //这个元素是null
        if(element==null){
            for (int i = 0; i <size ; i++) {
               if(elements[i]==null)
                   return i;
            }
        }else{
            for (int i = 0; i <size ; i++) {
                if(element.equals(elements[i]))
                    return i;
            }
        }
        return ELEMENT_NOT_FOUND;  //没有找到这个元素
    }

    @Override
    public String toString() {
        // size=3, [99, 88, 77]
        StringBuilder string = new StringBuilder();
        string.append("size=").append(size).append(", [");
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                string.append(", ");
            }
            string.append(elements[i]);
        }
        string.append("]");
        return string.toString();
    }

    private void ensureCapacity(int capacity) {
        int oldCapacity = elements.length;
        if(oldCapacity>capacity) return;

        int newCapacity=oldCapacity+(oldCapacity>>1);
        E[] newElements = (E[]) new Object[newCapacity];
        for (int i = 0; i <size ; i++) {
            newElements[i]=elements[i];
        }
        elements=newElements;

        System.out.println(oldCapacity + "扩容为" + newCapacity);
    }




    private void trim() {
        int oldCapacity=elements.length;
        int newCapacity=oldCapacity>>1; //打算要缩容的数组大小！

        if(size>newCapacity||oldCapacity<DEFAULT_CAPACITY) return;
        /**
         * 当前数组中的元素个数比 打算缩小到的容量还大 或者 上一次缩小的容量已经小于默认容量了
         * 就不继续缩小容量 退出方法！
         */

        //有很多的剩余空间，就可以缩小容量,先创建一个新的小容量的数组！
        E[] newElements = (E[]) new Object[newCapacity];
        //接下来的操作是将元素放到新数组中

        for (int i = 0; i <size ; i++) {
            newElements[i]=elements[i];
        }
        elements=newElements;
        System.out.println(oldCapacity + "缩容为" + newCapacity);
    }
}
