package lihai;

import java.util.Comparator;

@SuppressWarnings("unchecked")
public abstract class AbstractHeap<E> implements Heap<E> {
	protected int size;
	protected Comparator<E> comparator;
	
	public AbstractHeap(Comparator<E> comparator) {
		this.comparator = comparator;
	}
	
	public AbstractHeap() {
		this(null);
	}
	
	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}
	//二叉堆必须具备可比较性，子类继承之后会有这个比较的方法！
	protected int compare(E e1, E e2){
		return comparator != null ? comparator.compare(e1, e2) : ((Comparable<E>)e1).compareTo(e2);
	}
}
