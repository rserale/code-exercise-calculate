package helper;

import static org.junit.Assert.*;

import java.util.List;
import org.code.exercise.exception.ExpressionConverterInvalidTokenException;
import org.code.exercise.helper.ExpressionConverter;
import org.junit.Test;

public class ExpressionConverterTest {

  @Test
  public void testAddition() {
    List<String> infix = List.of("2", "+", "3");
    List<String> postfix = ExpressionConverter.infixToPostfix(infix);
    assertEquals(List.of("2", "3", "+"), postfix);
  }

  @Test
  public void testAdditionDivision() {
    List<String> infix = List.of("8", "+", "4", "/", "2");
    List<String> postfix = ExpressionConverter.infixToPostfix(infix);
    assertEquals(List.of("8", "4", "2", "/", "+"), postfix);
  }

  @Test
  public void testSubstractionMultiplication() {
    List<String> infix = List.of("7", "-", "3", "*", "2");
    List<String> postfix = ExpressionConverter.infixToPostfix(infix);
    assertEquals(List.of("7", "3", "2", "*", "-"), postfix);
  }

  @Test
  public void testNegativeNumbers() {
    List<String> infix = List.of("3", "*", "-2", "+", "6");
    List<String> postfix = ExpressionConverter.infixToPostfix(infix);
    assertEquals(List.of("3", "-2", "*", "6", "+"), postfix);
  }

  @Test
  public void testMultipleOperatorsExpression() {
    List<String> infix = List.of("5", "+", "-3", "*", "2", "/", "4", "-", "1");
    List<String> postfix = ExpressionConverter.infixToPostfix(infix);
    assertEquals(List.of("5", "-3", "2", "*", "4", "/", "+", "1", "-"), postfix);
  }

  @Test
  public void testInvalidToken() {
    List<String> infix = List.of("2", "+", "a");
    ExpressionConverterInvalidTokenException ex =
        assertThrows(
            ExpressionConverterInvalidTokenException.class,
            () -> ExpressionConverter.infixToPostfix(infix));
    assertEquals("Invalid token: a", ex.getMessage());
  }

  @Test
  public void testEmptyInputReturnsEmptyList() {
    List<String> infix = List.of();
    List<String> postfix = ExpressionConverter.infixToPostfix(infix);
    assertTrue(postfix.isEmpty());
  }

  // Bonus feature: parentheses

  @Test
  public void testParentheses() {
    List<String> infix1 =
        ExpressionConverter.infixToPostfix(List.of("(", "8", "+", "2", ")", "*", "12"));
    assertEquals(List.of("8", "2", "+", "12", "*"), infix1);

    List<String> infix2 =
        ExpressionConverter.infixToPostfix(
            List.of("(", "(", "8", "+", "2", ")", "/", "2", ")", "*", "3"));
    assertEquals(List.of("8", "2", "+", "2", "/", "3", "*"), infix2);
  }

  @Test
  public void testMismatchedParentheses() {
    List<String> infix1 = List.of("(", "6", "/", "3");
    ExpressionConverterInvalidTokenException ex1 =
        assertThrows(
            ExpressionConverterInvalidTokenException.class,
            () -> ExpressionConverter.infixToPostfix(infix1));
    assertEquals("Mismatched parentheses: right parenthesis missing", ex1.getMessage());

    List<String> infix2 = List.of("6", "/", "3", ")");
    ExpressionConverterInvalidTokenException ex2 =
        assertThrows(
            ExpressionConverterInvalidTokenException.class,
            () -> ExpressionConverter.infixToPostfix(infix2));
    assertEquals("Mismatched parentheses: left parenthesis missing", ex2.getMessage());

    List<String> infix3 = List.of("(", "(", "6", "+", "2", "*", "3", ")");
    ExpressionConverterInvalidTokenException ex3 =
        assertThrows(
            ExpressionConverterInvalidTokenException.class,
            () -> ExpressionConverter.infixToPostfix(infix3));
    assertEquals("Mismatched parentheses: right parenthesis missing", ex3.getMessage());
  }
}
