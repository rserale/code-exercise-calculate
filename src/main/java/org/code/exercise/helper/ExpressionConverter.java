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
      } else {
        throw new ExpressionConverterInvalidTokenException("Invalid token: " + token);
      }
    }

    // finally, we pop any operators left in the stack and append them to the output
    while (!operatorStack.isEmpty()) {
      output.add(operatorStack.pop());
    }

    return output;
  }

  private static void handleOperator(
      String operator, Deque<String> operatorStack, List<String> output) {
    // we pop operators from the stack while they have higher or equal priority than our current one
    // those operators are appended to the output in the unstacking order
    while (!operatorStack.isEmpty()
        && TokenUtilities.getOperatorPriority(operator)
            <= TokenUtilities.getOperatorPriority(operatorStack.peekFirst())) {
      output.add(operatorStack.pop());
    }
    // once the unstacking process is finished, we push our current operator on the stack
    operatorStack.push(operator);
  }
}
