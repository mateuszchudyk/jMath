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

package pl.mateuszchudyk.jmath.examples;

import pl.mateuszchudyk.jmath.Expression;
import pl.mateuszchudyk.jmath.Optimizer;
import pl.mateuszchudyk.jmath.OptimizerType;
import pl.mateuszchudyk.jmath.Parser;
import pl.mateuszchudyk.jmath.ParserType;
import pl.mateuszchudyk.jmath.Variable;
import pl.mateuszchudyk.jmath.exceptions.EvaluationException;
import pl.mateuszchudyk.jmath.exceptions.ParseException;

public class OptimizerExample {
    public static void main(String[] args) {
        Parser parser = new Parser(ParserType.Default);
        parser.addVariable(new Variable("x"));

        // Create default optimizer i.e.: containing all built-in optimization
        // passes.
        Optimizer optimizer = new Optimizer(OptimizerType.Default);

        try {
            Expression expression = parser.parse("2 * 2 * sin(x * 2 ^ 5)");

            System.out.println("Expression:");
            System.out.println(expression);
            System.out.println("Result (x = 5):");
            expression.setVariable("x", 5.0);
            System.out.println(expression.evaluate());

            System.out.println("");

            // Optimize the expression.
            expression = optimizer.optimize(expression);

            System.out.println("Expression after optimization:");
            System.out.println(expression);
            System.out.println("Result (x = 5):");
            expression.setVariable("x", 5.0);
            System.out.println(expression.evaluate());
        }
        catch (ParseException | EvaluationException e) {
            System.out.println(e.getMessage());
        }
    }
}
