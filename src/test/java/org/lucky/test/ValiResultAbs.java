/** 
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
public class ValiResultAbs extends OperResult{

	
	protected void getValiMeg(List message) {
		System.out.println("message: "+message);	
		
	}


	@Override
	public void executeAsync(Object t, boolean isSuccess) {
		Dog dog = (Dog)this.t;
		//System.out.println(dog.getThree());
		System.out.println(dog.getFour());
		
	}
}
