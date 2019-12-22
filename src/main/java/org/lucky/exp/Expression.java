/**
* @Project Name : LuckyExp
*
* @File name : Expression.java
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
package org.lucky.exp;

import static org.lucky.exp.HandlerResult.evaluateObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.lucky.exp.exception.CallBackException;
import org.lucky.exp.func.Funcs;
import org.lucky.exp.parent.Handle;
import org.lucky.exp.parent.OperResult;
import org.lucky.exp.tokenizer.Token;
import org.lucky.exp.tokenizer.VariableToken;

/**
 * 
 *
 * @author FayeWong
 * @since 1.0
 */
public class Expression {
	protected final Configuration configuration;
	private Serializable entity;
	private static Map<String, Double> createDefaultVariables() {
		final Map<String, Double> vars = new HashMap<String, Double>(4);
		vars.put("pi", Math.PI);
		vars.put("π", Math.PI);
		vars.put("φ", 1.61803398874d);
		vars.put("e", Math.E);
		return vars;
	}

	Expression(final Configuration configuration) {
		this.configuration = configuration;
		this.configuration.getVariables().putAll(createDefaultVariables());
		setVariables(configuration.getVariables());
	}

	public Expression setVariable(final String name, final double value) {
		this.checkVariableName(name);
		this.configuration.getVariables().put(name, Double.valueOf(value));
		return this;
	}

	private void checkVariableName(String name) {
		if (this.configuration.getUserFuncs().keySet().contains(name) || Funcs.getBuiltinFunction(name) != null) {
			throw new IllegalArgumentException("变量名称无效或函数名重复： '" + name + "'");
		}
	}

	public Expression setVariables(Map<String, Double> variables) {
		for (Map.Entry<String, Double> v : variables.entrySet()) {
			if (v.getValue() == null) {
				throw new IllegalArgumentException("变量名称 ' " + v.getKey() + "'没有值!");
			}
			this.setVariable(v.getKey(), v.getValue());
		}
		return this;
	}

	public Expression setEntity(Serializable entity) {
		this.entity = entity;
		return this;
	}

	public Set<String> getVariableNames() {
		final Set<String> variables = new HashSet<String>();
		for (final Token t : this.configuration.getTokens()) {
			if (t.getType() == Token.TOKEN_VARIABLE)
				variables.add(((VariableToken) t).getName());
		}
		return variables;
	}
	/**
	 * 普通计算
	 *  
	 */
	public void result(){
		try {
			evaluateObject(configuration,(HelloWorldThisIsJava)->{},true);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		} 
	}

	/**
	 * 针对比较精细的业务逻辑
	 * @param operResult 回调函数
	 * @throws CallBackException 回调异常
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void result(OperResult operResult) throws CallBackException {
		Handle handle = new Handle();
		handle.setT(entity);
		try {
			handle.setSuccess(evaluateObject(configuration,(HelloWorldThisIsJava)->{},false));			
		} catch (Exception e) {
			handle.setSuccess(false);
			throw new CallBackException(e);
		}finally {
			handle.setErrors(configuration.getErrors());
			operResult.setHandle(handle);
		}
	}
}
