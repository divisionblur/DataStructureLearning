package com.lihai;

/**
 * @author joy division
 * @date 2020/10/27 21:48
 */
public class UnionFind_QU_R_PC extends UnionFind_QU_R{

    public UnionFind_QU_R_PC(int capacity) {
        super(capacity);
    }

    /**
     * UnionFind quick union基于 rank 优化之后 再进行路径压缩优化!
     * @param v
     * @return
     */

    @Override
    public int find(int v) {
        rangeCheck(v);
        if (parents[v] != v) {
            parents[v] = find(parents[v]);
        }
        //递归出口在这里
        return parents[v];
    }
}
