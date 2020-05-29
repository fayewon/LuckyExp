/** 
 * @Project Name : LuckyExp
*
* @File name : Cat.java
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

import org.lucky.exp.annotation.BindObject;
import org.lucky.exp.annotation.BindVar;
import org.lucky.exp.annotation.Calculation;

/**
 * 需要自动计算的对象需要实现序列化接口
*
* @author FayeWong
* @date 2019年8月31日
 */
public class Cat implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@BindObject
	private Rabbit rabbit;
	@BindVar("AK")
	private String ak;
	@BindVar("K")
	private Double eleven;
	@BindVar("L")
	@Calculation(formula= {"C+K","max4(if(A>B,A,B),1,2,3)"},format = "##.###")
	private Double twelve;
	@BindVar("M")
	/**@Calculation(formula= {"A+B + 2.1 * roundUp(max4(A,1,2,300000)) + 2.1 * roundUp(max4(A,1,2,300000)) + "
			+ "2.1 * roundUp(max4(A,1,2,300000)) + A + 2.1 * roundUp(max4(A,1,2,300000)) + "
			+ "2.1 * roundUp(max4(A,1,2,300000)) + 2.1 * roundUp(max4(A,1,2,300000))","1+M * roundUp(max4(A,2,3,4)/2.1)"},format = "##.###")**/
	private Double thirteen;
	@BindVar("N")
	private Double fourteen;	
	@BindVar("O")
	/**@Calculation(formula= {"A+B + 2.1 * roundUp(max4(A,1,2,300000)) + "
			+ "2.1 * roundUp(max4(A,1,2,300000)) + 2.1 * roundUp(max4(A,1,2,300000)) + "
			+ "A + 2.1 * roundUp(max4(A,1,2,300000)) + 2.1 * roundUp(max4(A,1,2,300000)) + "
			+ "2.1 * roundUp(max4(A,1,2,300000))","1+M * roundUp(max4(A,2,3,90000)/2.1)"},format = "##.###")**/
	private Double fifteen;
	@BindVar("P")
	private Double sixteen;
	@BindVar("Q")
	//@Calculation(formula= {"O+R","M * roundUp(max4(A,2,3,4)/2.1)"},format = "##.###")
	private Double seventeen1;	
	@BindVar("R")
	//@Calculation(formula= {"O","M * roundUp(max4(A,2,3,4)/2.1)"},format = "##.###")
	private Double eighteen;
	@BindVar("S")
	private Double nineteen;
	@BindVar("T")
	private Double twenty;
	
	public String getAk() {
		return ak;
	}
	public void setAk(String ak) {
		this.ak = ak;
	}
	public Double getEleven() {
		return eleven;
	}
	public void setEleven(Double eleven) {
		this.eleven = eleven;
	}
	public Double getTwelve() {
		return twelve;
	}
	public void setTwelve(Double twelve) {
		this.twelve = twelve;
	}
	public Double getThirteen() {
		return thirteen;
	}
	public void setThirteen(Double thirteen) {
		this.thirteen = thirteen;
	}
	public Double getFourteen() {
		return fourteen;
	}
	public void setFourteen(Double fourteen) {
		this.fourteen = fourteen;
	}
	public Double getFifteen() {
		return fifteen;
	}
	public void setFifteen(Double fifteen) {
		this.fifteen = fifteen;
	}
	public Double getSixteen() {
		return sixteen;
	}
	public void setSixteen(Double sixteen) {
		this.sixteen = sixteen;
	}
	
	public Double getSeventeen1() {
		return seventeen1;
	}
	public void setSeventeen1(Double seventeen1) {
		this.seventeen1 = seventeen1;
	}
	public Double getEighteen() {
		return eighteen;
	}
	public void setEighteen(Double eighteen) {
		this.eighteen = eighteen;
	}
	public Double getNineteen() {
		return nineteen;
	}
	public void setNineteen(Double nineteen) {
		this.nineteen = nineteen;
	}
	public Double getTwenty() {
		return twenty;
	}
	public void setTwenty(Double twenty) {
		this.twenty = twenty;
	}
	public Rabbit getRabbit() {
		return rabbit;
	}
	public void setRabbit(Rabbit rabbit) {
		this.rabbit = rabbit;
	}
	
}
