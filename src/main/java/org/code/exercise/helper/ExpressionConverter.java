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
        // if the token is a number, we immediately add it to the output
        output.add(token);
      } else if (TokenUtilities.isOperator(token)) {
        // if the token is an operator, we apply the rules of priority for it
        handleOperator(token, operatorStack, output);
      } else if (TokenUtilities.isLeftParenthesis(token)) {
        // if the token is a left parenthesis, we push it on the operator stack
        operatorStack.push(token);
      } else if (TokenUtilities.isRightParenthesis(token)) {
        // if the token is a right parenthesis, we fetch in the operator stack all the operators
        // pushed since the left one was pushed
        handleRightPArenthesis(operatorStack, output);
      } else {
        throw new ExpressionConverterInvalidTokenException("Invalid token: " + token);
      }
    }

    // finally, we pop any operators left in the stack and append them to the output
    while (!operatorStack.isEmpty()) {
      String elem = operatorStack.pop();
      // at this step, if we find a left parenthesis in the stack, it means that there was no right
      // one, which means it never got popped from the stack
      if (TokenUtilities.isLeftParenthesis(elem)) {
        throw new ExpressionConverterInvalidTokenException(
            "Mismatched parentheses: right parenthesis missing");
      }
      output.add(elem);
    }

    return output;
  }

  private static void handleRightPArenthesis(Deque<String> operatorStack, List<String> output) {
    // we pop and append to output all the operators present in the stack until we reach the left
    // parenthesis in the stack
    while (!operatorStack.isEmpty() && !TokenUtilities.isLeftParenthesis(operatorStack.peek())) {
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
