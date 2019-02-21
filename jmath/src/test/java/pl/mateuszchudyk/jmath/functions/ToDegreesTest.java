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

package pl.mateuszchudyk.jmath.functions;

/**
 * @author Mateusz Chudyk
 */
public class ToDegreesTest extends FunctionTest {
    @Override
    public void evaluateTest() {
        evaluateCheck(/* expected = */ 0.0, /* inputs = */ 0.0);
        evaluateCheck(/* expected = */ 90.0, /* inputs = */ Math.PI / 2);
        evaluateCheck(/* expected = */ 180.0, /* inputs = */ Math.PI);
        evaluateCheck(/* expected = */ 270.0, /* inputs = */ 3 * Math.PI / 2);
        evaluateCheck(/* expected = */ 360.0, /* inputs = */ 2 * Math.PI);
    }
}