package org.lucky.exp.annotation;
/**
 * 允许 @BindVar @Calculation 绑定的变量支持的类型.
 * 
 * 也许世界就这样，我也还在路上，没有人能诉说。
 * 也许我只能沉默，眼泪湿润眼眶，可又不甘懦弱。
 * 低着头，期待白昼，接受所有的嘲讽。
 * 向着风，拥抱彩虹，勇敢的向前走。
 * 黎明的那道光会越过黑暗，打破一切恐惧我能找到答案。
 * 哪怕要逆着光就驱散黑暗，丢弃所有的负担不再孤单。不再孤单！ 
 * 
 * 
 *                                 来自歌曲《你的答案》
 *                                         2019/12/26
 * @author FayeWong
 * @since 1.0
 * 
 */
public enum BindType {
	f(float.class),
	F(Float.class),
	d(double.class),
	D(Double.class),
	s(short.class),
	S(Short.class),
	i(int.class),
	I(Integer.class),
	l(long.class),
	L(Long.class),
	STR(String.class);
	private Class<?> type;  
	private  BindType() {};
    private BindType(Class<?> type) {  
        this.type = type;  
 
    }
	public Class<?> getType() {
		return type;
	}
	public void setType(Class<?> type) {
		this.type = type;
	}
}
