/** 
 * @Project Name : LuckyExp
*
* @File name : Funcs.java
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
package org.lucky.exp.func;

import java.util.Arrays;
/**
  *   内置函数集合
 * @author FayeWong
 * @since 1.0
 *
 */
public class Funcs {
	//内置支持的函数
    private static final int INDEX_SIN   = 0;
    private static final int INDEX_COS   = 1;
    private static final int INDEX_TAN   = 2;
    private static final int INDEX_COT   = 3;
    private static final int INDEX_LOG   = 4;
    private static final int INDEX_LOG1P = 5;
    private static final int INDEX_ABS   = 6;
    private static final int INDEX_ACOS  = 7;
    private static final int INDEX_ASIN  = 8;
    private static final int INDEX_ATAN  = 9;
    private static final int INDEX_CBRT  = 10;
    private static final int INDEX_CEIL  = 11;
    private static final int INDEX_FLOOR = 12;
    private static final int INDEX_SINH  = 13;
    private static final int INDEX_SQRT  = 14;
    private static final int INDEX_TANH  = 15;
    private static final int INDEX_COSH  = 16;
    private static final int INDEX_POW   = 17;
    private static final int INDEX_EXP   = 18;
    private static final int INDEX_EXPM1 = 19;
    private static final int INDEX_LOG10 = 20;
    private static final int INDEX_LOG2  = 21;
    private static final int INDEX_SGN   = 22;
    private static final int INDEX_MIN4  = 23;
    private static final int INDEX_MAX4  = 24;
    private static final int INDEX_MIN2  = 25;
    private static final int INDEX_MAX2  = 26;
    private static final int INDEX_IF    = 27;

