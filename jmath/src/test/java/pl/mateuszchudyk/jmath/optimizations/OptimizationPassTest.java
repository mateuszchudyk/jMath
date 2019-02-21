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

import pl.mateuszchudyk.jmath.TestBase;

import pl.mateuszchudyk.jmath.Expression;
import pl.mateuszchudyk.jmath.Optimizer;
import pl.mateuszchudyk.jmath.OptimizerType;
import pl.mateuszchudyk.jmath.Parser;
import pl.mateuszchudyk.jmath.ParserType;
import pl.mateuszchudyk.jmath.Variable;
import pl.mateuszchudyk.jmath.exceptions.ParseException;
import pl.mateuszchudyk.jmath.optimizations.OptimizationPass;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Mateusz Chudyk
 */
public abstract class OptimizationPassTest extends TestBase {
    public void runCheck(String expected, String input) {
        Parser parser = new Parser(ParserType.Default);
        parser.addVariable(new Variable("x"));
        parser.addVariable(new Variable("y"));
        Optimizer optimizer = new Optimizer(OptimizerType.Empty);
        OptimizationPass pass = (OptimizationPass)createOperationInstance();

        try {
            Expression in = parser.parse(input);
            Expression exp = parser.parse(expected);
            Expression output = optimizer.apply(in, pass);
            assertEquals(exp.toString(), output.toString());
        }
        catch (ParseException e) {
            fail();
        }
    }

    @Test
    public abstract void runTest();
}
