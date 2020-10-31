package com.lihai;

/**
 * @author joy division
 * @date 2020/10/27 21:03
 */
public class UnionFind_QU extends UnionFind{
    public UnionFind_QU(int capacity) {
        super(capacity);
    }

    @Override
    public int find(int v) {
        rangeCheck(v);
        while (v != parents[v]) {
            v = parents[v];
        }
        return v;
    }

    @Override
    public void union(int v1, int v2) {
        int p1 = find(v1);
        int p2 = find(v2);
        if (p1 == p2) {
            return;
        }
        //parents数组中存放的是每个顶点对应的集合
        parents[p1] = p2;
    }
}
