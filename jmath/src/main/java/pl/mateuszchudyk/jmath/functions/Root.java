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
 * Root. It takes two arguments: x, n and returns n-th root of x.
 *
 * <p><table>
 * <tr><th>Signature</th><td>root(x, n)</td></tr>
 * <tr><th>Domain</th><td>if n is even then x in [0, +inf) otherwise any number
 *                        </td></tr>
 * </table></p>
 */
public class Root implements Function {
    @Override
    public String getName() {
        return "root";
    }

    @Override
    public String getDescription() {
        return "Root(x, n) = the nth root of x. Domain x: if n is even then [0, +inf) else any real number.";
    }

    @Override
    public Double evaluate(Double[] args) throws EvaluationException {
        // Check domain
        int n = (int)Math.floor(args[1]);
        if (n != args[1])
            throw new OutsideDomainException(this, 2, args[1], "{1, 2, 3, ...}");
        if (n % 2 == 0 && args[0] < 0)
            throw new OutsideDomainException(this, 1, args[0], "[0; +inf) for even 'n'");

        return (args[0] < 0 ? -1 : 1) * Math.pow(Math.abs(args[0]), 1.0 / n);
    }

    @Override
    public boolean checkNumberOfArguments(int numberOfArguments) {
        return numberOfArguments == 2;
    }
}
