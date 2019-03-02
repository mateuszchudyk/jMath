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

import java.util.HashMap;
import pl.mateuszchudyk.jmath.exceptions.EvaluationException;
import pl.mateuszchudyk.jmath.exceptions.OutsideDomainException;

/**
 * Factorial. It takes one argument and returns n!.
 *
 * <p><table>
 * <tr><th>Signature</th><td>factorial(n)</td></tr>
 * <tr><th>Domain</th><td>non-negative integer number</td></tr>
 * </table></p>
 */
public class Factorial implements Function {
    private HashMap<Integer, Double> lut;

    public Factorial() {
        lut = new HashMap<>();
    }

    @Override
    public String getName() {
        return "factorial";
    }

    @Override
    public String getDescription() {
        return "Factorial(n) = the product of the numbers from 1 to n. Domain n: {0, 1, 2, ...}.";
    }

    @Override
    public Double evaluate(Double[] args) throws EvaluationException {
        int n = (int)Math.floor(args[0]);
        if (n != args[0] || n < 0)
            throw new OutsideDomainException(this, 1, args[0], "{0, 1, 2, ...}");

        if (lut.containsKey(n))
            return lut.get(n);
        else {
            double result = 1;
            for (int i = 2; i <= n; i++) {
                result *= i;
            }
            lut.put(n, result);
            return result;
        }
    }

    @Override
    public boolean checkNumberOfArguments(int numberOfArguments) {
        return numberOfArguments == 1;
    }
}
