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
