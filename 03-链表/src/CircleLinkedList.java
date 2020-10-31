/**
 * @author lihai
 * @date 2020/9/19-14:56
 */
public class CircleLinkedList<E> extends AbstractList<E>{

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

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            if (prev != null) {
                sb.append(prev.element);
            } else {
                sb.append("null");
            }

            sb.append("_").append(element).append("_");

            if (next != null) {
                sb.append(next.element);
            } else {
                sb.append("null");
            }
            return sb.toString();
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
        return node(index).element;
    }



    @Override
    public E set(int index, E element) {
        rangeCheck(index);
        Node<E> Node = node(index);
        E oldValue = Node.element;
        Node.element=element;
        return oldValue;
    }

    @Override
    public void add(int index, E element) {
        rangeCheckForAdd(index);
        if(index==size){
            Node<E> oldLast=last;
            last=new Node<E>(element,oldLast,first);
            if(oldLast==null){//向双向链表中添加第一个元素！
                first=last;
                first.prev=first;
                first.next=first;
            }else{
                oldLast.next=last;
                first.prev=last;
            }
        }else{
            Node<E> next = node(index);
            Node<E> prev =next.prev;
            Node<E> node = new Node<>(element, prev, next);
            prev.next=node;
            next.prev=node;

            if(next==first){//在双向链表中的第一个节点插入
                first=node;
            }
        }
        size++;
    }
    @Override
    public E remove(int index) {
        rangeCheck(index);
        Node<E> node=first;
        if(size==1){
           first=null;
           last=null;
        }else{
            node = node(index);
            Node<E> prevNode = node.prev;
            Node<E> nextNode= node.next;
            prevNode.next=nextNode;
            nextNode.prev=prevNode;

            if(index==0){
                first=nextNode;
            }

            if(index==size-1){
                last=prevNode;
            }
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

    @Override
    public String toString() {
        return "CircleLinkedList{" +
                "first=" + first +
                ", last=" + last +
                '}';
    }

    private Node<E> node(int index) {
        rangeCheck(index);
        if(index<(size>>1)){
            Node<E> node=first;
            for (int i = 0; i <index ; i++) {
                node=node.next;
            }
            return node;
        }else{
            Node<E> node=first;
            for (int i =size-1; i >index ; i++) {
                node=node.next;
            }

            return node;
        }
    }
}
