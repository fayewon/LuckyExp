/** 
 * @Project Name : LuckyExp
*
* @File name : FunctionToken.java
*
* @Author : FayeWong
*
* @Email : 50125289@qq.com
*
----------------------------------------------------------------------------------
*    Who        Version     Comments
* 1. FayeWong    1.0         函数对象
*
*
*
*
----------------------------------------------------------------------------------
*/
package org.lucky.exp.tokenizer;

import org.lucky.exp.func.Func;
/**
 * 
* <p>函数token</p>
* @author FayeWong
* @since 1.0
 */
public final class FunctionToken extends Token{
    private final Func function;
    public FunctionToken(final Func function) {
        super(Token.TOKEN_FUNCTION);
        this.function = function;
    }

    public Func getFunction() {
        return function;
    }   
}
