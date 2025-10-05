package org.code.exercise.service.exception;

/**
 * Thrown when converting an infix expression to postfix fails.
 *
 * <p>Cause:
 *
 * <ul>
 *   <li>Invalid token that is not an integer neither an operator
 * </ul>
 */
public class ExpressionConverterInvalidTokenException extends IllegalArgumentException {
  public ExpressionConverterInvalidTokenException(String message) {
    super(message);
  }
}
