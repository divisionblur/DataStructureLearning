import java.util.HashMap;

/**
 * @author lihai
 * @date 2020/10/9-10:45
 */
public class Trie<V>{
    private int size;
    private Node<V> root;

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

    public V get(String key){
        Node<V> node = node(key);
        return node!=null && node.word ? node.value : null; //如果node()返回null说明没有这个字符串，而且这个字符串还要存在
        //于trie才行，就是说字符串的最后一个字符要打标记 即word==true 才能说明是一个完整的字符串，如果没有这个字符串
        //虽然所有字符都有 但是有可能是别的字符串的一部分！
    }

    public boolean contains(String key){
        Node<V> node = node(key);
        return node!=null && node.word;
    }

    public V add(String key,V value){
        keyCheck(key);

        if(root==null){
            root=new Node<>(null);
        }

        Node<V> node=root;
        int len = key.length();
        for (int i = 0; i <len ; i++) {
            char c = key.charAt(i);
            boolean emptyChildren = node.children == null;
            Node<V> childNode = emptyChildren ? null : node.children.get(c);

            if(childNode==null){
                childNode=new Node<>(node);
                childNode.character=c;
                node.children=emptyChildren ? new HashMap<>() : node.children;
                node.children.put(c,childNode);
            }
            node=childNode;
        }
        if (node.word) { //已经存在这个单词
            V oldValue = node.value;
            node.value = value;
            return oldValue;
        }

        // 新增一个单词
        node.word = true;
        node.value = value;
        size++;
        return null;
    }

    public V remove(String key){
        Node<V> node = node(key);
        if(node == null || ! node.word) return null;
        V oldValue = node.value;
        size--;
        if(node.children != null && ! node.children.isEmpty()){
            node.word = false;
            node.value = null;
            return oldValue;
        }

        Node<V> parent = null;
        while((parent=node.parent) != null){
            parent.children.remove(node.character);
            if(parent.word || !parent.children.isEmpty()) break;
            node = parent;
        }
        return oldValue;
    }

    private Node<V> node(String key){
        keyCheck(key);
        Node<V> node=root;
        int len = key.length();
        for (int i = 0; i <len; i++) {
            if(node==null || node.children==null || node.children.isEmpty()) return null; //上述条件不满足也会返回null
            char c = key.charAt(i);
            node=node.children.get(c);//如果没找到也会返回null
        }
        return node;
    }


    private void keyCheck(String key){
        if (key == null || key.length() == 0) {
            throw new IllegalArgumentException("key must not be empty");
        }
    }

    private static class Node<V>{
        Node<V> parent;
        HashMap<Character,Node<V>> children;
        Character character;
        V value;
        boolean word;
        public Node(Node<V> parent){
            this.parent=parent;
        }
    }
}
