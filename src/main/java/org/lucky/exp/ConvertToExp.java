/**
* @Project Name : LuckyExp
*
* @File name : ConvertToExp.java
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
package org.lucky.exp;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.lucky.exp.Selector.SelectorHandler;
import org.lucky.exp.annotation.BindObject;
import org.lucky.exp.annotation.Condition;
import org.lucky.exp.annotation.Formula_Choose;
import org.lucky.exp.exception.BindException;
import org.lucky.exp.annotation.Calculation;
import org.lucky.exp.annotation.BindVar;
/**
 * <p>计算对象组装成计算变量</p>
*
* @author FayeWong
* 
 */
public class ConvertToExp {
	private static ConvertToExp convertToExp;
	private static final Class<?>[] BINDVAR_TYPE;
	private static final Class<?>[] VOLIOBJECT_TYPE;
	static {
		/*允许 @BindVar 绑定的对象支持的类型*/
		BINDVAR_TYPE = new Class[] {
				float.class,
				Float.class,
				double.class,
				Double.class,
				short.class,
				Short.class,
				int.class,
				Integer.class,
				long.class,
				Long.class,
				String.class
	   };
		/*常见序列化对象*/
		VOLIOBJECT_TYPE = new Class[] {
				String.class,
				Byte.class,
				Character.class,
				Short.class,
				Integer.class,
				Long.class,
				Float.class,
				Double.class,
				Boolean.class
	   };
	};
	private ConvertToExp() {}

	public static ConvertToExp getInstance() {
			synchronized (ConvertToExp.class) {
				if (null == convertToExp) {
					convertToExp = new ConvertToExp();
				}
			}
		return convertToExp;
	}
	public   void assignment(Serializable entity,Field field,final Configuration configuration) {
		try {			
			if (field.isAnnotationPresent(BindVar.class)) {
				final Object fieldVal = (Object)field.get(entity);
				parseBindDouble(fieldVal, field, configuration);
			}
			if (field.isAnnotationPresent(Calculation.class)) {
				final Object fieldVal = (Object)field.get(entity);
				parseCalculation(fieldVal,entity, field,configuration);
			}
			if (field.isAnnotationPresent(BindObject.class)) {
				final Object fieldVal = (Object)field.get(entity);
				parseBindObject(fieldVal, field,configuration);
			}
		} catch (BindException e) {
			throw new IllegalArgumentException(e);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException("成员变量 '"+field.getName()+"' 没有get()方法",e);
		}
	}
	
