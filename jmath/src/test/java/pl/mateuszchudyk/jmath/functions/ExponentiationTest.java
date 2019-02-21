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
public class ExponentiationTest extends FunctionTest {
    @Override
    public void evaluateTest() {
        evaluateCheck(/* expected = */ 0.0, /* inputs = */ 0.0, 1.0);
        evaluateCheck(/* expected = */ 1.0, /* inputs = */ 1.0, 0.0);
        evaluateCheck(/* expected = */ 4.0, /* inputs = */ 2.0, 2.0);
        evaluateCheck(/* expected = */ 10.04510856630514, /* inputs = */ 3.0, 2.1);
        evaluateCheck(/* expected = */ 0.013602352551501938, /* inputs = */ 4.0, -3.1);
        evaluateCheck(/* expected = */ 1.4142135623730951, /* inputs = */ 2.0, 0.5);
        evaluateCheck(/* expected = */ 1.7320508075688772, /* inputs = */ 3.0, 0.5);
    }
}