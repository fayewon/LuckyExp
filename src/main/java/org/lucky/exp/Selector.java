/** 
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

package org.lucky.exp;

import java.util.HashMap;
import java.util.Map;

import org.lucky.exp.annotation.Formula_Choose;


/**
 * 公式选择器
*
* @author FayeWong
* @date 2019年8月29日
 */
public class Selector {
	public Map<String, Formula_Choose> selector = new HashMap<String, Formula_Choose>();
	public void put(String filedName, Formula_Choose select) {
		selector.put(filedName, select);
	}
}
