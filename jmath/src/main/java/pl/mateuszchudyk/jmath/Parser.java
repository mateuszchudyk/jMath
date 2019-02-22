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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import pl.mateuszchudyk.jmath.ast.ASTExpression;
import pl.mateuszchudyk.jmath.ast.ASTImmidiateValue;
import pl.mateuszchudyk.jmath.ast.ASTOperation;
import pl.mateuszchudyk.jmath.ast.ASTVariable;
import pl.mateuszchudyk.jmath.exceptions.ParseException;
import pl.mateuszchudyk.jmath.functions.Function;
import pl.mateuszchudyk.jmath.operators.AssociativeType;
import pl.mateuszchudyk.jmath.operators.Operator;
import pl.mateuszchudyk.jmath.operators.OperatorType;

/**
 * Parser class.
 *
 * <p>Parser class is responsible for converting an expression written as a string
 * to <i>Abstract Syntax Tree</i> form. To do that, it needs to know what
 * constants, variables, operators and functions are available. Parser is case
 * insensitive. During resolving symbols, parser uses following rules:
 * <ul>
 * <li>constants and variables by name,</li>
 * <li>operators by name and type ({@link OperatorType}),</li>
 * <li>functions by name and number of arguments,</li>
 * </ul></p>
 *
 * <p>Built-in constants:
 * <ul>
 * <li>numeric: <i>pi</i>, <i>e</i>, <i>inf</i>,</li>
 * <li>logical: <i>true</i>, <i>false</i>.</li>
 * </ul></p>
 *
 * <p>Built-in operators:
 * <ul>
 * <li>arithmetic: <i>+</i>, <i>-</i>, <i>*</i>, <i>/</i>, <i>^</i> (power), <i>-</i> (negation),</li>
 * <li>comparators: <i>=</i>, <i>&lt;&gt;</i>, <i>&lt;</i>, <i>&gt;</i>, <i>&lt;=</i>, <i>&gt;=</i>,</li>
 * <li>logical: <i>not</i>, <i>or</i>, <i>and</i>, <i>xor</i>, <i>nor</i>, <i>nand</i>, <i>&lt;=&gt;</i> (if and only if), <i>=&gt;</i> (consequence),</li>
 * <li>other: <i>%</i> (percentage), <i>mod</i> (modulo).</li>
 * </ul></p>
 *
 * <p>Built-in functions:
 * <ul>
 * <li>basic: <i>pow</i>, <i>sqrt</i>, <i>root</i>, <i>exp</i>, <i>log</i>, <i>log2</i>, <i>log10</i>, <i>abs</i>, <i>floor</i>, <i>ceil</i>, <i>round</i>, <i>clamp</i>, <i>sgn</i>, <i>indicator</i>,</li>
 * <li>trigonometric: <i>sin</i>, <i>cos</i>, <i>tan</i>, <i>asin</i>, <i>acos</i>, <i>atan</i>, <i>atan2</i>, <i>sinh</i>, <i>cosh</i>, <i>tanh</i>,</li>
 * <li>statistical: <i>min</i>, <i>max</i>, <i>mean</i>, <i>stddev</i>, <i>median</i>,</li>
 * <li>random: <i>rand</i> (uniform), <i>nrand</i> (normal),</li>
 * <li>conversion: <i>to_degrees</i>, <i>to_radians</i>, <i>to_logical</i>,</li>
 * <li>compound: <i>gcd</i>, <i>lcm</i>, <i>factorial</i>, <i>fib</i>, <i>lagrange</i>, <i>poly</i>.</li>
 * </ul></p>
 *
 */
public final class Parser {
    private final Map<TokenType, List<TokenType>> rules;

    private final Map<String, Constant> constants;
    private final Map<String, Variable> variables;
    private final Map<String, List<Operator>> operators;
    private final Map<String, List<Function>> functions;

    private enum TokenType {
        Begin,
        End,

        ImmidiateValue,
        Constant,
        Variable,
        OperatorLeft,
        OperatorMiddle,
        OperatorRight,
        Function,

        ParenthesisLeft,
        ParenthesisRight,
        DecimalPoint,
        Comma,
    }

    private final int UNPROCESSED_TOKEN = -1;
    private final int UNRESOLVED_TOKEN = -2;
    private final int AMBIGUOUS_TOKEN = -3;

    private class Token {
        String token;
        TokenType type;
        ASTExpression expression;