	@SuppressWarnings("unchecked")
	private   void parseCalculation(Object fieldVal,Serializable entity, Field field,final Configuration configuration) throws BindException {
		Calculation calculation = (Calculation) field.getAnnotation(Calculation.class);			
		if(field.getType() != Double.class) {
			throw new BindException("@Calculation() 必须绑定Double类型的字段 ：" + field.getType()
			+ "{ }{ } :  " + field.getName());
		}
		int index = 0;//默认使用第一个公式
		try {
			Selector selector = configuration.getSelector();
			if(selector != null) {
				List<SelectorHandler> selectors = selector.getSelectors();
				for(SelectorHandler handler : selectors) {
					InvocationHandler invocationHandler = Proxy.getInvocationHandler(calculation);
					Field hField = invocationHandler.getClass().getDeclaredField("memberValues");
					hField.setAccessible(true);
					Map<String, Object> memberValues = (Map<String, Object>) hField.get(invocationHandler);
					String[] formula = (String[]) memberValues.get("formula");
					field.setAccessible(field.isAccessible());
					hField.setAccessible(hField.isAccessible());
					if(handler.getClazz() == entity.getClass() && handler.getFiledName().equals(field.getName())) {
						Formula_Choose value = handler.getSelect();
						index = value.getIndex();
						if (index + 1 > formula.length) {
							throw new BindException("公式值选择过大，请检查变量'" +entity.getClass()+"'，'"+field.getName() + "'绑定的@Calculation的公式数："+(index+1));
						   }
					    }
			    	}
				}	
			if (calculation != null && fieldVal == null ) {
				Map<Condition, Object> parseObj = new HashMap<Condition, Object>();
				parseObj.put(Condition.field, field);
				parseObj.put(Condition.entity, entity);
				parseObj.put(Condition.format, calculation.format());
				parseObj.put(Condition.expression, calculation.formula()[index]);
				configuration.getPassExps().add(parseObj);
			}
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(e);
		} catch (NoSuchFieldException | SecurityException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	private  void parseBindObject(Object fieldVal, Field field,final Configuration configuration) throws BindException {
		boolean valiType = false;
		Class<?>[] clazzes = field.getType().getInterfaces();
		for(Class<?> clazz : clazzes) {
			if(clazz == Serializable.class) {
				valiType = true;
				break;
			}
		}
		if(!valiType) {
			throw new BindException("@BindObject()必须绑定实现序列化接口的对象 ：" + field.getType()
			+ "{ }{ } :  " + field.getName());
		}
		//对常见的实现序列化进行校验，基本类的包装类和String
		if(!checkObject(field,valiType)) {
			throw new BindException("@BindObject()不能绑定该对象上 ：" + field.getType()
			+ "{ }{ } :  " + field.getName());
		};
		if (fieldVal != null) {
			Class<?> clazz = field.getType();
			Field[] fields = clazz.getDeclaredFields();
			Arrays.asList(fields).forEach((filed)->{
				if (fieldVal instanceof Serializable) {
					filed.setAccessible(true);
					assignment((Serializable) fieldVal, filed, configuration);
					filed.setAccessible(field.isAccessible());
				}
			});
		}
	}
	private  void parseBindDouble(Object fieldVal, Field field,final Configuration configuration) throws BindException {
		bindVar(fieldVal,field,configuration.getVariables());
	}
	/**
	 * 
	 * @param field 检查bindObject对象
	 * @param valiType 检查类型
	 * @return 是否检查通过
	 * @throws BindException 绑定异常
	 */
	private static boolean checkObject(Field field, boolean valiType) throws BindException {
		Class<?> clazz = field.getType();
		if (clazz == VOLIOBJECT_TYPE[0]) {
			return valiType = false;
		} else if (clazz == VOLIOBJECT_TYPE[1]) {
			return valiType = false;
		} else if (clazz == VOLIOBJECT_TYPE[2]) {
			return valiType = false;
		} else if (clazz == VOLIOBJECT_TYPE[3]) {
			return valiType = false;
		} else if (clazz == VOLIOBJECT_TYPE[4]) {
			return valiType = false;
		} else if (clazz == VOLIOBJECT_TYPE[5]) {
			return valiType = false;
		} else if (clazz == VOLIOBJECT_TYPE[6]) {
			return valiType = false;
		} else if (clazz == VOLIOBJECT_TYPE[7]) {
			return valiType = false;
		} else if (clazz == VOLIOBJECT_TYPE[8]) {
			return valiType = false;
		}
		// .....
		return valiType;
	}
	/**
	 * 
	* 绑定变量转计算参数,支持类型(float,Float,double,Double,short,Short,int,Integer,long,Long,String)
	* @author FayeWong
	* @since 2019年9月16日
	* @param fieldVal 变量值
	* @param field 变量
	* @param variables 计算参数
	* @throws BindException 绑定异常
	 */
	private static void bindVar(Object fieldVal, Field field, Map<String, Double> variables) throws BindException{
		BindVar bind = (BindVar) field.getAnnotation(BindVar.class);
		if(!bind.enable() && fieldVal != null) 
			throw new BindException("@BindVar('" + bind.value() + "') 未启用 ：" + field.getType()+ "{ }{ } :  " + field.getName());
		
		if(fieldVal != null) {
			if(field.getType() == BINDVAR_TYPE[0]) {
				variables.put(bind.value(), Double.valueOf((float)fieldVal));			
			}else if(field.getType() == BINDVAR_TYPE[1]) {
				variables.put(bind.value(), Double.valueOf((Float)fieldVal));			
			}else if(field.getType() == BINDVAR_TYPE[2]) {
				variables.put(bind.value(), (double)fieldVal);			
			}else if(field.getType() == BINDVAR_TYPE[3]) {
				variables.put(bind.value(), (Double)fieldVal);			
			}else if(field.getType() == BINDVAR_TYPE[4]) {
				variables.put(bind.value(), Double.valueOf((short)fieldVal));			
			}else if(field.getType() == BINDVAR_TYPE[5]) {
				variables.put(bind.value(), Double.valueOf((Short)fieldVal));			
			}else if(field.getType() == BINDVAR_TYPE[6]) {
				variables.put(bind.value(), Double.valueOf((int)fieldVal));			
			}else if(field.getType() == BINDVAR_TYPE[7]) {
				variables.put(bind.value(), Double.valueOf((Integer)fieldVal));			
			}else if(field.getType() == BINDVAR_TYPE[8]) {
				variables.put(bind.value(), Double.valueOf((long)fieldVal));		
			}else if(field.getType() == BINDVAR_TYPE[9]) { 				
				variables.put(bind.value(), Double.valueOf((Long)fieldVal));			
			}else if(field.getType() == BINDVAR_TYPE[10]) {
				try {
					variables.put(bind.value(), Double.valueOf((String)fieldVal));	
				}catch (NumberFormatException e) {
					throw new BindException("@BindVar('" + bind.value() + "') 绑定变量值无法转换成double ：" + field.getType()
					+ "{ }{ } :  " + field.getName(),e);
				}				
			}else {
				throw new BindException("@BindVar('" + bind.value() + "') 不能绑定该变量类型上 ：" + field.getType()
				+ "{ }{ } :  " + field.getName());
			}
		}
	}
}
