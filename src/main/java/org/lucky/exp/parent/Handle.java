package org.lucky.exp.parent;

import java.util.List;

/**
 * 需要处理的对象
 * @since 1.0
 * @author FayeWong
 *
 */
public class Handle<T> {
	private T t;
	private boolean isSuccess;
	/**计算过程中计算字段参数引发的异常**/
	private List<String> errors;
	public T getT() {
		return t;
	}
	public void setT(T t) {
		this.t = t;
	}
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	
}
