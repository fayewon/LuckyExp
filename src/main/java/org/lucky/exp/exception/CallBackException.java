/**
* @Project Name : LuckyExp
*
* @File name : CallBackException.java
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
public class CallBackException extends Exception{
	private static final long serialVersionUID = 1L;
	public CallBackException() {
        super();
    }

    public CallBackException(String s) {
        super(s);
    }
    public CallBackException(String message, Throwable cause) {
        super(message, cause);
    }
    public CallBackException(Throwable cause) {
        super(cause);
    }
}
