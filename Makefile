# Detect OS and choose correct Maven Wrapper command
ifeq ($(OS),Windows_NT)
    MAVEN_CMD = mvnw.cmd
else
    MAVEN_CMD = ./mvnw
endif

PROJECT_NAME = calculate
JAR_FILE = target/$(PROJECT_NAME)-1.0-SNAPSHOT.jar

# Default target
.PHONY: all
all: compile

# Clean the project
.PHONY: clean
clean:
	$(MAVEN_CMD) clean

# Compile the project
.PHONY: compile
compile:
	$(MAVEN_CMD) compile

# Run tests
.PHONY: test
test:
	$(MAVEN_CMD) test

# Package the project
.PHONY: package
package:
	$(MAVEN_CMD) package

# Install to local repository
.PHONY: install
install:
	$(MAVEN_CMD) install

# Run the application (adjust main class as needed)
.PHONY: run
run: package
	java -jar $(JAR_FILE)

# Verify the project
.PHONY: verify
verify:
	$(MAVEN_CMD) verify

# Show dependency tree
.PHONY: deps
deps:
	$(MAVEN_CMD) dependency:tree

