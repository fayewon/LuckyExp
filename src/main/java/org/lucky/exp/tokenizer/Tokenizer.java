/** 
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
* 1. FayeWong    1.0        解析表达式，将表达式拆分成Token对象
*
*
*
*
----------------------------------------------------------------------------------
*/
package org.lucky.exp.tokenizer;
import java.lang.reflect.Field;
import java.util.Map;
import org.lucky.exp.Configuration;
import org.lucky.exp.exception.UnknownFunOrVarException;
import org.lucky.exp.func.Func;
import org.lucky.exp.func.Funcs;
import org.lucky.exp.oper.Oper;
import org.lucky.exp.oper.Opers;
/**
 * <p>将公式字符串转换成对应的token</p>
 * @author FayeWong
 * @since 1.0
 */
public class Tokenizer {

    private final char[] expression;
    public  final int expressionLength;
    private final Map<String, Func> userFunctions;
    private final Map<String, Oper> userOperators;       
    private final Field field;

    public int pos = 0;

    private Token lastToken;
    
    public Tokenizer(String expression, Field field,final Configuration configuration) {
        this.expression = expression.trim().toCharArray();
        this.expressionLength = this.expression.length;
        this.userFunctions = configuration.getUserFuncs();
        this.userOperators = configuration.getUserOperators();
        this.field = field;
    }

    

    public boolean hasNext() {
        return this.expression.length > pos;
    }
    /**
     * 
    * <p>获取下一个token</p>
    * @author FayeWong
    * @return 被拆分的Token对象
     */
    public Token nextToken(){
        char ch = expression[pos];
        /*不处理空白字符*/
        while (Character.isWhitespace(ch)) {
            ch = expression[++pos];
        }
        /*如果是数字则返回一个数字token*/
        if (Character.isDigit(ch) || ch == '.') {
            if (lastToken != null) {
                if (lastToken.getType() == Token.TOKEN_NUMBER) {
                    throw new IllegalArgumentException("无法解析字符 '" + ch + "' (编码:" + (int) ch + ") 位置 [" + pos + "]");
                } 
            }
            return parseNumberToken(ch);
            /*如果是逗号则返回一个逗号token*/
        } else if (isArgumentSeparator(ch)) {
            return parseArgumentSeparatorToken(ch);
            /*如果是左括号则返回一个左括号token*/
        } else if (isOpenParentheses(ch)) {
            
            return parseParentheses(true);
            /*如果是右括号则返回一个右括号token*/
        } else if (isCloseParentheses(ch)) {
            return parseParentheses(false);
            /*如果是运算符则返回一个运算符token*/
        } else if (Oper.isAllowedOperatorChar(ch)) {
            return parseOperatorToken(ch);
            /*如果是字母则返回一个函数或变量token*/
        } else if (isAlphabetic(ch) || ch == '_') {
            
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
    /**
     * 
     * @param open true:左括号 false:右括号
     * @return 左括号 || 右括号
     */
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
    /**
     * <p>思路：检查字符是否是字母、 _ 、 . @see #isVariableOrFunctionCharacter,
         * 如果是则长度+1，截取该长度的字符串判断变量池是否存在，判断是否在函数池内
         * 如果存在则返回一个变量token，否则如果存在返回一个函数token。最后下标位移到该长度后面</p>
     * @author FayeWong
     * @return 返回一个函数或者变量token
     */
    private Token parseFunctionOrVariable() {
        final int offset = this.pos;//需要截取的函数或者变量下标
        int testPos;
        int lastValidLen = 1;
        Token lastValidToken = null;
        int len = 1;//需要截取的函数或者变量长度
        if (isEndOfExpression(offset)) {
            this.pos++;
        }
        testPos = offset + len - 1;
        while (!isEndOfExpression(testPos) &&
                isVariableOrFunctionCharacter(expression[testPos])) {
            String name = new String(expression, offset, len);//函数名或者变量名
            /*
             * 如果name在变量池中则返回一个变量token
             */
            /**if (variableNames != null && variableNames.contains(name)) {
                lastValidLen = len;
                lastValidToken = new VariableToken(name);
            } else {
            	
                final Func f = getFunction(name);//如果该函数在自定义函数或者内置函数内，返回函数token
                if (f != null) {
                    lastValidLen = len;
                    lastValidToken = new FunctionToken(f);
                }
            }**/
            /**
                         * 这里返回函数名称验证，否则返回为验证名称的变量
                         * 因为从栈中推从结果的时候，会有变量名未找到的异常。
                         * 这个异常是由于关联性公式非顺序绑定导致，通过递归重算顺序计算出关联公式结果。
              * @see org.lucky.exp.HandlerResult#evaluate
             **/
            final Func f = getFunction(name);
            if(f != null) {
            	lastValidLen = len;
                lastValidToken = new FunctionToken(f);
            }else {
            	lastValidLen = len;
                lastValidToken = new VariableToken(name);
            }
            len++;
            testPos = offset + len - 1;
        }
        if (lastValidToken == null) {
            throw new UnknownFunOrVarException(field,new String(expression), pos, len);
        }
        /*注意最后下标要位移到最后检查到字母的后面*/
        pos += lastValidLen;
        lastToken = lastValidToken;
        return lastToken;
    }
    /**
     * 判断是否在自定义的函数名和内置函数中
     * @param name 函数名
     * @return 存在则返回
     */
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
    /**
     * @author FayeWong
     * @param firstChar 运算字符
     * @return 运算符token
     */
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
        /*注意最后下标要位移到最后检查到运算符的后面*/
        pos += symbol.length();
        lastToken = new OperToken(lastValid);
        return lastToken;
    }
    /**
     * 
     * @param symbol 运算字符
     * @return 运算符对象
     */
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
                    final Oper lastOp = ((OperToken) lastToken).getOper();
                    if (lastOp.getNumOperands() == 2 || (lastOp.getNumOperands() == 1 && !lastOp.isLeftAssociative())) {
                        argc = 1;
                    }
                }

            }
            op = Opers.getBuiltinOperator(symbol.charAt(0), argc);
        }
        return op;
    }
    /**
     * @author FayeWong
     * @param firstChar 数字字符
     * @return 数字token
     */
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
