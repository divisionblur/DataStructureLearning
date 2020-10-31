package com.lihai.list.util;

/**
 * 双向链表
 * @author lihai
 * @date 2020/9/18-21:25
 */
public class LinkedList<E> extends AbstractList<E> {

    private Node<E> first;
    private Node<E> last;

    private static class Node<E>{
        E element;
        Node<E> prev;
        Node<E> next;

        public Node(E element, Node<E> prev, Node<E> next) {
            this.element = element;
            this.prev = prev;
            this.next = next;
        }
    }


    @Override
    public void clear() {
        size=0;
        first=null;
        last=null;
    }

    @Override
    public E get(int index) {
        rangeCheck(index);
        return  node(index).element;
    }

    @Override
    public E set(int index, E element) {
        E oldValue = node(index).element;
        node(index).element=element;
        return oldValue;
    }

    @Override
    public void add(int index, E element) {
        rangeCheckForAdd(index);
        if(index==size){
            Node<E> oldLast = last;
            last=new Node<E>(element,oldLast,null);
            if(oldLast==null){
                first=last;
            }else{
                oldLast.next =last;
            }
        }else{
//            Node<E> next = node(index);//index索引位置的Node 当新插入的节点插入之后就相当于下一个节点了！
//            Node<E> prev = next.prev;
//            Node<E> node = new Node<>(element, prev, next);
//            next.prev=node;
//            if(prev==null){
//                first=node;
//            }else{
//                prev.next=node;
//            }

            Node<E> node=node(index);
            Node<E> prev = node.prev;
            Node<E> next = node.next;
            Node<E> eNode = new Node<>(element, prev, next);
            next.prev=eNode;

            if(prev==null){
                first=node;
            }else{
                prev.next=eNode;
            }
        }
        size++;
    }

    @Override
    public E remove(int index) {
        rangeCheck(index);
        Node<E> node = node(index);
        Node<E> prev = node.prev;
        Node<E> next = node.next;

        if(prev==null){
            first=next;
        }else{
            prev.next=next;
        }

        if(next==null){
            last=prev;
        }else{
            next.prev=prev;
        }
        size--;
        return node.element;
    }

    @Override
    public int indexOf(E element) {
        if(element==null){
            Node<E> node=first;
            for (int i = 0; i <size ; i++) {
                if(node.element==null) return i;
                node=node.next;
            }
        }else{
            Node<E> node=first;
            for (int i = 0; i <size; i++) {
                if(element.equals(node.element)) return i;
                node=node.next;
            }
        }
        return ELEMENT_NOT_FOUND;
    }

    private Node<E> node(int index){
        rangeCheck(index);
        Node<E> node=first;
        if(index<(size>>1)){
            for (int i = 0; i < index; i++) {
                node=node.next;
            }
            return node;
        }else{
            for (int i = size-1; i >index ; i++) {
                node=node.next;
            }
            return node;
        }
    }

    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("size=").append(size).append(", [");
        Node<E> node = first;
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                string.append(", ");
            }

            string.append(node);

            node = node.next;
        }
        string.append("]");
        return string.toString();
    }
}
