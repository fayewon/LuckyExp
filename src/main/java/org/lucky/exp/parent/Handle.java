package org.lucky.exp.parent;

import java.util.List;
import java.util.Set;

/**
 * 需要处理的对象
 * @since 1.0
 * @author FayeWong
 *
 */
public class Handle<T> {
	/**计算的结果对象**/
	private T t;
	/**1.成功计算完成，2.计算失败，及时返回t结果**/
	private boolean isSuccess;
	/**计算过程中计算字段参数引发的异常**/
	private Set<String> errors;
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
	public Set<String> getErrors() {
		return errors;
	}
	public void setErrors(Set<String> errors) {
		this.errors = errors;
	}
	
}
