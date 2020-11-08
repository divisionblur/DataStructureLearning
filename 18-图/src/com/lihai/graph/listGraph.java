package com.lihai.graph;

import com.lihai.MinHeap;
import com.lihai.UnionFind;
import org.omg.CORBA.OBJ_ADAPTER;
import java.nio.file.Path;
import java.util.*;

/**
 * @author joy division
 * @date 2020/10/28 18:14
 */
public class listGraph<V,E> extends Graph<V,E> {
    @Override
    public int edgesSize() {
        return edges.size();
    }

    @Override
    public int verticesSize() {
        return vertices.size();
    }

    @Override
    public void addVertex(V v) {
        //如果图中已经有这个顶点了,就返回。
        if (vertices.containsKey(v)) {
            return;
        }
        vertices.put(v,new Vertex(v));
    }

    @Override
    public void addEdge(V from, V to) {
        addEdge(from,to,null);
    }

    @Override
    public void addEdge(V from, V to, E weight) {
        Vertex fromVertex = vertices.get(from);
        //如果图中没有这个顶点,就创建一个。
        if (fromVertex == null) {
            fromVertex = new Vertex<>(from);
            vertices.put(from,fromVertex);
        }

        Vertex toVertex = vertices.get(to);
        if (toVertex == null) {
            toVertex = new Vertex<>(to);
            vertices.put(to,toVertex);
        }

        Edge edge = new Edge<>(fromVertex, toVertex);
        edge.weight = weight;

        if (fromVertex.outEdges.remove(edge)) {
            toVertex.inEdges.remove(edge);
            edges.remove(edge);
        }
        fromVertex.outEdges.add(edge);
        toVertex.inEdges.add(edge);
        edges.add(edge);
    }

    @Override
    public void removeVertex(V v) {
      //在hashMap中通过key删掉value并且返回value,在这里就是删掉图中的顶点,但是还要删除和这个顶点相关联的边!
      Vertex vertex = vertices.remove(v);
      if (vertex == null) {
          return;
      }

      //把这个顶点(此时相当于from)的所有出边从这个顶点的保存边的hashSet里删去,并且还有把这些出边从到达的顶点(to)的hashSet里删去
      //最后还要将这条边从保存整个图的边的hashSet里删去。
      for (Iterator<Edge<V,E>> iterator = vertex.outEdges.iterator();iterator.hasNext();) {
          Edge<V, E> edge = iterator.next();
          edge.to.inEdges.remove(edge);
          // 将当前遍历到的元素edge从集合vertex.outEdges中删掉
          iterator.remove();
          edges.remove(edge);
      }

      //把这个顶点(此时相当于to)的所有来边从这个顶点的保存边的hashSet里删去,并且还有把这些来边从出发的顶点(from)的hashSet里删去
      //最后还要将这条边从保存整个图的边的hashSet里删去。
      for (Iterator<Edge<V,E>> iterator = vertex.inEdges.iterator();iterator.hasNext();) {
          Edge<V, E> edge = iterator.next();
          edge.from.outEdges.remove(edge);
          // 将当前遍历到的元素edge从集合vertex.outEdges中删掉
          iterator.remove();
          edges.remove(edge);
      }
    }

    @Override
    public void removeEdge(V from, V to) {
        Vertex fromVertex = vertices.get(from);
        if (fromVertex == null) {
            return;
        }

        Vertex toVertex = vertices.get(to);
        if (toVertex == null) {
            return;
        }

        Edge edge = new Edge<>(fromVertex, toVertex);

        if (fromVertex.outEdges.remove(edge)) {
            toVertex.inEdges.remove(edge);
            edges.remove(edge);
        }
    }

    //从用户指定的v对应的顶点开始广度优先遍历！
    @Override
    public void bfs(V begin, VertexVisitor<V> visitor) {
        //用户没有传入比较器那么退出!
        if (visitor == null) {
            return;
        }
        //如果没有这个顶点,那么也退出!
        Vertex<V,E> beginVertex = vertices.get(begin);
        if (beginVertex == null) {
            return;
        }

        HashSet<Vertex<V,E>> visitedVertices = new HashSet<>();
        Queue<Vertex<V,E>> queue = new LinkedList<>();
        queue.offer(beginVertex);
        visitedVertices.add(beginVertex);

        while (!queue.isEmpty()) {
            Vertex<V, E> vertex = queue.poll();
            if (visitor.visit(vertex.value)) {
                return;
            }

            for (Edge<V,E> edge : vertex.outEdges) {
                if (visitedVertices.contains(edge.to)) {
                    continue;
                }
                queue.offer(edge.to);
                visitedVertices.add(edge.to);
            }
        }

    }

