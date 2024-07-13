# Utility used in SVLC mechanisms

## Table of Contents
* [Introduction](#introduction)
* [Request Builder](#request-builder)
* [Default Process](#default-process)
* [Command Result](#command-result)

## Introduction
This document provides an overview of the utilities that are not referenced in the main mechanisms of the SVLC project, 
but are used to support them.

## Request Builder

The [`RequestBuilder`](../src/main/java/pt/isel/leic/svlc/util/executers/ReqBuilder.java) functional interface is designed 
to define the request building process for HTTP requests (GET, POST, PUT, DELETE). 

```java
@FunctionalInterface
public interface ReqBuilder {
    Builder apply(Builder builder, BodyPublisher body) throws Exception;
}
```

## Default Process

The [`DefaultProcess`](../src/main/java/pt/isel/leic/svlc/util/DefaultProc.java) generic functional interface is designed
to encapsulate operations. This interface allows for a clear separation of the execution logic from the handling of its 
outcome, enabling a more functional programming approach.

```java
@FunctionalInterface
public interface DefaultProc<T> {
    T run() throws Exception;
}
```

## Command Result

The [`CommandResult`](../src/main/java/pt/isel/leic/svlc/util/executers/CommandResult.java) class is designed
to encapsulate the result of a command execution. This class is used to return the output of a command (synchronous execution)
or the thread that is running the command (asynchronous execution).

```java
public class CommandResult {
    private final String output;
    private final Thread thread;

    private CommandResult(String output, Thread thread) {
        this.output = output;
        this.thread = thread;
    }

    public static CommandResult fromOutput(String output) {
        return new CommandResult(output, null);
    }

    public static CommandResult fromThread(Thread thread) {
        return new CommandResult(null, thread);
    }
    
    // Getters...
}
```