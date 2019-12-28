/**
* @Project Name : LuckyExp
*
* @File name : LuckyExpEvaluateException.java
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
package org.lucky.exp.exception;
/**
 * 验证出结果的错误信息会发生此异常
*
* @author FayeWong
* 
 */
public class LuckyExpEvaluateException extends Exception{
	private static final long serialVersionUID = 1L;
	public LuckyExpEvaluateException() {
        super();
    }

    public LuckyExpEvaluateException(String s) {
        super(s);
    }
    public LuckyExpEvaluateException(String message, Throwable cause) {
        super(message, cause);
    }
    public LuckyExpEvaluateException(Throwable cause) {
        super(cause);
    }
}
