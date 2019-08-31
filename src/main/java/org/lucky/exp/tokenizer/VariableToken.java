/** Copyright 2019 SAIC General Motors Corporation Ltd. All Rights Reserved.
*
* This software is published under the terms of the SGM Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : LuckyExp
*
* @File name : VariableToken.java
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
 * 表示表达式中使用的setvariable
 */
public class VariableToken extends Token {
    private final String name;

    /**
     * 获取集合变量的名称
     * @return the 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param 命名集合变量的名称
     */
    public VariableToken(String name) {
        super(TOKEN_VARIABLE);
        this.name = name;
    }
}
