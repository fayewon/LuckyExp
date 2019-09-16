/**
* @Project Name : LuckyExp
*
* @File name : Formula_Choose.java
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
 * 公式指针，目前最多支持绑定6个公式
*
* @author FayeWong
* 
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
