package org.lucky.test;

import org.lucky.exp.func.Func;

/**
 * 自定义函数
*
* @author FayeWong
* @date 2019年8月31日
 */
public class CustomFunction {
	/**
	 * 向上取整，默认参数是1
	*
	* @author FayeWong
	* @date 2019年8月31日
	* @return
	 */
	public Func roundUp() {
		Func roundUp = new Func("roundUp") {
		    public double call(double... args) {
		        return Math.ceil(args[0]);
		    }
		};
		return roundUp;
	}
	/**
	 * 向下取整，默认参数是1
	*
	* @author FayeWong
	* @date 2019年8月31日
	* @return
	 */
	public Func roundDown() {
		Func roundDown = new Func("roundDown") {
		    public double call(double... args) {
		        return Math.floor(args[0]);
		    }
		};
		return roundDown;
	}
	/**
	 * 取最大值，默认参数是4.
	 * 该函数名已经是内置的函数了
	*
	* @author FayeWong
	* @date 2019年8月31日
	* @return
	 */
	public Func max() {
		Func max = new Func("max",4) {
		    public double call(double... args) {
		    	double max = args[0];
		    	for(double d : args) {
		    		max = max > d ? max : d;
		    	}
		    	return max;
		    }
		};
		return max;
	}
	/**
	 * 取最小值，默认参数是4.
	 * 该函数名已经是内置的函数了
	*
	* @author FayeWong
	* @date 2019年8月31日
	* @return
	 */
	public Func min() {
		Func min = new Func("min",4) {
		    public double call(double... args) {
		    	double min = args[0];
		    	for(double d : args) {
		    		min = min < d ? min : d;
		    	}
		    	return min;
		    }
		};
		return min;
	}
}
