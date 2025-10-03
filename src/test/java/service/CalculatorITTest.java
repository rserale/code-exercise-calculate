package service;

import static org.junit.Assert.*;

import org.code.exercise.service.Calculator;
import org.junit.Test;

public class CalculatorITTest {

  @Test
  public void testSimpleAddition() {
    assertEquals(5, Calculator.calculate("2 + 3"));
  }

  @Test
  public void testSubtraction() {
    assertEquals(-1, Calculator.calculate("2 - 3"));
  }

  @Test
  public void testMultiplication() {
    assertEquals(6, Calculator.calculate("2 * 3"));
  }

  @Test
  public void testDivision() {
    assertEquals(2, Calculator.calculate("6 / 3"));
  }

  @Test
  public void testNegativeNumbers() {
    assertEquals(0, Calculator.calculate("3 * -2 + 6"));
  }

  @Test
  public void testMultipleOperatorsExpression() {
    assertEquals(-42, Calculator.calculate("7 + 12 / 2 * -5 - 19"));
  }

  @Test
  public void testDivisionByZero() {
    ArithmeticException ex =
        assertThrows(ArithmeticException.class, () -> Calculator.calculate("5 / 0"));
    assertEquals("Division by zero", ex.getMessage());
  }

  @Test
  public void testInvalidToken() {
    IllegalArgumentException ex =
        assertThrows(IllegalArgumentException.class, () -> Calculator.calculate("2 + a"));
    assertTrue(ex.getMessage().contains("Invalid token"));
  }

  @Test
  public void testEmptyExpression() {
    IllegalArgumentException ex =
        assertThrows(IllegalArgumentException.class, () -> Calculator.calculate(""));
    assertEquals("Expression must not be null or empty", ex.getMessage());
  }
}
