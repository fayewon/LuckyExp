/** 
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
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.lucky.exp.Configuration;
import org.lucky.exp.ConvertToExp;
import org.lucky.exp.Selector;
import org.lucky.exp.annotation.ExceptionCode;
import org.lucky.exp.func.Func;
import org.lucky.exp.func.Funcs;
import org.lucky.exp.oper.Oper;
/**
 * 
* <p>幸运的表达式引擎</p>
* @author FayeWong
* @since 1.0
 */

public  abstract class  AbstractLuckyExpBuilder{
	protected final Configuration configuration = new Configuration();
    
    protected Serializable entity;
    private  void setVariables(Set<String> variableNames) {
        this.configuration.getVariableNames().addAll(variableNames);
    }
    public AbstractLuckyExpBuilder implicitMultiplication(boolean implicitMultiplication) {
    	this.configuration.setImplicitMultiplication(implicitMultiplication); 
        return this;
    }
    /**
	*
	* @author FayeWong
	* @param entity 对象结果集
	* @return this
	 */
    public AbstractLuckyExpBuilder build(Serializable entity) {
    	if(entity == null) throw new IllegalArgumentException(ExceptionCode.C_10046.getCode());
    	build(entity,null,null);
		return this;
	};
	/**
	*
	* @author FayeWong
	* @param entity 对象结果集
	* @param variables 公式选择器
	* @return this
	 */
	public AbstractLuckyExpBuilder build(Serializable entity,Map<String,Double> variables) {
		if(entity == null) throw new IllegalArgumentException(ExceptionCode.C_10046.getCode());
		if(variables == null) throw new IllegalArgumentException(ExceptionCode.C_10047.getCode());
		build(entity,variables,null);
		return this;
	};
	/**
	*
	* @author FayeWong
	* @param entity 对象结果集
	* @param variables 参数
	* @param selector 公式选择器
	* @return this
	 */
	public AbstractLuckyExpBuilder build(Serializable entity,Map<String,Double> variables,Selector selector) {
		if(entity == null) throw new IllegalArgumentException(ExceptionCode.C_10046.getCode());
		this.entity = entity;
		this.configuration.setSelector(selector);
		Class<?> clazz = entity.getClass();
		Field[] fields = clazz.getDeclaredFields();
		/* 检查是否有重复的变量/函数 */
		this.configuration.getVariableNames().forEach((var)->{
        	if (Funcs.getBuiltinFunction(var) != null || this.configuration.getUserFuncs().containsKey(var)) {
                throw new IllegalArgumentException(ExceptionCode.C_10045.getCode()+"[" + var + "]");
            }
        });       
		Arrays.asList(fields).stream().forEach((field)->{
			field.setAccessible(true);
			ConvertToExp.getInstance().assignment(entity, field, this.configuration);
			setVariables(this.configuration.getVariables().keySet());
			field.setAccessible(field.isAccessible());
		});   
		if(variables != null && !variables.isEmpty()) this.configuration.getVariables().putAll(variables);
		return this;
	};
	/**
	 * 
	*
	* @author FayeWong
	* @param func 自定义函数
	* @return this
	 */
	public AbstractLuckyExpBuilder func(Func func) {
        this.configuration.getUserFuncs().put(func.getName(), func);
        return this;
    }
	/**
	 * 
	*
	* @author FayeWong
	* @param funcs 自定义函数
	* @return this
	 */
	public AbstractLuckyExpBuilder funcs(Func... funcs) {
    	Arrays.asList(funcs).stream().forEach( f -> this.configuration.getUserFuncs().put(f.getName(), f));
        return this;
    }
	/**
	 * 
	*
	* @author FayeWong
	* @param funcs 自定义函数
	* @return this
	 */
	public AbstractLuckyExpBuilder funcs(List<Func> funcs) {
    	funcs.forEach(f -> this.configuration.getUserFuncs().put(f.getName(), f));
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
	public AbstractLuckyExpBuilder oper(Oper oper) {
        this.checkOperatorSymbol(oper);
        this.configuration.getUserOperators().put(oper.getSymbol(), oper);
        return this;
    }
	public AbstractLuckyExpBuilder oper(Oper... oper) {
    	Arrays.asList(oper).stream().forEach( o -> this.oper(o));
        return this;
    }
	public AbstractLuckyExpBuilder oper(List<Oper> oper) {
    	oper.forEach( o -> this.oper(o));
        return this;
    }
	/**
     * 
    *
    * @author FayeWong
    * @return 是否计算成功
     */
	public abstract boolean result();
	/**
	 * 
	*
	* @author FayeWong
	* @param executor 线程池
	* @param operResult 回调函数
	 */
	public  abstract void  result(OperResult operResult);
}
