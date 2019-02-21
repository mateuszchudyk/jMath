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

package pl.mateuszchudyk.jmath.optimizations;

import pl.mateuszchudyk.jmath.ast.ASTExpression;
import pl.mateuszchudyk.jmath.ast.ASTImmidiateValue;
import pl.mateuszchudyk.jmath.ast.ASTOperation;
import pl.mateuszchudyk.jmath.ast.ASTVariable;
import pl.mateuszchudyk.jmath.Variable;

/**
 * @author Mateusz Chudyk
 */
public class ConstantFoldingTest extends OptimizationPassTest  {
    @Override
    public void runTest() {
        {
            ASTExpression input = new ASTImmidiateValue(new Double(1));
            ASTExpression expected = new ASTImmidiateValue(new Double(1));
            runCheck(expected, input);
        }

        {
            ASTExpression input = new ASTVariable(new Variable("x"));
            ASTExpression expected = new ASTVariable(new Variable("x"));
            runCheck(expected, input);
        }

        {
            ASTExpression input = new ASTOperation(
                new pl.mateuszchudyk.jmath.operators.Addition(),
                new ASTExpression[] {
                    new ASTVariable(new Variable("x")),
                    new ASTImmidiateValue(new Double(2))
                }
            );
            ASTExpression expected = new ASTOperation(
                new pl.mateuszchudyk.jmath.operators.Addition(),
                new ASTExpression[] {
                    new ASTVariable(new Variable("x")),
                    new ASTImmidiateValue(new Double(2))
                }
            );
            runCheck(expected, input);
        }

        {
            ASTExpression input = new ASTOperation(
                new pl.mateuszchudyk.jmath.operators.Addition(),
                new ASTExpression[] {
                    new ASTImmidiateValue(new Double(2)),
                    new ASTImmidiateValue(new Double(3))
                }
            );
            ASTExpression expected = new ASTImmidiateValue(new Double(5));
            runCheck(expected, input);
        }
    }
}
