/** Copyright 2019 SAIC General Motors Corporation Ltd. All Rights Reserved.
*
* This software is published under the terms of the SGM Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : LuckyExp
*
* @File name : ValidationResult.java
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
package org.lucky.exp.parent;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lucky.exp.function.Func;
import org.lucky.exp.operator.Operator;
import org.lucky.exp.tokenizer.FunctionToken;
import org.lucky.exp.tokenizer.OperatorToken;
import org.lucky.exp.tokenizer.Token;
import org.lucky.exp.tokenizer.VariableToken;
/**
 * 检查错误的结果集
*
* @author FayeWong
 * @param <T>
* @date 2019年8月29日
 */
public abstract class ValiResult<T> {
	public void setT(T t,boolean isError) {
		getBean(t,isError);
	}
	protected abstract void getValiMeg(List<Map<String,String>> message);
	/**
	 * 
	*
	* @author FayeWong
	* @date 2019年8月30日
	* @param <T>
	* @param t
	* @param isError 1.成功计算完成，2.计算失败，3.产生CallBack异常均会及时返回t结果。
	* 1：isError = false
	* 2：isError = true
	* 3：isError = true
	 */
    public abstract <T> void getBean(T t,boolean isError);
    /**
     * 
    * 验证表达式是否符合规范
    * @author FayeWong
    * @date 2019年8月29日
    * @param tokens
    * @param variables
    * @param field
     */
    public void validate(Token[] tokens, Map<String, Double> variables,Field field) {
    	List<Map<String,String>> errors = new ArrayList<Map<String,String>>(0);
            /* 检查所有变量是否都有一个值集 */
        	Arrays.asList(tokens).stream().forEach((t)->{
        		Map<String,String> error = new HashMap<String,String>();
        		if (t.getType() == Token.TOKEN_VARIABLE) {
                    final String var = ((VariableToken) t).getName();
                    if (!variables.containsKey(var)) {
                    	error.put("message", "设置变量 '" + var + "' 尚未设置");
                    	error.put("fieldName", field.getName());
                        errors.add(error);
                    }
                }
        	});

    /* 检查操作数、函数和运算符的数目是否匹配。
         * 想法是增加操作数的计数器，减少操作数的计数器。
         * 当函数出现时，可用参数的数目必须大于大于或等于函数的预期参数数。
         * 计数必须始终大于1，并且在所有令牌之后正好为1已处理
     */
        String funcName = null;
        int count = 0;
        for (Token tok : tokens) {
        	Map<String,String> error = new HashMap<String,String>();
            switch (tok.getType()) {
                case Token.TOKEN_NUMBER:
                case Token.TOKEN_VARIABLE:
                    count++;
                    break;
                case Token.TOKEN_FUNCTION:
                    final Func func = ((FunctionToken) tok).getFunction();
                    funcName = func.getName();
                    final int argsNum = func.getNumArguments(); 
                    if (argsNum > count) {
                    	error.put("message", "没用足够的arguments '" + func.getName() + "'");
                    	error.put("fieldName", funcName);
                    	error.put("funcName",func.getName());
                        errors.add(error);
                    }
                    if (argsNum > 1) {
                        count -= argsNum - 1;
                    } else if (argsNum == 0) {
                        count++;
                    }
                    break;
                case Token.TOKEN_OPERATOR:
                    Operator op = ((OperatorToken) tok).getOperator();
                    if (op.getNumOperands() == 2) {
                        count--;
                    }
                    break;
            }
            if (count < 1) {
            	error.put("message", "操作数太少");
            	error.put("fieldName", field.getName());
            	error.put("funcName",funcName);
                errors.add(error);
            }
        }
        if (count > 1) {
        	Map<String,String> error = new HashMap<String,String>();
        	error.put("message", "操作数太多");
        	error.put("fieldName", field.getName());
        	error.put("funcName",funcName);
        	errors.add(error);
        }   
        getValiMeg(errors);
    }
}
