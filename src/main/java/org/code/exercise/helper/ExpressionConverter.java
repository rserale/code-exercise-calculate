package org.code.exercise.helper;

import java.util.*;
import org.code.exercise.exception.ExpressionConverterInvalidTokenException;

public class ExpressionConverter {

  private ExpressionConverter() {
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
   * @throws IllegalArgumentException if an invalid token is encountered
   */
  public static List<String> infixToPostfix(List<String> tokens) {
    List<String> output = new ArrayList<>();
    Deque<String> operatorStack = new ArrayDeque<>();

    for (String token : tokens) {
      if (TokenUtilities.isInteger(token)) {
        output.add(token);
      } else if (TokenUtilities.isOperator(token)) {
        handleOperator(token, operatorStack, output);
      } else if (TokenUtilities.isLeftParenthesis(token)) {
        operatorStack.push(token);
      } else if (TokenUtilities.isRightParenthesis(token)) {
        handleRightPArenthesis(operatorStack, output);
      } else {
        throw new ExpressionConverterInvalidTokenException("Invalid token: " + token);
      }
    }

    // finally, we pop any operators left in the stack and append them to the output
    while (!operatorStack.isEmpty()) {
      String elem = operatorStack.pop();
      if (TokenUtilities.isLeftParenthesis(elem) || TokenUtilities.isRightParenthesis(elem)) {
        throw new ExpressionConverterInvalidTokenException("Mismatched parentheses");
      }
      output.add(elem);
    }

    return output;
  }

  private static void handleRightPArenthesis(Deque<String> operatorStack, List<String> output) {
    while (!operatorStack.isEmpty() && !TokenUtilities.isLeftParenthesis(operatorStack.peek())) {
      output.add(operatorStack.pop());
    }
    if (operatorStack.isEmpty()) {
      throw new ExpressionConverterInvalidTokenException("Mismatched parentheses");
    }
    operatorStack.pop();
  }

  private static void handleOperator(
      String operator, Deque<String> operatorStack, List<String> output) {
    // we pop operators from the stack while they have higher or equal priority than our current one
    // those operators are appended to the output in the unstacking order
    while (!operatorStack.isEmpty()
        && TokenUtilities.isOperator(operatorStack.peek())
        && TokenUtilities.getOperatorPriority(operator)
            <= TokenUtilities.getOperatorPriority(operatorStack.peekFirst())) {
      output.add(operatorStack.pop());
    }
    // once the unstacking process is finished, we push our current operator on the stack
    operatorStack.push(operator);
  }
}
