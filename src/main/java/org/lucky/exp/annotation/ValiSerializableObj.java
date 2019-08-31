/** Copyright 2019 SAIC General Motors Corporation Ltd. All Rights Reserved.
*
* This software is published under the terms of the SGM Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : LuckyExp
*
* @File name : ValiSerializableObj.java
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

import java.io.Serializable;
import java.lang.reflect.Field;
import org.lucky.exp.exception.BindException;
/**
 * 对常见的实现序列化进行校验
*
* @author FayeWong
* @date 2019年8月31日
 */
public class ValiSerializableObj {
	public static boolean validation(Field field,boolean valiType) throws BindException {
		Class<?>[] clazzes = field.getType().getInterfaces();
		for(Class<?> clazz : clazzes) {
			if(clazz == String.class) {
				return valiType = false;
			}else if(clazz == Byte.class) {
				return valiType = false;
			}else if(clazz == Character.class) {
				return valiType = false;
			}else if(clazz == Short.class) {
				return valiType = false;
			}else if(clazz == Integer.class) {
				return valiType = false;
			}else if(clazz == Long.class) {
				return valiType = false;
			}else if(clazz == Float.class) {
				return valiType = false;
			}else if(clazz == Double.class) {
				return valiType = false;
			}else if(clazz == Boolean.class) {
				return valiType = false;
			}
		}
		return valiType;
	}
}
