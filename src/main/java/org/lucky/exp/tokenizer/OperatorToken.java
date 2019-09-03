/** 
* @Project Name : LuckyExp
*
* @File name : OperatorToken.java
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

import org.lucky.exp.oper.Oper;

/**
 * 运算符对象
 */
public class OperatorToken extends Token{
    private final Oper operator;
    public OperatorToken(Oper op) {
        super(Token.TOKEN_OPERATOR);
        if (op == null) {
            throw new IllegalArgumentException("Operator is unknown for token.");
        }
        this.operator = op;
    }

    /**
     * 获取运算符
     * @return  运算符
     */
    public Oper getOperator() {
        return operator;
    }
}

