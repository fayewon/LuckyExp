/** 
 * @Project Name : LuckyExp
*
* @File name : OperResult.java
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
package org.lucky.exp.parent;
/**
 * 回调结果
*
* @author FayeWong
 * 
 */
@FunctionalInterface
public interface OperResult{
	default void setHandle(Handle<?> handle) {
		accept(handle);
	};
	/**
	 * 
	*
	* @author FayeWong
	* @param handle 结果
	* @since 1.0
	 */
    void accept(Handle<?> handle); 
}
