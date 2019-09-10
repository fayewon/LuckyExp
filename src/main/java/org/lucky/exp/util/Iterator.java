/**
* @Project Name : LuckyExp
*
* @File name : Iterator.java
*
* @Author : FayeWong
*
* @Date : 2019年9月10日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2019年9月10日    FayeWong    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
package org.lucky.exp.util;

import java.util.LinkedList;
import java.util.List;
/**
 * 公式迭代器
*
* @author FayeWong
 * @param <E>
* @date 2019年9月10日
 */
public class Iterator<E> {
	private  final LinkedList<E> exps;
	private  int i = 0;
	private  E iterator;
	public Iterator(List<E> exps) {
		this.exps = (LinkedList<E>)exps;
	}
	public boolean hasNext() {
		return i < this.exps.size();
	}
	public E removeNext() {
		iterator = exps.removeFirst();
		return iterator;
	}
	public E next() {
		return exps.get(i++);		
	}
	public boolean offerLast() {
		if(iterator == null) throw new IllegalArgumentException("未先删除下一个元素.");
		return exps.offerLast(iterator);
	}
	public LinkedList<E> getList() {
		return exps;
	}
	public boolean isEmpty() {
		return exps.isEmpty();
	}
}
