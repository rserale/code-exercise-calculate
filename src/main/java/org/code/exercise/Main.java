package org.code.exercise;

import java.util.Map;
import org.code.exercise.service.CalculatorService;
import org.code.exercise.service.exception.EvaluatorStackException;
import org.code.exercise.service.exception.ExpressionConverterInvalidTokenException;

/**
 * Entry point of the calculator application.
 *
 * <p>Parses the command-line argument, calls CalculatorService to convert and evaluate the
 * expression, and handles exception thrown with user-friendly messages.
 */
public class Main {

  private static final Map<Class<? extends Exception>, String> ERROR_PREFIXES =
      Map.of(
          ExpressionConverterInvalidTokenException.class, "Syntax error",
          EvaluatorStackException.class, "Evaluation error",
          IllegalArgumentException.class, "Invalid input",
          ArithmeticException.class, "Arithmetic error");

  public static void main(String[] args) {
    if (args.length != 1) {
      printUsage();
      return;
    }
    String expression = args[0].trim();
    executeCalculation(expression);
  }

  private static void executeCalculation(String expression) {
    try {
      int result = CalculatorService.calculate(expression);
      System.out.println("Result: " + result);
    } catch (Exception e) {
      String prefix = ERROR_PREFIXES.getOrDefault(e.getClass(), "Unexpected error");
      printErrorAndExit(prefix + ": " + e.getMessage());
    }
  }

  private static void printUsage() {
    System.out.println("Usage: java -jar calculate.jar \"<expression>\"");
    System.out.println("Example: java -jar calculate.jar \"3 * -2 + 6\"");
  }

  private static void printErrorAndExit(String errorMessage) {
    System.err.println(errorMessage);
    System.exit(1);
  }
}
