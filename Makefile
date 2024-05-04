# Maven Wrapper
MVNW = ./mvnw

# Spring Boot main class
MAIN_CLASS = org.zsafder.idealo.ogdevicemanager.Application

# Test directory
TEST_DIR = src/test/java

# JUnit JAR file
JUNIT_JAR = lib/junit-platform-console-standalone.jar

# Compile application
compile:
	$(MVNW) clean compile

# Run application
run:
	$(MVNW) spring-boot:run

# Compile and run application
build: compile run

# Run tests
test:
	$(MVNW) test

# Clean Maven target directory
clean:
	$(MVNW) clean

.PHONY: compile run build test clean
