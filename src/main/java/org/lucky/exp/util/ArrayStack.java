/** 
 * @Project Name : LuckyExp
*
* @File name : ArrayStack.java
*
* @Author : FayeWong
*
* @Email : 50125289@qq.com
*
----------------------------------------------------------------------------------
*    Who        Version     Comments
* 1. FayeWong    1.0        数组栈
*
*
*
*
----------------------------------------------------------------------------------
*/
package org.lucky.exp.util;
import java.util.EmptyStackException;
public class ArrayStack {

    private double[] data;

    private int idx;

    public ArrayStack() {
        this(5);
    }

    ArrayStack(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException(
                    "堆栈容量不足");
        }

        data = new double[initialCapacity];
        idx = -1;
    }

    public void push(double value) {
        if (idx + 1 == data.length) {
            double[] temp = new double[(int) (data.length * 1.2) + 1];
            System.arraycopy(data, 0, temp, 0, data.length);
            data = temp;
        }

        data[++idx] = value;
    }

    public double peek() {
        if (idx == -1) {
            throw new EmptyStackException();
        }
        return data[idx];
    }

    public double pop() {
        if (idx == -1) {
            throw new EmptyStackException();
        }
        return data[idx--];
    }

    public boolean isEmpty() {
        return idx == -1;
    }

    public int size() {
        return idx + 1;
    }
}
