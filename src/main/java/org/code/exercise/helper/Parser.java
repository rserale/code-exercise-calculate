package org.code.exercise.helper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Parser {

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
    String expressionWithSpacedParenthesis = expression.replace("(", " ( ").replace(")", " ) ");

    // we split the expression with any character matching the regexp '\s', which in this case means
    // any blank character (space, tab, etc)
    String[] parts = expressionWithSpacedParenthesis.trim().split("\\s+");
    return Arrays.asList(parts);
  }
}
