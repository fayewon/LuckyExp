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
# 使用说明
## 创建一个LuckyExp对象
```java
//被计算的对象(entity)需要实现序列化接口
public class Dog implements Serializable
//绑定计算参数
@BindVar("A")
private Double one;
//绑定计算公式
@BindVar("C")
@Calculation(formula= {"100#A+B+J+M","M * roundUp(max(A,2,7,9))"},format = "##.###")
//支持绑定多个公式，通过公式选择器来选择公式
private Double three;
//我们也支持绑定对象
@BindObject
private Cat cat;
/**
	 * 自动计算
	*
	* @author FayeWong
	* @date 2019年8月31日
	 */
	ExecutorService executor = Executors.newFixedThreadPool(5);
	Oper oper = new Oper("#", 2/**操作数只接受1或2**/, true, Oper.PRECEDENCE_ADDITION) {
        @Override
        public double call(final double... args) {
            return args[0] + args[1];
        }
    };
    
	@Test
	public void test() {
		
		Selector selector = new Selector();//公式选择器
		selector.formulaFiled(Dog.class,"three",Formula_Choose._1);//成员变量three选择第二个公式
		Map<String,Double> param = new HashMap<String,Double>();
		param.put("M", 20.1);//追加计算参数
		Dog dog = new Dog();
		dog.setOne((short)1236);
		dog.setTwo(234.1);
		dog.setAk("123");
		Cat cat = new Cat();
		Rabbit rabbit = new Rabbit();
		rabbit.setTwentySecond(900.9);
		cat.setRabbit(rabbit);
		dog.setCat(cat);
		boolean result = new DefaultLuckyExpBuilder()
		//.build(dog)//不需要追加计算参数和只绑定一个公式  //默认使用第一个公式,param,selector
		//.build(dog,param)//只绑定一个公式
		.build(dog,param,selector)//复杂的计算  全都要0.0
		.implicitMultiplication(true)//是否插入隐式乘法标记，默认是false。使用默认就行
		.func(new CustomFunction().roundDown())//自定义公式
		.func(new CustomFunction().roundUp())//自定义公式
		.oper(oper)//自定义运算符
		.result();
		assertTrue(result);
		System.out.println("Three: "+dog.getThree());
		System.out.println("Four: "+dog.getFour());
		System.out.println("ten: "+dog.getTen());
		System.out.println("Fifteen: "+dog.getCat().getFifteen());
		System.out.println("Sixteen: "+dog.getCat().getSeventeen1());
		System.out.println("Eighteen: "+dog.getCat().getEighteen());
		System.out.println("Thirteen: "+dog.getCat().getThirteen());
		System.out.println("TwentyFirst: "+dog.getCat().getRabbit().getTwentyFirst());
	}
	@Test
	public void test2() {
		Map<String,Double> param = new HashMap<String,Double>();
		param.put("M", 20.1);//追加计算参数
		Dog dog = new Dog();
		dog.setOne((short)1236);
		dog.setTwo(234.1);
		//dog.setThree(5201314.1);//给自动计算变量设置默认值，则解绑自动计算的属性
		boolean result = new DefaultLuckyExpBuilder()
				.build(dog,param)//不需要追加计算参数和只绑定一个公式  //默认使用第一个公式,param,selector
				.func(new CustomFunction().roundDown())//自定义公式
				.func(new CustomFunction().roundUp())//自定义公式
				.oper(oper)//自定义运算符
				.result();
				assertTrue(result);
				//System.out.println(dog.getThree());//该值为默认值
				System.out.println(dog.getFour());
	  /**
	   * 注意还有种情况的处理方式，如果业务需要计算之后的变量进行判断，可以在该变量的get方法里判断
	   * 因为计算完的数据我最终的从get方法里获取的，建议在get里判断是对该值null判断
	   */
	}
	/**
	 * 循环计算
	*
	* @author FayeWong
	* @date 2019年8月31日
	 */
	@Test
	public void test3() {
		List<Dog> list = new ArrayList<Dog>();
		for(int i=0;i<10;i++) {
			Dog dog = new Dog();
			dog.setOne((short)3);
			dog.setTwo(2.1* i);
			Cat cat = new Cat();
			dog.setCat(cat);
			boolean result = new DefaultLuckyExpBuilder()
					.build(dog)//不需要追加计算参数和只绑定一个公式  //默认使用第一个公式,param,selector
					.func(new CustomFunction().roundDown())//自定义公式
					.func(new CustomFunction().roundUp())//自定义公式
					.oper(oper)//自定义运算符
					.result();
					assertTrue(result);
			list.add(dog);		
			
		}
		
		for(Dog dog : list) {
			//System.out.println(dog.getThree());
			System.out.println(dog.getFour());
		}
	}
	@Test
	public <T> void test4() {
		for(int i=0;i<10;i++) {
			Selector selector = new Selector();//公式选择器
			Dog dog = new Dog();
			dog.setOne((short)3);
			dog.setTwo(2.1* i);
			if( i == 4) {
				//dog.setThree(0.0);
			}
			if(i == 5) {
				selector.formulaFiled(Dog.class,"three", Formula_Choose._2);
			}
			//Cat cat = new Cat();
			//dog.setCat(cat);
			new DefaultLuckyExpBuilder()
					.build(dog,null,selector)//不需要追加计算参数和只绑定一个公式  //默认使用第一个公式,param,selector
					.func(new CustomFunction().roundDown())//自定义公式
					.func(new CustomFunction().roundUp())//自定义公式
					.oper(oper)//自定义运算符
					.result((h)->{
						System.out.println(h.isSuccess());
						System.out.println(h.getT());
					});						
		}
	}
	/**
	 * 测试 BindObject 
	*
	* @author FayeWong
	* @date 2019年8月31日
	 */
	@Test
	public <T> void test5() {
		Long start = System.currentTimeMillis();
		for(int i=0;i<10;i++) {
			Selector selector = new Selector();//公式选择器
			Map<String,Double> param = new HashMap<String,Double>();
			param.put("M", 20.1);//追加计算参数
			//param.put("V", 20.1);//追加计算参数
			Dog dog = new Dog();
			dog.setOne((short)3);
			dog.setTwo(2.1* i);
			if(i % 2 == 0) {
				selector.formulaFiled(Dog.class,"three", Formula_Choose._2);
				selector.formulaFiled(Cat.class,"fifteen", Formula_Choose._2);
				selector.formulaFiled(Rabbit.class,"twentyFirst", Formula_Choose._3);
			}
				
			
			Cat cat = new Cat();
			cat.setSixteen(5.8*i);
			cat.setEleven(3.1*i);
			dog.setCat(cat);
			Rabbit rabbit = new Rabbit();
			cat.setRabbit(rabbit);
			new DefaultLuckyExpBuilder()
					.build(dog,param,selector)//不需要追加计算参数和只绑定一个公式  //默认使用第一个公式,param,selector
					.func(new CustomFunction().roundDown())//自定义公式
					.func(new CustomFunction().roundUp())//自定义公式
					.oper(oper)//自定义运算符
					.result((h)->{//回调结果
						Dog result = (Dog)h.getT();
						if(h.isSuccess()) {
							System.out.println("TwentyFirst: "+result.getCat().getRabbit().getTwentyFirst());
							System.out.println("Three: "+result.getThree());
							System.out.println("Four: "+result.getFour());
							System.out.println("ten: "+result.getTen());
							System.out.println("Fifteen: "+result.getCat().getFifteen());
							System.out.println("Sixteen: "+result.getCat().getSeventeen1());
							System.out.println("Eighteen: "+result.getCat().getEighteen());
							System.out.println("Thirteen: "+result.getCat().getThirteen());
						}else {
							System.out.println("error: "+h.getErrors());
						}
					});	
		}
		Long end = System.currentTimeMillis();
		//System.out.println("简单测试一百万条计算时间："+(end-start)/1000+"秒");
	}
```
