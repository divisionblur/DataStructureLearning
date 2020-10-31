/**单向循环列表
 * @author lihai
 * @date 2020/9/19-13:16
 */
public class SingleCircleLinkedList<E> extends AbstractList<E> {

    private Node<E> first;
    private static class Node<E>{
        E element;
        Node<E> next;

        public Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(element).append("_").append(next.element);
            return sb.toString();
        }
    }

    @Override
    public void clear() {
        first=null;
        size=0;
    }

    @Override
    public E get(int index) {
        return node(index).element;
    }

    @Override
    public E set(int index, E element) {
        Node<E> node = node(index);
        E oldValue = node.element;
        node.element=element;
        return oldValue;
    }

    @Override
    public void add(int index, E element) {

      rangeCheckForAdd(index);

      if(index==0){//当向循环链表中的第一个位置添加元素时要考虑最后的节点的next域的指向(肯定要改变来指向新插入的节点)
          Node<E> newFirst = new Node<>(element, first);
          Node<E> last = (size == 0) ? newFirst : node(size - 1);
          last.next=newFirst;
          first=newFirst;
      }else{
          Node<E> prevNode = node(index - 1);
          prevNode.next=new Node<E>(element,prevNode.next);
      }
      size++;

      //自己写的
      if(index==size){
          if(size==0){
              Node<E> node = new Node<E>(element, null);
              first=node;
              node.next=node;
          }else{
              Node<E> oldLast = node(size - 1);
              oldLast.next=new Node<E>(element,oldLast.next);
          }

      }else{
          if(index==0){
              Node<E> last = node(size - 1);
              Node<E> node = new Node<>(element, first);
              first=node;
              last.next=node;

          }else{
              Node<E> prev = node(index - 1);
              prev.next=new Node<E>(element,prev.next);
          }
      }
    }


    @Override
    public E remove(int index) {
        rangeCheck(index);
        Node<E> node=first;
        if(index==0){
           if(size==1){//单向循环链表和双向循环链表删除操作时当链表中只有一个元素时要单独处理！
               first=null;
           }else{
               Node<E> last = node(size - 1);//拿到最后一个节点！
               first=first.next;
               last.next=first;
           }
        }else{
            Node<E> prev = node(index - 1);
            node= prev.next;
            prev.next=node.next;
        }
        size--;
        return node.element;

    }

    @Override
    public int indexOf(E element) {

        if(element==null){
            Node<E> node=first;
            for (int i = 0; i < size; i++) {
                if(node.element==null) return i;
            }
        }else{
            Node<E> node=first;
            for (int i = 0; i < size; i++) {
                if(element.equals(node.element)) return i;
            }
        }

        return ELEMENT_NOT_FOUND;
    }


    private Node<E> node(int index){

        rangeCheck(index);
        Node<E> node=first;
        for (int i = 0; i <index ; i++) {
            node=node.next;
        }
        return node;
    }
}
