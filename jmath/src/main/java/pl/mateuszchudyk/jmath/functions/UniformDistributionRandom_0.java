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

/**
 * Random number from uniform distribution [0, 1].
 *
 * <p><table>
 * <tr><th>Signature</th><td>rand()</td></tr>
 * </table></p>
 */
public class UniformDistributionRandom_0 implements Function {
    private final Random random;

    public UniformDistributionRandom_0() {
        random = new Random();
    }

    @Override
    public String getName() {
        return "rand";
    }

    @Override
    public String getDescription() {
        return "Rand() = the random number with a uniform distribution [0, 1).";
    }

    @Override
    public Double evaluate(Double[] args) throws EvaluationException {
        return random.nextDouble();
    }

    @Override
    public boolean checkNumberOfArguments(int numberOfArguments) {
        return numberOfArguments == 0;
    }
}
