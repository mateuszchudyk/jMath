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

import java.util.Random;
import pl.mateuszchudyk.jmath.exceptions.EvaluationException;
import pl.mateuszchudyk.jmath.exceptions.OutsideDomainException;

/**
 * Takes two parameters: mean, stddev. Return the random number with a normal distribution (stddev in (0, +inf)).
 */
public class NormalDistributionRandom_2 implements Function {
    private Random random;

    public NormalDistributionRandom_2() {
        random = new Random();
    }

    @Override
    public String getName() {
        return "nrand";
    }

    @Override
    public String getDescription() {
        return "Nrand(mean, stddev) = the random number with a normal distribution. Domain stddev: (0, +inf).";
    }

    @Override
    public Double evaluate(Double[] args) throws EvaluationException {
    // Check domain
        if (args[1] <= 0)
            throw new OutsideDomainException(this, 2, args[1], "(0, +inf)");
        return args[0] + (2*random.nextDouble()-1)*args[1];
    }

    @Override
    public boolean checkNumberOfArguments(int numberOfArguments) {
        return numberOfArguments == 2;
    }
}
