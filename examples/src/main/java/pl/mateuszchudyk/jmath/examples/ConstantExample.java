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
import pl.mateuszchudyk.jmath.Parser;
import pl.mateuszchudyk.jmath.ParserType;
import pl.mateuszchudyk.jmath.Constant;
import pl.mateuszchudyk.jmath.exceptions.EvaluationException;
import pl.mateuszchudyk.jmath.exceptions.ParseException;

public class ConstantExample {
    public static void main(String[] args) {
        Parser parser = new Parser(ParserType.Default);

        // Notify the parser that "MagicConstant" is a valid constant and is
        // equal to 42.
        parser.addConstant(new Constant("MagicConstant", 42.0));

        try {
            Expression expression = parser.parse("MagicConstant ^ 2");
            System.out.println(expression.evaluate());
        }
        catch (ParseException | EvaluationException e) {
            System.out.println(e.getMessage());
        }
    }
}
