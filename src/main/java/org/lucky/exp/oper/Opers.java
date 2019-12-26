/** 
 * @Project Name : LuckyExp
*
* @File name : Opers.java
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
 * <p>内置运算符</p>
 * @author FayeWong
 * @since 1.0
 */
public abstract class Opers {
    private static final int INDEX_ADDITION = 0;
    private static final int INDEX_SUBTRACTION = 1;
    private static final int INDEX_MUTLIPLICATION = 2;
    private static final int INDEX_DIVISION = 3;
    private static final int INDEX_POWER = 4;
    private static final int INDEX_MODULO = 5;
    private static final int INDEX_UNARYMINUS = 6;
    private static final int INDEX_UNARYPLUS = 7;
    private static final int INDEX_GREATER = 8;
    private static final int INDEX_LESS = 9;
    private static final int INDEX_EQUAL = 10;
    private static final Oper[] builtinOperators = new Oper[11];
    static {
        builtinOperators[INDEX_ADDITION]= new Oper("+", 2, true, Oper.PRECEDENCE_ADDITION) {
            @Override
            public Object call(final double... args) {
                return args[0] + args[1];
            }
        };
        builtinOperators[INDEX_SUBTRACTION]= new Oper("-", 2, true, Oper.PRECEDENCE_ADDITION) {
            @Override
            public Object call(final double... args) {
                return args[0] - args[1];
            }
        };
        /**负数**/
        builtinOperators[INDEX_UNARYMINUS]= new Oper("-", 1, false, Oper.PRECEDENCE_UNARY_MINUS) {
            @Override
            public Object call(final double... args) {
                return -args[0];
            }
        };
        /**正数**/
        builtinOperators[INDEX_UNARYPLUS]= new Oper("+", 1, false, Oper.PRECEDENCE_UNARY_PLUS) {
            @Override
            public Object call(final double... args) {
                return args[0];
            }
        };
        builtinOperators[INDEX_MUTLIPLICATION]= new Oper("*", 2, true, Oper.PRECEDENCE_MULTIPLICATION) {
            @Override
            public Object call(final double... args) {
                return args[0] * args[1];
            }
        };
        builtinOperators[INDEX_DIVISION]= new Oper("/", 2, true, Oper.PRECEDENCE_DIVISION) {
            @Override
            public Object call(final double... args) {
                if (args[1] == 0d) {
                    throw new ArithmeticException("被除数为0!");
                }
                return args[0] / args[1];
            }
        };
        builtinOperators[INDEX_POWER]= new Oper("^", 2, false, Oper.PRECEDENCE_POWER) {
            @Override
            public Object call(final double... args) {
                return Math.pow(args[0], args[1]);
            }
        };
        builtinOperators[INDEX_MODULO]= new Oper("%", 2, true, Oper.PRECEDENCE_MODULO) {
            @Override
            public Object call(final double... args) {
                if (args[1] == 0d) {
                    throw new ArithmeticException("被余数为0!");
                }
                return args[0] % args[1];
            }
        };
        /**
         *  主要为了搭配函数if设定的布尔判断取值
         * @see {@link org.lucky.exp.func.Funcs.java {if} }
         */
        builtinOperators[INDEX_GREATER]= new Oper(">", 2, true, Oper.PRECEDENCE_UNARY_MINUS) {
            @Override
            public Object call(final double... args) {  
                return args[0] > args[1];
            }
        };
        /**
         * 主要为了搭配函数if设定的布尔判断取值
         * @see {@link org.lucky.exp.func.Funcs.java {if} }
         */
        builtinOperators[INDEX_LESS]= new Oper("<", 2, true, Oper.PRECEDENCE_UNARY_PLUS) {
            @Override
            public Object call(final double... args) {
            	return args[0] < args[1];
            }
        };
        /**
         * 主要为了搭配函数if设定的布尔判断取值
         * @see {@link org.lucky.exp.func.Funcs.java {if} }
         */
        builtinOperators[INDEX_EQUAL]= new Oper("=", 2, true, Oper.PRECEDENCE_UNARY_PLUS) {
            @Override
            public Object call(final double... args) {
            	return args[0] = args[1];
            }
        };   
    }

    public static Oper getBuiltinOperator(final char symbol, final int numArguments) {
        switch(symbol) {
            case '+':
                if (numArguments != 1) {
                    return builtinOperators[INDEX_ADDITION];
                }else{
                    return builtinOperators[INDEX_UNARYPLUS];
                }
            case '-':
                if (numArguments != 1) {
                    return builtinOperators[INDEX_SUBTRACTION];
                }else{
                    return builtinOperators[INDEX_UNARYMINUS];
                }
            case '*':
                return builtinOperators[INDEX_MUTLIPLICATION];
            case '/':
                return builtinOperators[INDEX_DIVISION];
            case '^':
                return builtinOperators[INDEX_POWER];
            case '%':
                return builtinOperators[INDEX_MODULO];
            case '>':
                return builtinOperators[INDEX_GREATER];
            case '<':
                return builtinOperators[INDEX_LESS];
            case '=':
                return builtinOperators[INDEX_EQUAL];
            default:
                return null;
        }
    }
}
