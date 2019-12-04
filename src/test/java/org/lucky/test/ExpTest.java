package org.lucky.test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.Test;
import org.lucky.exp.DefaultLuckyExpBuilder;
import org.lucky.exp.Selector;
import org.lucky.exp.annotation.Formula_Choose;
import org.lucky.exp.exception.CallBackException;
import org.lucky.exp.func.Func;
import org.lucky.exp.oper.Oper;
/**
 * 面向对象计算
*
* @author FayeWong
* @date 2019年8月31日
 */
public class ExpTest {
	
	/**
	 * 自动计算
	*
	* @author FayeWong
	* @date 2019年8月31日
	 */
	ExecutorService executor = Executors.newFixedThreadPool(5);
	Oper oper = new Oper("#", 2/**操作数只接受1或2**/, true, Oper.PRECEDENCE_ADDITION) {
        @Override
        public Object call(final double... args) {
            return args[0] + args[1];
        }
    };
    
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
		//给计算公式变量设置默认值 则解绑自动计算属性，结果为默认值
		dog.setThree(123.8);
		dog.getCat().setTwelve(520.0);
		System.out.println(dog.getThree());//123.8
		System.out.println(dog.getCat().getTwelve());//520.0
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
	/**
	 * 缓存计算
	*
	* @author FayeWong
	 * @throws CallBackException 
	* @date 2019年8月31日
	 */
	@Test
	public void test3() throws CallBackException {
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
			//.addCache(false)//关闭缓存计算
			.addCache(true,3 * 60 * 1000)//开启缓存计算，缓存3分钟
			.result((handle)->{//回调结果
				if(handle.isSuccess()) {//全部计算成功返回 true
					Dog successDog = (Dog)handle.getT();
					System.out.println(successDog);
				}else {//部分计算成功或没有计算成功 都会返回该对象
					Dog errorDog = (Dog)handle.getT();
					Set<String> errors = handle.getErrors();
					//System.out.println(errors);
					//System.out.println(errorDog);
				}
			});
			//System.out.println(dog.getTen());
		}
		Long end = System.currentTimeMillis();
		System.out.println("简单测试一百万条计算时间："+(end-start)/1000+"秒");
	}
	@Test
	public <T> void test4() {
		for(int i=0;i<10;i++) {
			Selector selector = new Selector();//公式选择器
			Map<String,Double> param = new HashMap<String,Double>();
			param.put("HelloKitty", 5.0);//追加计算参数
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
			try {
				new DefaultLuckyExpBuilder()
						.build(dog,param,selector)//不需要追加计算参数和只绑定一个公式  //默认使用第一个公式,param,selector
						.addFunc(new CustomFunction().roundDown())//自定义公式
						.addFunc(new CustomFunction().roundUp())//自定义公式
						.addOper(oper)//自定义运算符
						.result((h)->{
							System.out.println(h.isSuccess());
							System.out.println(h.getT());
						});
			} catch (CallBackException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}						
		}
	}
	/**
	 * 测试 BindObject 
	*
	* @author FayeWong
	* @date 2019年8月31日
	 */
	@Test
	public <T> void test5() throws CallBackException {
		Long start = System.currentTimeMillis();
		for (int i = 0; i < 1; i++) {
			Selector selector = new Selector();// 公式选择器
			Map<String, Double> param = new HashMap<String, Double>();
			param.put("M", 20.1);// 追加计算参数
			param.put("HelloKitty", 5.0);// 追加计算参数
			// param.put("V", 20.1);//追加计算参数
			Dog dog = new Dog();
			dog.setOne((short) 3);
			dog.setTwo(2.1 * i);
			/**
			 * if(i % 2 == 0) { selector.formulaFiled(Dog.class,"three", Formula_Choose._2);
			 * selector.formulaFiled(Cat.class,"fifteen", Formula_Choose._2);
			 * selector.formulaFiled(Rabbit.class,"twentyFirst", Formula_Choose._3); }
			 **/

			Cat cat = new Cat();
			cat.setSixteen(5.8 * i);
			cat.setEleven(3.1 * i);
			dog.setCat(cat);
			Rabbit rabbit = new Rabbit();
			cat.setRabbit(rabbit);

			new DefaultLuckyExpBuilder().build(dog, param, selector)// 不需要追加计算参数和只绑定一个公式 //默认使用第一个公式,param,selector
					.addFunc(new CustomFunction().roundDown())// 自定义公式
					.addFunc(new CustomFunction().roundUp())// 自定义公式
					.addOper(oper)// 自定义运算符
					.result((h) -> {// 回调结果
						Dog result = (Dog) h.getT();
						if (h.isSuccess()) {
							System.out.println("TwentyFirst: " + result.getCat().getRabbit().getTwentyFirst());
							System.out.println("Three: " + result.getThree());
							System.out.println("Four: " + result.getFour());
							System.out.println("ten: " + result.getTen());
							System.out.println("Fifteen: " + result.getCat().getFifteen());
							System.out.println("Sixteen: " + result.getCat().getSeventeen1());
							System.out.println("Eighteen: " + result.getCat().getEighteen());
							System.out.println("Thirteen: " + result.getCat().getThirteen());
						} else {
							System.out.println("TwentyFirst: " + result.getCat().getRabbit().getTwentyFirst());
							System.out.println("Three: " + result.getThree());
							System.out.println("Four: " + result.getFour());
							System.out.println("ten: " + result.getTen());
							System.out.println("Fifteen: " + result.getCat().getFifteen());
							System.out.println("Sixteen: " + result.getCat().getSeventeen1());
							System.out.println("Eighteen: " + result.getCat().getEighteen());
							System.out.println("Thirteen: " + result.getCat().getThirteen());
							System.out.println("error: " + h.getErrors());
						}
					});

		}
		Long end = System.currentTimeMillis();
		// System.out.println("简单测试一百万条计算时间："+(end-start)/1000+"秒");
	}
	public static void main(String[] args) {
		Dog dog = new Dog();
		Cat cat = new Cat();
		cat.setSixteen(5.8);
		cat.setEleven(3.1);
		dog.setCat(cat);
		Rabbit rabbit = new Rabbit();
		cat.setRabbit(rabbit);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ObjectOutput oo = new ObjectOutputStream(os);
			oo.writeObject(dog);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ByteArrayInputStream bis = new ByteArrayInputStream(os.toByteArray());
		try {
			ObjectInputStream ois = new ObjectInputStream(bis);
			Dog dog1 = (Dog)ois.readObject();
			//System.out.println(dog1.getCat().getSixteen());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}	
}
