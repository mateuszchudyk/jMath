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
import pl.mateuszchudyk.jmath.exceptions.EvaluationException;

public class ConstantFolding implements OptimizationPass {
    @Override
    public ASTExpression run(ASTExpression ast) {
        if (ast.getClass() == ASTImmidiateValue.class)
            return null;
        else if (ast.getClass() == ASTVariable.class)
            return null;
        else if (ast.getClass() == ASTOperation.class) {
            ASTOperation operation = (ASTOperation)ast;

            for (int i = 0; i < operation.getNumberOfArguments(); i++) {
                if (operation.getArgument(i).getClass() != ASTImmidiateValue.class)
                    return null;
            }

            try {
                return new ASTImmidiateValue(ast.evaluate(null));
            }
            catch (EvaluationException ex) {
                return null;
            }
        }

        return null;
    }
}
