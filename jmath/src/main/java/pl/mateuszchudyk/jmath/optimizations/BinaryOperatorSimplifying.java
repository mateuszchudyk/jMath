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

import pl.mateuszchudyk.jmath.ast.ASTOperation;
import pl.mateuszchudyk.jmath.ast.ASTExpression;
import pl.mateuszchudyk.jmath.ast.ASTImmidiateValue;
import pl.mateuszchudyk.jmath.exceptions.EvaluationException;
import pl.mateuszchudyk.jmath.operators.Operator;

/**
 * @author Mateusz Chudyk
 */
public class BinaryOperatorSimplifying implements OptimizationPass {
    @Override
    public ASTExpression run(ASTExpression ast) {
        if (ast.getClass() != ASTOperation.class)
            return null;

        ASTOperation operation = (ASTOperation)ast;
        if (!(operation.getOperation() instanceof Operator) || operation.getNumberOfArguments() != 2)
            return null;

        Operator operator = (Operator)operation.getOperation();
        ASTExpression left = operation.getArgument(0);
        ASTExpression right = operation.getArgument(1);

        Double leftValue = null;
        Double rightValue = null;
        try {
            if (left.getClass() == ASTImmidiateValue.class)
                leftValue = left.evaluate(null);
            if (right.getClass() == ASTImmidiateValue.class)
                rightValue = right.evaluate(null);
        }
        catch (EvaluationException ex) {
            return null;
        }

        if (leftValue == null && rightValue == null)
            return null;

        /**
         * Addition. Cases:
         * - One operand is 0.
         */
        if (operator.getClass() == pl.mateuszchudyk.jmath.operators.Addition.class) {
            if (isEqual(leftValue, 0))
                return right;
            if (isEqual(rightValue, 0))
                return left;
        }
        /**
         * And. Cases:
         * - One operand is TRUE.
         * - One operand is FALSE.
         */
        else if (operator.getClass() == pl.mateuszchudyk.jmath.operators.And.class) {
            if (isTrue(leftValue))
                return toLogical(right);
            if (isTrue(rightValue))
                return toLogical(left);
            if (isFalse(leftValue) || isFalse(rightValue))
                return new ASTImmidiateValue(0.0);
        }
        /**
         * Division. Cases:
         * - Division by 1.
         */
        else if (operator.getClass() == pl.mateuszchudyk.jmath.operators.Division.class) {
            if (isEqual(rightValue, 1))
                return left;
        }
        /**
         * Multiplication. Cases:
         * - Multiplication by 0.
         * - Multiplication by 1.
         */
        else if (operator.getClass() == pl.mateuszchudyk.jmath.operators.Multiplication.class) {
            if (isEqual(leftValue, 0) || isEqual(rightValue, 0))
                return new ASTImmidiateValue(0.0);
            if (isEqual(leftValue, 1))
                return right;
            if (isEqual(rightValue, 1))
                return left;
        }
        /**
         * Nand. Cases:
         * - One operand is TRUE.
         * - One operand is FALSE.
         */
        else if (operator.getClass() == pl.mateuszchudyk.jmath.operators.Nand.class) {
            if (isTrue(leftValue))
                return new ASTOperation(new pl.mateuszchudyk.jmath.operators.Not(), new ASTExpression[] { right });
            if (isTrue(rightValue))
                return new ASTOperation(new pl.mateuszchudyk.jmath.operators.Not(), new ASTExpression[] { left });
            if (isFalse(leftValue) || isFalse(rightValue))
                return new ASTImmidiateValue(1.0);
        }
        /**
         * Nor. Cases:
         * - One operand is TRUE.
         * - One operand is FALSE.
         */
        else if (operator.getClass() == pl.mateuszchudyk.jmath.operators.Nor.class) {
            if (isTrue(leftValue) || isTrue(rightValue))
                return new ASTImmidiateValue(0.0);
            if (isFalse(leftValue))
                return new ASTOperation(new pl.mateuszchudyk.jmath.operators.Not(), new ASTExpression[] { right });
            if (isFalse(rightValue))
                return new ASTOperation(new pl.mateuszchudyk.jmath.operators.Not(), new ASTExpression[] { left });
        }
        /**
         * Or. Cases:
           - One operand is TRUE.
           - One operand is FALSE.
         */
        else if (operator.getClass() == pl.mateuszchudyk.jmath.operators.Or.class) {
            if (isTrue(leftValue) || isTrue(rightValue))
                return new ASTImmidiateValue(1.0);
            if (isFalse(leftValue))
                return toLogical(right);
            if (isFalse(rightValue))
                return toLogical(left);
        }
        /**
         * Power. Cases:
         * - x^0.
         * - x^1.
         * - 0^x.
         * - 1^x.
         */
        else if (operator.getClass() == pl.mateuszchudyk.jmath.operators.Exponentiation.class) {
            if (isEqual(rightValue, 0))
                return new ASTImmidiateValue(1.0);
            if (isEqual(rightValue, 1))
                return left;
            if (isEqual(leftValue, 0))
                return new ASTImmidiateValue(0.0);
            if (isEqual(leftValue, 1))
                return new ASTImmidiateValue(1.0);
        }
        /**
         * Subtraction. Cases:
         * - One operand is 0.
         */
        else if (operator.getClass() == pl.mateuszchudyk.jmath.operators.Subtraction.class) {
            if (isEqual(leftValue, 0))
                return new ASTOperation(new pl.mateuszchudyk.jmath.operators.AdditiveInverse(), new ASTExpression[] { right });
            if (isEqual(rightValue, 0))
                return left;
        }
        /**
         * Xor. Cases:
         * - One operand is FALSE.
         * - One operand is TRUE.
         */
        else if (operator.getClass() == pl.mateuszchudyk.jmath.operators.Xor.class) {
            if (isFalse(leftValue))
                return toLogical(right);
            if (isFalse(rightValue))
                return toLogical(left);
            if (isTrue(leftValue))
                return new ASTOperation(new pl.mateuszchudyk.jmath.operators.Not(), new ASTExpression[] { right });
            if (isTrue(rightValue))
                return new ASTOperation(new pl.mateuszchudyk.jmath.operators.Not(), new ASTExpression[] { left });
        }

        return null;
    }

    private ASTExpression toLogical(ASTExpression expression) {
        ASTExpression[] args = { expression };
        return new ASTOperation(new pl.mateuszchudyk.jmath.functions.ToLogical(), args);
    }

    private boolean isTrue(Double value) {
        if (value == null)
            return false;

        return value >= 1;
    }

    private boolean isFalse(Double value) {
        if (value == null)
            return false;

        return value < 1;
    }

    private boolean isEqual(Double value, double imm) {
        if (value == null)
            return false;

        return value == imm;
    }
}
