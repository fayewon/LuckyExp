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
* 1. FayeWong    1.0        绑定的成员变量会成为自动计算变量，设置非null的默认值则自动解绑
*
*
*
*
----------------------------------------------------------------------------------
*/

package org.lucky.exp.annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 
 * 绑定的成员变量会成为自动计算字段，若需要取消自动计算属性则动手设置值即可
* 支持绑定的类型 @see org.lucky.exp.annotation.BindType 的非基本类型
* @author FayeWong
* 
 */
@Target({ ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Calculation {
	String[] formula();//公式
	String format() default "#.#####"; //数据格式
}
