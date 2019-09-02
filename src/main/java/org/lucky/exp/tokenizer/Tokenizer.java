/** Copyright 2019 SAIC General Motors Corporation Ltd. All Rights Reserved.
*
* This software is published under the terms of the SGM Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : LuckyExp
*
* @File name : Tokenizer.java
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
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

import org.lucky.exp.exception.UnknownFunOrVarException;
import org.lucky.exp.func.Func;
import org.lucky.exp.func.Funcs;
import org.lucky.exp.oper.Oper;
import org.lucky.exp.oper.Opers;
/**
 * 解析表达式，将表达式拆分成Token对象
 * @author FayeWong
 *
 */
public class Tokenizer {

    private final char[] expression;

    public final int expressionLength;

    private final Map<String, Func> userFunctions;

    private final Map<String, Oper> userOperators;

    private final Set<String> variableNames;

    private final boolean implicitMultiplication;
    
    private final Field field;

    public int pos = 0;

    private Token lastToken;
    
    public Tokenizer(String expression, final Map<String, Func> userFunctions,
            final Map<String, Oper> userOperators, final Set<String> variableNames, final boolean implicitMultiplication,Field field) {
        this.expression = expression.trim().toCharArray();
        this.expressionLength = this.expression.length;
        this.userFunctions = userFunctions;
        this.userOperators = userOperators;
        this.variableNames = variableNames;
        this.implicitMultiplication = implicitMultiplication;
        this.field = field;
    }

    

    public boolean hasNext() {
        return this.expression.length > pos;
    }
    /**
     * 
    *
    * @author FayeWong
    * @date 2019年8月28日
    * @return 被拆分的Token对象
     */
    public Token nextToken(){
        char ch = expression[pos];
        //不处理空白字符
        while (Character.isWhitespace(ch)) {
            ch = expression[++pos];
        }
        if (Character.isDigit(ch) || ch == '.') {
            if (lastToken != null) {
                if (lastToken.getType() == Token.TOKEN_NUMBER) {
                    throw new IllegalArgumentException("无法解析字符 '" + ch + "' (编码:" + (int) ch + ") 位置 [" + pos + "]");
                } else if (implicitMultiplication && (lastToken.getType() != Token.TOKEN_OPERATOR
                        && lastToken.getType() != Token.TOKEN_PARENTHESES_OPEN
                        && lastToken.getType() != Token.TOKEN_FUNCTION
                        && lastToken.getType() != Token.TOKEN_SEPARATOR)) {
                    // 插入隐式乘法标记
                    lastToken = new OperatorToken(Opers.getBuiltinOperator('*', 2));
                    return lastToken;
                }
            }
            return parseNumberToken(ch);
        } else if (isArgumentSeparator(ch)) {
            return parseArgumentSeparatorToken(ch);
        } else if (isOpenParentheses(ch)) {
            if (lastToken != null && implicitMultiplication &&
                    (lastToken.getType() != Token.TOKEN_OPERATOR
                            && lastToken.getType() != Token.TOKEN_PARENTHESES_OPEN
                            && lastToken.getType() != Token.TOKEN_FUNCTION
                            && lastToken.getType() != Token.TOKEN_SEPARATOR)) {
                // 插入隐式乘法标记
                lastToken = new OperatorToken(Opers.getBuiltinOperator('*', 2));
                return lastToken;
            }
            return parseParentheses(true);
        } else if (isCloseParentheses(ch)) {
            return parseParentheses(false);
        } else if (Oper.isAllowedOperatorChar(ch)) {
            return parseOperatorToken(ch);
        } else if (isAlphabetic(ch) || ch == '_') {
            // 分析可以是集合变量或函数的名称
            if (lastToken != null && implicitMultiplication &&
                    (lastToken.getType() != Token.TOKEN_OPERATOR
                            && lastToken.getType() != Token.TOKEN_PARENTHESES_OPEN
                            && lastToken.getType() != Token.TOKEN_FUNCTION
                            && lastToken.getType() != Token.TOKEN_SEPARATOR)) {
                // 插入隐式乘法标记
                lastToken = new OperatorToken(Opers.getBuiltinOperator('*', 2));
                return lastToken;
            }
            return parseFunctionOrVariable();

        }
        throw new IllegalArgumentException("无法解析字符 '" + ch + "' (编码:" + (int) ch + ") 位置 [" + pos + "]");
    }

    private Token parseArgumentSeparatorToken(char ch) {
        this.pos++;
        this.lastToken = new ArgumentSeparatorToken();
        return lastToken;
    }

    private boolean isArgumentSeparator(char ch) {
        return ch == ',';
    }

