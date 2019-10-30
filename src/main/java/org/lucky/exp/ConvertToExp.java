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
import org.lucky.exp.util.ValiSerializableObj;
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
						if (value.getIndex() + 1 > formula.length) {
							throw new BindException("公式值选择过大，请检查变量'" +entity.getClass()+"'，'"+field.getName() + "'绑定的@Calculation的公式数");
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
		if(!ValiSerializableObj.validationObject(field,valiType)) {
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
		ValiSerializableObj.bindVar(fieldVal,field,configuration.getVariables());
	}
}
