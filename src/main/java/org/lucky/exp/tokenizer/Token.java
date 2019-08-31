/** Copyright 2019 SAIC General Motors Corporation Ltd. All Rights Reserved.
*
* This software is published under the terms of the SGM Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
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
* 1. FayeWong    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
package org.lucky.exp.tokenizer;

/**
 * luckyExp用于标记表达式
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
