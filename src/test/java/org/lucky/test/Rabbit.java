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

import org.lucky.exp.annotation.BindVar;

public class Rabbit implements Serializable{
	@BindVar("U")
	private Double twentyFirst;
	@BindVar("V")
	private Double twentySecond;
	@BindVar("W")
	private Double twentyThird;
	@BindVar("X")
	private Double twentyForth;
	@BindVar("Y")
	private Double twentyFifth;
	@BindVar("Z")
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
