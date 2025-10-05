package org.code.exercise.service;

import java.util.*;
import org.code.exercise.service.exception.ExpressionConverterInvalidTokenException;
import org.code.exercise.service.helper.CalculatorUtils;
import org.code.exercise.service.helper.enums.TokenType;

public class ExpressionConverterService {

  private ExpressionConverterService() {
    throw new UnsupportedOperationException("Utility class");
  }

  /**
   * Converts a list of tokens from infix to postfix notation using Shunting-yard algorithm, in
   * order to simplify the operator priority management when evaluating the expression.
   *
   * <p>Example: infix = ["7", "-", "3", "*", "2"] postfix = ["7", "3", "2", "*", "-"]
   *
   * @param tokens list of numbers and operators in infix order
   * @return list of numbers and operators in postfix order
   * @throws ExpressionConverterInvalidTokenException in case of syntax error or mismatched
   *     parentheses
   */
  public static List<String> infixToPostfix(List<String> tokens) {
    List<String> output = new ArrayList<>();
    Deque<String> operatorStack = new ArrayDeque<>();

    for (String token : tokens) {
      switch (CalculatorUtils.getTokenType(token)) {
        case NUMBER -> output.add(token);
        case OPERATOR -> handleOperator(token, operatorStack, output);
        case LEFT_PAREN -> operatorStack.push(token);
        case RIGHT_PAREN -> handleClosingParenthesis(operatorStack, output);
        case INVALID ->
            throw new ExpressionConverterInvalidTokenException("Invalid token: " + token);
      }
    }
    flushOperatorStack(operatorStack, output);
    return output;
  }

  /*
   * We pop and append to output all the operators present in the stack until we reach the left parenthesis in the stack, then we eliminate it.
   * */
  private static void handleClosingParenthesis(Deque<String> operatorStack, List<String> output) {
    while (!operatorStack.isEmpty()
        && CalculatorUtils.getTokenType(operatorStack.peekFirst()) != TokenType.LEFT_PAREN) {
      output.add(operatorStack.pop());
    }
    // If we reach the end of the stack, it means that a left parenthesis was missing in the
    // original expression
    if (operatorStack.isEmpty()) {
      throw new ExpressionConverterInvalidTokenException(
          "Mismatched parentheses: left parenthesis missing");
    }
    operatorStack.pop();
  }

  /*
   * We pop operators from the stack while they have higher or equal priority than the given one. Those operators are appended to the output in the unstacking order.
   * */
  private static void handleOperator(
      String operator, Deque<String> operatorStack, List<String> output) {
    while (!operatorStack.isEmpty()
        && CalculatorUtils.getTokenType(operatorStack.peekFirst()) == TokenType.OPERATOR
        && CalculatorUtils.getOperatorPriority(operator)
            <= CalculatorUtils.getOperatorPriority(operatorStack.peekFirst())) {
      output.add(operatorStack.pop());
    }
    // Once the unstacking process is finished, we push our current operator on the stack
    operatorStack.push(operator);
  }

  /*
   * After going through all the tokens, we pop all the operators left in the stack and append them to the output.
   * */
  private static void flushOperatorStack(Deque<String> operatorStack, List<String> output) {
    while (!operatorStack.isEmpty()) {
      String operator = operatorStack.pop();
      // At this step, if we find a left parenthesis in the stack, it means that no right
      // parenthesis was present in the expression to close it
      if (CalculatorUtils.getTokenType(operator) == TokenType.LEFT_PAREN) {
        throw new ExpressionConverterInvalidTokenException(
            "Mismatched parentheses: right parenthesis missing");
      }
      output.add(operator);
    }
  }
}
