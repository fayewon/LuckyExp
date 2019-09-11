/** 
 * @Project Name : LuckyExp
*
* @File name : FunctionToken.java
*
* @Author : FayeWong
*
* @Email : 50125289@qq.com
*
----------------------------------------------------------------------------------
*    Who        Version     Comments
* 1. FayeWong    1.0         函数对象
*
*
*
*
----------------------------------------------------------------------------------
*/
package org.lucky.exp.tokenizer;

import org.lucky.exp.func.Func;
/**
 * 
*
* @author FayeWong
 */
public class FunctionToken extends Token{
    private final Func function;
    public FunctionToken(final Func function) {
        super(Token.TOKEN_FUNCTION);
        this.function = function;
    }

    public Func getFunction() {
        return function;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((function == null) ? 0 : function.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FunctionToken other = (FunctionToken) obj;
		if (function == null) {
			if (other.function != null)
				return false;
		} else if (!function.equals(other.function))
			return false;
		return true;
	}
    
}
