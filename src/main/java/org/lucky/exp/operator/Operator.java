/** Copyright 2019 SAIC General Motors Corporation Ltd. All Rights Reserved.
*
* This software is published under the terms of the SGM Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : LuckyExp
*
* @File name : Operator.java
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
package org.lucky.exp.operator;

/**
 * 表示可在表达式中使用的运算符的类
 */
public abstract class Operator {
    /**
     * 加法运算的优先值
     */
    public static final int PRECEDENCE_ADDITION = 500;
    /**
     * 减法运算的优先值
     */
    public static final int PRECEDENCE_SUBTRACTION = PRECEDENCE_ADDITION;
    /**
     * 乘法运算的优先值
     */
    public static final int PRECEDENCE_MULTIPLICATION = 1000;
    /**
     * 除法运算的优先值
     */
    public static final int PRECEDENCE_DIVISION = PRECEDENCE_MULTIPLICATION;
    /**
     * 模运算的优先值
     */
    public static final int PRECEDENCE_MODULO = PRECEDENCE_DIVISION;
    /**
     * 电源操作的优先值
     */
    public static final int PRECEDENCE_POWER = 10000;
    /**
     * 一元减号运算的优先值
     */
    public static final int PRECEDENCE_UNARY_MINUS = 5000;
    /**
     * 一元加运算的优先值
     */
    public static final int PRECEDENCE_UNARY_PLUS = PRECEDENCE_UNARY_MINUS;

    /**
     * 允许的运算符字符集
     */
    public static final char[] ALLOWED_OPERATOR_CHARS = { '+', '-', '*', '/', '%', '^', '!', '#','§', '$', '&', ';', ':', '~', '<', '>', '|', '='};

    protected final int numOperands;
    protected final boolean leftAssociative;
    protected final String symbol;
    protected final int precedence;

    /**
     * 创建用于表达式的新运算符
     * @param 符号操作员的符号
     * @param NumberOfOperands运算符接受的操作数（1或2）
     * @param LeftAssociative如果运算符是左Associative，则设置为true；如果运算符是右Associative，则设置为false。
     * @param 优先级运算符的优先级值
     */
    public Operator(String symbol, int numberOfOperands, boolean leftAssociative,
                    int precedence) {
        super();
        this.numOperands = numberOfOperands;
        this.leftAssociative = leftAssociative;
        this.symbol = symbol;
        this.precedence = precedence;
    }

    /**
     * 检查字符是否为允许的运算符字符
     * @param 检查字符
     * @return 如果char允许使用运算符符号，则为true，否则为false
     */
    public static boolean isAllowedOperatorChar(char ch) {
        for (char allowed: ALLOWED_OPERATOR_CHARS) {
            if (ch == allowed) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查操作员是否与左侧关联
     * @return true os运算符是左相关的，否则为false
     */
    public boolean isLeftAssociative() {
        return leftAssociative;
    }

    /**
     * 检查运算符的优先值
     * @return 优先值
     */
    public int getPrecedence() {
        return precedence;
    }

    /**
     * 对给定的操作数应用该操作
     * @param args该操作的操作数
     * @return 操作的计算结果
     */
    public abstract double apply(double ... args);

    /**
     * 获取运算符符号
     * @return 符号
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * 获取操作数
     * @return 操作数的数目
     */
    public int getNumOperands() {
        return numOperands;
    }
}
