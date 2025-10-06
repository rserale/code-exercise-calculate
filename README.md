# Calculate

A Java command-line calculator that evaluates base arithmetic expressions for integers.

## How it works

It uses the [`Shunting-yard algorithm`](https://en.wikipedia.org/wiki/Shunting_yard_algorithm) to convert an infix expression into postfix notation (also called [`Reverse Polish Notation`](https://en.wikipedia.org/wiki/Reverse_Polish_notation)) in order to handle the priorities of the operators in an easy way during the evaluation.

**Step 1.** The input expression is parsed into a list of tokens representing numbers and operators.
**Step 2.** The tokens are converted from infix to postfix notation using the Shunting-yard algorithm.
   This ensures that operator priorities and parentheses are correctly handled.
**Step 3.** The postfix expression is then evaluated from left to right, and finally returns the result as an integer.
   At this stage, we need no priority checks anymore because they have already been applied during the conversion.

### Example :
| Stage | Description | Output |
|--------|--------------|---------|
| Input expression | Infix format | `3 + 4 * 2` |
| Parsing | Tokens | `[3, +, 4, *, 2]` |
| Conversion | Postfix format | `[3, 4, 2, *, +]` |
| Evaluation | Result | `11` |

## Features

- Supports basic arithmetic operations: `+`, `-`, `*`, `/`.
- Handles operator priority.

## Bonus features:
- Supports parentheses in expressions, allowing nested calculations.
- Logging with two verbosity levels (available on branch [`feature/4-logging`](https://github.com/rserale/code-exercise-calculate/tree/feature/4-logging)):
  - `-v`: shows the result of the postfix conversion of the base expression.
  - `-vv`: shows all the steps of the postfix conversion and the final evaluation, including the state of the stack and the output.

*(This feature was implemented on a separate branch in order to keep the main codebase simple and focused on the core logic.)*

## Requirements

- Java 21 or higher.
- The project includes a Maven Wrapper for both Unix/MacOS and Windows, so Maven installation is not required.

## Getting Started

### Clone the repository

```bash
git clone https://github.com/rserale/code-exercise-calculate
cd code-exercise-calculate
```

### Build the project

```bash
make all
```
This will create the executable calculate.jar in the project folder.

### Run tests

```
make test
```

### Run the calculator

```bash
java -jar calculate.jar "<expression>"
```

#### Usage examples

```bash
java -jar calculate.jar "3 * -2 + 6"
Result: 0

java -jar calculate.jar "(2 + 3) * 4"
Result: 20

# with logging (feature branch 'feature/4-logging')
java -jar calculate.jar -v "(2 + 3) * 4"
java -jar calculate.jar -vv "(2 + 3) * 4"
```

### Additional Makefile commands
```bash
# clean compiled files and remove the jar
make clean

# compile the project without packaging
make compile

# run Maven verify lifecycle
make verify

# check code formatting using Spotless plugin
make spotless-check

# format code using Spotless plugin
make spotless-apply
```

### Continuous Integration

This project includes a GitHub workflow that automatically runs tests on pull requests targeting the main branch.

This ensures that any changes submitted via PR are automatically validated before merging.

The workflow configuration can be found [here](https://github.com/rserale/code-exercise-calculate/blob/main/.github/workflows/tests_validation.yml).

### Notes

- The calculator only handles integer operands.
- Division by zero will throw an error.
- Expressions must be properly formatted with space between the operators and operands, excepted for the parentheses.
- For the logging feature, switch to branch [`feature/4-logging`](https://github.com/rserale/code-exercise-calculate/tree/feature/4-logging).
