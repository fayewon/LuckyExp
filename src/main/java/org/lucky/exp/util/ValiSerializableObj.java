/** 
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
* 1. FayeWong    1.0       对常见的实现序列化进行校验
*
*
*
*
----------------------------------------------------------------------------------
*/
package org.lucky.exp.util;
import java.lang.reflect.Field;
import java.util.Map;

import org.lucky.exp.annotation.BindVar;
import org.lucky.exp.exception.BindException;
/**
 * 
*
* @author FayeWong
 */
public class ValiSerializableObj {
	public static boolean validationObject(Field field,boolean valiType) throws BindException {
		Class<?> clazz = field.getType();
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
			//.....
		return valiType;
	}
	/**
	 * 
	* 绑定变量转计算参数,支持类型(float,Float,double,Double,short,Short,int,Integer,long,Long,String)
	* @author FayeWong
	* @since 2019年9月16日
	* @param fieldVal 变量值
	* @param field 变量
	* @param variables 计算参数
	* @throws BindException 绑定异常
	 */
	public static void bindVar(Object fieldVal, Field field, Map<String, Double> variables) throws BindException{
		BindVar bind = (BindVar) field.getAnnotation(BindVar.class);
		if(field.getType() == float.class) {
			if (bind.enable() && fieldVal != null)
				variables.put(bind.value(), Double.valueOf((float)fieldVal));			
		}else if(field.getType() == Float.class) {
			if (bind.enable() && fieldVal != null)
				variables.put(bind.value(), Double.valueOf((Float)fieldVal));			
		}else if(field.getType() == double.class) {
			if (bind.enable() && fieldVal != null)
				variables.put(bind.value(), (double)fieldVal);			
		}else if(field.getType() == Double.class) {
			if (bind.enable() && fieldVal != null)
				variables.put(bind.value(), (Double)fieldVal);			
		}else if(field.getType() == short.class) {
			if (bind.enable() && fieldVal != null) 
				variables.put(bind.value(), Double.valueOf((short)fieldVal));			
		}else if(field.getType() == Short.class) {
			if (bind.enable() && fieldVal != null)
				variables.put(bind.value(), Double.valueOf((Short)fieldVal));			
		}else if(field.getType() == int.class) {
			if (bind.enable() && fieldVal != null) 
				variables.put(bind.value(), Double.valueOf((int)fieldVal));			
		}else if(field.getType() == Integer.class) {
			if (bind.enable() && fieldVal != null)
				variables.put(bind.value(), Double.valueOf((Integer)fieldVal));			
		}else if(field.getType() == long.class) {
			if (bind.enable() && fieldVal != null)
				variables.put(bind.value(), Double.valueOf((long)fieldVal));		
		}else if(field.getType() == Long.class) {
			if (bind.enable() && fieldVal != null) 				
				variables.put(bind.value(), Double.valueOf((Long)fieldVal));			
		}else if(field.getType() == String.class) {
			if (bind.enable() && fieldVal != null) {
				try {
					variables.put(bind.value(), Double.valueOf((String)fieldVal));	
				}catch (NumberFormatException e) {
					throw new BindException("@Bind('" + bind.value() + "') 绑定变量值无法转换成double ：" + field.getType()
					+ "{ }{ } :  " + field.getName(),e);
				}
			}				
		}else {
			throw new BindException("@Bind('" + bind.value() + "') 不能绑定该变量类型上 ：" + field.getType()
			+ "{ }{ } :  " + field.getName());
		}
	}
}