        public Token(String token, TokenType type) {
            this.token = token;
            this.type = type;
        }
    }

    /**
     * Constructor.
     *
     * @param type Parser type, determine which built-in constants, operators
     * and function are added by default.
     */
    public Parser(ParserType type) {
        this.rules = new HashMap<>();
        this.constants = new HashMap<>();
        this.operators = new HashMap<>();
        this.functions = new HashMap<>();
        this.variables = new HashMap<>();

        // rules
        rules.put(TokenType.Begin, Arrays.asList(
            // no precedents
        ));
        rules.put(TokenType.End, Arrays.asList(
            TokenType.Begin,
            TokenType.ImmidiateValue,
            TokenType.Constant,
            TokenType.Variable,
            TokenType.OperatorRight,
            TokenType.ParenthesisRight
        ));
        rules.put(TokenType.ImmidiateValue, Arrays.asList(
            TokenType.Begin,
            TokenType.OperatorLeft,
            TokenType.OperatorMiddle,
            TokenType.ParenthesisLeft,
            TokenType.Comma
        ));
        rules.put(TokenType.Constant, Arrays.asList(
            TokenType.Begin,
            TokenType.OperatorLeft,
            TokenType.OperatorMiddle,
            TokenType.ParenthesisLeft,
            TokenType.Comma
        ));
        rules.put(TokenType.Variable, Arrays.asList(
            TokenType.Begin,
            TokenType.OperatorLeft,
            TokenType.OperatorMiddle,
            TokenType.ParenthesisLeft,
            TokenType.Comma
        ));
        rules.put(TokenType.OperatorLeft, Arrays.asList(
            TokenType.Begin,
            TokenType.OperatorLeft,
            TokenType.OperatorMiddle,
            TokenType.ParenthesisLeft,
            TokenType.Comma
        ));
        rules.put(TokenType.OperatorMiddle, Arrays.asList(
            TokenType.ImmidiateValue,
            TokenType.Constant,
            TokenType.Variable,
            TokenType.OperatorRight,
            TokenType.ParenthesisRight
        ));
        rules.put(TokenType.OperatorRight, Arrays.asList(
            TokenType.ImmidiateValue,
            TokenType.Constant,
            TokenType.Variable,
            TokenType.OperatorRight,
            TokenType.ParenthesisRight
        ));
        rules.put(TokenType.Function, Arrays.asList(
            TokenType.Begin,
            TokenType.OperatorLeft,
            TokenType.OperatorMiddle,
            TokenType.ParenthesisLeft,
            TokenType.Comma
        ));
        rules.put(TokenType.ParenthesisLeft, Arrays.asList(
            TokenType.Begin,
            TokenType.OperatorLeft,
            TokenType.OperatorMiddle,
            TokenType.Function,
            TokenType.ParenthesisLeft,
            TokenType.Comma
        ));
        rules.put(TokenType.ParenthesisRight, Arrays.asList(
            TokenType.ImmidiateValue,
            TokenType.Constant,
            TokenType.Variable,
            TokenType.OperatorRight,
            TokenType.ParenthesisLeft,
            TokenType.ParenthesisRight
        ));
        rules.put(TokenType.DecimalPoint, Arrays.asList(
            // no precedents
        ));
        rules.put(TokenType.Comma, Arrays.asList(
            TokenType.ImmidiateValue,
            TokenType.Constant,
            TokenType.Variable,
            TokenType.OperatorRight,
            TokenType.ParenthesisRight
        ));

        if (type == ParserType.Empty)
            return;

        // Constants
        addConstant(new Constant("false", 0.0));
        addConstant(new Constant("true", 1.0));
        addConstant(new Constant("e", Math.E));
        addConstant(new Constant("inf", Double.MAX_VALUE));
        addConstant(new Constant("pi", Math.PI));

        // Operators
        addOperator(new pl.mateuszchudyk.jmath.operators.Addition());
        addOperator(new pl.mateuszchudyk.jmath.operators.AdditiveInverse());
        addOperator(new pl.mateuszchudyk.jmath.operators.And());
        addOperator(new pl.mateuszchudyk.jmath.operators.Consequence());
        addOperator(new pl.mateuszchudyk.jmath.operators.Division());
        addOperator(new pl.mateuszchudyk.jmath.operators.Equal());
        addOperator(new pl.mateuszchudyk.jmath.operators.Exponentiation());
        addOperator(new pl.mateuszchudyk.jmath.operators.Factorial());
        addOperator(new pl.mateuszchudyk.jmath.operators.GreaterThen());
        addOperator(new pl.mateuszchudyk.jmath.operators.GreaterThenOrEqual());
        addOperator(new pl.mateuszchudyk.jmath.operators.IfAndOnlyIf());
        addOperator(new pl.mateuszchudyk.jmath.operators.LessThen());
        addOperator(new pl.mateuszchudyk.jmath.operators.LessThenOrEqual());
        addOperator(new pl.mateuszchudyk.jmath.operators.Modulo());
        addOperator(new pl.mateuszchudyk.jmath.operators.Multiplication());
        addOperator(new pl.mateuszchudyk.jmath.operators.Nand());
        addOperator(new pl.mateuszchudyk.jmath.operators.Nor());
        addOperator(new pl.mateuszchudyk.jmath.operators.Not());
        addOperator(new pl.mateuszchudyk.jmath.operators.NotEqual());
        addOperator(new pl.mateuszchudyk.jmath.operators.Or());
        addOperator(new pl.mateuszchudyk.jmath.operators.Percentage());
        addOperator(new pl.mateuszchudyk.jmath.operators.Subtraction());
        addOperator(new pl.mateuszchudyk.jmath.operators.Xor());

        // Functions
        addFunction(new pl.mateuszchudyk.jmath.functions.AbsoluteValue());
        addFunction(new pl.mateuszchudyk.jmath.functions.Arccosine());
        addFunction(new pl.mateuszchudyk.jmath.functions.Arcsine());
        addFunction(new pl.mateuszchudyk.jmath.functions.Arctangent_1());
        addFunction(new pl.mateuszchudyk.jmath.functions.Arctangent_2());
        addFunction(new pl.mateuszchudyk.jmath.functions.BinaryLogarithm());
        addFunction(new pl.mateuszchudyk.jmath.functions.Ceiling());
        addFunction(new pl.mateuszchudyk.jmath.functions.Clamp());
        addFunction(new pl.mateuszchudyk.jmath.functions.CommonLogarithm());
        addFunction(new pl.mateuszchudyk.jmath.functions.Cosine());
        addFunction(new pl.mateuszchudyk.jmath.functions.Exponential());
        addFunction(new pl.mateuszchudyk.jmath.functions.Exponentiation());
        addFunction(new pl.mateuszchudyk.jmath.functions.Factorial());
        addFunction(new pl.mateuszchudyk.jmath.functions.Fibonacci());
        addFunction(new pl.mateuszchudyk.jmath.functions.Floor());
        addFunction(new pl.mateuszchudyk.jmath.functions.GreatestCommonDivisor());
        addFunction(new pl.mateuszchudyk.jmath.functions.HiperbolicCosine());
        addFunction(new pl.mateuszchudyk.jmath.functions.HiperbolicSine());
        addFunction(new pl.mateuszchudyk.jmath.functions.HiperbolicTangent());
        addFunction(new pl.mateuszchudyk.jmath.functions.Indicator());
        addFunction(new pl.mateuszchudyk.jmath.functions.LagrangePolynomial());
        addFunction(new pl.mateuszchudyk.jmath.functions.LeastCommonMultiple());
        addFunction(new pl.mateuszchudyk.jmath.functions.Logarithm());
        addFunction(new pl.mateuszchudyk.jmath.functions.Maximum());
        addFunction(new pl.mateuszchudyk.jmath.functions.Mean());
        addFunction(new pl.mateuszchudyk.jmath.functions.Median());
        addFunction(new pl.mateuszchudyk.jmath.functions.Minimum());
        addFunction(new pl.mateuszchudyk.jmath.functions.NaturalLogarithm());
        addFunction(new pl.mateuszchudyk.jmath.functions.NormalDistributionRandom_0());
        addFunction(new pl.mateuszchudyk.jmath.functions.NormalDistributionRandom_2());
        addFunction(new pl.mateuszchudyk.jmath.functions.Polynomial());
        addFunction(new pl.mateuszchudyk.jmath.functions.Root());
        addFunction(new pl.mateuszchudyk.jmath.functions.Round());
        addFunction(new pl.mateuszchudyk.jmath.functions.Sign());
        addFunction(new pl.mateuszchudyk.jmath.functions.Sine());
        addFunction(new pl.mateuszchudyk.jmath.functions.SquareRoot());
        addFunction(new pl.mateuszchudyk.jmath.functions.StandardDeviation());
        addFunction(new pl.mateuszchudyk.jmath.functions.Tangent());
        addFunction(new pl.mateuszchudyk.jmath.functions.ToDegrees());
        addFunction(new pl.mateuszchudyk.jmath.functions.ToLogical());
        addFunction(new pl.mateuszchudyk.jmath.functions.ToRadians());
        addFunction(new pl.mateuszchudyk.jmath.functions.UniformDistributionRandom_0());
        addFunction(new pl.mateuszchudyk.jmath.functions.UniformDistributionRandom_2());
    }

