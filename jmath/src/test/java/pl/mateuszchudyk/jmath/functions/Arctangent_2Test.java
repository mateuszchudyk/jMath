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
public class Arctangent_2Test extends FunctionTest {
    @Override
    public void evaluateTest() {
        evaluateCheck(/* expected = */ 1.0038848218538872, /* inputs = */ Math.PI / 2, 1.0);
        evaluateCheck(/* expected = */ 0.36592070164208734, /* inputs = */ 0.123, 0.321);
        evaluateCheck(/* expected = */ 0.4875401204225192, /* inputs = */ 0.342, 0.645);
        evaluateCheck(/* expected = */ 0.4636476090008061, /* inputs = */ 0.111, 0.222);
        evaluateCheck(/* expected = */ 1.5704610135290578, /* inputs = */ 20.876, 0.007);
    }
}