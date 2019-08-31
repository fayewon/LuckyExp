/** Copyright 2019 SAIC General Motors Corporation Ltd. All Rights Reserved.
*
* This software is published under the terms of the SGM Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : LuckyExp
*
* @File name : ExceptionCode.java
*
* @Author : FayeWong
*
* @Email : 50125289@qq.com
*
----------------------------------------------------------------------------------
*    Who        Version     Comments
* 1. FayeWong    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
package org.lucky.exp.annotation;
/**
 * 公式指针，目前最多支持绑定6给公式
*
* @author FayeWong
* @date 2019年8月29日
 */
public enum Formula_Choose {
	_1(0),
	_2(1),
	_3(2),
	_4(3),
	_5(4),
	_6(5);
	private int index;  

    private Formula_Choose(int index) {  
        this.index = index;  
 
    }

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
