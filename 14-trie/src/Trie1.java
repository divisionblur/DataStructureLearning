import java.util.HashMap;

public class Trie1<V> {
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
    public V add(String key,V value){
        keyCheck(key);
        if(root==null){
            root=new Node<V>(null);
            size++;
            return null;
        }
        Node<V> node=root;
        int len = key.length();
        for (int i = 0; i <len ; i++) {
            char c = key.charAt(i);
            boolean emptyChildren = node.children == null;
            Node<V> childNode = emptyChildren ? null : node.children.get(c);
            if(childNode == null){
                childNode = new Node<V>(node);
                childNode.character=c;
                node.children = emptyChildren ? new HashMap<>() : node.children;
                node.children.put(c,childNode);
            }
            node=childNode;
        }
        if(node.word){//如果已经有这个单词了，那么就将value覆盖并且返回旧的value
            V oldValue = node.value;
            node.value = value;
            return oldValue;
        }
        node.word = true;
        node.value = value;
        size++;
        return null;
    }

    public V remove(String key){
        Node<V> node = node(key);
        if(node == null || !node.word) return null;
        V oldValue = node.value;
        size--;
        if(node.children != null && node.children.isEmpty()){
            node.value = null;
            node.word = false;
            return oldValue;
        }
        Node<V> parent = null;
        while ((parent = node.parent) != null){
            parent.children.remove(node.character);
            if(parent.word || !parent.children.isEmpty()) break;
            node=parent;
        }
        return oldValue;
    }



    private Node<V> node(String key){
        keyCheck(key);
        Node<V> node = root;
            int len = key.length();
            for (int i = 0; i < len; i++) {
                if(node!=null || node.children != null || node.children.isEmpty()) return null;
                char c = key.charAt(i);
                node = node.children.get(c);
            }
            return node;
    }

    private void keyCheck(String key) {
        if(key == null||key.length() == 0){
            throw new IllegalArgumentException("key must not be null");
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
