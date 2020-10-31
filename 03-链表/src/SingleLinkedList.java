/**
 * @author lihai
 * @date 2020/9/18-20:47
 */
public class SingleLinkedList<E> extends AbstractList<E> {

    private Node<E> first;

    private static class Node<E>{
         E element;
         Node<E> next;

        public Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }
    }

    @Override
    public void clear() {
        size=0;
        first=null;
    }

    @Override
    public E get(int index) {
        rangeCheck(index);
       return node(index).element;//通过node方法获得index位置的元素，再获取值 并返回！
    }

    @Override
    public E set(int index, E element) {
        rangeCheck(index);
        E oldValue = node(index).element;
        node(index).element=element;
        return oldValue;
    }



    @Override
    public void add(int index, E element) {
        rangeCheckForAdd(index);//添加元素时可以包括size位置，就相当于在最后面添加元素！
        if(index==0){
            first=new Node(element,first);
        }else{
            Node<E> prevNode = node(index - 1);
            prevNode.next=new Node(element,prevNode.next);
        }
        size++;
    }




    @Override
    public E remove(int index) {
        rangeCheck(index);//index范围是（0------size-1）
        Node<E> node=first;
        if(index==0){
            first=first.next;
        }else{
            Node<E> prevNode = node(index - 1);
            node=prevNode.next;
            prevNode.next=node.next;
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


    /**
     * 获取index位置对应的节点对象
     * @param index
     * @return
     */
    private Node<E> node(int index){
        rangeCheck(index);
        Node<E> node=first;
        for (int i = 0; i <index ; i++) {
            node=node.next;
        }
        return node;
    }
}
