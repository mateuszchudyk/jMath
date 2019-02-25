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
import pl.mateuszchudyk.jmath.exceptions.OutsideDomainException;

/**
 * Fibonnaci. It takes one argument and returns n-th element of Fibonacci's
 * sequence (starting from F_0 = 0, F_1 = 1).
 *
 * <p><table>
 * <tr><th>Signature</th><td>fib(n)</td></tr>
 * <tr><th>Domain</th><td>non-negative integer number</td></tr>
 * </table></p>
 */
public class Fibonacci implements Function {
    private double[] lut;

    public Fibonacci() {
        lut = new double[100];

        lut[0] = 0;
        lut[1] = 1;
        for (int i = 2; i < lut.length; i++)
            lut[i] = lut[i-1] + lut[i-2];
    }

    @Override
    public String getName() {
        return "fib";
    }

    @Override
    public String getDescription() {
        return "Fib(n) = the n-th element of Fibonacci's sequence (F_0 = 0, F_1 = 1). Domain n: {0, 1, 2, ...}.";
    }

    @Override
    public Double evaluate(Double[] args) throws EvaluationException {
        int n = (int)Math.floor(args[0]);
        if (n != args[0] || n < 0)
            throw new OutsideDomainException(this, 1, args[0], "{0, 1, 2, ...}");

        if (n < 100)
            return lut[n];
        else {
            double A = lut[lut.length-2];
            double B = lut[lut.length-1];
            double C = 0;
            for (int i = lut.length; i <= n; i++) {
                C = A + B;
                A = B;
                B = C;
            }
            return C;
        }
    }

    @Override
    public boolean checkNumberOfArguments(int numberOfArguments) {
        return numberOfArguments == 1;
    }
}
