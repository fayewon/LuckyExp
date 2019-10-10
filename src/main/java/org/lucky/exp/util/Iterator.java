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
* 1. 2019年9月10日    FayeWong    1.0  公式迭代器
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
 * 
*
* @author FayeWong
 * @param <E>  公式对象
 */
public class Iterator<E> {
	private  final LinkedList<E> exps;
	private  int i = 0;
	public Iterator(List<E> exps) {
		this.exps = (LinkedList<E>)exps;
	}
	public boolean hasNext() {
		return i < this.exps.size();
	}
	public E removeNext() {
		
		return exps.removeFirst();
	}
	public E next() {
		return exps.get(i++);		
	}
	public boolean offerLast(E iterator) {
		return exps.offerLast(iterator);
	}
	public LinkedList<E> getList() {
		return exps;
	}
	public boolean isEmpty() {
		return exps.isEmpty();
	}
	public void reset() {
		i = 0;
	}
}
