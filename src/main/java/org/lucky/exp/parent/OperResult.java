/** 
 * @Project Name : LuckyExp
*
* @File name : OperResult.java
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
import java.util.concurrent.ExecutorService;
import org.lucky.exp.func.Func;
import org.lucky.exp.oper.Oper;
import org.lucky.exp.tokenizer.FunctionToken;
import org.lucky.exp.tokenizer.OperToken;
import org.lucky.exp.tokenizer.Token;
import org.lucky.exp.tokenizer.VariableToken;
/**
 * 结果集线程回调操作
*
* @author FayeWong
 * @param <T>
* @date 2019年8月29日
 */
public abstract class OperResult<T> {
	protected T t;
	protected boolean isSuccess;
	protected List<Map<String,String>> message;	
	public void set(T t) {
		this.t = t;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public void setMessage(List<Map<String, String>> message) {
		getValiMeg(message);
		this.message = message;
	}
	public void setObject(ExecutorService executor,T t,boolean isSuccess) {
		set(t);
		setSuccess(isSuccess);
		executor.execute(new Runnable() {
			@Override
			public void run() {
				executeAsync(t,isSuccess);
			}
		});	
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
	* 1：isSuccess = true
	* 2：isSuccess = false
	* 3：isSuccess = false
	 */
    public abstract void executeAsync(T t,boolean isSuccess); 
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
        Map<String,String> error = new HashMap<String,String>();
        String funcName = null;
        int count = 0;
        for (Token tok : tokens) {
        	
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
                    	error.put("message", "函数没有足够的参数 '" + func.getName() + "'");
                    	error.put("fieldName", field.getName());
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
                    Oper op = ((OperToken) tok).getOper();
                    if (op.getNumOperands() == 2) {
                        count--;
                    }
                    break;
            }
            
        }
		
		if (count < 1) { 
		      error.put("message", "操作数太少"); error.put("fieldName", field.getName());
		      error.put("funcName",funcName); errors.add(error); 
		 }
		 
        if (count > 1) {
        	error.put("message", "操作数太多");
        	error.put("fieldName", field.getName());
        	error.put("funcName",funcName);
        	errors.add(error);
        }   
        setMessage(errors);
    }
}
