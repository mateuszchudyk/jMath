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
 * Return theta from polar coordinate (r, theta) of point (x, y). Any (x, y) without (0, 0).
 * @author Mateusz Chudyk
 */
public class Arctangent_2 implements Function {
    @Override
    public String getName() {
        return "atan2";
    }

    @Override
    public String getDescription() {
        return "Atan2(y, x) = theta from polar coordinate (r, theta) of point (x, y). Domain: any (x, y) without (0, 0).";
    }

    @Override
    public Double evaluate(Double[] args) throws EvaluationException {
        if (args[0] == 0 && args[1] == 0)
            return Double.NaN;
        else
            return Math.atan2(args[0], args[1]);
    }

    @Override
    public boolean checkNumberOfArguments(int numberOfArguments) {
        return numberOfArguments == 2;
    }
}
