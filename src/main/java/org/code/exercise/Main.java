package org.code.exercise;

import org.code.exercise.service.CalculatorService;
import org.code.exercise.service.exception.EvaluatorStackException;
import org.code.exercise.service.exception.ExpressionConverterInvalidTokenException;
import org.code.exercise.service.helper.LogUtils;
public class Main {
  private static final String ARG_VERBOSITY_1 = "-v";
  private static final String ARG_VERBOSITY_2 = "-vv";

  public static void main(String[] args) {
    ParsedArguments parsedArgs = parseArguments(args);

    LogUtils.setVerbosity(parsedArgs.verbosity());
    executeCalculation(parsedArgs.expression());
  }

  private static ParsedArguments parseArguments(String[] args) {
    if (args.length == 0) {
      printUsage();
      System.exit(0);
    }

    int verbosity = 0;
    String expression = null;

    for (String arg : args) {
      if (ARG_VERBOSITY_1.equals(arg)) {
        verbosity = 1;
      } else if (ARG_VERBOSITY_2.equals(arg)) {
        verbosity = 2;
      } else {
        expression = arg;
      }
    }

    if (expression == null || expression.isBlank()) {
      System.err.println("Error: Expression not provided.");
      printUsage();
      System.exit(1);
    }

    return new ParsedArguments(expression, verbosity);
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
    System.out.println("Usage: java -jar calculate.jar [options] \"<expression>\"");
    System.out.println("Options:");
    System.out.println("  -v     Verbose: show postfix conversion");
    System.out.println("  -vv    Very verbose: show detailed conversion and evaluation steps");
    System.out.println();
    System.out.println("Example:");
    System.out.println("  java -jar calculate.jar -v \"3 * -2 + 6\"");
  }

  private static void printErrorAndExit(String errorMessage) {
    System.err.println(errorMessage);
    System.exit(1);
  }

  private record ParsedArguments(String expression, int verbosity) {}
}
