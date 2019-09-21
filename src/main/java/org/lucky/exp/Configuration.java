package org.lucky.exp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.lucky.exp.annotation.Condition;
import org.lucky.exp.func.Func;
import org.lucky.exp.oper.Oper;
import org.lucky.exp.tokenizer.Token;
/**
 * 副本
 * @author FayeWong
 *
 */
public class Configuration {
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
    /**是否追加隐式乘法**/
	protected  boolean implicitMultiplication = false;
	/**公式选择器**/
	protected Selector selector;
	public Token[] getTokens() {
		return tokens;
	}
	public void setTokens(Token[] tokens) {
		this.tokens = tokens;
	}
	public boolean isImplicitMultiplication() {
		return implicitMultiplication;
	}
	public void setImplicitMultiplication(boolean implicitMultiplication) {
		this.implicitMultiplication = implicitMultiplication;
	}
	public Selector getSelector() {
		return selector;
	}
	public void setSelector(Selector selector) {
		this.selector = selector;
	}
	public Map<String, Func> getUserFuncs() {
		return userFuncs;
	}
	public Map<String, Oper> getUserOperators() {
		return userOperators;
	}
	public Set<String> getVariableNames() {
		return variableNames;
	}
	public Map<String, Double> getVariables() {
		return variables;
	}
	public List<Map<Condition, Object>> getPassExps() {
		return passExps;
	}
	public List<Map<Condition, Object>> getWaitExps() {
		return waitExps;
	}
	
}
