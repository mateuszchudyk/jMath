/*
 * MIT License
 *
 * Copyright (c) 2018-2019 Mateusz Chudyk
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package pl.mateuszchudyk.jmath;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import static org.junit.Assert.fail;

/**
 * @author Mateusz Chudyk
 */
public abstract class TestBase  {
    private Constructor<?> constructor = null;

    public TestBase() {
        try {
            String className = getClass().getName();
            className = className.substring(0, className.length() - 4);
            Class<?> functionClass = Class.forName(className);
            constructor = functionClass.getConstructor();
        }
        catch (ClassNotFoundException | NoSuchMethodException | SecurityException ex) {
            constructor = null;
        }
    }

    protected Object createOperationInstance() {
        if (constructor == null)
            return null;

        try {
            return constructor.newInstance();
        }
        catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            fail("Cannot create instance of class!");
        }

        return null;
    }
}
