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
 * Arithmetic sequence. It takes three parameters: a0, r, n and returns n-th
 * element of arithmetic sequence where a0 is 0-th element and r is a difference
 * between two consecutive elements.
 *
 * <p><table>
 * <tr><th>Signature</th><td>aseq(a0, r, n)</td></tr>
 * <tr><th>Domain</th><td>n is non-negative integer number</td></tr>
 * </table></p>
 */
public class ArithmeticSequence implements Function {
    @Override
    public String getName() {
        return "aseq";
    }

    @Override
    public String getDescription() {
        return "Aseq(a0, r, n) = n-th element of arithmetic sequence where a0 is 0-th element and r is a difference between consecutive elements.";
    }

    @Override
    public Double evaluate(Double[] args) throws EvaluationException {
        double a0 = args[0];
        double r = args[1];
        int n = (int)Math.floor(args[2]);
        if (n != args[2] || n < 0)
            throw new OutsideDomainException(this, 1, args[0], "{0, 1, 2, ...}");
        
        return a0 + r * n;
    }

    @Override
    public boolean checkNumberOfArguments(int numberOfArguments) {
        return numberOfArguments == 3;
    }
}
