/** 
 * @Project Name : LuckyExp
*
* @File name : CloseParenthesesToken.java
*
* @Author : FayeWong
*
* @Email : 50125289@qq.com
*
----------------------------------------------------------------------------------
*    Who        Version     Comments
* 1. FayeWong    1.0         右括号
*
*
*
*
----------------------------------------------------------------------------------
*/
package org.lucky.exp.tokenizer;


class CloseParenthesesToken extends Token {
    CloseParenthesesToken() {
        super(Token.TOKEN_PARENTHESES_CLOSE);
    }
}
