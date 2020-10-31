import com.lihai.list.ArrayList;
import com.lihai.list.List;

/**
 * �ö�̬������ʵ��ջ���ݽṹ��
 * ���ö�̬�����һЩ�ӿڶ���
 * ������õ���"����"  �����ǵ�����ȥ�̳ж�̬���飬������̳�һЩջ���ݽṹ������Ҫ��һЩ�ӿڷ�����
 * ����"����"�ķ�ʽ��ֻ��Ҫ���ö�̬������ʵ��ջ�����Ľӿڷ�����
 * ��ȻJDK���ջ�Ǽ̳е�  class Stack<E> extends Vector<E>
 * @param <E>
 */

public class Stack<E> {

	ArrayList<E> list=new ArrayList<>();
	
	public void clear() {
		list.clear();
	}
	
	public int size() {
		return list.size();
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	public void push(E element) {
		list.add(element);
	}


	public E pop() {
		return list.remove(list.size()-1);
	}


	public E top() {
		return list.get(list.size()-1);
	}
}
