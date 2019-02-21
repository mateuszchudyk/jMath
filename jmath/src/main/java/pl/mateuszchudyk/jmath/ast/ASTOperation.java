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

package pl.mateuszchudyk.jmath.ast;

import java.util.Map;
import pl.mateuszchudyk.jmath.Operation;
import pl.mateuszchudyk.jmath.exceptions.EvaluationException;
import pl.mateuszchudyk.jmath.functions.Function;
import pl.mateuszchudyk.jmath.operators.Operator;
import pl.mateuszchudyk.jmath.operators.OperatorType;

public class ASTOperation implements ASTExpression {
    private final ASTExpression[] arguments;
    private final Operation operation;

    public ASTOperation(Operation operation, ASTExpression[] arguments) {
        this.operation = operation;
        this.arguments = arguments;
    }

    @Override
    public Double evaluate(Map<String, Double> substitutions) throws EvaluationException {
        Double[] evaluatedArguments = new Double[arguments.length];
        for (int i = 0; i < arguments.length; i++)
            evaluatedArguments[i] = arguments[i].evaluate(substitutions);

        Double value = operation.evaluate(evaluatedArguments);
        if (value == null)
            throw new EvaluationException("ASTOperation '" + operation.getName() + "' cannot be evaluated!");

        return value;
    }

    @Override
    public String toString() {
        String result = "";
        for (ASTExpression arg : arguments)
            result += "\n" + arg.toString();
        result = result.replaceAll("\n", "\n   ");

        result = operation.getName() + result;

        if (operation instanceof Function)
            result = "[Function] " + result;
        else if (operation instanceof Operator) {
            if (((Operator)operation).getOperatorType() == OperatorType.Middle)
                result = "[BinaryOperator] " + result;
            else
                result = "[UnaryOperator] " + result;
        }

        return result;
    }

    public Operation getOperation() {
        return operation;
    }

    public int getNumberOfArguments() {
        return arguments.length;
    }

    public ASTExpression getArgument(int i) {
        if (arguments == null || i < 0 || i >= arguments.length)
            return null;

        return arguments[i];
    }

    public void setArgument(int i, ASTExpression argument) {
        if (arguments == null || i < 0 || i >= arguments.length)
            return;

        arguments[i] = argument;
    }
}
