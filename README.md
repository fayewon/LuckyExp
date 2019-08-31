# LuckyExp
幸运的表达式，一个快速易用的面向对象计算的开源工具
# Introduction
不需要依赖任何jar包，只需要JDK1.8即可。通过绑定对象计算出结果。

# requirements:
* JDK1.8
```java
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
		    public double apply(double... args) {
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
		    public double apply(double... args) {
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
		    public double apply(double... args) {
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
		Func min = new Func("min") {
		    public double apply(double... args) {
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

/**
	 * 自动计算
	*
	* @author FayeWong
	* @date 2019年8月31日
	 */
	@Test
	public void test() {
		Selector selector = new Selector();//公式选择器
		selector.put("three",Formula_Choose._2);//成员变量three选择第二个公式
		Map<String,Double> param = new HashMap<String,Double>();
		param.put("M", 20.1);//追加计算参数
		Dog dog = new Dog();
		dog.setOne(123.0);
		dog.setTwo(234.1);
		boolean result = new DefaultLuckyExpBuilder()
		//.build(dog)//不需要追加计算参数和只绑定一个公式  //默认使用第一个公式,param,selector
		//.build(dog,param)//只绑定一个公式
		.build(dog,param,selector)//复杂的计算  全都要0.0
		.setRecalLimit(30)//设置重新计算次数，选择默认就好，默认20次。检查完参数和公式算不出结果可以设置
		.implicitMultiplication(true)//是否插入隐式乘法标记，默认是true。使用默认就行
		.func(new CustomFunction().roundDown())//自定义公式
		.func(new CustomFunction().roundUp())//自定义公式
		.result();
		assertTrue(result);
		System.out.println(dog.getThree());
		System.out.println(dog.getFour());
	}
	@Test
	public void test2() {
		Dog dog = new Dog();
		dog.setOne(123.0);
		dog.setTwo(234.1);
		dog.setThree(5201314.1);//给自动计算变量设置默认值，则解绑自动计算的属性
		boolean result = new DefaultLuckyExpBuilder()
				.build(dog)//不需要追加计算参数和只绑定一个公式  //默认使用第一个公式,param,selector
				.func(new CustomFunction().roundDown())//自定义公式
				.func(new CustomFunction().roundUp())//自定义公式
				.result();
				assertTrue(result);
				System.out.println(dog.getThree());//该值为默认值
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
			dog.setOne(1.0 * i);
			dog.setTwo(2.1* i);
			boolean result = new DefaultLuckyExpBuilder()
					.build(dog)//不需要追加计算参数和只绑定一个公式  //默认使用第一个公式,param,selector
					.func(new CustomFunction().roundDown())//自定义公式
					.func(new CustomFunction().roundUp())//自定义公式
					.result();
					assertTrue(result);
			list.add(dog);		
			
		}
		
		for(Dog dog : list) {
			System.out.println(dog.getThree());
			System.out.println(dog.getFour());
		}
	}
	@Test
	public <T> void test4() {
		for(int i=0;i<10;i++) {
			Dog dog = new Dog();
			dog.setOne(1.0 * i);
			dog.setTwo(2.1* i);
			new DefaultLuckyExpBuilder()
					.build(dog)//不需要追加计算参数和只绑定一个公式  //默认使用第一个公式,param,selector
					.func(new CustomFunction().roundDown())//自定义公式
					.func(new CustomFunction().roundUp())//自定义公式
					.result(new ValiResult<T>() {//带回调的计算结果
						@Override
						protected void getValiMeg(List<Map<String, String>> message) {
							//计算过程中检查表达式是否合法,如果不合法 message会有不合法的信息
							//成功计算完毕message不会有信息
							System.out.println("message: "+message);
						}

						@Override
						public <T> void getBean(T t, boolean isError) {
							//isError,检查是否全部计算成功，如果全部计算成功 isError是false;
							//如果isError是true，没有全部计算成功依然会把已经部分计算成功的dog返回
							//这里的t == dog;
							Dog dog = (Dog)t;
							System.out.println(dog.getThree());
							System.out.println(dog.getFour());
						}
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
		for(int i=0;i<10;i++) {
			Selector selector = new Selector();//公式选择器
			Map<String,Double> param = new HashMap<String,Double>();
			param.put("M", 20.1);//追加计算参数
			Dog dog = new Dog();
			dog.setOne(1.0 * i);
			dog.setTwo(2.1* i);
			if(i ==2 ) {
				selector.put("three", Formula_Choose._2);//一层
				selector.put("cat.fifteen", Formula_Choose._2);//二层  目前最多两层
			}
			Cat cat = new Cat();
			cat.setSixteen(5.8*i);
			cat.setEleven(3.1*i);
			dog.setCat(cat);
			new DefaultLuckyExpBuilder()
					.build(dog,param,selector)//不需要追加计算参数和只绑定一个公式  //默认使用第一个公式,param,selector
					.func(new CustomFunction().roundDown())//自定义公式
					.func(new CustomFunction().roundUp())//自定义公式
					.result(new ValiResult<T>() {//带回调的计算结果
						@Override
						protected void getValiMeg(List<Map<String, String>> message) {
							//计算过程中检查表达式是否合法,如果不合法 message会有不合法的信息
							//成功计算完毕message不会有信息
							System.out.println("message: "+message);
						}

						@Override
						public <T> void getBean(T t, boolean isError) {
							//isError,检查是否全部计算成功，如果全部计算成功 isError是false;
							//如果isError是true，没有全部计算成功依然会把已经部分计算成功的dog返回
							//这里的t == dog;
							Dog dog = (Dog)t;
							System.out.println(dog.getThree());
							System.out.println(dog.getFour());
							System.out.println(dog.getCat().getFifteen());
							System.out.println(dog.getCat().getSeventeen());
							
						}
					});						
		}
	}
```
