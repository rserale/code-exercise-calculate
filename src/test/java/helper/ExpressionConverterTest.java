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
    List<String> tokens = List.of("8", "+", "4", "/", "2");
    List<String> postfix = ExpressionConverter.infixToPostfix(tokens);
    assertEquals(List.of("8", "4", "2", "/", "+"), postfix);
  }

  @Test
  public void testSubstractionMultiplication() {
    List<String> tokens = List.of("7", "-", "3", "*", "2");
    List<String> postfix = ExpressionConverter.infixToPostfix(tokens);
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
    List<String> tokens = List.of("5", "+", "-3", "*", "2", "/", "4", "-", "1");
    List<String> postfix = ExpressionConverter.infixToPostfix(tokens);
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
}
