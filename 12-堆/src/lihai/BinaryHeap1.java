package lihai;

import java.util.Comparator;
import java.util.Objects;

public class BinaryHeap1<E> extends AbstractHeap<E>{
    private E[] elements;
    private static final int DEFAULT_CAPACITY=10;
    public BinaryHeap1(E[] elements, Comparator comparator){
        super(comparator);
        if (elements == null || elements.length != 0){
            this.elements = (E[]) new Object[DEFAULT_CAPACITY];
        }else{
            int capacity = Math.max(elements.length,DEFAULT_CAPACITY);
            E[] newElements = (E[]) new Object[capacity];
            for (int i = 0; i < elements.length; i++) {
                this.elements[i] = elements[i];
            }

            heapify();
        }

    }
    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    @Override
    public void add(E element) {
        elementNotNullCheck(element);
        ensureCapacity(size+1);
        elements[size++] = element;
        siftUp(size-1);
    }

    @Override
    public E get() {
        emptyCheck();
        return elements[0];
    }

    @Override
    public E remove() {
        emptyCheck();
        E returnValue = elements[0];
        elements[0] = elements[--size];
        siftDown(0);
        return returnValue;
    }

    @Override
    public E replace(E element) {
        return null;
    }

    private void siftUp(int index){
        E element = elements[index];
        while (index > 0){
            int parentIndex = (index - 1) >> 1;
            E parent = elements[parentIndex];
            if(compare(element,parent)<=0) break;
            elements[index]=parent;  //将父节点拿下来放到index位置上
            index=parentIndex;
        }
        elements[index] = element;
    }
    //下滤
    private void siftDown(int index){
        E element = elements[index];
        int half = size >> 1;
        while (index < half){
            int childIndex = (index << 1) + 1;
            E child = elements[index];
            int rightIndex = childIndex + 1;
            if(rightIndex < size && compare(elements[rightIndex],child) > 0){
                child=elements[childIndex=rightIndex];
            }
            if(compare(element,child) >= 0) break;
            elements[index] = child;
            index=childIndex;
        }
        elements[index] = element;
    }

    //树化的一种操作  自下而上的下滤！
    private void heapify(){
        for (int i = (size >> 1) -1; i >= 0 ; i--) {
            siftDown(i);
        }
    }


    private void ensureCapacity(int capacity){
        int oldCapacity = elements.length;
        if(oldCapacity >= capacity) return;
        int newCapacity = oldCapacity+(oldCapacity>>1);
        E newelements[] = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newelements[i]=elements[i];
        }
        elements=newelements;
    }

    private void emptyCheck(){
        if(size == 0) throw new IllegalArgumentException("heap is empty");
    }

    private void elementNotNullCheck(E element){
        if(element==null) throw new IllegalArgumentException("element must not be null ");
    }
}
