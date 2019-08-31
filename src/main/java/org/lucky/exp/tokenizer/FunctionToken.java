/** Copyright 2019 SAIC General Motors Corporation Ltd. All Rights Reserved.
*
* This software is published under the terms of the SGM Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
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
* 1. FayeWong    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
package org.lucky.exp.tokenizer;

import org.lucky.exp.function.Func;

public class FunctionToken extends Token{
    private final Func function;
    public FunctionToken(final Func function) {
        super(Token.TOKEN_FUNCTION);
        this.function = function;
    }

    public Func getFunction() {
        return function;
    }
}
