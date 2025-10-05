package org.code.exercise.service.helper;

import org.code.exercise.service.helper.enums.TokenType;

import java.util.Map;
import java.util.function.IntBinaryOperator;

public class CalculatorUtilities {
  public static final String LEFT_PARENTHESIS = "(";
  public static final String RIGHT_PARENTHESIS = ")";

  private record Operator(int priority, IntBinaryOperator operation) {}

  private static final Map<String, Operator> OPERATORS_DEFINITION =
      Map.of(
          "+", new Operator(1, (a, b) -> a + b),
          "-", new Operator(1, (a, b) -> a - b),
          "*", new Operator(2, (a, b) -> a * b),
          "/",
              new Operator(
                  2,
                  (a, b) -> {
                    if (b == 0) throw new ArithmeticException("Division by zero");
                    return a / b;
                  }));

  private CalculatorUtilities() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static TokenType getTokenType(String token) {
    if (isInteger(token)) return TokenType.NUMBER;
    if (isOperator(token)) return TokenType.OPERATOR;
    if (isLeftParenthesis(token)) return TokenType.LEFT_PAREN;
    if (isRightParenthesis(token)) return TokenType.RIGHT_PAREN;
    return TokenType.INVALID;
  }

  public static boolean isOperator(String token) {
    return OPERATORS_DEFINITION.containsKey(token);
  }

  private static Operator getOperatorOrThrow(String operator) {
    Operator op = OPERATORS_DEFINITION.get(operator);
    if (op == null) throw new IllegalArgumentException("Unknown operator: " + operator);
    return op;
  }

  // TODO: test error in case of unknown operator
  public static int applyOperator(int a, int b, String operator) {
    return getOperatorOrThrow(operator).operation.applyAsInt(a, b);
  }

  // TODO: test error in case of unknown operator
  public static int getOperatorPriority(String operator) {
    return getOperatorOrThrow(operator).priority;
  }

  public static boolean isLeftParenthesis(String token) {
    return LEFT_PARENTHESIS.equals(token);
  }

  public static boolean isRightParenthesis(String token) {
    return RIGHT_PARENTHESIS.equals(token);
  }

  public static boolean isInteger(String token) {
    return token.matches("-?\\d+");
  }
}