    public static final Func[] builtinFunctions = new Func[28];
    static {
    	/**正弦函数**/
        builtinFunctions[INDEX_SIN] = new Func("sin") {
            @Override
            public double call(Object... args) {
                return Math.sin((double)args[0]);
            }
        };
        /**余弦函数**/
        builtinFunctions[INDEX_COS] = new Func("cos") {
            @Override
            public double call(Object... args) {
                return Math.cos((double)args[0]);
            }
        };
        /**正切函数**/
        builtinFunctions[INDEX_TAN] = new Func("tan") {
            @Override
            public double call(Object... args) {
                return Math.tan((double)args[0]);
            }
        };
        /**余切函数**/
        builtinFunctions[INDEX_COT] = new Func("cot") {
            @Override
            public double call(Object... args) {
                double tan = Math.tan((double)args[0]);
                if (tan == 0d) {
                    throw new ArithmeticException("余切中被零除!");
                }
                return 1d/Math.tan((double)args[0]);
            }
        };
        /**对角函数，1 以e为底的对数**/
        builtinFunctions[INDEX_LOG] = new Func("log") {
            @Override
            public double call(Object... args) {
                return Math.log((double)args[0]);
            }
        };
        /**对角函数，2 以e为底的对数**/
        builtinFunctions[INDEX_LOG2] = new Func("log2") {
            @Override
            public double call(Object... args) {
                return Math.log((double)args[0]) / Math.log(2d);
            }
        };
        /**对角函数，10 以e为底的对数**/
        builtinFunctions[INDEX_LOG10] = new Func("log10") {
            @Override
            public double call(Object... args) {
                return Math.log10((double)args[0]);
            }
        };
        /**对角函数，Ln（x+ 1）**/
        builtinFunctions[INDEX_LOG1P] = new Func("log1p") {
            @Override
            public double call(Object... args) {
                return Math.log1p((double)args[0]);
            }
        };
        /**绝对值函数**/
        builtinFunctions[INDEX_ABS] = new Func("abs") {
            @Override
            public double call(Object... args) {
                return Math.abs((double)args[0]);
            }
        };
        /**返回角度值[0~π]   arc cos（y/z）**/
        builtinFunctions[INDEX_ACOS] = new Func("acos") {
            @Override
            public double call(Object... args) {
                return Math.acos((double)args[0]);
            }
        };
        /**返回角度值[-π/2，π/2]  arc sin（x/z）**/
        builtinFunctions[INDEX_ASIN] = new Func("asin") {
            @Override
            public double call(Object... args) {
                return Math.asin((double)args[0]);
            }
        };
        /**返回角度值[-π/2，π/2]**/
        builtinFunctions[INDEX_ATAN] = new Func("atan") {
            @Override
            public double call(Object... args) {
                return Math.atan((double)args[0]);
            }
        };
        /**立方根函数**/
        builtinFunctions[INDEX_CBRT] = new Func("cbrt") {
            @Override
            public double call(Object... args) {
                return Math.cbrt((double)args[0]);
            }
        };
        /**向下取整**/
        builtinFunctions[INDEX_FLOOR] = new Func("floor") {
            @Override
            public double call(Object... args) {
                return Math.floor((double)args[0]);
            }
        };
        /**双曲正弦函数sinh(x)=(exp(x) - exp(-x)) / 2.0;**/	
        builtinFunctions[INDEX_SINH] = new Func("sinh") {
            @Override
            public double call(Object... args) {
                return Math.sinh((double)args[0]);
            }
        };
        /**平方根函数**/
        builtinFunctions[INDEX_SQRT] = new Func("sqrt") {
            @Override
            public double call(Object... args) {
                return Math.sqrt((double)args[0]);
            }
        };
        /**tanh(x) = sinh(x) / cosh(x);**/
        builtinFunctions[INDEX_TANH] = new Func("tanh") {
            @Override
            public double call(Object... args) {
                return Math.tanh((double)args[0]);
            }
        };
        /**双曲余弦函数cosh(x)=(exp(x) + exp(-x)) / 2.0;**/
        builtinFunctions[INDEX_COSH] = new Func("cosh") {
            @Override
            public double call(Object... args) {
                return Math.cosh((double)args[0]);
            }
        };
        /**向上取整**/
        builtinFunctions[INDEX_CEIL] = new Func("ceil") {
            @Override
            public double call(Object... args) {
                return Math.ceil((double)args[0]);
            }
        };
        /**次方函数**/
        builtinFunctions[INDEX_POW] = new Func("pow",2) {
            @Override
            public double call(Object... args) {
                return Math.pow((double)args[0], (double)args[1]);
            }
        };
        /**e的x次幂**/ 
        builtinFunctions[INDEX_EXP] = new Func("exp") {
            @Override
            public double call(Object... args) {
                return Math.exp((double)args[0]);
            }
        };
        /**e的x次幂 - 1**/
        builtinFunctions[INDEX_EXPM1] = new Func("expm1") {
            @Override
            public double call(Object... args) {
                return Math.expm1((double)args[0]);
            }
        };
        builtinFunctions[INDEX_SGN] = new Func("signum") {
            @Override
            public double call(Object... args) {
                if ((double)args[0] > 0) {
                    return 1;
                } else if ((double)args[0] < 0) {
                    return -1;
                } else {
                    return 0;
                }
            }
        };
        /**取最小值,支持2位**/
        builtinFunctions[INDEX_MIN2] = new Func("min2",2) {
			@Override
			public double call(Object... args) {
				return Math.min((double)args[0], (double)args[1]);
			}
    	};
    	/**取最大值,支持2位**/
    	builtinFunctions[INDEX_MAX2] = new Func("max2",2) {
			@Override
			public double call(Object... args) {
				return Math.max((double)args[0], (double)args[1]);
			}
    	};
    	/**取最小值,支持4位，不满足4位需要追加占位符**/
    	builtinFunctions[INDEX_MIN4] = new Func("min4",4) {
			@Override
			public double call(Object... args) {
				Double[] doubles = (Double[])Arrays.asList(args).toArray(new Double[args.length]);
				double min = doubles[0];
				for(double d : doubles) {
					min = min < d ? min : d;
				}
				return min;
			}
    	};
    	/**取最大值,支持4位，不满足4位需要追加占位符**/
    	builtinFunctions[INDEX_MAX4] = new Func("max4",4) {
			@Override
			public double call(Object... args) {
				Double[] doubles = (Double[])Arrays.asList(args).toArray(new Double[args.length]);
				double max = doubles[0];
				for(double d : doubles) {
					max = max > d ? max : d;
				}
				return max;
			}
    	};
        /**
         * 高级函数
         * 三目运算符函数，第一位boolean值。搭配boolean运算符  '<' , '>','=' 使用
         */
        builtinFunctions[INDEX_IF] = new Func("if",3) {
            @Override
            public double call(Object... args) {
            	if(!(args[0] instanceof Boolean)) {
            		throw new ArithmeticException("三目运算符函数(if)第一位' "+args[0]+" '必须是布尔类型!");
            	}
                return (boolean)args[0] ? (double)args[1] : (double)args[2];
            }
        };
    }

    
    public static Func getBuiltinFunction(final String name) {
    	switch (name) {
		case "sin":
			return builtinFunctions[INDEX_SIN];
		case "cos":
			return builtinFunctions[INDEX_COS];
		case "tan":
			return builtinFunctions[INDEX_TAN];
		case "cot":
			return builtinFunctions[INDEX_COT];
		case "asin":
			return builtinFunctions[INDEX_ASIN];
		case "acos":
			return builtinFunctions[INDEX_ACOS];
		case "atan":
			return builtinFunctions[INDEX_ATAN];
		case "sinh":
			return builtinFunctions[INDEX_SINH];
		case "cosh":
			return builtinFunctions[INDEX_COSH];
		case "tanh":
			return builtinFunctions[INDEX_TANH];
		case "abs":
			return builtinFunctions[INDEX_ABS];
		case "log":
			return builtinFunctions[INDEX_LOG];
		case "log10":
			return builtinFunctions[INDEX_LOG10];
		case "log2":
			return builtinFunctions[INDEX_LOG2];
		case "log1p":
			return builtinFunctions[INDEX_LOG1P];
		case "ceil":
			return builtinFunctions[INDEX_CEIL];
		case "floor":
			return builtinFunctions[INDEX_FLOOR];
		case "sqrt":
			return builtinFunctions[INDEX_SQRT];
		case "cbrt":
			return builtinFunctions[INDEX_CBRT];
		case "pow":
			return builtinFunctions[INDEX_POW];
		case "exp":
			return builtinFunctions[INDEX_EXP];
		case "expm1":
			return builtinFunctions[INDEX_EXPM1];
		case "signum":
			return builtinFunctions[INDEX_SGN];
		case "min4":
			return builtinFunctions[INDEX_MIN4];
		case "max4":
			return builtinFunctions[INDEX_MAX4];
		case "min2":
			return builtinFunctions[INDEX_MIN2];
		case "max2":
			return builtinFunctions[INDEX_MAX2];
		case "if":
			return builtinFunctions[INDEX_IF];
		default:
			return null;
		}
    }
}
