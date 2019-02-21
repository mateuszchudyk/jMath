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

import java.util.HashMap;
import java.util.Map;
import pl.mateuszchudyk.jmath.ast.ASTExpression;
import pl.mateuszchudyk.jmath.exceptions.EvaluationException;

public class Expression {
    private final ASTExpression ast;
    private HashMap<String, Double> substitutions;

    protected Expression(ASTExpression ast) {
        this.ast = ast;
        this.substitutions = new HashMap<>();
    }

    protected ASTExpression getAST() {
        return ast;
    }

    /**
     * Set value of the given variable.
     * @param name Variable name.
     * @param value New value of variable.
     */
    public void setVariable(String name, Double value) {
        substitutions.put(name, value);
    }

    /**
     * Set values of many variables at once.
     * @param substitutions Variable substitutions.
     */
    public void setVariables(Map<String, Double> substitutions) {
        if (substitutions == null)
            return;

        for (String name : substitutions.keySet())
            setVariable(name, substitutions.get(name));
    }

    /**
     * Evaluate expression.
     * @return Evaluation result.
     * @throws EvaluationException
     */
    public Double evaluate() throws EvaluationException {
        return ast.evaluate(substitutions);
    }

    @Override
    public String toString() {
        return ast.toString();
    }
}
