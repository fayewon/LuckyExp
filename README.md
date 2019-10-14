# LuckyExp
幸运的表达式，一个快速易用的面向对象计算的开源工具
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
  <version>1.0.1</version>
</dependency>
```
# 简述
  *LuckyExp是一款快速易用的面向对象计算的引擎，通过绑定实体类的全局变量作为计算参数并且自动注入结果。注入结果的变量也可做为绑定参数为下一个结果变量的计算参数。*    
  *LuckyExp的制作目的是为了整合项目开发中的计算业务，通过配置注解的方式即可完成整个计算过程达到与其他的业务逻辑实现分离的目的。
这样的好处是方便修改计算规则。*  
# 快速开始
#### 计算一个对象
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
  dog.setOne(40);//计算参数 'A' = 40
  dog.setTwo(60.0);//计算参数 'B' = 60.0
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
  .build(dog,param,selector)//计算入口
  .result();//获取结果
  System.out.println(dog.getCat().getTwelve());//max(if(A>B,A,B),1,2,3)=60
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
