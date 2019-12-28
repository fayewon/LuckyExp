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
import java.util.Objects;
import java.util.Set;
import org.lucky.exp.Configuration;
import org.lucky.exp.ConvertToExp;
import org.lucky.exp.Selector;
import org.lucky.exp.annotation.ExceptionCode;
import org.lucky.exp.exception.LuckyExpEvaluateException;
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
   /*
	*           N777777777NO                                                                                           
	*         N7777777777777N                                                                                     
	*        M777777777777777N                                                                                   
	*        $N877777777D77777M                                                                                 
	*       N M77777777ONND777M                                                                               
	*       MN777777777NN  D777                                                                               
	*     N7ZN777777777NN ~M7778                                                                            
	*    N777777777777MMNN88777N                                                                            
	*    N777777777777MNZZZ7777O
	*    DZN7777O77777777777777
	*     N7OONND7777777D77777N
	*      8$M++++?N???$77777$
	*       M7++++N+M77777777N
	*        N77O777777777777$                              M                            
	*          DNNM$$$$777777N                              D
	*         N$N:=N$777N7777M                             NZ
	*        77Z::::N777777777                          ODZZZ
	*       77N::::::N77777777M                         NNZZZ$
	*     $777:::::::77777777MN                        ZM8ZZZZZ
	*     777M::::::Z7777777Z77                        N++ZZZZNN
	*    7777M:::::M7777777$777M                       $++IZZZZM
    *   M777$:::::N777777$M7777M                       +++++ZZZDN
	*     NN$::::::7777$$M777777N                      N+++ZZZZNZ
	*       N::::::N:7$O:77777777                      N++++ZZZZN
	*       M::::::::::::N77777777+                   +?+++++ZZZM
	*       8::::::::::::D77777777M                    O+++++ZZ                           
	*        ::::::::::::M777777777N                      O+?D                              
	*        M:::::::::::M77777777778                     77=                                 
	*        D=::::::::::N7777777777N                    777                                    
	*       INN===::::::=77777777777N                  I777N                                     
	*      ?777N========N7777777777787M               N7777
	*      77777$D======N77777777777N777N?         N777777
	*     I77777$$$N7===M$$77777777$77777777$MMZ77777777N
    *      $$$$$$$$$$$NIZN$$$$$$$$$M$$7777777777777777ON
    *       M$$$$$$$$M    M$$$$$$$$N=N$$$$7777777$$$ND
    *      O77Z$$$$$$$     M$$$$$$$$MNI==$DNNNNM=~N
	*   7 :N MNN$$$$M$      $$$777$8      8D8I
	*     NMM.:7O           777777778                                                                  
	*                       7777777MN
    *                       M NO .7:
	*                       M   :   M
	*                            8
	*                            
	*/
	protected final Configuration configuration = new Configuration();    
    protected Serializable entity;
    private  void setVariables(Set<String> variableNames) {
        this.configuration.getVariableNames().addAll(variableNames);
    }
    /**
	*
	* @author FayeWong
	* @param entity 对象结果集
	* @return this
	 */
    public AbstractLuckyExpBuilder build(Serializable entity) {
    	Objects.requireNonNull(entity);
    	build(entity,null,null);
		return this;
	};
	/**
	*
	* @author FayeWong
	* @param entity 对象结果集
	* @param variables 追加变量
	* @return this
	 */
	public AbstractLuckyExpBuilder build(Serializable entity,Map<String,Double> variables) {
		Objects.requireNonNull(entity);
		Objects.requireNonNull(variables);
		build(entity,variables,null);
		return this;
	};
	/**
	 * @author FayeWong
	 * @param entity 对象结果集
	 * @param selector 公式选择器
	 * @return
	 */
	public AbstractLuckyExpBuilder build(Serializable entity,Selector selector) {
		Objects.requireNonNull(entity);
		build(entity,null,selector);
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
		Objects.requireNonNull(entity);
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
	public AbstractLuckyExpBuilder addFunc(Func func) {
		Objects.requireNonNull(func);
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
	public AbstractLuckyExpBuilder addFunc(Func... funcs) {
		Objects.requireNonNull(funcs);
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
	public AbstractLuckyExpBuilder addFunc(List<Func> funcs) {
		Objects.requireNonNull(funcs);
    	funcs.forEach(f -> this.configuration.getUserFuncs().put(f.getName(), f));
        return this;
    }  
	private void checkOperatorSymbol(Oper op) {
        String name = op.getSymbol();
        for (char ch : name.toCharArray()) {
            if(!Oper.isAllowedOperatorChar(ch)) {
            	throw new IllegalArgumentException("操作符无效：'" + name + "'");
        	}
        }
    }
	public AbstractLuckyExpBuilder addOper(Oper oper) {
		Objects.requireNonNull(oper);
        this.checkOperatorSymbol(oper);
        this.configuration.getUserOperators().put(oper.getSymbol(), oper);
        return this;
    }
	public AbstractLuckyExpBuilder addOper(Oper... oper) {
		Objects.requireNonNull(oper);
    	Arrays.asList(oper).stream().forEach( o -> this.addOper(o));
        return this;
    }
	public AbstractLuckyExpBuilder addOper(List<Oper> oper) {
		Objects.requireNonNull(oper);
    	oper.forEach( o -> this.addOper(o));
        return this;
    }
	public AbstractLuckyExpBuilder addCache(boolean openCache) {
		Objects.requireNonNull(openCache);
		Configuration.openCache = openCache;
		return this;
	}
	public AbstractLuckyExpBuilder addCache(boolean openCache,int expire) {
		Objects.requireNonNull(openCache);
		Configuration.openCache = openCache;
		Configuration.expire = expire;
		return this;
	}
	/**
     * 
    *
    * @author FayeWong
    * 
     */
	public abstract void result();
	/**
	 * 
	*
	* @author FayeWong
	* @param operResult 回调函数
	* @throws LuckyExpEvaluateException 回调异常
	 */
	public  abstract void  result(OperResult operResult) throws LuckyExpEvaluateException;
}