    @Override
    public void dfs(V begin, VertexVisitor<V> visitor) {
        if (visitor == null) {
            return;
        }
        Vertex beginVertex = vertices.get(begin);
        if (beginVertex == null) {
            return;
        }
        Stack<Vertex<V,E>> stack = new Stack<>();
        HashSet<Vertex<V,E>> visitedVertices = new HashSet<>();
        stack.push(beginVertex);
        visitedVertices.add(beginVertex);
        if (visitor.visit(begin)) {
            return;
        }
        while (!stack.isEmpty()) {
            Vertex<V, E> vertex = stack.pop();
            //如果弹出栈时访问的话节点会被访问多次
            for (Edge<V,E> edge : vertex.outEdges) {
                if (visitedVertices.contains(edge.to)) {
                    continue;
                }
                stack.push(edge.from);
                stack.push(edge.to);
                visitedVertices.add(edge.to);
                //访问的位置与bfs有所不同
                if (visitor.visit(edge.to.value)) {
                    return;
                }
                break;
            }
        }
    }

    @Override
    public Set<EdgeInfo<V, E>> mst() {
        return Math.random() > 0.5 ? prim() : null;
    }


    private Set<EdgeInfo<V, E>> prim() {
        Iterator<Vertex<V, E>> it = vertices.values().iterator();
        while (!it.hasNext()) {
            return null;
        }
        Vertex<V, E> vertex = it.next();
        HashSet<EdgeInfo<V,E>> edgeInfos = new HashSet<>();
        HashSet<Vertex<V,E>> addedVertices = new HashSet<>();
        addedVertices.add(vertex);
        MinHeap<Edge<V,E>> heap = new MinHeap<>(vertex.outEdges,edgeComparator);
        while (!heap.isEmpty() && addedVertices.size() < vertices.size()) {
            Edge<V, E> edge = heap.remove();
            if (addedVertices.contains(edge.to)) {
                continue;
            }

            edgeInfos.add(edge.info());
            addedVertices.add(edge.to);
            heap.addAll(edge.to.outEdges);
        }
        return edgeInfos;
    }


    private Set<EdgeInfo<V, E>> kruskal() {
        int edgeSize = vertices.size() - 1;
        if (edgeSize == -1) {
            return null;
        }
        
        Set<EdgeInfo<V,E>> edgeInfos = new HashSet<>();
        MinHeap<Edge<V,E>> heap = new MinHeap<>(edges,edgeComparator);
        UnionFind<Vertex<V,E>> uf = new UnionFind<>();
        //把所有顶点都加入了并查集但是一个顶点属于一个集合
        vertices.forEach((V v,Vertex<V,E> vertex) -> {
            uf.makeSet(vertex);
        });

        while (!heap.isEmpty() && edgeInfos.size() < edgeSize) {
            Edge<V, E> edge = heap.remove();
            //如果这条边的两个顶点属于一个"集合"的话,那么就跳过,不然会形成环
            if (uf.isSame(edge.from,edge.to)) {
                continue;
            }
            edgeInfos.add(edge.info());
            uf.union(edge.from,edge.to);
        }
        return edgeInfos;
    }



