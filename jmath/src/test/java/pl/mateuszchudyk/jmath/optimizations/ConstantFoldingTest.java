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
        runCheck(/* expected = */ "1", /* inputs = */ "1");
        runCheck(/* expected = */ "x", /* inputs = */  "x");
        runCheck(/* expected = */ "x + 2", /* inputs = */  "x + 2");
        runCheck(/* expected = */ "5", /* inputs = */  "2 + 3");
    }
}
