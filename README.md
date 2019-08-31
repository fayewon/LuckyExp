# LuckyExp
幸运的表达式，一个快速易用的面向对象计算的开源工具
# Introduction
幸运的表达式是一个快速易用的面向对象计算的开源工具

# requirements:
* JDK1.8

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
