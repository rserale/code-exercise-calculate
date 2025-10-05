package org.code.exercise.service;

import java.util.*;
import org.code.exercise.service.exception.EvaluatorStackException;
import org.code.exercise.service.helper.CalculatorUtils;
import org.code.exercise.service.helper.LogUtils;
import org.code.exercise.service.helper.enums.TokenType;

public class EvaluatorService {

  private EvaluatorService() {
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
    Deque<Integer> stack = new ArrayDeque<>();

    LogUtils.log("\nExpression evaluation:", LogUtils.LOW_V);

    // We simply evaluate the postfix expression from left to right
    for (String token : tokens) {
      LogUtils.log("Token: " + token + "   Stack: " + stack, LogUtils.HIGH_V);

      if (CalculatorUtils.getTokenType(token) == TokenType.NUMBER) {
        stack.push(Integer.parseInt(token));
      } else if (CalculatorUtils.getTokenType(token) == TokenType.OPERATOR) {
        applyOperatorToStack(token, stack);
      } else {
        throw new EvaluatorStackException("Invalid token in postfix expression: " + token);
      }
    }

    validateStackAfterEvaluation(stack);
    return stack.pop();
  }

  /*
   * We apply the operator to the last two numbers on the stack, and push the result.
   * */
  private static void applyOperatorToStack(String operator, Deque<Integer> stack) {
    if (stack.size() < 2) {
      throw new EvaluatorStackException(
          "Operation cannot be solved due to missing operands on the stack");
    }
    int b = stack.pop();
    int a = stack.pop();
    int result = CalculatorUtils.applyOperator(a, b, operator);
    stack.push(result);
  }

  /*
   * After processing all the tokens, there should be exactly one element left: the final result.
   * */
  private static void validateStackAfterEvaluation(Deque<Integer> stack) {
    LogUtils.log("Stack final state: " + stack + "\n", LogUtils.HIGH_V);

    if (stack.size() > 1) {
      throw new EvaluatorStackException("More than one element left on the stack");
    } else if (stack.size() == 0) {
      throw new EvaluatorStackException("Stack is empty");
    }
  }
}
