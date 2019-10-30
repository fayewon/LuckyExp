/** 
* @Project Name : LuckyExp
*
* @File name : ExceptionCode.java
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
import java.util.ArrayList;
import java.util.List;
import org.lucky.exp.annotation.Formula_Choose;
/**
 * 公式选择器
*
* @author FayeWong
* 
 */
public class Selector {
	private final List<SelectorHandler> selectors = new ArrayList<SelectorHandler>();
	final class SelectorHandler{
		private Class<?> clazz;
		private String filedName;
		private Formula_Choose select;
		public Class<?> getClazz() {
			return clazz;
		}
		public String getFiledName() {
			return filedName;
		}
		public Formula_Choose getSelect() {
			return select;
		}
	}
	/**
	 * 
	 * @param clazz 变量的类
	 * @param filedName 变量名
	 * @param select 选择哪一个公式
	 * @since 1.0
	 */
	public void formulaFiled(Class<?> clazz,String filedName, Formula_Choose select)  {
		if(clazz == null || filedName == null || filedName.length() == 0)
			throw new IllegalArgumentException("公式选择器选择无效.");
		SelectorHandler handler = new SelectorHandler();
		handler.clazz = clazz;
		handler.filedName = filedName;
		handler.select = select;
		selectors.add(handler);
	}

	public List<SelectorHandler> getSelectors() {
		return selectors;
	}	
}
