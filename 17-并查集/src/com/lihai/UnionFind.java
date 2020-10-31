package com.lihai;
public abstract class UnionFind {
	protected int[] parents;
	public UnionFind(int capacity) {
		if (capacity < 0) {
			throw new IllegalArgumentException("capacity must be >= 1");
		}
		//在构造方法里初始化parents数组
		parents = new int[capacity];
		for (int i = 0; i < parents.length; i++) {   //初始化时每一个元素是一个单独的集合！
			parents[i] = i;   //代表每一个节点它的父节点就是它自己！
		}
	}
	
	/**
	 * 查找v所属的集合（根节点）
	 * @param v
	 * @return
	 */
	public abstract int find(int v);

	/**
	 * 合并v1、v2所在的集合
	 */
	public abstract void union(int v1, int v2);
	
	/**
	 * 检查v1、v2是否属于同一个集合(根集合是一样的!)  是否有路相连!
	 */
	public boolean isSame(int v1, int v2) {
		return find(v1) == find(v2);
	}
	
	protected void rangeCheck(int v) {
		if (v < 0 || v >= parents.length) {
			throw new IllegalArgumentException("v is out of bounds");
		}
	}
}
