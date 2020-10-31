package com.lihai;

/**Quick Union基于rank的优化。
 * @author joy division
 * @date 2020/10/27 21:35
 */

/**
 * 基于rank的优化,矮的树嫁接到高的树上,高度不会发生变化,两颗树的高度一样,随便嫁接但整体的高度会加一
 */
public class UnionFind_QU_R extends UnionFind_QU{

    private int[] ranks;

    public UnionFind_QU_R(int capacity) {
        super(capacity);
        ranks = new int[capacity];
        for (int i = 0;i < ranks.length;i++) {
            ranks[i] = 1;
        }
    }

    @Override
    public void union(int v1, int v2) {
        int p1 = find(v1);
        int p2 = find(v2);
        if (p1 == p2) {
            return;
        }

        if (ranks[p1] < ranks[p2]) {
            parents[p1] = p2;
        }else if(ranks[p1] > ranks[p2]){
            parents[p2] = p1;
        }else {
            parents[p1] = p2;
            ranks[p2] = ranks[p2] + 1;
        }
    }
}