    @Override
    public List<V> topologicalSort() {
        ArrayList<V> list = new ArrayList<>();
        HashMap<Vertex<V,E>, Integer> map = new HashMap<>();
        Queue<Vertex<V,E>> queue = new LinkedList<>();
        //遍历存放节点的hashMap
        //foreach封装了entrySet
        vertices.forEach((V v,Vertex<V,E> vertex) ->{
            int inDegree = vertex.inEdges.size();
            if (inDegree == 0) {
                queue.offer(vertex);
            } else {
                map.put(vertex,inDegree);
            }
        });

        while (!queue.isEmpty()) {
            //度为0的节点出队!
            Vertex<V, E> vertex = queue.poll();
            list.add(vertex.value);
            for (Edge<V,E> edge : vertex.outEdges) {
                int toInDegree = map.get(edge.to) - 1;
                if (toInDegree == 0) {
                    queue.offer(edge.to);
                } else {
                    map.put(edge.to,toInDegree);
                }
            }
        }
        return list;
    }



//    @Override
//    public Map<V, PathInfo<V, E>> shortestPath(V begin) {
//        Vertex<V, E> beginVertex = vertices.get(begin);
//        if (beginVertex == null) {
//            return null;
//        }
//        HashMap<V, PathInfo<V, E>> selectedPaths = new HashMap<>();
//        HashMap<Vertex<V,E>, PathInfo<V, E>> paths = new HashMap<>();
//        for (Edge<V,E> edge : beginVertex.outEdges) {
//            paths.put(edge.to, new PathInfo<>(weightManager.zero()));
//        }
//
//        while (!paths.isEmpty()) {
//            Map.Entry<Vertex<V, E>, PathInfo<V, E>> minEntry = getMinPath(paths);
//            Vertex<V, E> minVertex = minEntry.getKey();
//            selectedPaths.put(minVertex.value,minEntry.getValue());
//            paths.remove(minVertex);
//
//            for (Edge<V,E> edge : minVertex.outEdges) {
//                if (selectedPaths.containsKey(edge.to.value)) {
//                    continue;
//                }
//                E newWeight = weightManager.add(minEntry.getValue().weight, edge.weight);
//                E oldWeight = paths.get(edge.to).weight;
//                if (oldWeight == null || weightManager.compare(newWeight,oldWeight) < 0) {
//                    paths.put(edge.to,new PathInfo<>(newWeight));
//                }
//            }
//        }
//        selectedPaths.remove(begin);
//        return selectedPaths;
//
//    }
//
    private Map.Entry<Vertex<V, E>, PathInfo<V, E>> getMinPath(Map<Vertex<V, E>, PathInfo<V, E>> paths) {
        Iterator<Map.Entry<Vertex<V, E>, PathInfo<V, E>>> it = paths.entrySet().iterator();
        Map.Entry<Vertex<V, E>, PathInfo<V, E>> minEntry = it.next();
        while (it.hasNext()) {
            Map.Entry<Vertex<V, E>, PathInfo<V, E>> entry = it.next();
            if (weightManager.compare(entry.getValue().weight, minEntry.getValue().weight) < 0) {
                minEntry = entry;
            }
        }
        return minEntry;
    }


    @Override
    public Map<V, PathInfo<V, E>> shortestPath(V begin) {
        return dijkstra(begin);
    }

    private Map<V, PathInfo<V, E>> dijkstra(V begin) {
        Vertex<V, E> beginVertex = vertices.get(begin);
        if (beginVertex == null) {
            return null;
        }
        HashMap<V,PathInfo<V,E>> selectedPaths = new HashMap();
        HashMap<Vertex<V,E>,PathInfo<V,E>> paths = new HashMap();
        paths.put(beginVertex,new PathInfo<>(weightManager.zero()));
        
        while (!paths.isEmpty()) {
            Map.Entry<Vertex<V, E>, PathInfo<V, E>> minEntry = getMinPath(paths);
            Vertex<V, E> minVertex = minEntry.getKey();
            PathInfo<V, E> minPath = minEntry.getValue();
            selectedPaths.put(minVertex.value,minPath);
            paths.remove(minVertex);

            for (Edge<V,E> edge : minVertex.outEdges) {
                if (selectedPaths.containsKey(edge.to)) {
                    continue;
                }
                relaxForDijkstra(edge,minPath,paths);
            }

        }
        selectedPaths.remove(begin);
        return selectedPaths;
    }

    private void relaxForDijkstra(Edge<V,E> edge,PathInfo<V,E> fromPath,Map<Vertex<V, E>, PathInfo<V, E>> paths) {
        E newWeight = weightManager.add(fromPath.weight, edge.weight);

        PathInfo<V, E> oldPath = paths.get(edge.to);
        //先到达一个节点把路径信息加进去,当经过其他节点时有可能有路径到达这个节点,那么此时路径就不为空
        if (oldPath != null && weightManager.compare(newWeight,oldPath.weight) >= 0) {
            return;
        }
        if (oldPath == null) {
            oldPath = new PathInfo<>();
            paths.put(edge.to,oldPath);
        } else {
            oldPath.edgeInfos.clear();
        }
        oldPath.weight = newWeight;
        oldPath.edgeInfos.addAll(fromPath.edgeInfos);
        oldPath.edgeInfos.add(edge.info());
    }

