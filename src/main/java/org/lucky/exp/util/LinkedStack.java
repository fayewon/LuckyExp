/** 
 * @Project Name : LuckyExp
*
* @File name : LinkedStack.java
*
* @Author : FayeWong
*
* @Date : 2019年9月3日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2019年9月3日    FayeWong    1.0   链表栈
*
*
*
*
----------------------------------------------------------------------------------
*/
package org.lucky.exp.util;

import java.util.LinkedList;
/**
 * 链表栈
*
* @author FayeWong
* @since 2019年9月16日
* @param <E> 泛型
 */
public class LinkedStack<E> {
	private final LinkedList<E> list ;
	public LinkedStack(){
		list = new LinkedList<E>();
	}
	public void push(E e ) {
		list.add(e);
	}
	public E peek() {
		return list.getLast();
	}
	public E pop() {
		return list.removeLast();
	}
	public boolean isEmpty() {
		return list.size() == 0;		
	}
	public E search(int index) {
		return list.get(index);
	}
	public int size() {
		return list.size();
	}
}
