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
import pl.mateuszchudyk.jmath.exceptions.EvaluationException;
import pl.mateuszchudyk.jmath.exceptions.ParseException;
import pl.mateuszchudyk.jmath.exceptions.OutsideDomainException;
import pl.mateuszchudyk.jmath.functions.Function;

class IsEven implements Function {
    // Indentificator of the function. Keep in mind that parser is case
    // insensitive.
    @Override
    public String getName() {
        return "isEven";
    }

    // Short description of the function.
    @Override
    public String getDescription() {
        return "isEven(x) = return 1 if even, otherwise 0";
    }

    // Evalutation of the function.
    @Override
    public Double evaluate(Double[] args) throws EvaluationException {
        int value = (int)Math.floor(args[0]);
        if (value != args[0])
            throw new OutsideDomainException(this, 1, args[0], "Integers");

        return value % 2 == 0 ? 1.0 : 0.0;
    }

    // Information about number of arguments.
    @Override
    public boolean checkNumberOfArguments(int numberOfArguments) {
        return numberOfArguments == 1;
    }
}

public class CustomFunctionExample {
    public static void main(String[] args) {
        Parser parser = new Parser(ParserType.Default);

        // Notify the parser that "IsEven" is a valid function.
        parser.addFunction(new IsEven());

        try {
            Expression expression_even = parser.parse("isEven(4)");
            System.out.println(expression_even.evaluate());

            Expression expression_odd = parser.parse("isEven(5)");
            System.out.println(expression_odd.evaluate());
        }
        catch (ParseException | EvaluationException e) {
            System.out.println(e.getMessage());
        }
    }
}
