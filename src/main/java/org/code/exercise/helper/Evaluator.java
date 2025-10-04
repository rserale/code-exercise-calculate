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
   * @throws EvaluatorStackException in case of wrong state of operator stack
   */
  public static int evaluatePostfixExpression(List<String> tokens) {
    //TODO: maybe use tokenType logic from ExpressionConverter ?
    Deque<Integer> stack = new ArrayDeque<>();

    // now that the expression is converted to postfix notation, we can simply evaluate it from left
    // to right
    for (String token : tokens) {
      if (CalculatorUtilities.isInteger(token)) {
        // if the token is a number, we push it on the stack
        stack.push(Integer.parseInt(token));
      } else if (CalculatorUtilities.isOperator(token)) {
        // if this is an operator, we apply it to the two last numbers on the stack
        applyOperatorToStack(token, stack);
      } else {
        // token is neither a number nor a recognized operator
        throw new EvaluatorStackException("Invalid token in postfix expression: " + token);
      }
    }
    // we alidate the final stack state
    validateStackAfterEvaluation(stack);
    return stack.pop();
  }

  private static void applyOperatorToStack(String operator, Deque<Integer> stack) {
    // we need two numbers in the stack for solving the operation
    if (stack.size() < 2) {
      throw new EvaluatorStackException(
              "Operation cannot be solved due to missing operands on the stack");
    }
    int b = stack.pop();
    int a = stack.pop();
    // we apply the operator to the two numbers and push the result back on the stack
    int result = CalculatorUtilities.applyOperator(a, b, operator);
    stack.push(result);
  }


  private static void validateStackAfterEvaluation(Deque<Integer> stack) {
    // After processing all the tokens, there should be exactly one element left: the final result
    if (stack.size() > 1) {
      throw new EvaluatorStackException("More than one element left on the stack");
    } else if (stack.size() == 0) {
      throw new EvaluatorStackException("Stack is empty");
    }
  }
}
