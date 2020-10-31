package com.lihai;

/**
 * @author joy division
 * @date 2020/10/27 22:45
 */

public class UnionFind_QU_R_PS extends UnionFind_QU_R{
    public UnionFind_QU_R_PS(int capacity) {
        super(capacity);
    }

    /**
     * 使路径上的每一个节点都指向其祖父节点!
     * @param v
     * @return
     */
    @Override
    public int find(int v) {
        rangeCheck(v);
        while (v != parents[v]) {
            int p = parents[v];
            parents[v] = parents[parents[v]];
            v = p;
        }
        return v;
    }
}
