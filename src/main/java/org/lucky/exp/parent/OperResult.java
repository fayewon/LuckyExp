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
	* @param t 结果
	* @param isSuccess 1.成功计算完成，2.计算失败，3.产生CallBack异常均会及时返回t结果。
	 */
    void accept(Handle<?> handle); 
}
