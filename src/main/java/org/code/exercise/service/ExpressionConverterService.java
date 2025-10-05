package org.code.exercise.service;

import java.util.*;
import org.code.exercise.service.exception.ExpressionConverterInvalidTokenException;
import org.code.exercise.service.helper.CalculatorUtilities;
import org.code.exercise.service.helper.LogUtils;
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
    LogUtils.log(
        "\nInfix expression: " + tokens + "\nConverting infix expression to postfix:",
        LogUtils.LOW_V);
    for (String token : tokens) {
      LogUtils.log(
          "Token: " + token + "\nOperator stack: " + operatorStack + "\nOutput: " + output + "\n",
          LogUtils.HIGH_V);
      switch (CalculatorUtilities.getTokenType(token)) {
        case NUMBER -> output.add(token); // we immediately add the number to the output
        case OPERATOR ->
            handleOperator(
                token, operatorStack, output); // we apply the rules of priority for the operator
        case LEFT_PAREN ->
            operatorStack.push(token); // we push the left parenthesis on the operator stack
        case RIGHT_PAREN ->
            handleClosingParenthesis(
                operatorStack,
                output); // we fetch in the stack all the operators pushed since left parenthesis
        case INVALID ->
            throw new ExpressionConverterInvalidTokenException("Invalid token: " + token);
      }
    }
    // finally, we pop any operators left in the stack and append them to the output
    flushOperatorStack(operatorStack, output);
    LogUtils.log("Postfix expression: " + output, LogUtils.LOW_V);
    return output;
  }

  private static void handleClosingParenthesis(Deque<String> operatorStack, List<String> output) {
    // we pop and append to output all the operators present in the stack until we reach the left
    // parenthesis in the stack
    LogUtils.log("[CLOSING PARENTHESIS]", LogUtils.HIGH_V);
    while (!operatorStack.isEmpty()
        && CalculatorUtilities.getTokenType(operatorStack.peekFirst()) != TokenType.LEFT_PAREN) {
      LogUtils.log("Appending operator : " + operatorStack.peekFirst(), LogUtils.HIGH_V);
      output.add(operatorStack.pop());
      LogUtils.log("Operator stack: " + operatorStack, LogUtils.HIGH_V);
      LogUtils.log("Output: " + output, LogUtils.HIGH_V);
    }
    // if we reach the end of the stack, it means that a left parenthesis was missing in the
    // original expression
    if (operatorStack.isEmpty()) {
      throw new ExpressionConverterInvalidTokenException(
          "Mismatched parentheses: left parenthesis missing");
    }
    // finally, we get rid of the left parenthesis once we reach it
    operatorStack.pop();
    LogUtils.log("[PARENTHESIS CLOSED]\n", LogUtils.HIGH_V);
  }

  private static void handleOperator(
      String operator, Deque<String> operatorStack, List<String> output) {
    // we pop operators from the stack while they have higher or equal priority than our current one
    // those operators are appended to the output in the unstacking order
    while (!operatorStack.isEmpty()
        && CalculatorUtilities.getTokenType(operatorStack.peekFirst()) == TokenType.OPERATOR
        && CalculatorUtilities.getOperatorPriority(operator)
            <= CalculatorUtilities.getOperatorPriority(operatorStack.peekFirst())) {
      output.add(operatorStack.pop());
    }
    // once the unstacking process is finished, we push our current operator on the stack
    operatorStack.push(operator);
  }

  private static void flushOperatorStack(Deque<String> operatorStack, List<String> output) {
    while (!operatorStack.isEmpty()) {
      String operator = operatorStack.pop();
      // at this step, if we find a left parenthesis in the stack, it means that no right
      // parenthesis was present to close it
      if (CalculatorUtilities.getTokenType(operator) == TokenType.LEFT_PAREN) {
        throw new ExpressionConverterInvalidTokenException(
            "Mismatched parentheses: right parenthesis missing");
      }
      output.add(operator);
    }
  }
}
