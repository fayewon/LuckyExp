/** Copyright 2019 SAIC General Motors Corporation Ltd. All Rights Reserved.
*
* This software is published under the terms of the SGM Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : LuckyExp
*
* @File name : Dog.java
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
import org.lucky.exp.annotation.BindObject;
import org.lucky.exp.annotation.Calculation;
import org.lucky.exp.annotation.Status;
/**
 * 需要自动计算的对象需要实现序列化接口
*
* @author FayeWong
* @date 2019年8月31日
 */
public class Dog implements Serializable{
	@BindObject
	private Cat cat;
	@BindObject
	private Rabbit rabbit;
	@BindDouble(key = "A")
	private Double one;
	@BindDouble(key = "B")
	private Double two;
	@BindDouble(key = "C")
	@Calculation(formula= {"A+B*D","M * roundUp(max(A,2,3,4)/2.1)"},format = "##.###")
	private Double three;
	@BindDouble(key = "D")
	@Calculation(formula= {"A+B","M * roundUp(max(A,2,3,4)/2.1)"})
	private Double four;
	@BindDouble(key = "E")
	private Double five;
	@BindDouble(key = "F")
	private Double six;
	@BindDouble(key = "G")
	private Double seven;
	@BindDouble(key = "H")
	private Double eight;
	@BindDouble(key = "I")
	private Double nine;
	@BindDouble(key = "J")
	private Double ten;
	public Double getOne() {
		return one;
	}
	public void setOne(Double one) {
		this.one = one;
	}
	public Double getTwo() {
		return two;
	}
	public void setTwo(Double two) {
		this.two = two;
	}
	public Double getThree() {
		return three;
	}
	public void setThree(Double three) {
		this.three = three;
	}
	public Double getFour() {
		return four;
	}
	public void setFour(Double four) {
		this.four = four;
	}
	public Double getFive() {
		return five;
	}
	public void setFive(Double five) {
		this.five = five;
	}
	public Double getSix() {
		return six;
	}
	public void setSix(Double six) {
		this.six = six;
	}
	public Double getSeven() {
		return seven;
	}
	public void setSeven(Double seven) {
		this.seven = seven;
	}
	public Double getEight() {
		return eight;
	}
	public void setEight(Double eight) {
		this.eight = eight;
	}
	public Double getNine() {
		return nine;
	}
	public void setNine(Double nine) {
		this.nine = nine;
	}
	public Double getTen() {
		return ten;
	}
	public void setTen(Double ten) {
		this.ten = ten;
	}
	public Cat getCat() {
		return cat;
	}
	public void setCat(Cat cat) {
		this.cat = cat;
	}
	public Rabbit getRabbit() {
		return rabbit;
	}
	public void setRabbit(Rabbit rabbit) {
		this.rabbit = rabbit;
	}
	
}
