package com.lihai;

/**
 * @author joy division
 * @date 2020/10/27 22:57
 */
public class UnionFind_QU_R_PH extends UnionFind_QU_R{
    public UnionFind_QU_R_PH(int capacity) {
        super(capacity);
    }

    @Override
    public int find(int v) {
        rangeCheck(v);
        while (parents[v] != v) {
            parents[v] = parents[parents[v]];
            v = parents[v];
        }
        return v;
    }
}
