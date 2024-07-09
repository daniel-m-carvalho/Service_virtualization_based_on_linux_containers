# Podman Mechanisms in SVLC

## Table of Contents
* [Introduction](#introduction)
* [Using Podman CLI](#using-podman-cli)
  * [PodmanCmd Class](#podmancmd-class)
  * [Commands](#commands)
* [Using Podman REST API](#using-podman-rest-api)
  * [PodmanHttp Class](#podmanhttp-class)
  * [Endpoints](#endpoints)
  * [Authentication](#authentication)
* [Result Handling](#result-handling)
  * [Implementation Detail](#implementation-detail)
* [Environment Variables](#environment-variables)
* [Limitations and requirements](#limitations-and-requirements)

## Introduction

SVLC leverages Podman for container management through both the command-line interface (CLI) and HTTP requests. This integration is encapsulated in two main classes: `PodmanCmd` and `PodmanHttp`, which implement the `Podman` interface. This design allows for flexible interaction with Podman, enabling operations such as creating, starting, stopping, and managing pods and containers, as well as pulling and deleting images.

Both implement the `Podman` interface, which defines the operations that can be performed with Podman. This interface is designed to be extensible, allowing for additional implementations to be added in the future.

```java
public interface Podman {
  Result<Errors, Success> createPod(String podName);
  Result<Errors, Success> startPod(String podName);
  Result<Errors, Success> stopPod(String podName);
  Result<Errors, Success> prunePods();
  Result<Errors, Success> createContainer(String podName, String containerName, String imageName);
  Result<Errors, Success> startContainer(String podName, String containerName);
  Result<Errors, Success> stopContainer(String podName, String containerName);
  Result<Errors, Success> pullImage(String imageName);
  Result<Errors, Success> deleteImage(String imageName);
}
```

## Using Podman CLI

### PodmanCmd Class

The **[PodmanCmd](../src/main/java/pt/isel/leic/svlc/pod/cmd/PodmanCmd.java)** class is responsible for executing Podman commands via the CLI. 
It utilizes the `CmdExec` utility class to run these commands in a process, capturing the output or errors. This class supports a variety of operations:

- **Creating Pods and Containers:** Commands like `podman pod create` and `podman create` are constructed and executed.
- **Managing Pods:** Starting, stopping, and pruning pods are handled through respective Podman commands.
- **Image Management:** Pulling new images or deleting existing ones is performed via commands like `podman pull` and `podman image rm`.

This class works in any environment, Linux or Windows, as long as Podman is installed on the machine. Error handling is a critical aspect, with the class 
returning a `Result` object that encapsulates either a success message or an error detail, facilitating robust error management in the application.

### Commands

The PodmanCmd class uses the Commands class to define the commands that are executed. 
The `Commands` class contains static methods that return the commands as arrays of strings.

`Examples:`
```java
public static String[] createPodCMD(String podName, String ports) {
    return new String[] {"podman", "pod", "create", "--name", podName, "-p", ports};
}

public static String[] deployInPodCMD(String podName, String image) {
    return new String[] {"podman", "run", "--pod", podName, "-d", image};
}
```

## Using Podman REST API

### PodmanHttp Class

The [PodmanHttp](../src/main/java/pt/isel/leic/svlc/pod/http/PodmanHttp.java) class interacts with Podman's REST API to manage containers and pods. 
It uses the `HttpReq` utility class to send HTTP requests to the Podman server. This class supports similar operations as `PodmanCmd` but through HTTP requests:

- **Creating and Managing Pods:** HTTP POST requests are sent to endpoints like `/pods/create` and `/pods/{podName}/start`.
- **Image Management:** Pulling images is done by sending a POST request to `/images/pull`, with authentication handled via headers if necessary.

Note that the `PodmanHttp` class only works in a Linux environment. Additionally, it requires Podman to be running as a service, which can be enabled using the 
command `podman system service tcp:{ip}:{port}`. By default, this service will run for 5 seconds if the `--time` flag is not specified, 
and for an endless duration if set to 0. The class constructs JSON payloads for requests that require them and handles responses, 
converting them into `Result` objects. This approach allows for handling operations asynchronously and integrating with web-based services more naturally.

### Endpoints

The PodmanHttp class uses the Requests class to define the endpoints that are called.
The `Requests` class contains static methods that return the endpoints as strings.
    
`Examples:`
```java 
private final static String BASE_URL = "http://localhost:8080/v5.0.0/libpod";

public static String createPodHTTP() {
    return BASE_URL + "/pods/create";
}

public static String startPodHTTP(String podName) {
    return BASE_URL + "/pods/" + podName + "/start";
}
```

### Authentication

To be able to interact with image registries, is necessary include the authentication headers in the HTTP requests.
For that, the `PodmanHttp` class uses the function createAuthHeader of class `AuthHeader` to create the authentication header.
This function gets the username and password/token from environment variables and encodes them in Base64.

```java

public static String createAuthHeader() {
    //get username and password/token to environment variables
    String username = System.getenv("USERNAME_ENV_VAR");
    String access = System.getenv("ACCESS_ENV_VAR");
    // Manually constructing the JSON string
    String authJson = "{\"username\":\"" + username + "\", \"password\":\"" + access + "\"}";
    // Encoding the JSON string using Base64
    return Base64.getEncoder().encodeToString(authJson.getBytes());
}

```

## Result Handling

Both `PodmanCmd` and `PodmanHttp` classes utilize the **[Result](../src/main/java/pt/isel/leic/svlc/util/results/Result.java)** class for operation outcomes, which is a generic container for either a *success* or an *error* result. 
This pattern is crucial for chaining operations and handling errors gracefully in the application.

`Success`
```java
public record Success<I>(I value) {

    public I value() {
        return value;
    }
}
```

`Errors`
```java
public record Errors(String message) {

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

```java

public Result<L, R> then(RunProc<L, R> next) throws RuntimeException {
    if (isSuccess()) {
        return next.run();
    }
    throw new RuntimeException(left.toString());
}
```

## Environment Variables

* `USERNAME_ENV_VAR` - Registry username 
* `TOKEN_ENV_VAR` - Registry access Token


Linux/MacOs

```bash
export USERNAME_ENV_VAR=<username>
export TOKEN_ENV_VAR=<access_token>
```

## Limitations and requirements

> **Limitation:** This implementation is designed for simple service elements that only need to export a port in order to work. 

> **Requirement:** It is required that these service elements have an image available in any registry.

