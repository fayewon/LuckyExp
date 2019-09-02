/** Copyright 2019 SAIC General Motors Corporation Ltd. All Rights Reserved.
*
* This software is published under the terms of the SGM Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : LuckyExp
*
* @File name : Func.java
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
package org.lucky.exp.func;
/**
 * 函数对象
*
* @author FayeWong
* @date 2019年8月30日
 */
public abstract class Func {

    protected final String name;

    protected  int numArguments;

    /**
     * 使用给定的名称和参数数目创建新函数
     * 
     * @param 命名函数的名称
     * @param numArguments函数接受的参数数
     */
    public Func(String name, int numArguments) {
        if (numArguments < 0) {
            throw new IllegalArgumentException("参数数量不能小于0 '" +
                    name + "'");
        }
        if (!isValidFunctionName(name)) {
            throw new IllegalArgumentException("函数名无效 '" + name + "'");
        }
        this.name = name;
        this.numArguments = numArguments;

    }
    public Func(String name) {
    	this(name, 1);
    }
    public String getName() {
        return name;
    }
    public int getNumArguments() {
        return numArguments;
    }

    /**
     * 函数入口
    *
    * @author FayeWong
    * @param args 函数的参数，但是需要自定义函数参数数量  numArguments->自定义数量
    * 内置函数的参数数量也是自定义的
    * see org.lucky.exp.function.Funcs;
    * @return
     */
    public abstract double call(double... args);
    /**
     * 校验函数名是否有效
    *
    * @author FayeWong
    * @param name
    * @return
     */
    public static boolean isValidFunctionName(final String name) {
        if (name == null) {
            return false;
        }

        final int size = name.length();

        if (size == 0) {
            return false;
        }

        for (int i = 0; i < size; i++) {
            final char c = name.charAt(i);
            if (Character.isLetter(c) || c == '_') {
                continue;
            } else if (Character.isDigit(c) && i > 0) {
                continue;
            }
            return false;
        }
        return true;
    }
}