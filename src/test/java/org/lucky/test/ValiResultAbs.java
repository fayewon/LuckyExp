/** Copyright 2019 SAIC General Motors Corporation Ltd. All Rights Reserved.
*
* This software is published under the terms of the SGM Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : LuckyExp
*
* @File name : ValiResultAbs.java
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
package org.lucky.test;

import java.util.List;

import org.lucky.exp.parent.OperResult;
/**
 * 验证结果的回调
*
* @author FayeWong
* @date 2019年8月31日
 */
public class ValiResultAbs /* extends ExecuteResult */{

	
	protected void getValiMeg(List message) {
		System.out.println("message: "+message);	
		
	}

	
	public void execute(Object t,boolean isError) {
		Dog u = (Dog)t;
		
		
	}


}