    private Token parseParentheses(final boolean open) {
        if (open) {
            this.lastToken = new OpenParenthesesToken();
        } else {
            this.lastToken = new CloseParenthesesToken();
        }
        this.pos++;
        return lastToken;
    }
    private boolean isOpenParentheses(char ch) {
        return ch == '(' || ch == '{' || ch == '[';
    }

    private boolean isCloseParentheses(char ch) {
        return ch == ')' || ch == '}' || ch == ']';
    }

    private Token parseFunctionOrVariable() {
        final int offset = this.pos;
        int testPos;
        int lastValidLen = 1;
        Token lastValidToken = null;
        int len = 1;
        if (isEndOfExpression(offset)) {
            this.pos++;
        }
        testPos = offset + len - 1;
        while (!isEndOfExpression(testPos) &&
                isVariableOrFunctionCharacter(expression[testPos])) {
            String name = new String(expression, offset, len);
            if (variableNames != null && variableNames.contains(name)) {
                lastValidLen = len;
                lastValidToken = new VariableToken(name);
            } else {
                final Func f = getFunction(name);
                if (f != null) {
                    lastValidLen = len;
                    lastValidToken = new FunctionToken(f);
                }
            }
            len++;
            testPos = offset + len - 1;
        }
        if (lastValidToken == null) {
            throw new UnknownFunOrVarException(field,new String(expression), pos, len);
        }
        pos += lastValidLen;
        lastToken = lastValidToken;
        return lastToken;
    }

    private Func getFunction(String name) {
        Func f = null;
        if (this.userFunctions != null) {
            f = this.userFunctions.get(name);
        }
        if (f == null) {
            f = Funcs.getBuiltinFunction(name);
        }
        return f;
    }

    private Token parseOperatorToken(char firstChar) {
        final int offset = this.pos;
        int len = 1;
        final StringBuilder symbol = new StringBuilder();
        Oper lastValid = null;
        symbol.append(firstChar);

        while (!isEndOfExpression(offset + len)  && Oper.isAllowedOperatorChar(expression[offset + len])) {
            symbol.append(expression[offset + len++]);
        }

        while (symbol.length() > 0) {
            Oper op = this.getOperator(symbol.toString());
            if (op == null) {
                symbol.setLength(symbol.length() - 1);
            }else{
                lastValid = op;
                break;
            }
        }

        pos += symbol.length();
        lastToken = new OperatorToken(lastValid);
        return lastToken;
    }

    private Oper getOperator(String symbol) {
        Oper op = null;
        if (this.userOperators != null) {
            op = this.userOperators.get(symbol);
        }
        if (op == null && symbol.length() == 1) {
            int argc = 2;
            if (lastToken == null) {
                argc = 1;
            } else {
                int lastTokenType = lastToken.getType();
                if (lastTokenType == Token.TOKEN_PARENTHESES_OPEN || lastTokenType == Token.TOKEN_SEPARATOR) {
                    argc = 1;
                } else if (lastTokenType == Token.TOKEN_OPERATOR) {
                    final Oper lastOp = ((OperatorToken) lastToken).getOperator();
                    if (lastOp.getNumOperands() == 2 || (lastOp.getNumOperands() == 1 && !lastOp.isLeftAssociative())) {
                        argc = 1;
                    }
                }

            }
            op = Opers.getBuiltinOperator(symbol.charAt(0), argc);
        }
        return op;
    }

    private Token parseNumberToken(final char firstChar) {
        final int offset = this.pos;
        int len = 1;
        this.pos++;
        if (isEndOfExpression(offset + len)) {
            lastToken = new NumberToken(Double.parseDouble(String.valueOf(firstChar)));
            return lastToken;
        }
        while (!isEndOfExpression(offset + len) &&
                isNumeric(expression[offset + len], expression[offset + len - 1] == 'e' ||
                        expression[offset + len - 1] == 'E')) {
            len++;
            this.pos++;
        }
        // 检查E是否在末端
        if (expression[offset + len - 1] == 'e' || expression[offset + len - 1] == 'E') {
            // 因为e在末尾，所以它不是数字的一部分，需要回滚
            len--;
            pos--;
        }
        lastToken = new NumberToken(expression, offset, len);
        return lastToken;
    }

    private static boolean isNumeric(char ch, boolean lastCharE) {
        return Character.isDigit(ch) || ch == '.' || ch == 'e' || ch == 'E' ||
                (lastCharE && (ch == '-' || ch == '+'));
    }

    public static boolean isAlphabetic(int codePoint) {
        return Character.isLetter(codePoint);
    }

    public static boolean isVariableOrFunctionCharacter(int codePoint) {
        return isAlphabetic(codePoint) ||
                Character.isDigit(codePoint) ||
                codePoint == '_' ||
                codePoint == '.';
    }

    private boolean isEndOfExpression(int offset) {
        return this.expressionLength <= offset;
    }
}
