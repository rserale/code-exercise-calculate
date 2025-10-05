package org.code.exercise;

import org.code.exercise.service.CalculatorService;
import org.code.exercise.service.exception.EvaluatorStackException;
import org.code.exercise.service.exception.ExpressionConverterInvalidTokenException;

public class Main {

  public static void main(String[] args) {
    if (args.length != 0) {
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
    } catch (ExpressionConverterInvalidTokenException e) {
      printErrorAndExit("Syntax error: " + e.getMessage());
    } catch (EvaluatorStackException e) {
      printErrorAndExit("Evaluation error: " + e.getMessage());
    } catch (IllegalArgumentException e) {
      printErrorAndExit("Invalid input: " + e.getMessage());
    } catch (ArithmeticException e) {
      printErrorAndExit("Arithmetic error: " + e.getMessage());
    } catch (Exception e) {
      printErrorAndExit("Unexpected error: " + e.getMessage());
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
