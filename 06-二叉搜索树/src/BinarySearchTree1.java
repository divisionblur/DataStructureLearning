import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author lihai
 * @date 2020/9/28-11:22
 */
public class BinarySearchTree1<E> {
    private int size;
    private Comparator comparator;
    private Node<E> root;


    public BinarySearchTree1() {//不适用自定义比较器的构造方法，可能是使用放在二叉树中对象所在类实现Comparable重写的compareTo方法
        this(null);
    }

    public BinarySearchTree1(Comparator comparator) {
        this.comparator = comparator;
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size==0;
    }

    public void clear(){
        root=null;
        size=0;
    }

    public void add(E element){
        elementNotNullCheck(element);
        if(root==null){
            root=new Node<E>(element,null);//相当于节点互指中只有一根线
            size++;
            return;
        }
        Node<E> parent=root;
        Node<E> node=root;
        int cmp=0;
        while(node!=null){
             cmp = compare(element, node.element);
             parent=node;
             if(cmp>0){
                 node=node.right;
             }else if(cmp<0){
                 node=node.left;
             }else{
                 node.element=element;
                 return;
             }
        }
        Node<E> newNode=new Node<>(element,parent);
        if(cmp>0){
            parent.right=newNode;
        }else{
            parent.left=newNode;
        }
        size++;
    }

    public void remove(E element){
        remove(node(element));
    }

    private void remove(Node<E> node){

        if(node.hasTwoChildren()){
            Node<E> s = successor(node);
            node.element=s.element;
            node=s;
        }

        Node<E> replacement = node.left != null ? node.left : node.right;

        if(replacement!=null){
            if(node.parent==null){
                root=replacement;
            }
            replacement.parent=node.parent;
            if(node == node.parent.left){
                node.parent.left=replacement;
            }else{
                node.parent.right=replacement;
            }
        }else if(node.parent==null){
            root=null;
        }else{
            if(node==node.parent.left){
                node.parent.left=null;
            }else{
                node.parent.right=null;
            }
        }

        size--;
    }



    private Node<E> node(E element){
        Node<E> node=root;
        int cmp=0;
        while(node!=null){
            cmp=compare(element,node.element);

            if(cmp>0){
                node=node.right;
            }else if(cmp<0){
                node=node.right;
            }else{
                return node;
            }
        }
        return null;
    }


    private Node<E> predecessor(Node<E> node){
        if(node==null) return null;

        Node<E> p=node.left;
        if(p!=null){//说明这个节点有右子树！
            while(p.right!=null){
                p=p.right;
            }
            return p;
        }
        //能够直接来到这里说明这个节点没有左子树，那就需要往上面找，一直找到node.parent==null或者node!=node.parent.left
        //如果发现在某个节点的右子树上 那么这个节点就是前驱节点！！！因为一个节点可定小于它的右子树的所有节点(找前驱)
        while(node.parent!=null && node==node.parent.left){
            node=node.parent;
        }
        return node.parent;
    }


    private Node<E> successor(Node<E> node){
        if(node==null) return null;

        Node<E> p=node.right;
        if(p!=null){//说明这个节点有右子树！
            while(p.left!=null){
                p=p.left;
            }
            return p;
        }

        //来到这里说明节点没有右子树
        while(node.parent!=null && node==node.parent.right){
            node=node.parent;
        }
        return node.parent;
    }






    public void preOrder(Visitor visitor){
        if(visitor==null) return;
        preOrder(root,visitor);
    }
    public void preOrder(Node<E> node,Visitor visitor){
        if(node==null||visitor.stop) return;

        visitor.stop=visitor.visit(node.element);
        preOrder(node.left,visitor);
        preOrder(node.right,visitor);
    }







    public void inOrder(Visitor visitor){
        if(visitor==null) return;
        inOrder(root,visitor);
    }

    public void inOrder(Node<E> node,Visitor visitor){
        if(node==null||visitor.stop) return;

        inOrder(node.left,visitor);
        if(visitor.stop) return;
        visitor.stop=visitor.visit(node.element);
        inOrder(node.right,visitor);
    }



    public void postOrder(Visitor visitor){
        if(visitor==null) return;
        postOrder(root,visitor);
    }
    public void postOrder(Node<E> node,Visitor visitor){
        if(node==null||visitor.stop) return;
        postOrder(node.left,visitor);
        postOrder(node.right,visitor);
        if(visitor.stop) return;
        visitor.stop=visitor.visit(node.element);
    }


    public void levelOrder(Visitor visitor){
        if(root==null||visitor==null) return;
        Queue<Node<E>> queue=new LinkedList<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            Node<E> node = queue.poll();//出队列一个访问一个！
            if(visitor.visit(node.element)) return;

            if(node.left!=null){
                queue.offer(node.left);
            }
            if(node.right!=null){
                queue.offer(node.right);
            }
        }
    }


    public boolean isComplete(){
        if(root==null) return false;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        boolean leaf=false;
        while(!queue.isEmpty()){
            Node<E> node = queue.poll();
            if(leaf && !node.ifLeaf()) return false;
            if(node.left!=null){
                queue.offer(node.left);
            }else{
                if(node.right!=null) return false;
            }
            if(node.right!=null){
                queue.offer(node.right);
            }else{
                leaf=true;
            }
        }
        return true;
    }


    public int height(){
        if(root==null) return 0;
        Queue<Node<E>> queue=new LinkedList<>();
        queue.offer(root);
        //树的高度！
        int height=0;
        //存储着每一层的元素数量！
        int levelSize=1;
        while (!queue.isEmpty()){
            Node<E> node = queue.poll();
            levelSize--;
            if(node.left!=null){
                queue.offer(node.left);
            }
            if(node.right!=null){
                queue.offer(node.right);
            }
            if(levelSize==0){
                levelSize=queue.size();
                height++;
            }
        }
        return height;
    }


    public int height2(){
       return height(root);
    }
    private int height(Node<E> node){
        if(node==null) return  0;
        return Math.max(height(node.left),height(node.right));
    }


    private int compare(E e1,E e2){
        if(comparator!=null){
            return comparator.compare(e1, e2);
        }else{
            return ((Comparable<E>)e1).compareTo(e2);
        }
    }

    private void elementNotNullCheck(E element){
        if(element==null){
            throw new IllegalArgumentException("element must be not null");
        }
    }



    public static abstract class Visitor<E>{
        boolean stop;

        public abstract boolean visit(E element);
    }


    private static class Node<E>{
        E element;
        Node<E> parent;
        Node<E> left;
        Node<E> right;

        public Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }

        public boolean hasTwoChildren(){
            return left!=null && right!=null;
        }

        public boolean ifLeaf(){
            return left==null&& right==null;
        }
    }
}
