package org.code.exercise.helper;

import java.util.*;

public class Evaluator {

  private static final Map<String, Integer> OPERATORS_PRIORITIES =
          Map.of(
                  "+", 1,
                  "-", 1,
                  "*", 2,
                  "/", 2);

  private Evaluator() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static int evaluatePostfix(List<String> tokens) {
    Deque<Integer> stack = new ArrayDeque<>();

    for (String token : tokens) {
      if (isInteger(token)) {
        stack.push(Integer.parseInt(token));
      } else if (isOperator(token)) {
        if (stack.size() < 2) {
          throw new IllegalArgumentException("Invalid expression");
        }
        int b = stack.pop();
        int a = stack.pop();
        int result = applyOperator(a, b, token);
        stack.push(result);
      }
    }

    if (stack.size() != 1) {
      throw new IllegalArgumentException("Invalid expression evaluation");
    }

    return stack.pop();
  }

  private static int applyOperator(int a, int b, String op) {
    return switch (op) {
      case "+" -> a + b;
      case "-" -> a - b;
      case "*" -> a * b;
      case "/" -> {
        if (b == 0) throw new ArithmeticException("Division by zero");
        yield a / b;
      }
      default -> throw new IllegalArgumentException("Unknown operator: " + op);
    };
  }

  private static boolean isOperator(String token) {
    return OPERATORS_PRIORITIES.containsKey(token);
  }

  private static boolean isInteger(String token) {
    return token.matches("-?\\d+");
  }
}
