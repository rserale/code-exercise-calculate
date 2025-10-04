package org.code.exercise.helper;

import java.util.*;
import org.code.exercise.exception.ExpressionConverterInvalidTokenException;

public class ExpressionConverter {

  private ExpressionConverter() {
    throw new UnsupportedOperationException("Utility class");
  }

  private enum TokenType {
    NUMBER,
    OPERATOR,
    LEFT_PAREN,
    RIGHT_PAREN,
    INVALID
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
      switch (getTokenType(token)) {
        case NUMBER -> output.add(token); // we immediately add the number to the output
        case OPERATOR ->
            handleOperator(
                token, operatorStack, output); // we apply the rules of priority for the operator
        case LEFT_PAREN ->
            operatorStack.push(token); // we push the left parenthesis on the operator stack
        case RIGHT_PAREN ->
            handleRightParenthesis(
                operatorStack,
                output); // we fetch in the stack all the operators pushed since left parenthesis
        case INVALID ->
            throw new ExpressionConverterInvalidTokenException("Invalid token: " + token);
      }
    }
    // finally, we pop any operators left in the stack and append them to the output
    flushOperatorStack(operatorStack, output);
    return output;
  }

  private static TokenType getTokenType(String token) {
    if (CalculatorUtilities.isInteger(token)) return TokenType.NUMBER;
    if (CalculatorUtilities.isOperator(token)) return TokenType.OPERATOR;
    if (CalculatorUtilities.isLeftParenthesis(token)) return TokenType.LEFT_PAREN;
    if (CalculatorUtilities.isRightParenthesis(token)) return TokenType.RIGHT_PAREN;
    return TokenType.INVALID;
  }

  private static void handleRightParenthesis(Deque<String> operatorStack, List<String> output) {
    // TODO: maybe use getTokenType here instead of isoperator ?
    // we pop and append to output all the operators present in the stack until we reach the left
    // parenthesis in the stack
    while (!operatorStack.isEmpty()
        && !CalculatorUtilities.isLeftParenthesis(operatorStack.peek())) {
      output.add(operatorStack.pop());
    }
    // if we reach the end of the stack, it means that a left parenthesis was missing in the
    // original expression
    if (operatorStack.isEmpty()) {
      throw new ExpressionConverterInvalidTokenException(
          "Mismatched parentheses: left parenthesis missing");
    }
    // finally, we get rid of the left parenthesis once we reach it
    operatorStack.pop();
  }

  private static void handleOperator(
      String operator, Deque<String> operatorStack, List<String> output) {
    // we pop operators from the stack while they have higher or equal priority than our current one
    // those operators are appended to the output in the unstacking order
    // TODO: maybe use getTokenType here instead of isoperator ?
    while (!operatorStack.isEmpty()
        && CalculatorUtilities.isOperator(operatorStack.peek())
        && CalculatorUtilities.getOperatorPriority(operator)
            <= CalculatorUtilities.getOperatorPriority(operatorStack.peekFirst())) {
      output.add(operatorStack.pop());
    }
    // once the unstacking process is finished, we push our current operator on the stack
    operatorStack.push(operator);
  }

  private static void flushOperatorStack(Deque<String> operatorStack, List<String> output) {
    // TODO: maybe change elem syntax ?
    while (!operatorStack.isEmpty()) {
      String elem = operatorStack.pop();
      // at this step, if we find a left parenthesis in the stack, it means that no right
      // parenthesis was present to close it
      if (CalculatorUtilities.isLeftParenthesis(elem)) {
        throw new ExpressionConverterInvalidTokenException(
            "Mismatched parentheses: right parenthesis missing");
      }
      output.add(elem);
    }
  }
}
