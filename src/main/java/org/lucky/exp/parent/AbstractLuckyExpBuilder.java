/** Copyright 2019 SAIC General Motors Corporation Ltd. All Rights Reserved.
*
* This software is published under the terms of the SGM Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : LuckyExp
*
* @File name : AbstractLuckyExpBuilder.java
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
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;

import org.lucky.exp.ConvertToExp;
import org.lucky.exp.Selector;
import org.lucky.exp.annotation.Condition;
import org.lucky.exp.annotation.ExceptionCode;
import org.lucky.exp.func.Func;
import org.lucky.exp.func.Funcs;
import org.lucky.exp.oper.Oper;
import org.lucky.exp.tokenizer.Token;
/**
 * 
* 幸运的表达式引擎
* @author FayeWong
* @date 2019年8月29日
 */

public  abstract class  AbstractLuckyExpBuilder{
	/**解析字符串获取的认证数组（函数，运算符，左括号，右括号，逗号...）**/
	protected  Token[] tokens;
	/**用户自定义的函数，不包含内置的函数**/
	protected  final Map<String, Func> userFuncs = new HashMap<String, Func>(4);
	/**用户自定义的运算符，不包含内置运算符**/
	protected  final Map<String, Oper> userOperators = new HashMap<String, Oper>(4);
	/**参数名**/
    protected  final Set<String> variableNames = new HashSet<String>(4);
    /**参数**/
    protected  final Map<String, Double> variables = new HashMap<String, Double>(0); 
    /**计算公式和输入结果**/
    protected  final List<Map<Condition, Object>> passExps = new LinkedList<Map<Condition, Object>>(); 
    /**待计算公式和输入结果**/
    protected  final List<Map<Condition, Object>> waitExps = new LinkedList<Map<Condition, Object>>(); 
    /**重算上限**/
    protected  int recalLimit = 100;
    /**是否追加隐式乘法**/
    protected  boolean implicitMultiplication = false;
    
    protected Serializable entity;
    private  void setVariables(Set<String> variableNames) {
        this.variableNames.addAll(variableNames);
    }
    public AbstractLuckyExpBuilder setRecalLimit(int recalLimit) {
    	this.recalLimit = recalLimit;
    	return this;
    }
    public AbstractLuckyExpBuilder implicitMultiplication(boolean enabled) {
        this.implicitMultiplication = enabled;
        return this;
    }
    /**
	 * 建立一个幸运表达式^。^
	*
	* @author FayeWong
	* @date 2019年8月30日
	* @param entity 对象结果集
	* @return
	 */
    public AbstractLuckyExpBuilder build(Serializable entity) {
    	if(entity == null) throw new IllegalArgumentException(ExceptionCode.C_10046.getCode());
    	build(entity,null,null);
		return this;
	};
	/**
	 * 建立一个幸运表达式^。^
	*
	* @author FayeWong
	* @date 2019年8月30日
	* @param entity 对象结果集
	* @param variables 公式选择器
	* @return
	 */
	public AbstractLuckyExpBuilder build(Serializable entity,Map<String,Double> variables) {
		if(entity == null) throw new IllegalArgumentException(ExceptionCode.C_10046.getCode());
		if(variables == null) throw new IllegalArgumentException(ExceptionCode.C_10047.getCode());
		build(entity,variables,null);
		return this;
	};
	/**
	 * 建立一个幸运表达式^。^
	*
	* @author FayeWong
	* @date 2019年8月30日
	* @param entity 对象结果集
	* @param variables 参数
	* @param selector 公式选择器
	* @return
	 */
	public AbstractLuckyExpBuilder build(Serializable entity,Map<String,Double> variables,Selector selector) {
		if(entity == null) throw new IllegalArgumentException(ExceptionCode.C_10046.getCode());
		this.entity = entity;
		Class<?> clazz = entity.getClass();
		Field[] fields = clazz.getDeclaredFields();
		/* 检查是否有重复的变量/函数 */
        variableNames.forEach((var)->{
        	if (Funcs.getBuiltinFunction(var) != null || userFuncs.containsKey(var)) {
                throw new IllegalArgumentException(ExceptionCode.C_10045.getCode()+"[" + var + "]");
            }
        });       
		Arrays.asList(fields).stream().forEach((field)->{
			field.setAccessible(true);
			ConvertToExp.getInstance().assignment(entity, field, this.variables, selector,this.passExps,this.waitExps);
			setVariables(this.variables.keySet());
			field.setAccessible(field.isAccessible());
		});   
		if(variables != null && !variables.isEmpty()) this.variables.putAll(variables);
		return this;
	};
	/**
	 * 自定义函数
	*
	* @author FayeWong
	* @date 2019年8月30日
	* @param func
	* @return
	 */
	public AbstractLuckyExpBuilder func(Func func) {
        this.userFuncs.put(func.getName(), func);
        return this;
    }
	/**
	 * 自定义函数
	*
	* @author FayeWong
	* @date 2019年8月30日
	* @param funcs
	* @return
	 */
	public AbstractLuckyExpBuilder funcs(Func... funcs) {
    	Arrays.asList(funcs).stream().forEach( f -> this.userFuncs.put(f.getName(), f));
        return this;
    }
	/**
	 * 自定义函数
	*
	* @author FayeWong
	* @date 2019年8月30日
	* @param funcs
	* @return
	 */
	public AbstractLuckyExpBuilder funcs(List<Func> funcs) {
    	funcs.forEach(f -> this.userFuncs.put(f.getName(), f));
        return this;
    }  
	public void checkOperatorSymbol(Oper op) {
        String name = op.getSymbol();
        for (char ch : name.toCharArray()) {
            if(!Oper.isAllowedOperatorChar(ch)) {
            	throw new IllegalArgumentException("操作符无效：'" + name + "'");
        	}
        }
    }
	public AbstractLuckyExpBuilder operator(Oper operator) {
        this.checkOperatorSymbol(operator);
        this.userOperators.put(operator.getSymbol(), operator);
        return this;
    }
	public AbstractLuckyExpBuilder operator(Oper... operators) {
    	Arrays.asList(operators).stream().forEach( o -> this.operator(o));
        return this;
    }
	public AbstractLuckyExpBuilder operator(List<Oper> operators) {
    	operators.forEach( o -> this.operator(o));
        return this;
    }
	/**
     * 普通结果集
    *
    * @author FayeWong
    * @date 2019年8月30日
     */
	public abstract boolean result();
	/**
	 * 
	*
	* @author FayeWong
	* @date 2019年8月31日
	* @param valiResult 带回调函数错误结果集
	 */
	public  abstract boolean result(ExecutorService executor,OperResult operResult);
}
