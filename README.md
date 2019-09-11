# LuckyExp
幸运的表达式，一个快速易用的面向对象计算的开源工具
# Introduction
* 极大降低企业项目业务逻辑和计算逻辑的粘度，实现业务和计算结构分离。    
* 不需要依赖任何jar包，只需要JDK1.8即可。通过绑定对象计算出结果。    
* 开发者是持续维护的原动力，更多功能敬请期待。  

# requirements:
* JDK1.8
# maven
![Alt text](/img/maven.PNG)
# pom.xml
![Alt text](/img/pom.PNG)
```java
/**
	 /**
	 * 自动计算
	*
	* @author FayeWong
	* @date 2019年8月31日
	 */
	ExecutorService executor = Executors.newFixedThreadPool(5);
	@Test
	public void test() {
		Oper oper = new Oper("#", 2/**操作数只接受1或2**/, true, Oper.PRECEDENCE_ADDITION) {
            @Override
            public double call(final double... args) {
                return args[0] + args[1];
            }
        };
		Selector selector = new Selector();//公式选择器
		//selector.put("three",Formula_Choose._2);//成员变量three选择第二个公式
		Map<String,Double> param = new HashMap<String,Double>();
		param.put("M", 20.1);//追加计算参数
		Dog dog = new Dog();
		dog.setOne(123.0);
		dog.setTwo(234.1);
		Cat cat = new Cat();
		dog.setCat(cat);
		boolean result = new DefaultLuckyExpBuilder()
		//.build(dog)//不需要追加计算参数和只绑定一个公式  //默认使用第一个公式,param,selector
		//.build(dog,param)//只绑定一个公式
		.build(dog,param,selector)//复杂的计算  全都要0.0
		.setRecalLimit(30)//设置重新计算次数，选择默认就好，默认20次。检查完参数和公式算不出结果可以设置
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
			Cat cat = new Cat();
			dog.setCat(cat);
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
			Selector selector = new Selector();//公式选择器
			Dog dog = new Dog();
			dog.setOne(1.0 * i);
			dog.setTwo(2.1* i);
			if( i == 4) {
				dog.setThree(0.0);
			}
			if(i == 5) {
				selector.put("three", Formula_Choose._2);
			}
			Cat cat = new Cat();
			dog.setCat(cat);
			new DefaultLuckyExpBuilder()
					.build(dog,null,selector)//不需要追加计算参数和只绑定一个公式  //默认使用第一个公式,param,selector
					.func(new CustomFunction().roundDown())//自定义公式
					.func(new CustomFunction().roundUp())//自定义公式
					.result(executor,new OperResult<T>() {

						@Override
						protected void getValiMeg(List<Map<String, String>> message) {
							//System.out.println("message: "+message);
							
						}

						@Override
						public void executeAsync(T t, boolean isSuccess) {
							Dog dog = (Dog)t;
							System.out.println("Three: "+dog.getThree());
							System.out.println("Four: "+dog.getFour());
							System.out.println("Thirteen: "+dog.getCat().getThirteen());
						}//带回调的计算结果
						
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
					.result(executor,new OperResult<T>() {
						
						@Override
						protected void getValiMeg(List<Map<String, String>> message) {
							System.out.println("message: "+message);
							
						}

						@Override
						public void executeAsync(T t, boolean isSuccess) {
							Dog dog = (Dog)this.t;
							System.out.println(dog.getThree());
							System.out.println(dog.getFour());
							
						}//带回调的计算结果
						
						
					});						
		}
	}
```
