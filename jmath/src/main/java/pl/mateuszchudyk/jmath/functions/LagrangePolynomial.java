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
 * Lagrange polynomial. It evaluates Lagrange polynomial interpolation in the
 * given point. First argument is point in which polynomial is evaluated,
 * the rest of arguments is a list of points.
 *
 * <p><table>
 * <tr><th>Signature</th><td>lagrange(x, x1, y1, ...)</td></tr>
 * <tr><th>Arguments number</th><td>Odd number, at least 3.</td></tr>
 * <tr><th>Domain</th><td>any numbers</td></tr>
 * </table></p>
 */
public class LagrangePolynomial implements Function {
    @Override
    public String getName() {
        return "lagrange";
    }

    @Override
    public String getDescription() {
        return "Lagrange(x, x1, y1, ...) = value of lagrange interpolation polynomial in point x. First argument is x (where calculate value of interpolation polynomial) next there are pairs (x, y) of check points. Number of arguments must be odd.";
    }

    @Override
    public Double evaluate(Double[] args) throws EvaluationException {
        int numberOfPoints = (args.length-1)/2;
        double result = 0;
        double x = args[0];
        double a;
        double b;
        for (int i = 0; i < numberOfPoints; i++) {
            a = 1;
            b = 1;
            for (int j = 0; j < numberOfPoints; j++) {
                if (i != j) {
                    a *= (x - args[1+2*j]);
                    b *= (args[1+2*i] - args[1+2*j]);
                }
            }
            result += a / b * args[1+2*i+1];
        }

        return result;
    }

    @Override
    public boolean checkNumberOfArguments(int numberOfArguments) {
        return numberOfArguments >= 3 && numberOfArguments % 2 == 1;
    }
}
