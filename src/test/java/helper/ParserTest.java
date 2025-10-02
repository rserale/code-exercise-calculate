package helper;

import org.code.exercise.helper.Parser;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ParserTest {

    @Test
    public void testSimpleExpression() {
        String expr = "2 + 3";
        List<String> tokens = Parser.tokenize(expr);
        assertEquals(List.of("2", "+", "3"), tokens);
    }

    @Test
    public void testMultipleOperators() {
        String expr = "3 * 4 - 5 / 2";
        List<String> tokens = Parser.tokenize(expr);
        assertEquals(List.of("3", "*", "4", "-", "5", "/", "2"), tokens);
    }

    @Test
    public void testNegativeNumbers() {
        String expr = "3 * -2 + 6";
        List<String> tokens = Parser.tokenize(expr);
        assertEquals(List.of("3", "*", "-2", "+", "6"), tokens);
    }

    @Test
    public void testExtraSpaces() {
        String expr = "  10   +   5  ";
        List<String> tokens = Parser.tokenize(expr);
        assertEquals(List.of("10", "+", "5"), tokens);
    }

    @Test
    public void testEmptyExpression() {
        String expr = "";
        List<String> tokens = Parser.tokenize(expr);
        assertEquals(Collections.emptyList(), tokens);
    }

    @Test
    public void testSingleNumber() {
        String expr = "42";
        List<String> tokens = Parser.tokenize(expr);
        assertEquals(List.of("42"), tokens);
    }
}
