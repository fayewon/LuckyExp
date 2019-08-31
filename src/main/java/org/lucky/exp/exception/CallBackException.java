/** Copyright 2019 SAIC General Motors Corporation Ltd. All Rights Reserved.
*
* This software is published under the terms of the SGM Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
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
* @date 2019年8月31日
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
