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
public class BinaryOperatorSimplifyingTest extends OptimizationPassTest  {
    @Override
    public void runTest() {
        runCheck(/* expected = */ "x", /* inputs = */ "x + 0");
        runCheck(/* expected = */ "x", /* inputs = */ "0 + x");

        runCheck(/* expected = */ "to_logical(x)", /* inputs = */ "x and true");
        runCheck(/* expected = */ "to_logical(x)", /* inputs = */ "true and x");
        runCheck(/* expected = */ "0", /* inputs = */ "x and false");
        runCheck(/* expected = */ "0", /* inputs = */ "false and x");

        runCheck(/* expected = */ "x", /* inputs = */ "x / 1");

        runCheck(/* expected = */ "0", /* inputs = */ "x * 0");
        runCheck(/* expected = */ "x", /* inputs = */ "x * 1");
        runCheck(/* expected = */ "0", /* inputs = */ "0 * x");
        runCheck(/* expected = */ "x", /* inputs = */ "1 * x");

        runCheck(/* expected = */ "not(x)", /* inputs = */ "x nand true");
        runCheck(/* expected = */ "not(x)", /* inputs = */ "true nand x");
        runCheck(/* expected = */ "1", /* inputs = */ "x nand false");
        runCheck(/* expected = */ "1", /* inputs = */ "false nand x");

        runCheck(/* expected = */ "0", /* inputs = */ "x nor true");
        runCheck(/* expected = */ "0", /* inputs = */ "true nor x");
        runCheck(/* expected = */ "not(x)", /* inputs = */ "x nor false");
        runCheck(/* expected = */ "not(x)", /* inputs = */ "false nor x");

        runCheck(/* expected = */ "1", /* inputs = */ "x or true");
        runCheck(/* expected = */ "1", /* inputs = */ "true or x");
        runCheck(/* expected = */ "to_logical(x)", /* inputs = */ "x or false");
        runCheck(/* expected = */ "to_logical(x)", /* inputs = */ "false or x");

        runCheck(/* expected = */ "1", /* inputs = */ "x ^ 0");
        runCheck(/* expected = */ "x", /* inputs = */ "x ^ 1");
        runCheck(/* expected = */ "0", /* inputs = */ "0 ^ x");
        runCheck(/* expected = */ "1", /* inputs = */ "1 ^ x");

        runCheck(/* expected = */ "x", /* inputs = */ "x - 0");
        runCheck(/* expected = */ "-x", /* inputs = */ "0 - x");

        runCheck(/* expected = */ "not(x)", /* inputs = */ "x xor true");
        runCheck(/* expected = */ "not(x)", /* inputs = */ "true xor x");
        runCheck(/* expected = */ "to_logical(x)", /* inputs = */ "x xor false");
        runCheck(/* expected = */ "to_logical(x)", /* inputs = */ "false xor x");
    }
}
