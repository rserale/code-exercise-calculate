package helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.List;
import org.code.exercise.service.Evaluator;
import org.code.exercise.service.exception.EvaluatorStackException;
import org.junit.Test;

public class EvaluatorTest {

  @Test
  public void testAddition() {
    List<String> postfix = List.of("2", "3", "+");
    int result = Evaluator.evaluatePostfixExpression(postfix);
    assertEquals(5, result);
  }

  @Test
  public void testSubtraction() {
    List<String> postfix = List.of("5", "2", "-");
    int result = Evaluator.evaluatePostfixExpression(postfix);
    assertEquals(3, result);
  }

  @Test
  public void testMultiplication() {
    List<String> postfix = List.of("3", "4", "*");
    int result = Evaluator.evaluatePostfixExpression(postfix);
    assertEquals(12, result);
  }

  @Test
  public void testDivision() {
    List<String> postfix = List.of("10", "2", "/");
    int result = Evaluator.evaluatePostfixExpression(postfix);
    assertEquals(5, result);
  }

  @Test
  public void testMixedOperators() {
    List<String> postfix = List.of("2", "3", "4", "*", "+"); // 2 + 3 * 4
    int result = Evaluator.evaluatePostfixExpression(postfix);
    assertEquals(14, result);
  }

  @Test
  public void testNegativeNumbers() {
    List<String> postfix = List.of("2", "-3", "*"); // 2 * -3
    int result = Evaluator.evaluatePostfixExpression(postfix);
    assertEquals(-6, result);
  }

  @Test
  public void testDivisionByZero() {
    List<String> postfix = List.of("5", "0", "/");
    ArithmeticException ex =
        assertThrows(ArithmeticException.class, () -> Evaluator.evaluatePostfixExpression(postfix));
    assertEquals("Division by zero", ex.getMessage());
  }

  @Test
  public void testTooFewOperands() {
    List<String> postfix = List.of("5", "+");
    EvaluatorStackException ex =
        assertThrows(
            EvaluatorStackException.class, () -> Evaluator.evaluatePostfixExpression(postfix));
    assertEquals(
        "Operation cannot be solved due to missing operands on the stack", ex.getMessage());
  }

  @Test
  public void testTooManyOperands() {
    List<String> postfix = List.of("2", "3", "4", "+");
    EvaluatorStackException ex =
        assertThrows(
            EvaluatorStackException.class, () -> Evaluator.evaluatePostfixExpression(postfix));
    assertEquals("More than one element left on the stack", ex.getMessage());
  }

  @Test
  public void testTooFewOperators() {
    List<String> postfix = List.of("5", "2", "3", "+");
    EvaluatorStackException ex =
        assertThrows(
            EvaluatorStackException.class, () -> Evaluator.evaluatePostfixExpression(postfix));
    assertEquals("More than one element left on the stack", ex.getMessage());
  }

  @Test
  public void testTooManyOperators() {
    List<String> postfix = List.of("5", "2", "+", "*");
    EvaluatorStackException ex =
        assertThrows(
            EvaluatorStackException.class, () -> Evaluator.evaluatePostfixExpression(postfix));
    assertEquals(
        "Operation cannot be solved due to missing operands on the stack", ex.getMessage());
  }

  @Test
  public void testEmptyExpression() {
    List<String> postfix = List.of();
    EvaluatorStackException ex =
        assertThrows(
            EvaluatorStackException.class, () -> Evaluator.evaluatePostfixExpression(postfix));
    assertEquals("Stack is empty", ex.getMessage());
  }
}
