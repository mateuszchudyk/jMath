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
 * Greates common divisor of the numbers (all in {1, 2, ...}).
 * @author Mateusz Chudyk
 */
public class GreatestCommonDivisor implements Function {
    @Override
    public String getName() {
        return "gcd";
    }

    @Override
    public String getDescription() {
        return "Gcd(a, ...) = the greatest common divisor of the numbers. Domain for all numbers: {1, 2, ...}.";
    }

    @Override
    public Double evaluate(Double[] args) throws EvaluationException {
        // Check domain
        for (int i = 0; i < args.length; i++) {
            double arg = args[0];
            if ((int)Math.floor(arg) != arg || arg < 1)
                throw new OutsideDomainException(this, i+1, args[i], "{1, 2, ...}");
        }

        long A = (int)Math.floor(args[0]);
        for (int i = 1; i < args.length; i++) {
            long B = (long)Math.floor(args[i]);
            long C = A;

            A = Math.max(C, B);
            B = Math.min(C, B);
            while (B != 0) {
                C = A % B;
                A = B;
                B = C;
            }

            if (A == 1)
                break;
        }
        return (double)A;
    }

    @Override
    public boolean checkNumberOfArguments(int numberOfArguments) {
        return numberOfArguments >= 1;
    }
}
