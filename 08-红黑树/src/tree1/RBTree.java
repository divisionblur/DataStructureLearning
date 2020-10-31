package tree1;

import java.util.Comparator;

/**
 * @author lihai
 * @date 2020/10/1-12:27
 */
public class RBTree<E> extends BBST<E>{
    private static final boolean RED=false;
    private static final boolean BLACK=true;

    public RBTree() {
        this(null);
    }

    public RBTree(Comparator comparator){
        super(comparator);
    }

    @Override
    protected void afterAdd(Node<E> node) {
        Node<E> parent = node.parent;

        if(parent==null){
            black(node);
            return;
        }

        if(isBlack(parent)) return;
        Node<E> uncle = parent.sibling();

        Node<E> grand = red(parent.parent);


        if(isRed(uncle)){
            black(parent);
            black(uncle);
            afterAdd(grand);
            return;
        }else{
            if(parent.isLeftChild()){
                if(node.isLeftChild()){
                    black(parent);
                }else{
                    black(node);
                    rotateLeft(parent);
                }

                rotateRight(grand);

            }else{//parent.isRightChild()
                if(node.isLeftChild()){
                    black(node);
                    rotateRight(parent);

                }else{
                    black(parent);
                }
                rotateLeft(grand);
            }
        }
    }

    @Override
    protected void afterRemove(Node<E> node) {

        //当删除的节点是红色节点时，或者删除的节点是度为1的黑节点且替代它的节点是红色节点，完成删除之后，只需要将红色染成黑色即可
        //这两种情况代码和在一起写，(只是当删除的节点是红色时多染了一次色而已!)此时被删除的节点与父节点的"线"已经断开，所以没有关系！
        if(isRed(node)){
            black(node);
            return;
        }

        Node<E> parent = node.parent;

        //当被删除的节点是头节点时,理所应当不需要做任何处理,此时红黑树中已经没有了节点!
        if(parent==null) return;

        //特别提醒,这是在节点已经删除之后对节点的颜色进行控制，使其满足红黑树的五条性质!
        //所以说此时节点node已经被删除不能通过node.sibling()方法找到其兄弟节点,因为parent指向node节点的"线"已经断了!
        //可以通过判断parent.left是不是null来推出删除的节点是左节点还是右节点，若删除的是左节点,则被删除节点的兄弟节点就是右节点
        boolean left = parent.left == null || node.isLeftChild();
        Node<E> sibling = left ? parent.right : parent.left;

        if(left){
            if(isRed(sibling)){
                red(parent);
                black(sibling);
                rotateLeft(parent);
                sibling=parent.right;
            }

            if(isBlack(sibling.left) && isBlack(sibling.right)){
                boolean parentIsBlack = isBlack(parent);
                red(sibling);
                black(parent);
                if(parentIsBlack){
                    afterRemove(parent);
                }
            }else{// 兄弟节点至少有1个红色子节点，向兄弟节点借元素
                if(isBlack(sibling.right)){//兄弟节点右边是空的，说明左边是红色子节点，要多旋转一次
                    rotateLeft(sibling);
                    sibling=parent.right;  //旋转之后注意更新兄弟节点
                }

                color(sibling,colorOf(parent));
                black(sibling.right);
                black(parent);
                rotateLeft(parent);
            }
        }else{
            //被删除的节点是右节点
            if(isRed(sibling)){//如果兄弟节点是红色的就通过旋转将兄弟节点变为黑色的，与下面兄弟节点是黑色到的进行统一处理！
                red(parent);    //将父节点染成红色，兄弟结点染成黑色
                black(sibling);
                rotateRight(parent); //父节点右旋
                sibling=parent.left;  //旋转之后兄弟结点已经发生改变，所以要将兄弟节点更新为parent.left这个黑节点！
            }
            //来到这里说明兄弟节点本来就是黑色的，或者已经修改为黑色的了！
            if(isBlack(sibling.left) && isBlack(sibling.right)){
                //进来的话说明就说明兄弟节点没有任何子节点(因为空节点颜色设置为黑色的)

                //此时还要判断父节点是否是黑色的，如果是黑色的下来合并之后又会造成下溢
                boolean parentIsBlack = isBlack(parent);
                red(sibling);
                black(parent);//不管父节点是红是黑都将它染成黑色!
                if(parentIsBlack){
                    afterRemove(parent);//将父节点当成被删除的节点处理!
                }

            }else{// 兄弟节点至少有1个红色子节点(左边一个or右边一个or左右各一个)，向兄弟节点借元素
                if(isBlack(sibling.left)){//兄弟节点的左边是黑色的话,说明兄弟节点左边是空的！
                    rotateLeft(sibling);  //兄弟节点左边是空的话，红色子节点在右边，那么就会多进行一次旋转！
                    sibling=parent.left;  //旋转之后更新兄弟节点!
                }
                color(sibling,colorOf(parent));//不管兄弟节点是什么颜色,将其染成父节点的颜色!
                black(sibling.left);
                black(parent);
                rotateRight(parent);
            }
        }
    }

    // 染色的方法(将传入的节点染色之后再返回!)
    private Node<E> color(Node<E> node,boolean color){
        if(node==null) return null;
        ((RBNode)node).color=color;
        return node;
    }


    private Node<E> red(Node<E> node){
        return color(node,RED);
    }

    private Node<E> black(Node<E> node){
        return color(node,BLACK);
    }

    private boolean colorOf(Node<E> node){
        return node==null ? BLACK : ((RBNode)node).color;
    }

    private boolean isBlack(Node<E> node){
        return colorOf(node)==BLACK;
    }

    private boolean isRed(Node<E> node){
        return colorOf(node)==RED;
    }


    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new RBNode<>(element,parent);
    }


    private static class RBNode<E> extends Node<E>{
        boolean color=RED;//多了一个颜色属性，而AVL是多了一个高度属性！
        public RBNode(E element, Node<E> parent) {
            super(element, parent);
        }

        @Override
        public String toString(){
            String str = "";
            if (color == RED) {
                str = "R_";
            }
            return str + element.toString();
        }
    }

}
