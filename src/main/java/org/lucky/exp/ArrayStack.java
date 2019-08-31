/** Copyright 2019 SAIC General Motors Corporation Ltd. All Rights Reserved.
*
* This software is published under the terms of the SGM Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
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
* 1. FayeWong    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
package org.lucky.exp;
import java.util.EmptyStackException;
class ArrayStack {

    private double[] data;

    private int idx;

    ArrayStack() {
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

    void push(double value) {
        if (idx + 1 == data.length) {
            double[] temp = new double[(int) (data.length * 1.2) + 1];
            System.arraycopy(data, 0, temp, 0, data.length);
            data = temp;
        }

        data[++idx] = value;
    }

    double peek() {
        if (idx == -1) {
            throw new EmptyStackException();
        }
        return data[idx];
    }

    double pop() {
        if (idx == -1) {
            throw new EmptyStackException();
        }
        return data[idx--];
    }

    boolean isEmpty() {
        return idx == -1;
    }

    int size() {
        return idx + 1;
    }
}
