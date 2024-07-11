# Result Handling in SVLC

## Table of Contents
* [Introduction](#introduction)
* [Result](#result)
  * [Success](#success)
  * [Failure](#failure)
  * [Implementation Detail](#implementation-detail)
* [Limitation](#limitation)

## Introduction

This document provides an overview of the result handling mechanism in the SVLC project.
It focuses on the `Result` class, which is used to encapsulate the outcome of operations, 
and the `Success` and `Failure` classes, which represent successful and error outcomes, respectively.

## Result

The [`Result`](../src/main/java/pt/isel/leic/svlc/util/results/Result.java) class is a generic container for either a *success* or an *error* result.
It is used to encapsulate the outcome of operations, allowing for a clear separation of success and error handling.

### Success

The [`Success`](../src/main/java/pt/isel/leic/svlc/util/results/Success.java) class is a generic record that represents a successful outcome of an operation.
It contains a single field, `value`, which holds the result of the successful operation.

```java
public record Success<I>(I value) {
    
    public I value() {
        return value;
    }
}
```

### Failure

The [`Failure`](../src/main/java/pt/isel/leic/svlc/util/results/Failure.java) class is a record that represents an error outcome of an operation.
It contains a single field, `message`, which holds the error message describing the failure.

```java
public record Failure(String message) {

    public String message() {
        return message;
    }

    @Override
    public String toString() {
        return "[ERROR] " + message;
    }
}
```

### Implementation Detail

The `RunProc` functional interface is designed to encapsulate operations that can potentially result in either a success or an error outcome.
This interface allows for a clear separation of the execution logic from the handling of its outcome, enabling a more functional programming approach.
Operations executed through `RunProc` are defined with the expectation that they will return a `Result` object, which can then be processed
to determine if the operation was successful or if an error occurred.

```java
@FunctionalInterface
public interface RunProc<L, R> {
  Result<L, R> run();
}
```

In the `Result` class, the `RunProc` interface is specifically used within the `then` function.
This design is particularly useful in scenarios where operations are chained or composed, as it simplifies error
handling and success processing by leveraging the `Result` object to encapsulate the outcome of each operation.

`Result::then`

```java

public Result<L, R> then(RunProc<L, R> next) throws RuntimeException {
        if (isSuccess()) {
            return next.run();
        }
        throw new RuntimeException(left.toString());
    }
```

## Limitation

> **Limitation:** The `Result` class is designed to handle the outcome of operations in a functional programming style,
> and may not be suitable for applications that require more complex error handling mechanisms.

