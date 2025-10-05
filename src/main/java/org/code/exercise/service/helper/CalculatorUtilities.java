package org.code.exercise.service.helper;

import java.util.Map;
import java.util.function.IntBinaryOperator;
import org.code.exercise.service.helper.enums.TokenType;

public class CalculatorUtilities {
  public static final String LEFT_PARENTHESIS = "(";
  public static final String RIGHT_PARENTHESIS = ")";

  private static final String OP_ADD = "+";
  private static final String OP_SUB = "-";
  private static final String OP_MUL = "*";
  private static final String OP_DIV = "/";

  private static final int PRIO_HIGH = 2;
  private static final int PRIO_LOW = 1;

  private static final String INT_REGEXP = "-?\\d+";

  private record Operator(int priority, IntBinaryOperator operation) {}

  private static final Map<String, Operator> OPERATORS_DEFINITION =
      Map.of(
          OP_ADD,
          new Operator(PRIO_LOW, (a, b) -> a + b),
          OP_SUB,
          new Operator(PRIO_LOW, (a, b) -> a - b),
          OP_MUL,
          new Operator(PRIO_HIGH, (a, b) -> a * b),
          OP_DIV,
          new Operator(
              PRIO_HIGH,
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

  public static int applyOperator(int a, int b, String operator) {
    return getOperatorOrThrow(operator).operation.applyAsInt(a, b);
  }

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
    return token.matches(INT_REGEXP);
  }
}
