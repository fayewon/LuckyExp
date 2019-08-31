/** Copyright 2019 SAIC General Motors Corporation Ltd. All Rights Reserved.
*
* This software is published under the terms of the SGM Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : LuckyExp
*
* @File name : ArgumentSeparatorToken.java
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
package org.lucky.exp.tokenizer;
/**
 * 
* 表示函数中的参数分隔符 i.e: ','
* @author FayeWong
* @date 2019年8月28日
 */
class ArgumentSeparatorToken extends Token{

    ArgumentSeparatorToken() {
        super(Token.TOKEN_SEPARATOR);
    }
}
