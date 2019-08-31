/** Copyright 2019 SAIC General Motors Corporation Ltd. All Rights Reserved.
*
* This software is published under the terms of the SGM Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
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
* @date 2019年8月28日
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
