/** 
* @Project Name : LuckyExp
*
* @File name : OperToken.java
*
* @Author : FayeWong
*
* @Email : 50125289@qq.com
*
----------------------------------------------------------------------------------
*    Who        Version     Comments
* 1. FayeWong    1.0        运算符对象
*
*
*
*
----------------------------------------------------------------------------------
*/
package org.lucky.exp.tokenizer;

import org.lucky.exp.oper.Oper;

/**
 * <p>运算符token</p>
 * @author FayeWong
 * @since 1.0
 */
public class OperToken extends Token{
    private final Oper oper;
    public OperToken(Oper op) {
        super(Token.TOKEN_OPERATOR);
        if (op == null) {
            throw new IllegalArgumentException("未知的运算符.");
        }
        this.oper = op;
    }

    /**
     * 获取运算符
     * @return  运算符
     */
    public Oper getOper() {
        return oper;
    }
}

