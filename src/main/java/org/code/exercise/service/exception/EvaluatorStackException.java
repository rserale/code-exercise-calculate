package org.code.exercise.service.exception;

/**
 * Thrown when evaluating a postfix expression fails due to an invalid stack state.
 *
 * <p>Possible causes :
 *
 * <ul>
 *   <li>Too few operands for an operation
 *   <li>More than one element left on the stack after evaluation
 *   <li>Stack is empty when a value is expected
 * </ul>
 */
public class EvaluatorStackException extends RuntimeException {
  public EvaluatorStackException(String message) {
    super(message);
  }
}
