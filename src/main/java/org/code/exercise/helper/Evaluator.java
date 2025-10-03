package org.code.exercise.helper;

import java.util.*;
import org.code.exercise.exception.EvaluatorStackException;

public class Evaluator {

  private Evaluator() {
    throw new UnsupportedOperationException("Utility class");
  }

  /**
   * Evaluates a postfix (Reverse Polish Notation) arithmetic expression
   *
   * @param tokens list of numbers and operators in postfix order
   * @return the result of evaluating the expression
   * @throws IllegalArgumentException if the expression is invalid
   */
  public static int evaluatePostfixExpression(List<String> tokens) {
    Deque<Integer> stack = new ArrayDeque<>();

    // now that the expression is converted to postfix notation, we can simply evaluate it from left
    // to right
    for (String token : tokens) {
      if (TokenUtilities.isInteger(token)) {
        // if the token is a number, we push it on the stack
        stack.push(Integer.parseInt(token));
      } else if (TokenUtilities.isOperator(token)) {
        // if this is an operator, we're going to apply it to the two last numbers on the stack
        solveOperation(token, stack);
      }
    }

    // After processing all the tokens, there should be exactly one element left: the final result
    if (stack.size() > 1) {
      throw new EvaluatorStackException("More than one element left on the stack");
    } else if (stack.size() == 0) {
      throw new EvaluatorStackException("Stack is empty");
    }

    return stack.pop();
  }

  private static void solveOperation(String operator, Deque<Integer> stack) {
    // we need two numbers in the stack for solving the operation
    if (stack.size() < 2) {
      throw new EvaluatorStackException(
          "Operation cannot be solved due to missing operands on the stack");
    }
    int b = stack.pop();
    int a = stack.pop();
    // we apply the operator to the two numbers and push the result back on the stack
    int result = TokenUtilities.applyOperator(a, b, operator);
    stack.push(result);
  }
}
