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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.lucky.exp.annotation.BindObject;
import org.lucky.exp.annotation.Condition;
import org.lucky.exp.annotation.ExceptionCode;
import org.lucky.exp.annotation.Formula_Choose;
import org.lucky.exp.annotation.Status;
import org.lucky.exp.exception.BindException;
import org.lucky.exp.exception.UnknownRuntimeException;
import org.lucky.exp.util.ValiSerializableObj;
import org.lucky.exp.annotation.Calculation;
import org.lucky.exp.annotation.BindDouble;
/**
 * 计算对象组装成计算变量
*
* @author FayeWong
* 
 */
public class ConvertToExp {
	private static ConvertToExp convertToExp;

	private ConvertToExp() {

	}

	public static ConvertToExp getInstance() {
			synchronized (ConvertToExp.class) {
				if (null == convertToExp) {
					convertToExp = new ConvertToExp();
				}
			}
		return convertToExp;
	}
	public   void assignment(Serializable entity,Field field,Map<String,Double> variables,Selector selector,List<Map<Condition, Object>> passExps,List<Map<Condition, Object>> waitExps) {
		try {			
			if (field.isAnnotationPresent(BindObject.class)) {
				final Object fieldVal = (Object)field.get(entity);
				parseBindObject(fieldVal, field,selector,variables,passExps,waitExps);
			}
			if (field.isAnnotationPresent(BindDouble.class)) {
				final Object fieldVal = (Object)field.get(entity);
				parseBindDouble(fieldVal, field, variables);
			}
			if (field.isAnnotationPresent(Calculation.class)) {
				final Object fieldVal = (Object)field.get(entity);
				parseCalculation(fieldVal,entity, field,selector,passExps,waitExps);
			}
		} catch (BindException e) {
			throw new UnknownRuntimeException(ExceptionCode.C_10043.getCode(),e);
		} catch (IllegalArgumentException e) {
			throw new UnknownRuntimeException(ExceptionCode.C_10042.getCode(),e);
		} catch (IllegalAccessException e) {
			throw new UnknownRuntimeException("成员变量 '"+field.getName()+"' 没有get()方法",e);
		}
	}
	
	@SuppressWarnings("unchecked")
	private   synchronized void parseCalculation(Object fieldVal,Serializable entity, Field field,Selector selector,List<Map<Condition, Object>> passExps,List<Map<Condition, Object>> waitExps) throws BindException {
		Calculation calculation = (Calculation) field.getAnnotation(Calculation.class);	
		if(field.getType() != Double.class) {
			throw new BindException("@Calculation() 必须绑定Double类型的字段 ：" + field.getType()
			+ "{ }{ } :  " + field.getName());
		}
		int index = 0;//默认使用第一个公式
		try {
			if(selector != null) {
				Iterator<Map.Entry<String, Formula_Choose>> iterator = selector.selector.entrySet().iterator();
				while (iterator.hasNext()) {
					InvocationHandler invocationHandler = Proxy.getInvocationHandler(calculation);
					Field hField = invocationHandler.getClass().getDeclaredField("memberValues");
					hField.setAccessible(true);
					Map<String, Object> memberValues = (Map<String, Object>) hField.get(invocationHandler);
					String[] formula = (String[]) memberValues.get("formula");
					field.setAccessible(field.isAccessible());
					hField.setAccessible(hField.isAccessible());
					Map.Entry<String, Formula_Choose> it = iterator.next();
					String key = it.getKey();
					key = key.contains(".") ? key.split("\\.")[1] : key;
					if (key.equals(field.getName())) {
						Formula_Choose value = it.getValue();
						index = value.getIndex();
						if (value.getIndex() + 1 > formula.length) {
							throw new BindException("公式值选择过大，请检查字段：{" + field.getName() + "}绑定的@Calculation的公式数");
						}
					}
				}
			}			
			if (calculation != null && fieldVal == null ) {
				Map<Condition, Object> parseObj = new HashMap<Condition, Object>();
				parseObj.put(Condition.field, field);
				parseObj.put(Condition.entity, entity);
				parseObj.put(Condition.status, calculation.action());
				parseObj.put(Condition.format, calculation.format());
				parseObj.put(Condition.expression, calculation.formula()[index]);
				switch ((Status) parseObj.get(Condition.status)) {
				case PASS:
					passExps.add(parseObj);
					break;
				case WAIT:
					waitExps.add(parseObj);
					break;
				}
			}
		} catch (IllegalArgumentException e) {
			throw new UnknownRuntimeException(ExceptionCode.C_10042.getCode(),e);
		} catch (IllegalAccessException e) {
			throw new UnknownRuntimeException(ExceptionCode.C_10042.getCode(),e);
		} catch (NoSuchFieldException | SecurityException e) {
			throw new UnknownRuntimeException(ExceptionCode.C_10042.getCode(),e);
		}
	}
	
	private  void parseBindObject(Object fieldVal, Field field,Selector selector,Map<String, Double> variables, List<Map<Condition, Object>> passExps,List<Map<Condition, Object>> waitExps) throws BindException {
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
		if(!ValiSerializableObj.validation(field,valiType)) {
			throw new BindException("@BindObject()不能绑定该对象上 ：" + field.getType()
			+ "{ }{ } :  " + field.getName());
		};
		if (fieldVal != null) {
			Class<?> clazz = field.getType();
			Field[] fields = clazz.getDeclaredFields();
			Arrays.asList(fields).forEach((filed)->{
				if (fieldVal instanceof Serializable) {
					filed.setAccessible(true);
					assignment((Serializable) fieldVal, filed, variables,selector,passExps,waitExps);
					filed.setAccessible(field.isAccessible());
				}
			});
		}
	}
	private  void parseBindDouble(Object fieldVal, Field field, Map<String, Double> variables) throws BindException {
		BindDouble bind = (BindDouble) field.getAnnotation(BindDouble.class);
		if(field.getType() != Double.class) {
			throw new BindException("@BindDouble('" + bind.key() + "') 必须绑定Double类型的字段 ：" + field.getType()
			+ "{ }{ } :  " + field.getName());
		}
		if (bind.enable() && fieldVal != null) {
			variables.put(bind.key(), (Double) fieldVal);
		}
	}
}
