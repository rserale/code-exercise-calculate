package org.code.exercise.helper;

import java.util.Map;

public class TokenUtilities {
  private static final Map<String, Integer> OPERATORS_PRIORITIES =
      Map.of(
          "+", 1,
          "-", 1,
          "*", 2,
          "/", 2);

  private TokenUtilities() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static boolean isOperator(String token) {
    return OPERATORS_PRIORITIES.containsKey(token);
  }

  public static int applyOperator(int a, int b, String operator) {
    return switch (operator) {
      case "+" -> a + b;
      case "-" -> a - b;
      case "*" -> a * b;
      case "/" -> {
        if (b == 0) throw new ArithmeticException("Division by zero");
        yield a / b;
      }
      default -> throw new IllegalArgumentException("Unknown operator: " + operator);
    };
  }

  public static int getOperatorPriority(String operator) {
    return OPERATORS_PRIORITIES.get(operator);
  }

  public static boolean isLeftParenthesis(String token) {
    return "(".equals(token);
  }

  public static boolean isRightParenthesis(String token) {
    return ")".equals(token);
  }

  public static boolean isInteger(String token) {
    return token.matches("-?\\d+");
  }
}
