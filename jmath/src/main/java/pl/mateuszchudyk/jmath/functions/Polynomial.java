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
 * Polynomial. It evaluates polynomial: a0 + a1 * x + a2 * x^2 + ... + an * x^n.
 *
 * <p><table>
 * <tr><th>Signature</th><td>poly(x, a0, a1, ...)</td></tr>
 * <tr><th>Arguments number</th><td>At least 2: x and 0-th coefficient.</td></tr>
 * <tr><th>Domain</th><td>any numbers</td></tr>
 * </table></p>
 */
public class Polynomial implements Function {
    @Override
    public String getName() {
        return "poly";
    }

    @Override
    public String getDescription() {
        return "Poly(x, a0, a1, ...) = value of polynomial in point x. First argument is x (where calculate value of polynomial) next there are coefficients of polynomial (a0+a1*x+a2*x^2+...).";
    }

    @Override
    public Double evaluate(Double[] args) throws EvaluationException {
        double result = 0;

        for (int i = args.length-1; i >= 1; i--) {
            result *= args[0];
            result += args[i];
        }

        return result;
    }

    @Override
    public boolean checkNumberOfArguments(int numberOfArguments) {
        return numberOfArguments >= 2;
    }
}
