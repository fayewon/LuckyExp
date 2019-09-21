package org.lucky.exp.parent;
/**
 * 需要处理的对象
 * @since 1.0
 * @author FayeWong
 *
 */
public class Handle<T> {
	private T t;
	private boolean isSuccess;
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
	
}
