# LuckyExp
幸运的表达式，首款面向对象计算的开源工具。
# gitee ：https://gitee.com/wang_fei123111/LuckyExp
![Alt text](/img/luckyExp.jpg)
# Introduction
* 轻量，快速，易用，面向对象计算
* 极大降低企业项目业务逻辑和计算逻辑的粘度，实现业务和计算结构分离。    
* 不需要依赖任何jar包，只需要JDK1.8即可。通过绑定对象计算出结果。 
* 开发者是持续维护的原动力，更多功能敬请期待。  
# requirements:
* JDK1.8
# maven
[maven仓库](https://oss.sonatype.org/content/groups/public/com/github/fayewon/LuckyExp/)  
```java
<mirror>  
    <id>sonatype-nexus-snapshots</id>  
    <mirrorOf>*</mirrorOf>  
    <name>sonatype-nexus-snapshots</name>  
    <url>https://oss.sonatype.org/content/groups/public/</url>  
</mirror>
```
# pom.xml
```java
<dependency>
  <groupId>com.github.fayewon</groupId>
  <artifactId>LuckyExp</artifactId>
  <version>1.0.2</version>
</dependency>
```
# 简述
&nbsp;&nbsp;&nbsp;*LuckyExp是一款快速易用的面向对象计算的引擎，通过绑定实体类的全局变量作为计算参数并且自动注入结果。注入结果的变量也可做为绑定参数为下一个结果变量的计算参数。*    
&nbsp;&nbsp;&nbsp;*LuckyExp的制作目的是为了整合项目开发中的计算业务，通过配置注解的方式即可完成整个计算过程达到与其他的业务逻辑实现分离的目的。
这样的好处是方便修改计算规则。*  
# 快速开始
#### 简单计算
```java
//被计算的对象(entity)需要实现序列化接口
public class Dog implements Serializable{
  //绑定计算参数
  @BindVar("A")
  private Double one;
  //绑定计算参数
  @BindVar("B")
  private int two;
  //绑定计算参数和公式
  @BindVar("C")
  @Calculation(formula= {"A+B"},format = "##.###")//format = "##.###" 格式，默认是保留后五位小数
  private Double three;
}
@Test
public void test(){
  Dog dog = new Dog();
  dog.setOne(40);//计算参数 'A'
  dog.setTwo(60.0);//计算参数 'B'
  new DefaultLuckyExpBuilder()//创建一个幸运表达式对象
  .build(dog)//计算入口
  .result();//计算结果
  System.out.println(dog.getThree());//A+B=100
}
```
#### 选择公式和追加参数计算
```java 
public class Dog implements Serializable{
  //绑定计算参数
  @BindVar("A")
  private Double one;
  //绑定计算参数
  @BindVar("B")
  private int two;
  //绑定计算参数和公式
  @BindVar("C")
  @Calculation(formula= {"A+B","A+B*HelloKitty"},format = "##.###")//format = "##.###" 格式，默认是保留后五位小数
  private Double three;
}
@Test
public void test() {
  Map<String,Double> param = new HashMap<String,Double>();
  param.put("HelloKitty", 5.0);//追加计算参数
  Selector selector = new Selector();//创建一个公式选择器
  selector.formulaFiled(Dog.class, "three", Formula_Choose._2);//计算对象dog的类信息，需要选择的变量名称，选择第二个公式
  Dog dog = new Dog();
  dog.setOne(40);//计算参数 'A'
  dog.setTwo(60.0);//计算参数 'B'
  new DefaultLuckyExpBuilder()//创建一个幸运表达式对象
  .build(dog,param,selector)//计算入口  计算对象，追加计算参数，公式选择器
  //.addSelector(selector)
  .result();//获取结果
  System.out.println(dog.getThree());//A+B*HelloKitty=340.0
}
```
#### 依赖计算和复杂计算
```java
public class Dog implements Serializable{
  @BindObject //绑定对象变量,当setCat的时候该注解生效
  private Cat cat;
  //绑定计算参数
  @BindVar("A")
  private Double one;
  //绑定计算参数
  @BindVar("B")
  private int two;
  //绑定计算参数和公式
  @BindVar("C")
  @Calculation(formula= {"A+B","A+B*HelloKitty"},format = "##.###")//format = "##.###" 格式，默认是保留后五位小数
  private Double three;
}
public class Cat implements Serializable{
  @BindVar("K")
  private Double eleven;
  @BindVar("L")
  //max 求4位最大值，位数不够添加占位符。if：高级函数，搭配运算符 '>','<','='使用 三目运算函数 第1位返回布尔值.2，3位返回浮点数
  @Calculation(formula= {"C+K","max(if(A>B,A,B),1,2,300000)"},format = "##.###")
  private Double twelve;
}
@Test
public void test() {
  Map<String,Double> param = new HashMap<String,Double>();
  param.put("HelloKitty", 5.0);//追加计算参数
		Selector selector = new Selector();//创建一个公式选择器
		selector.formulaFiled(Dog.class, "three", Formula_Choose._2);//计算对象Dog的类信息，需要选择的变量名称，选择第二个公式
		Dog dog = new Dog();
		dog.setOne(40.0);//计算参数 'A' = 40
		dog.setTwo(60);//计算参数 'B' = 60.0
		Cat cat = new Cat();
		cat.setEleven(50.0);//计算参数 'K' = 50.0
		dog.setCat(cat);//使@BindObject注解生效
		new DefaultLuckyExpBuilder()//创建一个幸运表达式对象
		.build(dog,param,selector)//计算入口
		.result();//获取结果
		System.out.println(dog.getThree());//A+B*HelloKitty=(C)340.0
		System.out.println(dog.getCat().getTwelve());//C+K=(L)390.0
		dog.getCat().setTwelve(null);
				
		//选择cat的twelve第二个公式
		selector.formulaFiled(Cat.class, "twelve", Formula_Choose._2);//计算对象Cat的类信息，需要选择的变量名称，选择第二个公式
		new DefaultLuckyExpBuilder()//创建一个幸运表达式对象
		.build(dog,param)//计算入口
		.result();//获取结果
		System.out.println(dog.getCat().getTwelve());//max(if(A>B,A,B),1,2,3)=60
		  
		//给计算公式变量设置默认值 则解绑自动计算属性，结果为默认值
		dog.setThree(123.8);
		dog.getCat().setTwelve(520.0);
		System.out.println(dog.getThree());//123.8
		System.out.println(dog.getCat().getTwelve());//520.0		
}
```
#### 自定义函数和运算符
```java
public class Dog implements Serializable{
  //绑定计算参数
  @BindVar("A")
  private Double one;
  //绑定计算参数
  @BindVar("B")
  private int two;
  //绑定计算参数和公式
  @BindVar("C")
  @Calculation(formula= {"funTest(A+B#1.5)","A+B*HelloKitty"},format = "##.###")//format = "##.###" 格式，默认是保留后五位小数
  private Double three;
}
@Test
public void test2() {
  Dog dog = new Dog();
  dog.setOne(40);//计算参数 'A'
  dog.setTwo(60.0);//计算参数 'B'
  new DefaultLuckyExpBuilder()//创建一个幸运表达式对象
  .build(dog)//计算入口
  .addFunc(new Func("funTest") {//自定义公式
	public double call(Object... args) {
		 return Math.ceil(((double)args[0]));//向上取整
		}
      })
  .addOper(new Oper("#", 2/**操作数只接受1或2**/, true/**true:向左运算：false向右运算**/, Oper.PRECEDENCE_ADDITION/**加法优先值**/) {//自定义运算符
	public Object call(final double... args) {
             return args[0] + args[1];
           }
      })
  .result();//计算结果
  System.out.println(dog.getThree());//funTest(A+B#1.5)=102.0   40+60+1.5向上取整 = 102
}
```
#### 缓存计算和回调结果
###### 公式多且复杂，遍历次数多建议开启缓存计算
```java
@Test
public void test3() {
  Long start = System.currentTimeMillis();
  for(int i=0;i<1000000;i++) {
  Dog dog = new Dog();
  dog.setOne((short)3);
  dog.setTwo(2.1* i);
  Cat cat = new Cat();
  cat.setEleven(123.9);
  dog.setCat(cat);
  new DefaultLuckyExpBuilder()
  .build(dog)//不需要追加计算参数和只绑定一个公式  //默认使用第一个公式,param,selector
  .addCache(false)//关闭缓存计算
  .addCache(true,3 * 60 * 1000)//开启缓存计算，缓存3分钟  默认关闭缓存计算
  .result((handle)->{//回调结果
        if(handle.isSuccess()) {//全部计算成功返回 true
	Dog successDog = (Dog)handle.getT();
	System.out.println(successDog);
	}else {//部分计算出来或没有计算出来 都会返回该对象
	Dog errorDog = (Dog)handle.getT();
	Set<String> errors = handle.getErrors();//没有计算出来的变量缺少的计算参数
	System.out.println(errors);
	System.out.println(errorDog);
	}
    });
 }
  Long end = System.currentTimeMillis();
  System.out.println("简单测试一百万条缓存计算时间："+(end-start)/1000+"秒"); // 19秒
  System.out.println("简单测试一百万条不缓存计算时间："+(end-start)/1000+"秒"); // 26秒
}
```
##### &nbsp;&nbsp;&nbsp;&nbsp;内置函数 Funcs.java
##### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;函数名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位数&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;作用
###### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;min4&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;取最小值，位数不够添加占位符  
###### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;max4&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;取最大值，位数不够添加占位符   
###### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;sin&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;正弦函数   
###### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;cos&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;余弦函数  
###### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;tan&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;正切函数  
###### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;cot&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;余切函数  
###### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;log&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;对角函数，1 以e为底的对数  
###### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;log2&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;对角函数，2 以e为底的对数  
###### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;log10&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;对角函数，10 以e为底的对数  
###### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;log1p&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;对角函数，Ln（x+ 1）  
###### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;abs&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;绝对值函数  
###### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;acos&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;返回角度值[0~π]   arc cos（y/z）  
###### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;asin&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;返回角度值[-π/2，π/2]  arc sin（x/z）  
###### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;atan&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;返回角度值[-π/2，π/2]  
###### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;cbrt&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;立方根函数  
###### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;floor&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;向下取整  
###### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;sinh&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;双曲正弦函数sinh(x)=(exp(x) - exp(-x)) / 2.0  
###### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;sqrt&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;平方根函数  
###### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;tanh&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;tanh(x) = sinh(x) / cosh(x)  
###### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;cosh&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;双曲余弦函数cosh(x)=(exp(x) + exp(-x)) / 2.0  
###### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ceil&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;向上取整  
###### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;pow&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;次方函数  
###### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;exp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;e的x次幂  
###### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;expm1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;e的x次幂 - 1  
###### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;signum&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;整数比较   
###### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;if&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;高级函数。三目运算符函数，第一位boolean值。搭配boolean运算符  '<' , '>','=' 使用  
##### &nbsp;&nbsp;&nbsp;&nbsp;内置运算符 Opers.java
##### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;运算名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;优先值&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;返回值
###### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;+&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;加法运算的优先值&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;double  
###### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;减法运算的优先值&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;double  
###### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;乘法运算的优先值&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;double  
###### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;除法运算的优先值&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;double  
###### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;^&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;电源操作的优先值&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;double  
###### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;%&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;除法运算的优先值&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;double  
###### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;一元加运算的优先值&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;boolean  
###### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;一元加运算的优先值&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;boolean  
###### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;=&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;一元加运算的优先值&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;boolean  

