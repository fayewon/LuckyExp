/** 
 * @Project Name : LuckyExp
*
* @File name : ExceptionCode.java
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
package org.lucky.exp.annotation;
public enum ExceptionCode {
	C_10042("未知异常"),
	C_10043("绑定异常"),
	C_10044("重新计算异常"),
	C_10045("变量不能与函数同名"),
	C_10046("计算对象为空"),
	C_10047("计算参数为空"),
	C_10048("公式选择器为空"),
	C_10049("异步计算异常");
	private String code;  
	private  ExceptionCode() {};
    private ExceptionCode(String code) {  
        this.code = code;  
 
    }
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
    
}
