/** Copyright 2019 SAIC General Motors Corporation Ltd. All Rights Reserved.
*
* This software is published under the terms of the SGM Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : LuckyExp
*
* @File name : Operators.java
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

public abstract class Operators {
    private static final int INDEX_ADDITION = 0;
    private static final int INDEX_SUBTRACTION = 1;
    private static final int INDEX_MUTLIPLICATION = 2;
    private static final int INDEX_DIVISION = 3;
    private static final int INDEX_POWER = 4;
    private static final int INDEX_MODULO = 5;
    private static final int INDEX_UNARYMINUS = 6;
    private static final int INDEX_UNARYPLUS = 7;

    private static final Operator[] builtinOperators = new Operator[8];

    static {
        builtinOperators[INDEX_ADDITION]= new Operator("+", 2, true, Operator.PRECEDENCE_ADDITION) {
            @Override
            public double apply(final double... args) {
                return args[0] + args[1];
            }
        };
        builtinOperators[INDEX_SUBTRACTION]= new Operator("-", 2, true, Operator.PRECEDENCE_ADDITION) {
            @Override
            public double apply(final double... args) {
                return args[0] - args[1];
            }
        };
        builtinOperators[INDEX_UNARYMINUS]= new Operator("-", 1, false, Operator.PRECEDENCE_UNARY_MINUS) {
            @Override
            public double apply(final double... args) {
                return -args[0];
            }
        };
        builtinOperators[INDEX_UNARYPLUS]= new Operator("+", 1, false, Operator.PRECEDENCE_UNARY_PLUS) {
            @Override
            public double apply(final double... args) {
                return args[0];
            }
        };
        builtinOperators[INDEX_MUTLIPLICATION]= new Operator("*", 2, true, Operator.PRECEDENCE_MULTIPLICATION) {
            @Override
            public double apply(final double... args) {
                return args[0] * args[1];
            }
        };
        builtinOperators[INDEX_DIVISION]= new Operator("/", 2, true, Operator.PRECEDENCE_DIVISION) {
            @Override
            public double apply(final double... args) {
                if (args[1] == 0d) {
                    throw new ArithmeticException("被除数为0!");
                }
                return args[0] / args[1];
            }
        };
        builtinOperators[INDEX_POWER]= new Operator("^", 2, false, Operator.PRECEDENCE_POWER) {
            @Override
            public double apply(final double... args) {
                return Math.pow(args[0], args[1]);
            }
        };
        builtinOperators[INDEX_MODULO]= new Operator("%", 2, true, Operator.PRECEDENCE_MODULO) {
            @Override
            public double apply(final double... args) {
                if (args[1] == 0d) {
                    throw new ArithmeticException("被余数为0!");
                }
                return args[0] % args[1];
            }
        };
    }

    public static Operator getBuiltinOperator(final char symbol, final int numArguments) {
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
            default:
                return null;
        }
    }

}
