package com.lihai.list;

public interface List<E> {
	static final int ELEMENT_NOT_FOUND = -1;
	/**
	 * 清除所有元素
	 */
	void clear();

	/**
	 * 元素的数量
	 * @return
	 */
	int size();

	/**
	 * 是否为空
	 * @return
	 */
	boolean isEmpty();

	/**
	 * 是否包含某个元素
	 * @param element
	 * @return
	 */
	boolean contains(E element);

	/**
	 * 添加元素到尾部
	 * @param element
	 */
	void add(E element);

	/**
	 * 获取index位置的元素
	 * @param index
	 * @return
	 */
	E get(int index);

	/**
	 * 设置index位置的元素
	 * @param index
	 * @param element
	 * @return 原来的元素ֵ
	 */
	E set(int index, E element);

	/**
	 * 在index位置插入一个元素
	 * @param index
	 * @param element
	 */
	void add(int index, E element);

	/**
	 * 删除index位置的元素
	 * @param index
	 * @return
	 */
	E remove(int index);

	/**
	 * 查看元素的索引
	 * @param element
	 * @return
	 */
	int indexOf(E element);

    abstract class AbstractList<E> implements List<E> {
        /**
         * 一个抽象类如果继承或实现了另一个抽象类或者是接口的话  在这个抽象类中可以不用重写抽象方法。
         */



        /**
         * 元素的数量
         */
        protected int size;
        /**
         * 元素的数量
         * @return
         */

        public int size() {
            return size;
        }

        /**
         * 是否为空
         * @return
         */
        public boolean isEmpty() {
             return size == 0;
        }

        /**
         * 是否包含某个元素
         * @param element
         * @return
         */
        public boolean contains(E element) {
            return indexOf(element) != ELEMENT_NOT_FOUND;
        }

        /**
         * 添加元素到尾部
         * @param element
         */
        public void add(E element) {
            add(size, element);
        }

        protected void outOfBounds(int index) {
            throw new IndexOutOfBoundsException("Index:" + index + ", Size:" + size);
        }

        protected void rangeCheck(int index) {
            if (index < 0 || index >= size) {
                outOfBounds(index);
            }
        }

        protected void rangeCheckForAdd(int index) {
            if (index < 0 || index > size) {
                outOfBounds(index);
            }
        }
    }
}
