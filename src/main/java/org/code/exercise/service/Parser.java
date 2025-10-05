package org.code.exercise.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.code.exercise.service.helper.CalculatorUtilities;

public class Parser {

  private static String DELIMITER_REGEXP = "\\s+";

  private Parser() {
    throw new UnsupportedOperationException("Utility class");
  }

  /**
   * Tokenize the input string into numbers and operators
   *
   * @param expression arithmetic expression (ex: "2 + 3 * -1")
   * @return list of numbers and operators as tokens in the infix order
   */
  public static List<String> tokenize(String expression) {
    if (expression == null || expression.trim().isEmpty()) {
      return Collections.emptyList();
    }

    // we add spaces around parenthesis to make them separate tokens
    String expressionWithSpacedParenthesis =
        expression
            .replace(
                CalculatorUtilities.LEFT_PARENTHESIS,
                String.format(" %s ", CalculatorUtilities.LEFT_PARENTHESIS))
            .replace(
                CalculatorUtilities.RIGHT_PARENTHESIS,
                String.format(" %s ", CalculatorUtilities.RIGHT_PARENTHESIS));

    // we split the expression using characters matching the regexp '\s' as delimiters, which means
    // any blank character (space, tab, etc)
    String[] parts = expressionWithSpacedParenthesis.trim().split(DELIMITER_REGEXP);
    return Arrays.asList(parts);
  }
}
