package org.lucky.exp;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.lucky.exp.annotation.BindVar;
import org.lucky.exp.annotation.Condition;
import org.lucky.exp.cache.Cache;
import org.lucky.exp.cache.CacheToken;
import org.lucky.exp.exception.CallBackException;
import org.lucky.exp.missYaner.MissYaner;
import org.lucky.exp.tokenizer.FunctionToken;
import org.lucky.exp.tokenizer.NumberToken;
import org.lucky.exp.tokenizer.OperToken;
import org.lucky.exp.tokenizer.Token;
import org.lucky.exp.tokenizer.VariableToken;
import org.lucky.exp.util.Iterator;
import org.lucky.exp.util.LinkedStack;
/**
 * 计算结果处理，
 * 目的是在组装回对象时，把计算的记过给组装进来
 * @author FayeWong
 * @since 1.0
 *
 */
public class HandlerResult {
	/**
	 *  将计算出来的结果通过get方法组装到对象中
	 * @param configuration 配置类
	 * @param result 计算结果
	 * @param exp 被拆分的对象
	 * @throws CallBackException 回调异常
	 */
	private static  void Handler(Configuration configuration, double result, Map<Condition, Object> exp)
			throws CallBackException {
		Field field = (Field) exp.get(Condition.field);
		BindVar bind = field.getAnnotation(BindVar.class);
		result = Double.valueOf(new DecimalFormat(exp.get(Condition.format).toString()).format(result));
		try {
			field.set(exp.get(Condition.entity), result);
			if (bind != null) {
				// 从get中获取结果变量，get方法逻辑返回的值给下一个结果计算
				PropertyDescriptor pd = new PropertyDescriptor(field.getName(), exp.get(Condition.entity).getClass());
				Method getMethod = pd.getReadMethod();
				Object getResult = getMethod.invoke((Object) exp.get(Condition.entity));
				configuration.getVariables().put(bind.value(), (Double) getResult);
				configuration.getVariableNames().addAll(configuration.getVariables().keySet());
			}
		} catch (IllegalArgumentException e) {
			throw new CallBackException(e);
		} catch (IllegalAccessException e) {
			throw new CallBackException(e);
		} catch (InvocationTargetException e) {
			throw new CallBackException(e);
		} catch (IntrospectionException e) {
			throw new CallBackException("变量 '" + field.getName() + "' 没有get()方法", e);
		}
	}

	/**
	 * 从栈堆推送结果
	 *
	 * @author FayeWong
	 * @return 结果
	 * @throws CallBackException 回调异常
	 */
	private static  void evaluate(Configuration configuration, Map<String, Token[]> tokensMap,
			Iterator<Map<Condition, Object>> iterator) throws CallBackException {		
		while (iterator.hasNext()) {
			LinkedStack<Object> output = new LinkedStack<Object>();
			Map<Condition, Object> exp = iterator.removeNext();
			String expressionKey = (String) exp.get(Condition.expression);
			Token[] tokens = tokensMap.get(expressionKey);
			Field field = (Field) exp.get(Condition.field);
			for (int i = 0; i < tokens.length; i++) {				
				Token t = tokens[i];
				if (t.getType() == Token.TOKEN_NUMBER) {
					output.push(((NumberToken) t).getValue());
				} else if (t.getType() == Token.TOKEN_VARIABLE) {
					final String name = ((VariableToken) t).getName();
					final Double value = configuration.getVariables().get(name);
					try {
						if (value == null) {
							configuration.addErrors("' 公式 "+expressionKey+" ' 变量 ' "+field.getName()+" ',参数 ' "+name+" ' 为空\r\n");
							iterator.offerLast(exp);
							evaluate(configuration, tokensMap, iterator);
							return;
						} else {
							output.push(value);
						}
					}catch (StackOverflowError error) {
						throw new CallBackException("重新计算失败，有未知参数 ' "+" ' "+error);
					}
					
				} else if (t.getType() == Token.TOKEN_OPERATOR) {
					OperToken op = (OperToken) t;
					if (output.size() < op.getOper().getNumOperands()) {
						throw new CallBackException("变量 '" + field.getName() + "',可用于的操作数无效 '"
								+ op.getOper().getSymbol() + "' oper(操作数只接受1或2)");
					}
					if (op.getOper().getNumOperands() == 2) {
						/* 弹出操作数并推送操作结果 */
						double rightArg = (double)output.pop();
						double leftArg = (double)output.pop();
						output.push(op.getOper().call(leftArg, rightArg));
					} else if (op.getOper().getNumOperands() == 1) {
						/* 弹出操作数并推送操作结果 */
						double arg = (double)output.pop();
						output.push(op.getOper().call(arg));
					}
				} else if (t.getType() == Token.TOKEN_FUNCTION) {
					FunctionToken func = (FunctionToken) t;
					final int numArguments = func.getFunction().getNumArguments();
					if (output.size() < numArguments) {
						throw new CallBackException("变量' " + field.getName() + " ',无可用于计算的参数 '"
								+ func.getFunction().getName() + "' function");
					}
					/* 从堆栈收集参数 */
					Object[] args = new Object[numArguments];
					for (int j = numArguments - 1; j >= 0; j--) {
						args[j] = (Object)output.pop();
					}
					output.push(func.getFunction().call(args));
				}
			}
			if (output.size() > 1) {
				throw new CallBackException("变量 ' " + field.getName() + " ',输出队列中的参数无效。可能是函数的参数无法解析导致的.");
			}
			double result = (double)output.pop();
			Handler(configuration, result, exp);
		};
	}

	/**
	 * 从表达式中推送出来的结果组装到各个对象中
	 *
	 * @author FayeWong
	 * @param exps 拆分的计算对象
	 * @return 是否算计成功
	 * @throws CallBackException
	 */
	public static boolean evaluateObject(Configuration configuration,CacheToken cacheToken) throws CallBackException {
		final Iterator<Map<Condition, Object>> iterator = new Iterator<Map<Condition, Object>>(configuration.getPassExps());
		final Map<String,Token[]> tokensMap = new ConcurrentHashMap<String,Token[]>();
		while (iterator.hasNext()) {
			Map<Condition, Object> exp = iterator.next();
			Field field = (Field) exp.get(Condition.field);
			final String expression = (String) exp.get(Condition.expression);
			cacheToken  = Cache.getInstance().builder((cToken)->{
				if(cToken.openCache()) {
					Token[] cacheTokens = cToken.getToken(expression);
					if(cacheTokens == null) {
						cacheTokens = MissYaner.convertToRPN(expression, field, configuration);
						cToken.putTokens(expression, cacheTokens,cToken.expire());
					}
				}else {
					Token[] tokens = MissYaner.convertToRPN(expression, field, configuration);
					tokensMap.put(expression, tokens);
				}
			});
			
		}
		iterator.reset();
		evaluate(configuration, cacheToken.openCache() ? cacheToken.getTokensMap() : tokensMap, iterator);
		if(iterator.isEmpty()) {
			configuration.getErrors().clear();
			return true;
		}
		return false;
	}
}
