package org.code.exercise;

import org.code.exercise.service.CalculatorService;

public class Main {
  public static void main(String[] args) {
    if (args.length == 0) {
      System.out.println("Usage: java -jar calculate.jar \"<expression>\"");
      System.out.println("Example: java -jar calculate.jar \"3 * -2 + 6\"");
      return;
    }

    String expression = args[0].trim();
    int result = CalculatorService.calculate(expression);

    System.out.printf("Result: %d%n", result);
  }
}
