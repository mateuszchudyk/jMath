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
 * Standard deviation.
 *
 * <p><table>
 * <tr><th>Signature</th><td>stddev(a, ...)</td></tr>
 * <tr><th>Arguments number</th><td>at least one</td></tr>
 * <tr><th>Domain</th><td>any numbers</td></tr>
 * </table></p>
 */
public class StandardDeviation implements Function {
    @Override
    public String getName() {
        return "stddev";
    }

    @Override
    public String getDescription() {
        return "Stddev(a, ...) = the standard deviation of the given numbers.";
    }

    @Override
    public Double evaluate(Double[] args) throws EvaluationException {
        double mean = 0;
        for (Double arg : args)
            mean += arg;
        mean /= args.length;
        
        double result = 0;
        for (Double arg : args)
            result += (arg - mean) * (arg - mean);
        
        return Math.sqrt(result / args.length);
    }

    @Override
    public boolean checkNumberOfArguments(int numberOfArguments) {
        return numberOfArguments >= 1;
    }
}
