import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import sun.reflect.generics.visitor.Visitor;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author lihai
 * @date 2020/9/24
 */
public class BinarySearchTree<E> {
    private int size;
    private Comparator comparator;
    private Node<E> root;

    public BinarySearchTree() {
        this(null);
    }

    public BinarySearchTree(Comparator comparator) { //建二叉搜索树时传入一个比较器！
        this.comparator = comparator;
    }



    public int size(){
        return size;
    }

    public boolean idEmpty(){
        return size==0;
    }


    public void clear(){
        root=null;
        size=0;
    }

//    public void add(E element){
//        elementNotNullCheck(element);
//        //向树中插入第一个节点！
//        if(root==null){
//            root = new Node<>(element, null);
//            size++;
//            return;
//        }
//        // 添加的不是第一个节点
//        Node<E> parent=root;
//        Node<E> node=root;
//        int cmp=0;
//        while(node!=null){
//            cmp=compare(element,node.element);
//            parent=node;//将父节点保存！
//            if(cmp>0){
//                node=node.right;
//            }else if(cmp<0){
//                node=node.left;
//            }else{
//                node.element=element;
//                return;
//            }
//        }
//
//        Node<E> newNode=new Node(element,parent);
//        if(cmp>0){
//           parent.right=newNode;
//        }else{
//            parent.left=newNode;
//        }
//        size++;
//    }
    public void add(E element){
        elementNotNullCheck(element);
        if(root==null){
            root=new Node<E>(element,null);
            size++;
            return;
        }
        Node<E> parent=root;
        Node<E> node=root;
        int cmp=0;
        while(node!=null){
            cmp= compare(element, node.element);
            parent=node;
            if(cmp>0){
                node=node.right;
            }else if(cmp<0){
                node=node.left;
            }else{
                node.element=element;  //将原有节点的值进行覆盖！
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


    public void remove(E element){  //删除值所对应的节点 里面又调用一个remove方法
        remove(node(element));      //node方法通过值找到对应的节点！
    }



    public void remove(Node<E> node){
        if(node==null) return;

        if(node.hasTwoChildren()){//删除度为2的节点！度为2的节点的前驱或者是后继节点度一定为0或1，
                                    // 将前驱或者后继节点中的值去替换要删除节点的值。最后只需要删除前驱或者后继就行了
                                    // 也就转换成了删除度为0或者1的节点！
            Node<E> sNode = successor(node);

            node.element=sNode.element;
            node=sNode;
        }

        //若直接来到这里说明要删除的节点的度为1或者是0;
        Node<E> replacement = node.left != null ? node.left : node.right;
        if(replacement!=null) {   //说明是删除度为1的节点！
            replacement.parent=node.parent;
            if(node.parent==null){//说明要删除的节点是根节点且度为1,那么就将指向根节点的指针指向replacement,replacement就变为了根节点！
                root=replacement;
            }
            if(node==node.parent.left){//不是看replacement在要删除节点的哪一边而是看要删除的节点在它的父节点的哪一边！
                node.parent.left=replacement;
            }else{
                node.parent.right=replacement;
            }
        }else if(node.parent==null){//从这里往后就说明是要删除度为0的节点即叶子节点！
            root=null;              //要删除的节点是根节点
        }else{//// node是叶子节点，但不是根节点  而且还要区分是父节点的左节点还是右节点来讨论!！
            if(node==node.parent.left){
                node.parent.left=null;
            }else{
                node.parent.right=null;
            }
        }
    }

    private Node<E> node(E element) {
        Node<E> node=root;
        int cmp=0;
        while(node!=null){
            cmp=compare(element,node.element);

            if(cmp>0){
                node=node.right;
            }else if(cmp<0){
                node=node.left;
            }else{
                return node;
            }
        }
        return null;
    }

    /**
     * 前序遍历
     */
//    public void preorderTraversal(){
//        preorderTraversal(root);
//    }
//
//    public void preorderTraversal(Node<E> node){
//        if(node==null) return;
//
//        System.out.println(node.element);
//        preorderTraversal(node.left);
//        preorderTraversal(node.right);
//    }
//
//
//    /**
//     * 中序遍历
//     */
//    public void inorderTraversal(){
//        inorderTraversal(root);
//    }
//    public void inorderTraversal(Node<E> node){
//        if(node==null)  return;
//
//        inorderTraversal(node.left);
//        System.out.println(node.element);
//        inorderTraversal(node.right);
//    }
//
//    /**
//     * 后序遍历
//     */
//    public void postorderTraversal(){
//        postorderTraversal(root);
//    }
//    public void postorderTraversal(Node<E> node){
//        if(node==null) return;
//        postorderTraversal(node.left);
//        postorderTraversal(node.right);
//        System.out.println(node.element);
//    }
//
//
//    /**
//     * 层序遍历(非常重要)
//     */
//    public void levelOrderTraversal(){
//
//        if(root==null) return;
//        Queue<Node<E>> queue=new LinkedList<>();
//        queue.offer(root);
//        while(!queue.isEmpty()){
//            Node<E> node = queue.poll();
//            System.out.println(node.element);
//
//            if(node.left!=null){
//                queue.offer(node.left);
//            }
//
//            if(node.right!=null){
//                queue.offer(node.right);
//            }
//        }
//    }

    public void levelOrderTraversal(){
        if(root==null) return;
        Queue<Node<E>> queue=new LinkedList<>();
        queue.offer(root);

        while(!queue.isEmpty()){
            Node<E> node = queue.poll();

            System.out.println(node.element);
            if(node.left!=null){
                queue.offer(node.left);
            }

            if(node.right!=null){
                queue.offer(node.right);
            }
        }
    }

    /**
     * 前序遍历
     * @param visitor
     */
    public void preOrder(Visitor<E> visitor){ //传入具体的遍历节点的方式，就不一定是输出了 用户可以自己定义！
        if(visitor==null) return;
        preOrder(root,visitor);
    }

    public void preOrder(Node<E> node, Visitor<E> visitor){
        if(node==null||visitor.stop) return;

        visitor.stop=visitor.visit(node.element);
        preOrder(node.left,visitor);
        preOrder(node.right,visitor);
    }

    /**
     * 中序遍历
     * @param visitor
     */
    public void inOrder(Visitor<E> visitor){
        if(visitor==null) return;
        inOrder(root,visitor);
    }

    /**
     *     bst.preorder(new Visitor<Integer>() {
     * 			public boolean visit(Integer element) {
     * 				System.out.print(element + " ");
     * 				return element == 2 ? true : false;
     *                        }* 		});
     * @param node
     * @param visitor
     */
    public void inOrder(Node<E> node,Visitor<E> visitor){
        if(node==null||visitor.stop) return;

        inOrder(node.left,visitor);
        if(visitor.stop) return;
        visitor.stop=visitor.visit(node.element);
        inOrder(node.right,visitor);
    }

    /**
     * 后序遍历
     * @param visitor
     */

    public void postOrder(Visitor visitor){
        if(visitor==null) return;
        postOrder(root,visitor);
    }
    public void postOrder(Node<E> node,Visitor<E> visitor){
        if(node==null||visitor.stop) return;
        postOrder(node.left,visitor);
        postOrder(node.right,visitor);
        if(visitor.stop) return;
        visitor.stop=visitor.visit(node.element);
    }



    /**
     * 自定义了访问方式的层序遍历！
     * @param visitor
     */
    public void levelOrder(Visitor<E> visitor) {
        if(root == null || visitor == null) return;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            Node<E> node = queue.poll();

            if(visitor.visit(node.element)) return;

            if(node.left != null){
                queue.offer(node.left);
            }
            if(node.right != null){
                queue.offer(node.right);
            }
        }
    }

    /**
     * 用递归方式求一棵树的高度！
     * @param
     * @return
     */
    public int height2(){
         return height(root);
    }
    private int height(Node<E> node){
        if(node==null) return 0;

        return  1+Math.max(height(node.left),height(node.right));
    }

    /**
     * 用层序遍历的方式求树的高度！
     * @return
     */
    public int height(){
        if(root==null) return 0;

        int height=0;
        int levelSize=1;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);

        while(!queue.isEmpty()){
            Node<E> node = queue.poll();
            levelSize--;

            if(node.left!=null){
                queue.offer(node.left);
            }

            if(node.right!=null){
                queue.offer(node.right);
            }
            while(levelSize==0){
                levelSize=queue.size();
                height++;
            }
        }
        return height;
    }


    /**
     * 判断一棵树是否是完全二叉树！
     * @return
     */
    public boolean isComplete() {
        if(root == null) return false;

        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        boolean leaf = false;

        while(!queue.isEmpty()) {
            Node<E> node = queue.poll();
            if(leaf && !node.isLeaf()) return false;

            if(node.left != null) {
                queue.offer(node.left);
            }else if(node.right != null) {//node.left == null && node.right !=null
                return false;
            }

            if(node.right != null) {
                queue.offer(node.right);
            }else{
                //node.left != null && node.right == null
                //node.left == null && node.right == null
                //要求这个节点后面的节点全部是叶子节点!
                leaf = true;
            }
        }
        return true;
    }

    /**
     * 找一个节点的前驱节点！
     * 一个节点的左子树中的所有节点，肯定都比它小，那么就找左子树中最大的那个节点，就是它的前驱节点。
     * @param node
     * @return
     */
    private Node<E> predecessor(Node<E> node) {
        if (node == null) return null;
        Node<E> p = node.left;
        while (p != null) {
            if (p.right != null) {
                p = p.right;
            }
            return p;
        }
        //来到这里说明没有左子树,前驱节点只能到"祖先节点"去找。
        while (node.parent != null && node == node.parent.left) {
            node = node.parent;
        }

        return node.parent;
    }

    /**
     * 找一个节点的后继节点！
     * 一个节点的右子树中的所有节点，肯定都比它大，那么就找右子树中最小的那个节点，就是它的后继节点！！！
     * 来到右子树一直往左边找!
     * @param node
     * @return
     */
    private Node<E> successor(Node<E> node){
        if(node == null) return null;
        Node<E> p = node.right;
        while(p != null){
            if(p.left != null){
                p = p.left;
            }
            return p;
        }
        //来到这里说明没有右子树,后继节点只能到"祖先节点"去找。
        while(node.parent != null && node == node.parent.right){
            node = node.parent;
        }
        return node.parent;
    }



    public int compare(E e1,E e2){
        if(comparator!=null){//查看是否传入了比较器，如果有就调用比较器来比较！
            return comparator.compare(e1, e2);
        }else{
            return ((Comparable<E>)e1).compareTo(e2);//没有比较器就调用实现Comparable自己写的比较规则
        }
    }

    public void elementNotNullCheck(E element){
        if(element==null){
            throw new IllegalArgumentException("element must not be null ! ");
        }
    }





    public static abstract class Visitor<E>{ //抽象类，子类若不是抽象类必须要重写抽象方法
        boolean stop;  //标记位
        public abstract boolean visit(E element);
    }


    private static class Node<E> {
        E element;
        Node<E> parent;
        Node<E> left;
        Node<E> right;

        public Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }

        public boolean isLeaf(){
            return left == null && right == null;
        }

        public boolean hasTwoChildren(){
            return left != null && right != null;
        }
    }
}
