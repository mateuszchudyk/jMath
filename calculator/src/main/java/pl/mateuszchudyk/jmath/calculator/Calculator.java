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

package pl.mateuszchudyk.jmath.calculator;

import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

import pl.mateuszchudyk.jmath.Constant;
import pl.mateuszchudyk.jmath.functions.Function;
import pl.mateuszchudyk.jmath.Expression;
import pl.mateuszchudyk.jmath.operators.Operator;
import pl.mateuszchudyk.jmath.Parser;
import pl.mateuszchudyk.jmath.ParserType;
import pl.mateuszchudyk.jmath.Variable;
import pl.mateuszchudyk.jmath.exceptions.EvaluationException;
import pl.mateuszchudyk.jmath.exceptions.ParseException;

public class Calculator {
    public static void main(String[] args) {
        Parser parser = new Parser(ParserType.Default);
        HashMap<String, Double> substitutions = new HashMap<>();

        System.out.println("#");
        System.out.println("# jMath Calculator");
        System.out.println("# Type 'help' to get information about available commands.");
        System.out.println("#");
        System.out.println("");

        Scanner input = new Scanner(System.in);
        do {
            System.out.print("> ");
            System.out.flush();

            while (!input.hasNext());
            String line = input.nextLine();

            if (line.equals("exit")) {
                break;
            }
            else if (line.equals("help")) {
                printCommandResult("name := value  - set variable 'name' to value 'value'");
                printCommandResult("constants      - list all defined constants");
                printCommandResult("variables      - list all defined variables");
                printCommandResult("functions      - list all defined functions");
                printCommandResult("operators      - list all defined operators");
                printCommandResult("");
                printCommandResult("help           - show this message");
                printCommandResult("exit           - quit from calculator");
            }
            else if (line.equals("constants")) {
                for (Constant constant : parser.getAllConstants()) {
                    printCommandResult(constant.getName() + " = " + constant.getValue());
                }
            }
            else if (line.equals("variables")) {
                ArrayList<String> names = new ArrayList<String>(substitutions.keySet());
                Collections.sort(names);

                for (String name : names) {
                    printCommandResult(name + " = " + substitutions.get(name));
                }
            }
            else if (line.equals("functions")) {
                ArrayList<Function> functions = new ArrayList<Function>();

                for (List<Function> funs : parser.getAllFunctions()) {
                    for (Function fun : funs) {
                        functions.add(fun);
                    }
                }
                Collections.sort(functions, new Comparator<Function>() {
                    @Override
                    public int compare(Function f1, Function f2) {
                        return f1.getName().compareTo(f2.getName());
                    }
                });

                for (Function function : functions) {
                    printCommandResult(function.getName() + ": " + function.getDescription());
                }
            }
            else if (line.equals("operators")) {
                ArrayList<Operator> operators = new ArrayList<Operator>();

                for (List<Operator> ops : parser.getAllOperators()) {
                    for (Operator op : ops) {
                        operators.add(op);
                    }
                }
                Collections.sort(operators, new Comparator<Operator>() {
                    @Override
                    public int compare(Operator o1, Operator o2) {
                        int result = o1.getPriority() - o2.getPriority();
                        if (result != 0)
                            return result;

                        return o1.getName().compareTo(o2.getName());
                    }
                });

                for (Operator operator : operators) {
                    printCommandResult("" + operator.getPriority() + " | " + operator.getName() + ": " + operator.getDescription());
                }
            }
            else if (line.contains(":=")) {
                String name = line.substring(0, line.indexOf(":=")).trim();
                String value = line.substring(line.indexOf(":=") + 2).trim();

                if (name.equals("") || value.equals("")) {
                    printCommandResult("Incorrect assignment!");
                    continue;
                }

                try {
                    Expression expression = parser.parse(value);
                    expression.setVariables(substitutions);
                    substitutions.put(name, expression.evaluate());
                    parser.addVariable(new Variable(name));
                    printCommandResult(name + " = " + substitutions.get(name));
                }
                catch (ParseException | EvaluationException e) {
                    printCommandResult(e.getMessage());
                }
            }
            else {
                try {
                    Expression expression = parser.parse(line);
                    expression.setVariables(substitutions);
                    printCommandResult(expression.evaluate().toString());
                }
                catch (ParseException | EvaluationException e) {
                    printCommandResult(e.getMessage());
                }
            }
        } while (true);
    }

    private static void printCommandResult(String result) {
        System.out.println("  " + result);
    }
}
