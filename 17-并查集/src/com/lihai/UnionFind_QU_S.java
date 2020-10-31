package com.lihai;

import com.sun.xml.internal.ws.wsdl.writer.UsingAddressing;

/**
 * Quick Union基于size的优化。
 *
 * @author joy division
 * @date 2020/10/27 21:23
 */
public class UnionFind_QU_S extends UnionFind_QU{
    private int[] sizes;

    public UnionFind_QU_S(int capacity) {
        super(capacity);
        sizes = new int[capacity];
        for (int i = 0;i < sizes.length;i++) {
            //一开始的时候每一个元素的父节点就是自己!每个元素单独成为一个集合,size就是1
            sizes[i] = 1;
        }
    }

    @Override
    public void union(int v1, int v2) {
        int p1 = find(v1);
        int p2 = find(v2);
        if (p1 == p2) {
            return;
        }

        if (sizes[p1] < sizes[p2]) {
            parents[p1] = p2;
            sizes[p2] = sizes[p1] + sizes[p2];
        }else {
            parents[p2] = p1;
            sizes[p1] = sizes[p1] + sizes[p2];
        }
    }
}
