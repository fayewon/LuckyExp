/** 
 * @Project Name : LuckyExp
*
* @File name : UnknownRunTimeException.java
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
 * 
*
* @author FayeWong
* 
 */
public class UnknownRuntimeException extends IllegalArgumentException{
	private static final long serialVersionUID = 1L;
	public UnknownRuntimeException() {
        super();
    }

    public UnknownRuntimeException(String s) {
        super(s);
    }
    public UnknownRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
    public UnknownRuntimeException(Throwable cause) {
        super(cause);
    }
}
