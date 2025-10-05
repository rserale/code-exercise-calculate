package org.code.exercise.service.helper;

import java.util.logging.*;

public final class LogUtils {

  public static final int LOW_V = 1;
  public static final int HIGH_V = 2;

  private static final Logger LOGGER = Logger.getLogger("CalculatorLogger");
  private static int verbosity = 0;

  private LogUtils() {
    throw new UnsupportedOperationException("Utility class");
  }

  static {
    LOGGER.setUseParentHandlers(false);

    ConsoleHandler handler = new ConsoleHandler();
    handler.setFormatter(
        new SimpleFormatter() {
          @Override
          public String format(LogRecord record) {
            return record.getMessage() + System.lineSeparator();
          }
        });

    LOGGER.addHandler(handler);
    LOGGER.setLevel(Level.ALL);
  }

  public static void setVerbosity(int level) {
    verbosity = level;
  }

  public static void log(String message, int level) {
    if (verbosity >= level) {
      LOGGER.info(message);
    }
  }
}
