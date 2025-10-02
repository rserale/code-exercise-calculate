# Detect OS and choose correct Maven Wrapper command
ifeq ($(OS),Windows_NT)
    MAVEN_CMD = mvnw.cmd -s maven-settings.xml
else
    MAVEN_CMD = ./mvnw -s maven-settings.xml
endif

PROJECT_NAME = calculate
TARGET_JAR = target/$(PROJECT_NAME)-1.0-SNAPSHOT.jar
EXECUTABLE_JAR = $(PROJECT_NAME).jar

# Default target
.PHONY: all
all: clean package
	cp $(TARGET_JAR) $(EXECUTABLE_JAR)

# Clean the project
.PHONY: clean
clean:
	$(MAVEN_CMD) clean
	rm -f $(EXECUTABLE_JAR)

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

# Verify the project
.PHONY: verify
verify:
	$(MAVEN_CMD) verify

# Show dependency tree
.PHONY: deps
deps:
	$(MAVEN_CMD) dependency:tree

# Runs spotless plugin to format code correctly
.PHONY: spotless
spotless:
	$(MAVEN_CMD) spotless:apply