    @Override
    public Map<V, Map<V, PathInfo<V, E>>> shortestPath() {
        HashMap<V, Map<V,PathInfo<V,E>>> paths = new HashMap<>();
        for (Edge<V,E> edge : edges) {
            HashMap<V, PathInfo<V,E>> map = new HashMap<>();
            PathInfo<V, E> pathInfo = new PathInfo<>(edge.weight);
            pathInfo.edgeInfos.add(edge.info());
            map.put(edge.to.value,pathInfo);
            paths.put(edge.from.value,map);
        }
        vertices.forEach((V v2,Vertex<V,E> vertex2) -> {
            vertices.forEach((V v1,Vertex<V,E> vertex1) -> {
                vertices.forEach((V v3,Vertex<V,E> vertex3) -> {
                    if (v1.equals(v2) || v2.equals(v3) || v1.equals(v3)) {
                        return;
                    }
                    PathInfo<V, E> path12 = getPathInfo(v1, v2, paths);
                    if (path12 == null) {
                        return;
                    }
                    PathInfo<V, E> path23 = getPathInfo(v2, v3, paths);
                    if (path12 == null) {
                        return;
                    }
                    PathInfo<V, E> path13 = getPathInfo(v1, v3, paths);
                    E newWeight = weightManager.add(path12.weight, path23.weight);
                    if (path13 != null && weightManager.compare(path13.weight,newWeight) < 0) {
                        return;
                    }
                    if (path13 == null) {
                        path13 = new PathInfo<>();
                        paths.get(v1).put(v3,path13);
                    } else {
                        path13.edgeInfos.clear();
                    }
                    path13.weight = newWeight;
                    path13.edgeInfos.addAll(path12.edgeInfos);
                    path13.edgeInfos.addAll(path23.edgeInfos);

                });
            });
        });

        return paths;

    }
    
    private PathInfo<V, E> getPathInfo(V from, V to, Map<V, Map<V, PathInfo<V, E>>> paths) {
        Map<V, PathInfo<V, E>> map = paths.get(from);
        //当这个节点没有指向任何一个节点时这时它的map为空
        return map == null ? null : map.get(to);
    }




    //存放着图中的所有顶点
    private Map<V,Vertex<V,E>> vertices = new HashMap<>();
    //存放着图中的所有边
    private Set<Edge<V,E>> edges = new HashSet<>();
    //Comparator也是一个函数式接口,只要含有一个抽象方法的就是,虽然它有Object类继承下来的equals和其他方法
    //但是他们都不是抽象方法!
    private Comparator<Edge<V, E>> edgeComparator = (Edge<V, E> e1, Edge<V, E> e2) -> {
        //WeightManager也是一个接口使用匿名内部类创建"接口实例"时,要重写compare等几个抽象方法
        return weightManager.compare(e1.weight, e2.weight);
    };

    private static class Vertex<V,E> {
        V value;
        Set<Edge<V,E>> inEdges = new HashSet<>();
        Set<Edge<V,E>> outEdges = new HashSet<>();

        public Vertex(V value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            return Objects.equals(value,((Vertex<V,E>)obj).value);
        }

        @Override
        public int hashCode() {
            return value == null ? 0 : value.hashCode();
        }

        @Override
        public String toString() {
            return value == null ? "null" : value.toString();
        }
    }

    private static class Edge<V,E> {
        Vertex<V,E> from;
        Vertex<V,E> to;
        E weight;

        public Edge(Vertex<V, E> from, Vertex<V, E> to) {
            this.from = from;
            this.to = to;
        }
        //这个方法是为求最小生成树准备的,只需要边的两个顶点的信息以及权重!
        EdgeInfo<V, E> info() {
            return new EdgeInfo<>(from.value, to.value, weight);
        }

        @Override
        public int hashCode() {
            return from.hashCode() * 31 + to.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            Edge<V,E> edge = (Edge<V,E>)obj;
            return Objects.equals(from,edge.from) && Objects.equals(to,edge.to);
        }

        @Override
        public String toString() {
            return "Edge [from=" + from + ", to=" + to + ", weight=" + weight + "]";
        }
    }
}
