package com.lihai.map1;

import javax.swing.*;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * @author lihai
 * @date 2020/10/6-12:55
 */
public class HashMap<K,V> implements Map<K,V> {

    private static final boolean RED= false;
    private static final boolean BLACK=true;

    private int size;
    String s;

    private Node<K,V> [] table;
    private static final int DEFAULT_CAPACITY=1<<4;
    private static final float DEFAULT_LOAD_FACTOR=0.75f;

    public HashMap(){
        table=new Node[DEFAULT_CAPACITY];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public void clear() {
        if(size==0) return;
        for (int i = 0; i <table.length ; i++) {
            table[i]=null;
        }
    }

    @Override
    public V put(K key, V value) {

        resize();
        int index = index(key);     //获取在数组中的索引位置!
        //取出index位置的红黑树根节点
        Node<K, V> root = table[index];
        if(root==null){
            table[index]=createNode(key,value,null);
            size++;
            fixAfterPut(root);
            return null;
        }

        //添加新节点到红黑树上面
        Node<K,V> parent= root;

        Node<K,V> node=root;

        int cmp=0;
        K k1=key;
        int h1 = hash(key);
        Node<K, V> result = null;
        boolean searched=false;

        while(node!=null){
            parent=node;
            K k2=node.key;
            int h2 = node.hash;

            if(h1>h2){
                cmp=1;
            }else if(h1<h2){
                cmp=-1;
            }else if(Objects.equals(k1,k2)){
                cmp=0;
            }else if(k1!=null && k2!=null
                    && k1 instanceof Comparable
                    && k1.getClass()==k2.getClass()
                    && (cmp = ((Comparable)k1).compareTo(k2))!= 0){
            }else if(searched){
                cmp=System.identityHashCode(k1)-System.identityHashCode(k2);
            }else {
                if((node.left!=null && (result=node(node.left,k1))!=null)
                        ||node.right!=null && (result=node(node.right,k1))!=null){
                    node=result;
                    cmp=0;
                }else{ //不存在这个key
                    searched=true;
                    cmp=System.identityHashCode(k1)-System.identityHashCode(k2);
                }
            }


            if(cmp>0){
                node=node.right;
            }else if(cmp<0){
                node=node.left;
            }else{
                V oldValue=node.value;
                node.key=key;
                node.value=value;
                node.hash=h1;
                return oldValue;
            }
        }

        Node<K, V> newNode = createNode(key, value, parent);
        if(cmp>0){
            parent.right=newNode;
        }else{
            parent.left=newNode;
        }
        size++;
        fixAfterPut(newNode);
        return null;   
    }

    private void resize(){

        if(size/table.length<=DEFAULT_LOAD_FACTOR) return;
        Node<K,V>[] oldTable=table;
        table=new Node[oldTable.length<<1];

        Queue<Node<K,V>> queue=new LinkedList<>();
        for (int i = 0; i <table.length ; i++) {
            if(oldTable[i]==null) continue;
            queue.offer(oldTable[i]);

            while(!queue.isEmpty()){
                Node<K, V> node = queue.poll();

                if(node.left!=null){
                    queue.offer(node.left);
                }

                if(node.right!=null){
                    queue.offer(node.right);
                }

                moveNode(node);
            }
        }
    }

    private void moveNode(Node<K,V> newNode){
        newNode.left=null;
        newNode.right=null;
        newNode.parent=null;
        //将节点颜色恢复成默认的红色！
        newNode.color=RED;
        //计算要移动节点在新数组中的索引！
        int index = index(newNode);
        //找到这个索引位置的红黑树根节点,如果根节点是空的话，就将移过去的节点放在数组中充当根节点！
        Node<K, V> root = table[index];
        if(root==null){
            root=newNode;
            table[index]=root;
            //添加后的处理，维持红黑树的颜色，添加的是根节点，进去会将其染成黑色！
            fixAfterPut(root);
            return;
        }

        Node<K,V> parent=root;
        Node<K,V> node=root;
        int cmp=0;
        K k1 = newNode.key;
        int h1 = newNode.hash;

        while(node!=null){
            parent=node;
            K k2 = node.key;
            int h2 = node.hash;

            if(h1>h2){
                cmp=1;
            }else  if(h1<h2){
                cmp=-1;
            }else if(k1!=null && k2!=null
                    &&k1.getClass()==k2.getClass()
                    &&k1 instanceof  Comparable
                    &&(cmp=((Comparable) k1).compareTo(k2))!=0);
            else{
                cmp=System.identityHashCode(k1)-System.identityHashCode(k2);
            }

            if(cmp>0){
                node=node.right;
            }else{
                node=node.left;
            }
        }

        //将新节点的parent指针域存放parent的地址！
        newNode.parent=parent;
        // 看看插入到父节点的哪个位置
        if(cmp>0){
            node=node.right;
        }else{
            node=node.left;
        }

        fixAfterPut(newNode);
    }


    @Override
    public V get(K key) {
        Node<K, V> node = node(key);
        return node==null ? null : node.value;
    }

    @Override
    public V remove(K key){
        return remove(node(key));
    }

    @Override
    public boolean containsKey(K key) {
        return node(key)!=null;
    }

    @Override
    public boolean containsValue(V value){

        if(size==0) return false;
        Queue<Node<K,V>> queue = new LinkedList<>();
        for (int i = 0; i <table.length ; i++) {
            if(table[i]==null)  continue;
            queue.offer(table[i]);
            while(!queue.isEmpty()) {
                Node<K, V> node = queue.poll();
                if(Objects.equals(value,node.value)) return true;

                if(node.left!=null){
                    queue.offer(node.left);
                }
                if(node.right!=null){
                    queue.offer(node.right);
                }
            }
        }
        return false;
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
        if(size==0||visitor==null) return;

        Queue<Node<K,V>> queue= new LinkedList<>();
        for (int i = 0; i <table.length ; i++) {
            if(table[i]==null) continue;

            queue.offer(table[i]);
            while (!queue.isEmpty()){
                Node<K, V> node = queue.poll();

                if(visitor.visit(node.key,node.value)) return;

                if(node.left!=null){
                    queue.offer(node.left);
                }

                if(node.right!=null){
                    queue.offer(node.right);
                }
            }
        }
    }

    protected Node<K, V> createNode(K key, V value, Node<K, V> parent) {
        return new Node<>(key, value, parent);
    }

    protected V remove(Node<K, V> node){
        if(node==null) return null;

        Node<K,V> willNode=node;
        size--;
        V oldValue = node.value;

        if(node.hasTwoChildren()){
            Node<K, V> s = successor(node);
            node.key=s.key;
            node.value=s.value;
            node.hash=s.hash;
            node=s;
        }

        Node<K, V> replacement = node.left != null ? node.left : node.right;
        int index = index(node);
        if(replacement!=null){//说明要删除的节点是度为1的节点!
            replacement.parent=node.parent;
            if(node.parent==null){  //说明删除的是根节点且度为1
                table[index]=replacement;
            }else if(node==node.parent.left){
                node.parent.left=replacement;
            }else{
                node.parent.right=replacement;
            }
        }
        return oldValue;
    }


    private void fixAfterRemove(Node<K, V> node){
        if(isRed(node)){
            black(node);
            return;
        }

        Node<K, V> parent = node.parent;
        if(parent==null) return;

        boolean left = parent.left == null || node.isLeftChild();
        Node<K, V> sibling = left ? parent.right : parent.left;
        if(left){
            if (isRed(sibling)) { // 兄弟节点是红色
                black(sibling);
                red(parent);
                rotateLeft(parent);
                // 更换兄弟
                sibling = parent.right;
            }

            // 兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // 兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    fixAfterRemove(parent);
                }
            } else { // 兄弟节点至少有1个红色子节点，向兄弟节点借元素
                // 兄弟节点的左边是黑色，兄弟要先旋转
                if (isBlack(sibling.right)) {
                    rotateRight(sibling);
                    sibling = parent.right;
                }

                color(sibling, colorOf(parent));
                black(sibling.right);
                black(parent);
                rotateLeft(parent);
            }

        }else{
            if(isRed(sibling)){
                black(sibling);
                red(parent);
                rotateRight(parent);
                sibling=parent.left;
            }
            //来到这里兄弟节点一定是黑色的！
            if(isBlack(sibling.left) && isBlack(sibling.right)){
                boolean blackParent = isBlack(parent);
                red(sibling);
                black(parent);
                if(blackParent){
                    fixAfterRemove(parent);
                }
            }else{
                if(isBlack(sibling.left)){
                    rotateLeft(sibling);
                    sibling=parent.left;
                }
                color(sibling,colorOf(parent));
                black(sibling.left);
                black(parent);
                rotateRight(parent);

            }
        }
    }


    private Node<K, V> successor(Node<K, V> node){
        if(node==null) return null;
        Node<K, V> p = node.right;
        if(p!=null){
            while(p.left!=null){
                p=p.left;
            }
            return p;
        }

        //没有右子树就往上找
        while(node.parent!=null && node==node.parent.right){
            node=node.parent;
        }

        return node.parent;
    }



    private Node<K, V> node(K key){
        Node<K, V> root = table[index(key)];
        return root==null ? null : node(root,key);
    }

    //递归扫描
    private Node<K, V> node(Node<K, V> node, K k1){
        int h1 = hash(k1);

        Node<K,V> result=null;
        int cmp=0;

        K k2 = node.key;
        while(node!=null){
            int h2 = node.hash;

            if(h1>h2){
                node=node.right;
            }else if(h1<h2){
                node=node.left;
            }else if(Objects.equals(k1,k2)){
                return node;
            }else if(k1!=null && k2!=null
                    &&k1 instanceof Comparable
                    &&k1.getClass()==k2.getClass()
                    &&(cmp = ((Comparable)k1).compareTo(k2)) != 0){
                node= cmp>0 ? node.right : node.left;
            }else if(node.right!=null && (result=node(node.right,k1))!=null){
                return result;
            }else{
                node=node.left;
            }
        }
        return null;
    }

    private int index(K key){
        return hash(key) & (table.length-1);
    }

    private int hash(K key){
        if(key==null) return 0;
        int hash=key.hashCode();
        return hash ^ (hash>>>16);  //哈希值要经过扰动之后
    }

    private int index(Node<K,V> node){
      return  node.hash & (table.length-1);
    }

    private void fixAfterPut(Node<K,V> node){
        Node<K, V> parent = node.parent;

        if(parent==null){
            black(node);
            return;
        }
        // 如果父节点是黑色，直接返回
        if(isBlack(parent)) return;

        Node<K, V> grand = red(parent.parent);
        Node<K, V> uncle = parent.sibling();

        if(isRed(uncle)){
            black(parent);
            black(uncle);
            fixAfterPut(grand);
            return;
        }

        if(parent.isLeftChild()){
            if(node.isLeftChild()){
                black(parent);
            }else{
                black(node);
                rotateLeft(parent);
            }
            rotateRight(grand);
        }else{
            if(node.isLeftChild()){
                black(node);
                rotateRight(parent);
            }else{
                black(parent);
            }
            rotateLeft(grand);
        }
    }

    private void rotateLeft(Node<K, V> grand){
        Node<K, V> parent = grand.right;
        Node<K, V> child = parent.left;
        grand.right=child;
        parent.left=grand;
        afterRotate(grand,parent,child);
    }

    private void rotateRight(Node<K,V> grand){
        Node<K, V> parent = grand.left;
        Node<K, V> child = parent.right;
        grand.left=child;
        parent.right=grand;
        afterRotate(grand,parent,child);
    }

    private void afterRotate(Node<K, V> grand, Node<K, V> parent, Node<K, V> child){
        parent.parent=grand.parent;

        if(grand.isLeftChild()){
            grand.parent.left=parent;
        }else if(grand.isRightChild()){
            grand.parent.right=parent;
        }else{
            table[index(grand)]=parent;
        }
    }

    private Node<K, V> color(Node<K, V> node, boolean color) {
        if (node == null) return node;
        node.color = color;
        return node;
    }

    private Node<K, V> red(Node<K, V> node) {
        return color(node, RED);
    }

    private Node<K, V> black(Node<K, V> node) {
        return color(node, BLACK);
    }

    private boolean colorOf(Node<K, V> node) {
        return node == null ? BLACK : node.color;
    }

    private boolean isBlack(Node<K, V> node) {
        return colorOf(node) == BLACK;
    }

    private boolean isRed(Node<K, V> node) {
        return colorOf(node) == RED;
    }



    private static final class Node<K,V>{
        int hash;
        K key;
        V value;
        boolean color=RED;
        Node<K,V> left;
        Node<K,V> right;
        Node<K,V> parent;

        public Node(K key, V value, Node<K, V> parent) {
            int hash = key==null ? 0 : key.hashCode();
            this.hash = hash ^ (hash >>> 16);

            this.key = key;
            this.value = value;
            this.parent = parent;
        }


        public boolean hasTwoChildren() {
            return left != null && right != null;
        }

        public boolean isLeftChild() {
            return parent != null && this == parent.left;
        }

        public boolean isRightChild() {
            return parent != null && this == parent.right;
        }

        public Node<K, V> sibling() {
            if (isLeftChild()) {
                return parent.right;
            }

            if (isRightChild()) {
                return parent.left;
            }

            return null;
        }
    }
}
