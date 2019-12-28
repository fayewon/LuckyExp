/** 
 * @Project Name : LuckyExp
*
* @File name : DefaultLuckyExpBuilder.java
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
import org.lucky.exp.exception.LuckyExpEvaluateException;
import org.lucky.exp.parent.AbstractLuckyExpBuilder;
import org.lucky.exp.parent.OperResult;
/**
 * 
*
* @author FayeWong
* 
 */
public class DefaultLuckyExpBuilder extends AbstractLuckyExpBuilder{    
    public DefaultLuckyExpBuilder() {
    	this.configuration.getVariableNames().add("pi");
    	this.configuration.getVariableNames().add("π");
    	this.configuration.getVariableNames().add("e");
    	this.configuration.getVariableNames().add("φ");
    }

	@Override
	public void result(){	
		new Expression(configuration)
		        .result();

	}

	@Override
	public void  result(OperResult operResult) throws LuckyExpEvaluateException {
		new Expression(configuration)
				.setEntity(entity)
		        .result(operResult);
	}   
}

