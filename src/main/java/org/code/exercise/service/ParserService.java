package org.code.exercise.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.code.exercise.service.helper.CalculatorUtils;

public class ParserService {

  private static String DELIMITER_REGEXP = "\\s+";

  private ParserService() {
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

    // We add spaces around parentheses to make them separate tokens
    String expressionWithSpacedParenthesis =
        expression
            .replace(
                CalculatorUtils.LEFT_PARENTHESIS,
                String.format(" %s ", CalculatorUtils.LEFT_PARENTHESIS))
            .replace(
                CalculatorUtils.RIGHT_PARENTHESIS,
                String.format(" %s ", CalculatorUtils.RIGHT_PARENTHESIS));

    // We split the expression using as delimiters any characters matching a blank space
    String[] parts = expressionWithSpacedParenthesis.trim().split(DELIMITER_REGEXP);
    return Arrays.asList(parts);
  }
}
