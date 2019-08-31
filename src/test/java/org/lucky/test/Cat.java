/** Copyright 2019 SAIC General Motors Corporation Ltd. All Rights Reserved.
*
* This software is published under the terms of the SGM Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
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

import org.lucky.exp.annotation.BindDouble;
import org.lucky.exp.annotation.Calculation;

/**
 * 需要自动计算的对象需要实现序列化接口
*
* @author FayeWong
* @date 2019年8月31日
 */
public class Cat implements Serializable{
	@BindDouble(key = "K")
	private Double eleven;
	@BindDouble(key = "L")
	private Double twelve;
	@BindDouble(key = "M")
	private Double thirteen;
	@BindDouble(key = "N")
	private Double fourteen;
	@Calculation(formula= {"A+B*D+P","1+M * roundUp(max(A,2,3,4)/2.1)"},format = "##.###")
	@BindDouble(key = "O")
	private Double fifteen;
	@BindDouble(key = "P")
	private Double sixteen;
	@Calculation(formula= {"A+B*D*K","M * roundUp(max(A,2,3,4)/2.1)"},format = "##.###")
	@BindDouble(key = "Q")
	private Double seventeen;
	@BindDouble(key = "R")
	private Double eighteen;
	@BindDouble(key = "S")
	private Double nineteen;
	@BindDouble(key = "T")
	private Double twenty;
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
	public Double getSeventeen() {
		return seventeen;
	}
	public void setSeventeen(Double seventeen) {
		this.seventeen = seventeen;
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
	
}
