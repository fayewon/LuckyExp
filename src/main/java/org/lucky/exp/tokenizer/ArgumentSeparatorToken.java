/** 
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
