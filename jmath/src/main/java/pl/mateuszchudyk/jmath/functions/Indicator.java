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

import pl.mateuszchudyk.jmath.exceptions.EvaluationException;

/**
 * Indicator. It takes three arguments: x, a, b and returns if a &lt;= x &lt;= b.
 *
 * <p><table>
 * <tr><th>Signature</th><td>indicator(x, a, b)</td></tr>
 * <tr><th>Domain</th><td>any numbers</td></tr>
 * </table></p>
 */
public class Indicator implements Function {
    @Override
    public String getName() {
        return "indicator";
    }

    @Override
    public String getDescription() {
        return "Indicator(x, a, b) = return true (1.0) if and only if a < x < b else return false (0.0).";
    }

    @Override
    public Double evaluate(Double[] args) throws EvaluationException {
        return args[1] <= args[0] && args[0] <= args[2] ? 1.0 : 0.0;
    }

    @Override
    public boolean checkNumberOfArguments(int numberOfArguments) {
        return numberOfArguments == 3;
    }
}