    /**
     * Add new constant to the parser.
     *
     * <p>Constant name must be unique in respect to all added constants,
     * variables, operator and functions. Otherwise, the constant is not added
     * and false is returned.</p>
     *
     * @param constant Constant to be added.
     * @return True if the constant has beed added, false otherwise.
     */
    public boolean addConstant(Constant constant) {
        String name = constant.getName().toLowerCase();

        if (constants.containsKey(name) ||
            variables.containsKey(name) ||
            operators.containsKey(name) ||
            functions.containsKey(name))
        {
            return false;
        }

        constants.put(name, constant);
        return true;
    }

    /**
     * Get added constant by its name.
     *
     * @param name Name of the constant.
     * @return Constant or null if there is no added constant with that name.
     */
    public Constant getConstantByName(String name) {
        name = name.toLowerCase();

        if (constants.containsKey(name)) {
            return constants.get(name);
        }
        else {
            return null;
        }
    }

    /**
     * Get list of all added constants sorted by name.
     *
     * @return List of all added constants sorted by name.
     */
    public List<Constant> getAllConstants() {
        List<Constant> result = new ArrayList<>();

        for (Constant constant : constants.values()) {
            result.add(constant);
        }
        Collections.sort(
            result,
            (Constant a, Constant b) ->
                a.getName().toLowerCase().compareTo(b.getName().toLowerCase())
        );

        return result;
    }

