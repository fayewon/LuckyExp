/** 
 * @Project Name : LuckyExp
*
* @File name : Rabbit.java
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
package org.lucky.test;

import java.io.Serializable;

import org.lucky.exp.annotation.BindDouble;

public class Rabbit implements Serializable{
	@BindDouble(key = "U")
	private Double twentyFirst;
	@BindDouble(key = "V")
	private Double twentySecond;
	@BindDouble(key = "W")
	private Double twentyThird;
	@BindDouble(key = "X")
	private Double twentyForth;
	@BindDouble(key = "Y")
	private Double twentyFifth;
	@BindDouble(key = "Z")
	private Double twentySixth;
	public Double getTwentyFirst() {
		return twentyFirst;
	}
	public void setTwentyFirst(Double twentyFirst) {
		this.twentyFirst = twentyFirst;
	}
	public Double getTwentySecond() {
		return twentySecond;
	}
	public void setTwentySecond(Double twentySecond) {
		this.twentySecond = twentySecond;
	}
	public Double getTwentyThird() {
		return twentyThird;
	}
	public void setTwentyThird(Double twentyThird) {
		this.twentyThird = twentyThird;
	}
	public Double getTwentyForth() {
		return twentyForth;
	}
	public void setTwentyForth(Double twentyForth) {
		this.twentyForth = twentyForth;
	}
	public Double getTwentyFifth() {
		return twentyFifth;
	}
	public void setTwentyFifth(Double twentyFifth) {
		this.twentyFifth = twentyFifth;
	}
	public Double getTwentySixth() {
		return twentySixth;
	}
	public void setTwentySixth(Double twentySixth) {
		this.twentySixth = twentySixth;
	}
	
}
