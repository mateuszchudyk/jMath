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

package pl.mateuszchudyk.jmath.operators;

import pl.mateuszchudyk.jmath.exceptions.EvaluationException;

/**
 * Less then or equal.
 *
 * <p><table>
 * <tr><th>Signature</th><td>x &lt;= y</td></tr>
 * <tr><th>Domain</th><td>any numbers</td></tr>
 * <tr><th>Priority</th><td>7</td></tr>
 * <tr><th>Associative</th><td>left</td></tr>
 * </table></p>
 */
public class LessThenOrEqual implements Operator {
    @Override
    public String getName() {
        return "<=";
    }

    @Override
    public String getDescription() {
        return "Less then or equal operator.";
    }

    @Override
    public Double evaluate(Double[] args) throws EvaluationException {
        return args[0] <= args[1] ? 1.0 : 0.0;
    }

    @Override
    public int getPriority() {
        return 7;
    }

    @Override
    public OperatorType getOperatorType() {
        return OperatorType.Middle;
    }

    @Override
    public AssociativeType getAssociativeType() {
        return AssociativeType.Left;
    }
}
