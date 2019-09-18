/** 
 * @Project Name : LuckyExp
*
* @File name : Token.java
*
* @Author : FayeWong
*
* @Email : 50125289@qq.com
*
----------------------------------------------------------------------------------
*    Who        Version     Comments
* 1. FayeWong    1.0         luckyExp表达式对象
*
*
*
*
----------------------------------------------------------------------------------
*/
package org.lucky.exp.tokenizer;

/**
 * token
 * @author FayeWong
 *
 */
public abstract class Token {
    public static final short TOKEN_NUMBER = 1;
    public static final short TOKEN_OPERATOR = 2;
    public static final short TOKEN_FUNCTION = 3;
    public static final short TOKEN_PARENTHESES_OPEN = 4;
    public static final short TOKEN_PARENTHESES_CLOSE = 5;
    public static final short TOKEN_VARIABLE = 6;
    public static final short TOKEN_SEPARATOR = 7;
    public static final short TOKEN_ANNOTATION = 8;

    protected final int type;

    Token(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

}
