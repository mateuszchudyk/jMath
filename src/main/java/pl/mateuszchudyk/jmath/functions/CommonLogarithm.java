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
 * Common logarithm of x, x in (0, +inf).
 * @author Mateusz Chudyk
 */
public class CommonLogarithm implements Function {
    @Override
    public String getName() {
        return "log10";
    }

    @Override
    public String getDescription() {
        return "Log10(x) = logarithm of x to base 10. Domain x: (0, +inf).";
    }

    @Override
    public Double evaluate(Double[] args) throws EvaluationException {
        if (args[0] <= 0)
            throw new OutsideDomainException(this, 1, args[0], "(0, +inf)");

        return Math.log10(args[0]);
    }

    @Override
    public boolean checkNumberOfArguments(int numberOfArguments) {
        return numberOfArguments == 1;
    }
}
