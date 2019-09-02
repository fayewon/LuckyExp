/** Copyright 2019 SAIC General Motors Corporation Ltd. All Rights Reserved.
*
* This software is published under the terms of the SGM Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
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
import java.util.concurrent.ExecutorService;

import org.lucky.exp.parent.AbstractLuckyExpBuilder;
import org.lucky.exp.parent.OperResult;
/**
 * 
*
* @author FayeWong
* @date 2019年8月28日
 */
public class DefaultLuckyExpBuilder extends AbstractLuckyExpBuilder{    
    public DefaultLuckyExpBuilder() {
    	this.variableNames.add("pi");
    	this.variableNames.add("π");
    	this.variableNames.add("e");
    	this.variableNames.add("φ");
    }

	@Override
	public boolean result() {		
		Expression expression = new Expression(userFuncs,userOperators,variableNames,implicitMultiplication,passExps,waitExps,recalLimit,this.userFuncs.keySet())
				.setVariables(variables)
				.setTokens(tokens);
		return expression.result();
	}

	@Override
	public void  result(ExecutorService executor,OperResult operResult) {
		Expression expression = new Expression(userFuncs,userOperators,variableNames,implicitMultiplication,passExps,waitExps,recalLimit,this.userFuncs.keySet())
				.setVariables(variables)
				.setTokens(tokens)
				.setEntity(entity);
		expression.result(executor,operResult);
	}   
}

