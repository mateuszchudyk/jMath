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

import java.util.ArrayList;
import pl.mateuszchudyk.jmath.ast.ASTExpression;
import pl.mateuszchudyk.jmath.ast.ASTImmidiateValue;
import pl.mateuszchudyk.jmath.ast.ASTOperation;
import pl.mateuszchudyk.jmath.ast.ASTVariable;
import pl.mateuszchudyk.jmath.optimizations.OptimizationPass;

/**
 * @author Mateusz Chudyk
 */
public class Optimizer {
    private final ArrayList<OptimizationPass> passes;

    public Optimizer(OptimizerType type) {
        passes = new ArrayList<>();

        if (type == OptimizerType.Default) {
        }
    }

    /**
     * Add optimization pass.
     * @param pass Pass to add.
     */
    public final void addPass(OptimizationPass pass) {
        passes.add(pass);
    }

    /**
     * Return result of applying all optimization passes to given expression.
     * @param expression Expression to optimization.
     * @return Optimized expression.
     */
    public Expression optimize(Expression expression) {
        ASTExpression result = expression.getAST();
        boolean end;

        do {
            end = true;
            for (int i = 0; i < passes.size(); i++) {
                ASTExpression temp = applyRec(result, passes.get(i));
                if (temp != null) {
                    end = false;
                    result = temp;
                }
            }
        } while (!end);

        return new Expression(result);
    }

    /**
     * Return result of applying given optimization pass to given expression.
     * @param expression Expression to optimization.
     * @param pass Pass which will be applied.
     * @return Optimized expression.
     */
    public Expression apply(Expression expression, OptimizationPass pass) {
        if (pass == null)
            return expression;

        ASTExpression ast = applyRec(expression.getAST(), pass);
        return (ast == null ? expression : new Expression(ast));
    }

    private ASTExpression applyRec(ASTExpression ast, OptimizationPass pass) {
        if (ast.getClass() == ASTImmidiateValue.class)
            return pass.run(ast);
        else if (ast.getClass() == ASTVariable.class)
            return pass.run(ast);
        else if (ast.getClass() == ASTOperation.class) {
            ASTOperation operation = (ASTOperation)ast;

            boolean anyChanges = false;
            ASTExpression[] arguments = new ASTExpression[operation.getNumberOfArguments()];
            for (int i = 0; i < operation.getNumberOfArguments(); i++) {
                arguments[i] = applyRec(operation.getArgument(i), pass);
                if (arguments[i] != null)
                    anyChanges = true;
                else
                    arguments[i] = operation.getArgument(i);
            }

            if (!anyChanges)
                return pass.run(ast);

            ASTExpression temp = new ASTOperation(operation.getOperation(), arguments);
            ASTExpression result = pass.run(temp);
            if (result == null)
                return temp;
            else
                return result;
        }

        return null;
    }
}
