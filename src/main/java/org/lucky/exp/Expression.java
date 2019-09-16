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
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import org.lucky.exp.annotation.BindVar;
import org.lucky.exp.annotation.Condition;
import org.lucky.exp.annotation.ExceptionCode;
import org.lucky.exp.exception.CallBackException;
import org.lucky.exp.exception.UnknownFunOrVarException;
import org.lucky.exp.func.Func;
import org.lucky.exp.func.Funcs;
import org.lucky.exp.missYaner.MissYaner;
import org.lucky.exp.oper.Oper;
import org.lucky.exp.parent.OperResult;
import org.lucky.exp.tokenizer.FunctionToken;
import org.lucky.exp.tokenizer.NumberToken;
import org.lucky.exp.tokenizer.OperToken;
import org.lucky.exp.tokenizer.Token;
import org.lucky.exp.tokenizer.VariableToken;
import org.lucky.exp.util.Iterator;
import org.lucky.exp.util.LinkedStack;
/**
 * 
 *
 * @author FayeWong
 * 
 */
public class Expression {
	/** 解析字符串获取的认证数组（函数，运算符，左括号，右括号，逗号...） **/
	private Token[] tokens;
	/** 条件参数 **/
	private final Map<String, Double> variables;
	/** 自定义函数名 **/
	private final Set<String> userFunctionNames;
	/** 直接结果集 **/
	private final List<Map<Condition, Object>> passExps;
	/** 等待直接结果集 **/
	private final List<Map<Condition, Object>> waitExps;
	/** 用户自定义的函数，不包含内置的函数 **/
	private final Map<String, Func> userFuncs;
	/** 用户自定义的运算符，不包含内置运算符 **/
	private final Map<String, Oper> userOperators;
	/** 参数名 **/
	private final Set<String> variableNames;
	/** 是否追加隐式乘法 **/
	private final boolean implicitMultiplication;
	private Serializable entity;
	private String unknownFunOrVarException;

	private static Map<String, Double> createDefaultVariables() {
		final Map<String, Double> vars = new HashMap<String, Double>(4);
		vars.put("pi", Math.PI);
		vars.put("π", Math.PI);
		vars.put("φ", 1.61803398874d);
		vars.put("e", Math.E);
		return vars;
	}

	Expression(final Map<String, Func> userFuncs, final Map<String, Oper> userOperators,
			final Set<String> variableNames, final boolean implicitMultiplication,
			final List<Map<Condition, Object>> passExps, final List<Map<Condition, Object>> waitExps,
			final Set<String> userFunctionNames) {
		this.userFuncs = userFuncs;
		this.userOperators = userOperators;
		this.variableNames = variableNames;
		this.implicitMultiplication = implicitMultiplication;
		this.passExps = passExps;
		this.waitExps = waitExps;
		this.userFunctionNames = userFunctionNames;
		this.variables = createDefaultVariables();
	}

	public Expression setVariable(final String name, final double value) {
		this.checkVariableName(name);
		this.variables.put(name, Double.valueOf(value));
		return this;
	}

