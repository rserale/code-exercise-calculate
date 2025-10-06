package org.code.exercise.service.helper;

import java.util.Map;
import java.util.function.IntBinaryOperator;
import org.code.exercise.service.helper.enums.TokenType;

public class CalculatorUtils {
  // Parentheses symbols
  public static final String LEFT_PARENTHESIS = "(";
  public static final String RIGHT_PARENTHESIS = ")";

  // Operator symbols
  private static final String OP_ADD = "+";
  private static final String OP_SUB = "-";
  private static final String OP_MUL = "*";
  private static final String OP_DIV = "/";

  // Operator priority levels
  private static final int PRIORITY_HIGH = 2;
  private static final int PRIORITY_LOW = 1;

  // Regex to match signed integers
  private static final String INT_REGEXP = "-?\\d+";

  // Definition of an operator: a priority and the operation itself implemented from a functional
  // interface
  private record Operator(int priority, IntBinaryOperator operation) {}

  // Map of operator symbols to their definitions
  private static final Map<String, Operator> OPERATORS_DEFINITION =
      Map.of(
          OP_ADD,
          new Operator(PRIORITY_LOW, (a, b) -> a + b),
          OP_SUB,
          new Operator(PRIORITY_LOW, (a, b) -> a - b),
          OP_MUL,
          new Operator(PRIORITY_HIGH, (a, b) -> a * b),
          OP_DIV,
          new Operator(
              PRIORITY_HIGH,
              (a, b) -> {
                if (b == 0) throw new ArithmeticException("Division by zero");
                return a / b;
              }));

  private CalculatorUtils() {
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

  public static int applyOperator(int a, int b, String operator) {
    return getOperatorOrThrow(operator).operation.applyAsInt(a, b);
  }

  public static int getOperatorPriority(String operator) {
    return getOperatorOrThrow(operator).priority;
  }

  private static Operator getOperatorOrThrow(String operator) {
    Operator op = OPERATORS_DEFINITION.get(operator);
    if (op == null) throw new IllegalArgumentException("Unknown operator: " + operator);
    return op;
  }

  private static boolean isLeftParenthesis(String token) {
    return LEFT_PARENTHESIS.equals(token);
  }

  private static boolean isRightParenthesis(String token) {
    return RIGHT_PARENTHESIS.equals(token);
  }

  private static boolean isInteger(String token) {
    return token.matches(INT_REGEXP);
  }
}
