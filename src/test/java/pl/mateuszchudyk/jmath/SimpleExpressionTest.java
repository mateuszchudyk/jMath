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

package pl.mateuszchudyk.jmath;

import pl.mateuszchudyk.jmath.Expression;
import pl.mateuszchudyk.jmath.Parser;
import pl.mateuszchudyk.jmath.ParserType;
import pl.mateuszchudyk.jmath.exceptions.EvaluationException;
import pl.mateuszchudyk.jmath.exceptions.ParseException;

import java.util.Map;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Mateusz Chudyk
 */
public class SimpleExpressionTest {
    @Test
    public void evaluateTest() {
        testEvaluate(0.0, "false");
        testEvaluate(1.0, "true");

        testEvaluate(1.0, "1");
        testEvaluate(1.0, "(1)");
        testEvaluate(1.0, "(((1)))");
        testEvaluate(-1.0, "-1");
        testEvaluate(-1.0, "(-1)");
        testEvaluate(-1.0, "(((-1)))");
        testEvaluate(-1.0, "((-(1)))");
        testEvaluate(1.0, "--1");
        testEvaluate(1.0, "-(-1)");
        testEvaluate(1.0, "----1");
        testEvaluate(1.0, "-1.0*-1.0");
        testEvaluate(-1.0, "-1.0*-1.0/-1.0");
        testEvaluate(4.0, "2+2");
        testEvaluate(6.0, "2+2*2");
        testEvaluate(8.0, "(2+2)*2");
        testEvaluate(512.0, "2^3^2");
        testEvaluate(24.0, "2*(3)*4");
        testEvaluate(-2.64, "-2^3!%---2");
    }

    private void testEvaluate(Double output, String expression) {
        Parser parser = new Parser(ParserType.Default);

        try {
            Expression expr = parser.parse(expression);
            assertEquals("Expression: " + expr, output, expr.evaluate());
        }
        catch (ParseException | EvaluationException ex) {
            fail();
        }
    }
}