	private void checkVariableName(String name) {
		if (this.userFunctionNames.contains(name) || Funcs.getBuiltinFunction(name) != null) {
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

	public Expression setTokens(Token[] tokens) {
		this.tokens = tokens;
		return this;
	}

	public Expression setEntity(Serializable entity) {
		this.entity = entity;
		return this;
	}

	public Set<String> getVariableNames() {
		final Set<String> variables = new HashSet<String>();
		for (final Token t : tokens) {
			if (t.getType() == Token.TOKEN_VARIABLE)
				variables.add(((VariableToken) t).getName());
		}
		return variables;
	}

	/**
	 * 从栈堆推送结果
	 *
	 * @author FayeWong
	 * @return 结果
	 * @throws CallBackException 回调异常
	 */
	private double evaluate(final Token[] tokens, final Field field) throws CallBackException {
		final LinkedStack<Double> output = new LinkedStack<Double>();
		for (int i = 0; i < tokens.length; i++) {
			Token t = tokens[i];
			if (t.getType() == Token.TOKEN_NUMBER) {
				output.push(((NumberToken) t).getValue());
			} else if (t.getType() == Token.TOKEN_VARIABLE) {
				final String name = ((VariableToken) t).getName();
				final Double value = this.variables.get(name);
				if (value == null) {
					throw new CallBackException("变量 '" + field.getName() + "',参数为空 '" + name + "'.");
				}
				output.push(value);
			} else if (t.getType() == Token.TOKEN_OPERATOR) {
				OperToken op = (OperToken) t;
				if (output.size() < op.getOper().getNumOperands()) {
					throw new CallBackException("变量 '" + field.getName() + "',可用于的操作数无效 '" + op.getOper().getSymbol()
							+ "' oper(操作数只接受1或2)");
				}
				if (op.getOper().getNumOperands() == 2) {
					/* 弹出操作数并推送操作结果 */
					double rightArg = output.pop();
					double leftArg = output.pop();
					output.push(op.getOper().call(leftArg, rightArg));
				} else if (op.getOper().getNumOperands() == 1) {
					/* 弹出操作数并推送操作结果 */
					double arg = output.pop();
					output.push(op.getOper().call(arg));
				}
			} else if (t.getType() == Token.TOKEN_FUNCTION) {
				FunctionToken func = (FunctionToken) t;
				final int numArguments = func.getFunction().getNumArguments();
				if (output.size() < numArguments) {
					throw new CallBackException(
							"变量 '" + field.getName() + "',无可用于计算的参数 '" + func.getFunction().getName() + "' function");
				}
				/* 从堆栈收集参数 */
				double[] args = new double[numArguments];
				for (int j = numArguments - 1; j >= 0; j--) {
					args[j] = output.pop();
				}
				output.push(func.getFunction().call(args));
			}
		}
		if (output.size() > 1) {
			throw new CallBackException("变量 '" + field.getName() + "',输出队列中的参数无效。可能是函数的参数无法解析导致的.");
		}
		return output.pop();
	}
	/**
	 * 从表达式中推送出来的结果组装到各个对象中
	 *
	 * @author FayeWong
	 * @param exps 拆分的计算对象
	 * @param operResult 回调对象
	 * @return 是否算计成功
	 * @throws CallBackException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean convertToBean(final List<Map<Condition, Object>> exps, final OperResult operResult) throws CallBackException {
		Iterator<Map<Condition, Object>> iterator = new Iterator<Map<Condition, Object>>(exps);
		boolean isSuccess = false;
		while(iterator.hasNext()) {
			Map<Condition, Object> exp = iterator.removeNext();
			Field field = (Field) exp.get(Condition.field);
			try {
				String expression = (String) exp.get(Condition.expression);
				BindVar bind = field.getAnnotation(BindVar.class);
				Token[] tokens = MissYaner.convertToRPN(expression, this.userFuncs, this.userOperators,
						this.variableNames, this.implicitMultiplication, field);
				this.tokens = tokens;
				if (operResult != null) {
					operResult.validate(tokens, this.variables, field);// 验证表达式是否合法
				}
				double result = evaluate(tokens, field);
				result = Double.valueOf(new DecimalFormat(exp.get(Condition.format).toString()).format(result));
				field.set(exp.get(Condition.entity), result);
				if (bind != null) {
					// 从get中获取结果变量，get方法逻辑返回的值给下一个结果计算
					PropertyDescriptor pd = new PropertyDescriptor(field.getName(),
							exp.get(Condition.entity).getClass());
					Method getMethod = pd.getReadMethod();
					Object getResult = getMethod.invoke((Object) exp.get(Condition.entity));
					this.variables.put(bind.value(), (Double) getResult);
					this.variableNames.addAll(variables.keySet());
				}
			} catch (UnknownFunOrVarException e) {
				unknownFunOrVarException = e.getMessage();
				iterator.offerLast();
				convertToBean(iterator.getList(), operResult);
			} catch (IllegalArgumentException e) {
				throw new CallBackException(ExceptionCode.C_10042.getCode(), e);
			} catch (IllegalAccessException e) {
				throw new CallBackException(ExceptionCode.C_10042.getCode(), e);
			} catch (InvocationTargetException e) {
				throw new CallBackException(ExceptionCode.C_10042.getCode(), e);
			} catch (IntrospectionException e) {
				throw new CallBackException("变量 '" + field.getName() + "' 没有get()方法", e);
			}
		}
		if (iterator.isEmpty())
			isSuccess = true;
		return isSuccess;
	}

	public boolean result() {
		if (!passExps.isEmpty()) {
			try {
				if (convertToBean(passExps, null)) {
					return convertToBean(waitExps, null);
				} else {
					return false;
				}
			} catch (CallBackException e) {
				throw new IllegalArgumentException(e);
			} catch (StackOverflowError error) {
				throw new IllegalArgumentException("重算失败，请检查" + unknownFunOrVarException + "或者相互引用");
			}
		}
		return false;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void result(ExecutorService executor, OperResult operResult) {
		if (!passExps.isEmpty()) {
			try {
				if (convertToBean(passExps, operResult)) {
					if (convertToBean(waitExps, operResult)) {
						operResult.setObject(executor, entity, true);
					} else {
						operResult.setObject(executor, entity, false);
					}
				} else {
					operResult.setObject(executor, entity, false);
				}
			} catch (CallBackException e) {
				operResult.setObject(executor, entity, false);
				throw new IllegalArgumentException(e);
			} catch (StackOverflowError error) {
				operResult.setObject(executor, entity, false);
				throw new IllegalArgumentException("重算失败，请检查" + unknownFunOrVarException + ",是否相互引用");
			}
		} else {
			operResult.setObject(executor, entity, false);
		}
	}
}
