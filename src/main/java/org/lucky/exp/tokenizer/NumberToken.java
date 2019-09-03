/** 
 * @Project Name : LuckyExp
*
* @File name : NumberToken.java
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
 * 数字对象
 */
public final class NumberToken extends Token {
    private final double value;
    public NumberToken(double value) {
        super(TOKEN_NUMBER);
        this.value = value;
    }

    NumberToken(final char[] expression, final int offset, final int len) {
        this(Double.parseDouble(String.valueOf(expression, offset, len)));
    }

    /**
     * 获取数字的值
     * @return the 该值
     */
    public double getValue() {
        return value;
    }
}
