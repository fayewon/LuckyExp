/**
* Copyright 2019 SAIC General Motors Corporation Ltd. All Rights Reserved.
*
* This software is published under the terms of the SGM Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
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
* 1. 2019年9月3日    FayeWong    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
package org.lucky.exp.util;

import java.util.LinkedList;

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
	public boolean empty() {
		return list.size() == 0;		
	}
	public E search(int index) {
		return list.get(index);
	}
	public int size() {
		return list.size();
	}
}