    /**
     * Add new variable to the parser.
     *
     * <p>Variable name must be unique in respect to all added constants,
     * variables, operator and functions. Otherwise, the variable is not added
     * and false is returned.</p>
     *
     * @param variable Variable to be added.
     * @return True if the variable has beed added, false otherwise.
     */
    public boolean addVariable(Variable variable) {
        String name = variable.getName().toLowerCase();

        if (constants.containsKey(name) ||
            variables.containsKey(name) ||
            operators.containsKey(name) ||
            functions.containsKey(name))
        {
            return false;
        }

        variables.put(name, variable);
        return true;
    }

    /**
     * Get list of all added variables sorted by name.
     *
     * @return List of all added variables sorted by name.
     */
    public List<Variable> getAllVariables() {
        List<Variable> result = new ArrayList<>();

        for (Variable variable : variables.values()) {
            result.add(new Variable(variable.getName()));
        }
        Collections.sort(
            result,
            (Variable a, Variable b) -> a.getName().toLowerCase().compareTo(b.getName().toLowerCase())
        );

        return result;
    }

    /**
     * Add new operator to the parser.
     *
     * <p>Operator name must be unique in respect to all added constants,
     * variables and functions. Pair (operator.name, operator.type) must be
     * unique in respect to all addded operators. Otherwise the constant is not
     * added and false is returned.</p>
     *
     * @param operator Operator to be added.
     * @return True if the operator has beed added, false otherwise.
     */
    public boolean addOperator(Operator operator) {
        String name = operator.getName().toLowerCase();

        if (constants.containsKey(name) ||
            variables.containsKey(name) ||
            functions.containsKey(name))
        {
            return false;
        }

        if (!operators.containsKey(name)) {
            operators.put(name, new ArrayList<>());
            operators.get(name).add(operator);
        }
        else {
            List<Operator> list = operators.get(name);
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getOperatorType() == operator.getOperatorType()) {
                    return false;
                }
            }
            list.add(operator);
        }

        return true;
    }

    /**
     * Get added operator by its name.
     *
     * @param name Name of the operator.
     * @return List of operators or null if there is no added operator with that name.
     */
    public List<Operator> getOperatorByName(String name) {
        name = name.toLowerCase();

        if (operators.containsKey(name)) {
            return operators.get(name);
        }
        else {
            return null;
        }
    }

    /**
     * Get list of all added operators sorted by name.
     *
     * @return List of all added operators sorted by name.
     */
    public List<List<Operator>> getAllOperators() {
        List<List<Operator>> result = new ArrayList<>();

        for (List<Operator> list : operators.values()) {
            result.add(new ArrayList<>(list));
        }
        Collections.sort(
            result,
            (List<Operator> a, List<Operator> b) ->
                a.get(0).getName().toLowerCase().compareTo(b.get(0).getName().toLowerCase())
        );

        return result;
    }

    /**
     * Add new function to the parser.
     *
     * <p>Function name must be unique in respect to all added constants,
     * variables and functions. Otherwise the constant is not
     * added and false is returned.</p>
     *
     * <p>If pair (function.name, function.numberOfArguments) is not unique in
     * respect to all added functions then {@link ParseException}
     * "Expression is ambiguous" can be thrown.</p>
     *
     * @param function The function to be added.
     * @return True if the function has beed added, false otherwise.
     */
    public boolean addFunction(Function function) {
        String name = function.getName().toLowerCase();

        if (constants.containsKey(name) ||
            variables.containsKey(name) ||
            operators.containsKey(name))
        {
            return false;
        }

        if (!functions.containsKey(name)) {
            functions.put(name, new ArrayList<>());
            functions.get(name).add(function);
        }
        else {
            List<Function> list = functions.get(name);
            list.add(function);
        }

        return true;
    }

    /**
     * Get added function by its name.
     *
     * @param name Name of the function.
     * @return List of functions or null if there is no added function with that name.
     */
    public List<Function> getFunctionByName(String name) {
        name = name.toLowerCase();

        if (functions.containsKey(name)) {
            return functions.get(name);
        }
        else {
            return null;
        }
    }

    /**
     * Get list of all added functions sorted by name.
     *
     * @return List of all added functions sorted by name.
     */
    public List<List<Function>> getAllFunctions() {
        List<List<Function>> result = new ArrayList<>();

        for (List<Function> list : functions.values()) {
            result.add(new ArrayList<>(list));
        }
        Collections.sort(
            result,
            (List<Function> a, List<Function> b) ->
                a.get(0).getName().toLowerCase().compareTo(b.get(0).getName().toLowerCase())
        );

        return result;
    }

    /**
     * Parse an expression written as a string.
     *
     * <p>Expression must satisfy following conditions, otherwise {@link ParseException}
     * is thrown:
     * <ul>
     *   <li>All constants, variables, operators and functions used in the given
     *   expression have to be known to parser (added before parsing).</li>
     *   <li>Parentheses must be balanced.</li>
     *   <li>Expression must be resolvable.</li>
     *   <li>Expression cannot be ambiguous.</li>
     * </ul>
     * </p>
     *
     * @param expression Expression written as a string.
     * @return Exression object.
     * @throws ParseException
     */
    public Expression parse(String expression) throws ParseException {
        if (expression.isEmpty())
            return null;

        if (!areParenthesesBalanced(expression))
            throw new ParseException("Imbalanced parentheses!");

        List<Token> tokens = resolve(expression.toLowerCase());

        return new Expression(createAST(tokens));
    }

    private boolean areParenthesesBalanced(String expression) {
        int parentheses = 0;

        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '(')
                parentheses++;
            else if (expression.charAt(i) == ')')
                parentheses--;

            if (parentheses < 0)
                return false;
        }

        return parentheses == 0;
    }

    private List<TokenType> getPossibleTokenTypes(String token) {
        List<TokenType> result = new ArrayList<>();

        // Value
        try {
            if (Double.parseDouble(token) >= 0)
                result.add(TokenType.ImmidiateValue);
        }
        catch(Exception e) {}

        // Constant
        if (constants.containsKey(token)) {
            result.add(TokenType.Constant);
        }

        // Operators
        if (operators.containsKey(token)) {
            boolean left = false;
            boolean middle = false;
            boolean right = false;

            for (Operator o : operators.get(token)) {
                left |= o.getOperatorType() == OperatorType.Left;
                middle |= o.getOperatorType() == OperatorType.Middle;
                right |= o.getOperatorType() == OperatorType.Right;
            }

            if (left) {
                result.add(TokenType.OperatorLeft);
            }
            if (middle) {
                result.add(TokenType.OperatorMiddle);
            }
            if (right) {
                result.add(TokenType.OperatorRight);
            }
        }

        // Function
        if (functions.containsKey(token)) {
            result.add(TokenType.Function);
        }

        // Variable
        if (variables.containsKey(token)) {
            result.add(TokenType.Variable);
        }

        // ParenthesisLeft
        if (token.equals("(")) {
            result.add(TokenType.ParenthesisLeft);
        }

        // ParenthesisRight
        if (token.equals(")")) {
            result.add(TokenType.ParenthesisRight);
        }

        // DecimalPoint
        if (token.equals(".")) {
            result.add(TokenType.DecimalPoint);
        }

        // Comma
        if (token.equals(",")) {
            result.add(TokenType.Comma);
        }

        return result;
    }

    private List<Token> resolve(String expression) throws ParseException{
        int n = TokenType.values().length;
        int m = expression.length()+1;

        int[][] dp = new int[n][m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                dp[i][j] = UNPROCESSED_TOKEN;

        int numberOfSolutions = resolveRec(dp, expression, 0, TokenType.Begin);
        if (numberOfSolutions == UNRESOLVED_TOKEN) {
            throw new ParseException("Expression cannot be resolved!");
        }
        else if (numberOfSolutions == AMBIGUOUS_TOKEN) {
            throw new ParseException("Expression is ambiguous!");
        }

        List<Token> tokens = new ArrayList<>();
        String token = "";
        TokenType tt = null;
        for (int j = 0; j < m; j++) {
            for (int i = 0; i < n; i++) {
                if (dp[i][j] >= 0) {
                    if (!token.equals("")) {
                        tokens.add(new Token(token, tt));
                        token = "";
                    }
                    tt = TokenType.values()[dp[i][j]];
                    break;
                }
            }
            if (j < m-1 && expression.charAt(j) != ' ') {
                token += expression.charAt(j);
            }
        }

        return tokens;
    }

    private int resolveRec(int dp[][], String expression, int j, TokenType predecessor) {
        int i = predecessor.ordinal();

        if (j == expression.length())
            dp[i][j] = rules.get(TokenType.End).contains(predecessor) ? predecessor.ordinal() : UNRESOLVED_TOKEN;

        if (dp[i][j] != UNPROCESSED_TOKEN)
            return dp[i][j];

        dp[i][j] = UNRESOLVED_TOKEN;
        String token = "";
        for (int k = j; k < expression.length(); k++) {
            if (expression.charAt(k) == ' ') {
                if (token.equals(""))
                    continue;
                else
                    break;
            }
            token += expression.charAt(k);
            for (TokenType tt : getPossibleTokenTypes(token)) {
                if (rules.get(tt).contains(predecessor)) {
                    int rr = resolveRec(dp, expression, k+1, tt);
                    if (rr >= 0 || rr == AMBIGUOUS_TOKEN) {
                        if (dp[i][j] != UNRESOLVED_TOKEN || rr == AMBIGUOUS_TOKEN)
                            dp[i][j] = AMBIGUOUS_TOKEN;
                        else
                            dp[i][j] = tt.ordinal();
                    }
                }
            }
        }

        return dp[i][j];
    }

    private ASTExpression createAST(List<Token> tokens) throws ParseException {
        Stack<Operator> O = new Stack<>();              // operators
        Queue<ASTExpression> R = new LinkedList<>();    // result
        Stack<Integer> P = new Stack<>();               // paranthesis

        for (int i = 0; i < tokens.size(); i++) {
            Token currentToken = tokens.get(i);

            if (currentToken.type == TokenType.ImmidiateValue) {
                R.add(new ASTImmidiateValue(Double.parseDouble(currentToken.token)));
            }
            else if (currentToken.type == TokenType.Constant) {
                R.add(new ASTImmidiateValue(constants.get(currentToken.token).getValue()));
            }
            else if (currentToken.type == TokenType.Variable) {
                R.add(new ASTVariable(variables.get(currentToken.token)));
            }
            else if (currentToken.type == TokenType.OperatorLeft ||
                     currentToken.type == TokenType.OperatorMiddle ||
                     currentToken.type == TokenType.OperatorRight)
            {
                Operator op = null;
                for (int j = 0; j < operators.get(currentToken.token).size(); j++) {
                    Operator op2 = operators.get(currentToken.token).get(j);
                    if ((tokens.get(i).type == TokenType.OperatorLeft && op2.getOperatorType() == OperatorType.Left) ||
                        (tokens.get(i).type == TokenType.OperatorMiddle && op2.getOperatorType() == OperatorType.Middle) ||
                        (tokens.get(i).type == TokenType.OperatorRight && op2.getOperatorType() == OperatorType.Right))
                    {
                        op = op2;
                        break;
                    }
                }

                while (!O.isEmpty()) {
                    Operator op_top = O.peek();

                    if (!P.isEmpty() && O.size() == P.peek()) {
                        break;
                    }
                    else if (op.getOperatorType() == OperatorType.Left) {
                        break;
                    }
                    else if (op.getOperatorType() == OperatorType.Middle) {
                        if (op_top.getOperatorType() != OperatorType.Right && op_top.getPriority() > op.getPriority())
                            break;
                        else if (op.getAssociativeType() == AssociativeType.Right &&
                            op_top.getPriority() == op.getPriority() &&
                            op_top.getName().equals(op.getName()))
                        {
                            break;
                        }
                    }
                    else if (op.getOperatorType() == OperatorType.Right) {
                        if (op_top.getOperatorType() != OperatorType.Right && op_top.getPriority() > op.getPriority())
                            break;
                    }

                    R.add(new ASTOperation(O.pop(), null));
                }
                O.push(op);
            }
            else if (currentToken.type == TokenType.Function) {
                List<ASTExpression> args = new ArrayList<>();

                List<Token> argTokens = new ArrayList<>();
                int inner = 0;

                // Skip function name and parenthesis
                for (i = i+2; true; i++) {
                    Token token = tokens.get(i);

                    if (token.type == TokenType.ParenthesisLeft) {
                        argTokens.add(token);
                        inner++;
                    }
                    else if (token.type == TokenType.ParenthesisRight) {
                        if (inner != 0) {
                            argTokens.add(token);
                            inner--;
                        }
                        else
                            break;
                    }
                    else if (token.type == TokenType.Comma) {
                        if (inner != 0)
                            argTokens.add(token);
                        else {
                            args.add(createAST(argTokens));
                            argTokens.clear();
                        }
                    }
                    else {
                        argTokens.add(token);
                    }
                }
                if (!argTokens.isEmpty())
                    args.add(createAST(argTokens));

                Function function = null;
                for (int j = 0; j < functions.get(currentToken.token).size(); j++) {
                    Function f = functions.get(currentToken.token).get(j);
                    if (f.checkNumberOfArguments(args.size()))
                    {
                        if (function == null)
                            function = f;
                        else
                            throw new ParseException("Expression is ambiguous!");
                    }
                }
                if (function == null)
                    throw new ParseException("Expression cannot be resolved!");

                ASTExpression[] arguments = new ASTExpression[args.size()];
                arguments = args.toArray(arguments);
                R.add(new ASTOperation(function, arguments));
            }
            else if (currentToken.type == TokenType.ParenthesisLeft) {
                P.push(O.size());
            }
            else if (currentToken.type == TokenType.ParenthesisRight) {
                int limit = P.pop();

                while (O.size() > limit)
                    R.add(new ASTOperation(O.pop(), null));
            }
            else {
                throw new ParseException("Unknown type of token!");
            }
        }

        while (!O.isEmpty())
            R.add(new ASTOperation(O.pop(), null));

        Stack<ASTExpression> result = new Stack<>();
        while (!R.isEmpty()) {
            ASTExpression ast = R.poll();

            if (ast instanceof ASTImmidiateValue) {
                result.push(ast);
            }
            else if (ast instanceof ASTVariable) {
                result.push(ast);
            }
            else if (ast instanceof ASTOperation) {
                Operation operation = ((ASTOperation)ast).getOperation();

                if (operation instanceof Operator) {
                    if (((Operator)operation).getOperatorType() == OperatorType.Middle) {
                        ASTExpression right = result.pop();
                        ASTExpression left = result.pop();
                        result.push(new ASTOperation(operation, new ASTExpression[] { left, right }));
                    }
                    else {
                        result.push(new ASTOperation(operation, new ASTExpression[] { result.pop() }));
                    }
                }
                else if (operation instanceof Function) {
                    result.push(ast);
                }
                else {
                    throw new ParseException("Unknown type of AST!");
                }
            }
            else {
                throw new ParseException("Unknown type of AST!");
            }
        }

        if (result.size() != 1)
            throw new ParseException("Cannot create AST!");

        return result.get(0);
    }
}
