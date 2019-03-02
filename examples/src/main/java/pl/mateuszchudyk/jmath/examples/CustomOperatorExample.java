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
import pl.mateuszchudyk.jmath.operators.AssociativeType;
import pl.mateuszchudyk.jmath.operators.Operator;
import pl.mateuszchudyk.jmath.operators.OperatorType;

class PlusOne implements Operator {
    // Identifier of the function. Keep in mind that parser is case
    // insensitive.
    @Override
    public String getName() {
        return "++";
    }

    // Short description of the function.
    @Override
    public String getDescription() {
        return "Increase value by 1.";
    }

    // Evaluation of the operator.
    @Override
    public Double evaluate(Double[] args) throws EvaluationException {
        return args[0] + 1;
    }

    // Priority of the operator. The lower the stronger.
    @Override
    public int getPriority() {
        return 1;
    }

    // Position of the operator:
    //   - Left - before the operand
    //   - Middle - between the operands
    //   - Right - after the operand
    @Override
    public OperatorType getOperatorType() {
        return OperatorType.Right;
    }

    // Associative: Left/Right/Both
    @Override
    public AssociativeType getAssociativeType() {
        return AssociativeType.Both;
    }
}

public class CustomOperatorExample {
    public static void main(String[] args) {
        Parser parser = new Parser(ParserType.Default);

        // Notify the parser that "PlusOne" is a valid operator.
        parser.addOperator(new PlusOne());

        try {
            Expression expression = parser.parse("1++");
            System.out.println(expression.evaluate());
        }
        catch (ParseException | EvaluationException e) {
            System.out.println(e.getMessage());
        }
    }
}
