package org.code.exercise.helper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Parser {

    private Parser() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Tokenizes the input string into numbers and operators
     *
     * @param expression arithmetic expression (ex: "2 + 3 * -1")
     * @return a list of tokens in the infix order
     */
    public static List<String> tokenize(String expression) {
        if (expression == null || expression.trim().isEmpty()) {
            return Collections.emptyList();
        }
        String[] parts = expression.trim().split("\\s+");
        return Arrays.asList(parts);
    }
}
