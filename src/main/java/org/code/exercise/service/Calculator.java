package org.code.exercise.service;

import java.util.List;
import org.code.exercise.helper.ExpressionConverter;
import org.code.exercise.helper.Parser;

public class Calculator {

  private Calculator() {
    throw new UnsupportedOperationException("Utility class");
  }

  /**
   * Parse and calculate the result of a simple arithmetic expression.
   *
   * @param expression arithmetic expression (ex: "2 + 3 * -1")
   * @return result of the calculation
   * @throws IllegalArgumentException if expression is invalid
   */
  public static int calculate(String expression) {
    if (expression == null || expression.isEmpty()) {
      throw new IllegalArgumentException("Expression must not be null or empty");
    }

    List<String> infixTokens = Parser.tokenize(expression);
    List<String> postfixTokens = ExpressionConverter.infixToPostfix(infixTokens);
    // TODO
    return 0; // temporary value
  }
}
