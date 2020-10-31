package tree1;

import tree.BinaryTree;

import java.util.Comparator;

/**
 * @author lihai
 * @date 2020/9/29-12:54
 */
public class AVLTree<E> extends BST<E>{
    public AVLTree(){
        this(null);
    }

    public AVLTree(Comparator comparator){//AVLTree继承了父类的属性，用父类的构造方法来初始化comparator属性
        super(comparator);
    }

    @Override
    protected void afterAdd(Node<E> node) {  //将新添加的节点传入进来！
        // 一层一层往上找并且更新节点的高度,如果找到第一个不平衡的节点，恢复这个节点那么整棵树就恢复了平衡！
        while(( node=node.parent)!=null){

            if(isBalanced(node)){//判断节点的平衡因子的绝对值是不是小于等于1的！

                updateHeight(node);  //是平衡的就更新高度！
            }else{
                rebalance(node);    //恢复平衡！
                break;
            }
        }
    }

    @Override
    protected void afterRemove(Node<E> node) {//删除节点可能会使父节点或者祖先节点中的一个失去平衡(只会有一个失去平衡)
        while ((node=node.parent)!=null){//将失去平衡的树恢复平衡之后，有可能会导致父节点或者祖先节点所在的树失去平衡
                                        //所以需要一直往上找直到找到整棵树的根节点为止！

            if(isBalanced(node)){

                updateHeight(node);
            }else {
                rebalance(node);   //不要break 要确定所有的祖先节点都没有失去平衡！
            }
        }
    }

    private void rebalance(Node<E> grand) {
        Node<E> parent = ((AVLNode<E>) grand).tallerChild();//失去平衡的节点的左右子树中最高的为parent
        Node<E> node = ((AVLNode<E>) parent).tallerChild();//parent的左右子树最高的为node
        if(parent.isLeftChild()){//L
            if(node.isLeftChild()){//LL
                rotateRight(grand);
            }else{//LR
                rotateLeft(parent);
                rotateRight(grand);
            }
        }else {
            if(node.isLeftChild()){
                rotateRight(parent);
                rotateLeft(grand);
            }else{
                rotateLeft(grand);
            }
        }
    }


    //在左旋和右旋当中grand是失去平衡的那个节点,当进行旋转的时候会将parent的左子树或者右子树变为grand的右子树或者左子树！
    //将这个需要改变其父节点的树 叫为 child 节点！
    private void rotateRight(Node<E> grand) {
        Node<E> parent = grand.left;
        Node<E> child = parent.right;
        grand.left=child;
        parent.right=grand;
        afterRotate(grand,parent,child);

    }
    private void rotateLeft(Node<E> grand) {
        Node<E> parent = grand.right;
        Node<E> child= parent.left;
        grand.right=child;
        parent.left=grand;
        afterRotate(grand,parent,child);
    }

    //在进行完旋转之后需要更新一下grand,parent,child这几个节点的parent！
    public void afterRotate(Node<E> grand,Node<E> parent,Node<E> child){
        parent.parent=grand.parent;
        if(grand.isLeftChild()){
            grand.parent.left=parent;
        }else if(grand.isRightChild()){
            grand.parent.right=parent;
        }else{ //grand 是root节点
            root=parent;
        }

        // 更新child的parent
        if(child!=null){
            child.parent=grand;
        }
        grand.parent=parent;
        //他俩改变了左右子树，故需要更新一下高度！！！！
        updateHeight(grand);
        updateHeight(parent);
    }



    //调用了每个节点内部的计算平衡因子的方法，主要是看平衡因子的绝对值是否是小于1的来进行判断！
    private boolean isBalanced(Node<E> node){
        return Math.abs(((AVLNode)node).balanceFactor())<=1;
    }

    private void updateHeight(Node<E> node) {
        ((AVLTree.AVLNode<E>)node).updateHeight();//将转换放在这里！
    }


    //因为AVLTree继承了BST而BST又继承了BinaryTree(二叉树)  BinaryTree里有个创建节点的方法，默认是创建BST中的Node节点
    //而AVLTree中的 AVLNode节点比Node节点多了一个height属性  故在AVLTree中重写createNode方法里面是创建AVLNode。
    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new AVLNode<>(element,parent);
    }



    private static class AVLNode<E> extends Node<E>{
        private int height=1;

        public AVLNode(E element, Node<E> parent){
            super(element, parent);
        }

        //节点内求平衡因子的方法
        public int balanceFactor(){
            int leftHeight= left==null ? 0 : ((AVLNode<E>)left).height;
            int rightHeight= right==null ? 0 : ((AVLNode<E>)right).height;
            return leftHeight-rightHeight;
        }
        //更新本节点的高度
        public void updateHeight(){
            int leftHeight=left==null ? 0 :((AVLNode<E>)left).height;
            int rightHeight=right==null ? 0: ((AVLNode<E>)right).height;
            height=1+Math.max(leftHeight,rightHeight);
        }

        public Node<E> tallerChild(){
            int leftHeight= left==null ? 0 :((AVLNode)left).height;
            int rightHeight= right==null ? 0 :((AVLNode)right).height;
            if(leftHeight>rightHeight) return left;
            if(leftHeight<rightHeight) return right;
            return isLeftChild() ? left :right;//若两棵子树高度一样的话，就选和本节点同方向的当最高子树！！
        }

        @Override
        public String toString() {
            String parentString = "null";
            if (parent != null) {
                parentString = parent.element.toString();
            }
            return element + "_p(" + parentString + ")_h(" + height + ")";
        }
    }

}
