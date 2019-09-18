/**
* @Project Name : LuckyExp
*
* @File name : OpenParenthesesToken.java
*
* @Author : FayeWong
*
* @Email : 50125289@qq.com
*
----------------------------------------------------------------------------------
*    Who        Version     Comments
* 1. FayeWong    1.0          左括号对象
*
*
*
*
----------------------------------------------------------------------------------
*/
package org.lucky.exp.tokenizer;
/**
 * <p>左括号token
 * @author FayeWong
 * @since 1.0
 */
public final class OpenParenthesesToken extends Token{

    OpenParenthesesToken() {
        super(TOKEN_PARENTHESES_OPEN);
    }
}
