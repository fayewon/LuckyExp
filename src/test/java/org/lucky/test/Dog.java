/** 
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

import org.lucky.exp.annotation.BindVar;
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
	//13 17;16 12;
	//
	//@BindObject
	private Cat cat;
	//@BindObject
	private String str = "helloworld";
	@BindObject
	private Rabbit rabbit;
	@BindVar("AK")
	private String ak;
	@BindVar("D")
	@Calculation(formula= {"max(if(A>B,A,B),1,2,300000)"},format = "##.###")
	private Double four;
	@BindVar("J")
	@Calculation(formula= {"helloworld+A + 2.1 * roundUp(max(A,1,2,300000)) + 2.1 * roundUp(max(A,1,2,300000)) + 2.1 * roundUp(max(A,1,2,300000)) + A + 2.1 * roundUp(max(A,1,2,300000)) + 2.1 * roundUp(max(A,1,2,300000)) + 2.1 * roundUp(max(A,1,2,300000))","2.1 * roundUp(max(A,1,2,300000))"},format = "##.###")
	private Double ten;
	@BindVar("A")
	private short one;
	@BindVar("B")
	private Double two;
	@BindVar("C")
	//@Calculation(formula= {"A+B"},format = "##.###")
	private Double three;
	
	
	
	@BindVar("E")
	private Double five;
	@BindVar("F")
	private Double six;
	@BindVar("G")
	private Double seven;
	@BindVar("H")
	private Double eight;
	@BindVar("I")
	private Double nine;
	
	
	public String getAk() {
		return ak;
	}
	public void setAk(String ak) {
		this.ak = ak;
	}
	public short getOne() {
		return one;
	}
	public void setOne(short one) {
		this.one = one;
	}
	public Double getTwo() {
		return two;
	}
	public void setTwo(Double two) {
		this.two = two;
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
		//return ten < 160 ?  160.0:ten;
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
	public Double getThree() {
		return three;
	}
	public void setThree(Double three) {
		this.three = three;
	}
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	
}
