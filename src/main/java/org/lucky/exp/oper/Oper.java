/**
* @Project Name : LuckyExp
*
* @File name : Oper.java
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
package org.lucky.exp.oper;

/**
 * <p>运算符</p>
 * @author FayeWong
 * @since 1.0
 */
public abstract class Oper {
    /**
     * 加法运算的优先值
     */
    public static final int PRECEDENCE_ADDITION = 2;//巨浪-2
    /**
     * 减法运算的优先值
     */
    public static final int PRECEDENCE_SUBTRACTION = PRECEDENCE_ADDITION;
    /**
     * 乘法运算的优先值
     */
    public static final int PRECEDENCE_MULTIPLICATION = 5;//东风-5
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
    public static final int PRECEDENCE_POWER = 31;//东风-31
    /**
     * 一元减号运算的优先值
     */
    public static final int PRECEDENCE_UNARY_MINUS = 41;//东风-41
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
     * 
     * @param symbol 符号操作员的符号
     * @param numberOfOperands 运算符接受的操作数（1或2）
     * @param leftAssociative 如果运算符是左Associative，则设置为true；如果运算符是右Associative，则设置为false。
     * @param precedence 优先级运算符的优先级值
     */
    public Oper(String symbol, int numberOfOperands, boolean leftAssociative,
                    int precedence) {
        super();
        this.numOperands = numberOfOperands;
        this.leftAssociative = leftAssociative;
        this.symbol = symbol;
        this.precedence = precedence;
    }

    /**
     * 
     * @param ch 检查字符 检查字符是否为允许的运算符字符
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
     * 
     * @return true os运算符是左相关的，否则为false
     */
    public boolean isLeftAssociative() {
        return leftAssociative;
    }

    /**
     * 
     * @return 优先值
     */
    public int getPrecedence() {
        return precedence;
    }

    /**
     * 
     * @param args 该操作的操作数
     * @return 操作的计算结果
     */
    public abstract Object call(double ... args);

    /**
     * 
     * @return 符号
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * 
     * @return 操作数的数目
     */
    public int getNumOperands() {
        return numOperands;
    }
}
