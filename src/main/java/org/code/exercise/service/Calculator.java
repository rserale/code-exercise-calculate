package org.code.exercise.service;

import org.code.exercise.helper.Parser;

import java.util.List;

public class Calculator {

    private Calculator() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Parses and calculates the result of a simple arithmetic expression.
     *
     * @param expression arithmetic expression (ex: "2 + 3 * -1")
     * @return the result of the calculation
     * @throws IllegalArgumentException if expression is invalid
     */
    public static int calculate(String expression) {
        if (expression == null || expression.isEmpty()) {
            throw new IllegalArgumentException("Expression must not be null or empty");
        }

        List<String> tokens = Parser.tokenize(expression);
        //TODO
        return 0; // temporary value
    }
}
