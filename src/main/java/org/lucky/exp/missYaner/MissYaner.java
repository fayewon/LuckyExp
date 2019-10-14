/** 
 * @Project Name : LuckyExp
*
* @File name : ShuntingYard.java
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
package org.lucky.exp.missYaner;
import java.lang.reflect.Field;
import java.util.*;
import org.lucky.exp.Configuration;
import org.lucky.exp.exception.CallBackException;
import org.lucky.exp.tokenizer.OperToken;
import org.lucky.exp.tokenizer.Token;
import org.lucky.exp.tokenizer.Tokenizer;
import org.lucky.exp.util.LinkedStack;
/**
 * <p>中缀表达式转逆波兰表达式</p>
*
* @author FayeWong
* 
 */
public class MissYaner {
	/**
	 * 将一组标记从中缀表达式转逆波兰表达式
	 * 例如中缀表达式：5 + ( 6 – 4 / 2 ) * 3，逆波兰表达式：5 6 4 2 / - 3 * +
	 * @param expression expression 表达式要转换的表达式
	 * @param field field 抛出异常信息时使用
	 * @param configuration 配置对象
	 * @return 包含结果的{@link org.lucky.exp.tokenizer.Token} 数组
	 */
    public static Token[] convertToRPN(final String expression,Field field,final Configuration configuration){
        final LinkedStack<Token> stack = new LinkedStack<Token>();
        final List<Token> output = new ArrayList<Token>();
        final Tokenizer tokenizer = new Tokenizer(expression,field,configuration);
        while (tokenizer.hasNext()) {
            Token token = tokenizer.nextToken();
            switch (token.getType()) {
            case Token.TOKEN_NUMBER:                        	
            case Token.TOKEN_VARIABLE:
                output.add(token);
                break;
            case Token.TOKEN_FUNCTION:
                stack.push(token);
                break;
            case Token.TOKEN_SEPARATOR:
                while (!stack.isEmpty() && stack.peek().getType() != Token.TOKEN_PARENTHESES_OPEN) {
                    output.add(stack.pop());
                }
                if (stack.isEmpty() || stack.peek().getType() != Token.TOKEN_PARENTHESES_OPEN) {
                    throw new IllegalArgumentException("变量 '"+field.getName()+"',函数分隔符','位置错误或括号不匹配");
                }
                break;
            case Token.TOKEN_OPERATOR:
                while (!stack.isEmpty() && stack.peek().getType() == Token.TOKEN_OPERATOR) {
                    OperToken o1 = (OperToken) token;
                    OperToken o2 = (OperToken) stack.peek();
                    if (o1.getOper().getNumOperands() == 1 && o2.getOper().getNumOperands() == 2) {
                        break;
                    } else if ((o1.getOper().isLeftAssociative() && o1.getOper().getPrecedence() <= o2.getOper().getPrecedence())
                            || (o1.getOper().getPrecedence() < o2.getOper().getPrecedence())) {
                        output.add(stack.pop());
                    }else {
                        break;
                    }
                }
                stack.push(token);
                break;
            case Token.TOKEN_PARENTHESES_OPEN:
                stack.push(token);
                break;
            case Token.TOKEN_PARENTHESES_CLOSE:  
                while (stack.peek().getType() != Token.TOKEN_PARENTHESES_OPEN) {
                    output.add(stack.pop());
                }
                stack.pop();
                if (!stack.isEmpty() && stack.peek().getType() == Token.TOKEN_FUNCTION) {
                    output.add(stack.pop());
                }
                break;
            default:
                throw new IllegalArgumentException("未知的认证类型");
            }
        }
        while (!stack.isEmpty()) {
            Token t = stack.pop();
            if (t.getType() == Token.TOKEN_PARENTHESES_CLOSE || t.getType() == Token.TOKEN_PARENTHESES_OPEN) {
                throw new IllegalArgumentException("变量 '"+field.getName()+"',检测到不匹配的括号。请检查表达式");
            } else {
                output.add(t);
            }
        }
        return (Token[]) output.toArray(new Token[output.size()]);
    }
}