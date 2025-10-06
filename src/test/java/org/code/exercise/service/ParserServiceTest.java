package org.code.exercise.service;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class ParserServiceTest {

  @Test
  public void testSimpleExpression() {
    String expr = "2 + 3";
    List<String> tokens = ParserService.tokenize(expr);
    assertEquals(List.of("2", "+", "3"), tokens);
  }

  @Test
  public void testMultipleOperators() {
    String expr = "3 * 4 - 5 / 2";
    List<String> tokens = ParserService.tokenize(expr);
    assertEquals(List.of("3", "*", "4", "-", "5", "/", "2"), tokens);
  }

  @Test
  public void testNegativeNumbers() {
    String expr = "3 * -2 + 6";
    List<String> tokens = ParserService.tokenize(expr);
    assertEquals(List.of("3", "*", "-2", "+", "6"), tokens);
  }

  @Test
  public void testExtraSpaces() {
    String expr = "  10   +   5  ";
    List<String> tokens = ParserService.tokenize(expr);
    assertEquals(List.of("10", "+", "5"), tokens);
  }

  @Test
  public void testEmptyExpression() {
    String expr = "";
    List<String> tokens = ParserService.tokenize(expr);
    assertEquals(Collections.emptyList(), tokens);
  }

  @Test
  public void testSingleNumber() {
    String expr = "42";
    List<String> tokens = ParserService.tokenize(expr);
    assertEquals(List.of("42"), tokens);
  }

  // Bonus feature: parentheses

  @Test
  public void testParenthesis() {
    List<String> expectedResult = List.of("(", "2", "+", "1", ")", "*", "3");

    String expr1 = "(2 + 1) * 3";
    List<String> tokens1 = ParserService.tokenize(expr1);
    assertEquals(expectedResult, tokens1);

    String expr2 = "( 2 + 1 ) * 3";
    List<String> tokens = ParserService.tokenize(expr2);
    assertEquals(expectedResult, tokens);
  }
}
