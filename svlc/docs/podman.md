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
* [Environment Variables](#environment-variables)
* [Limitations and requirements](#limitations-and-requirements)

## Introduction

This document provides an overview of the Podman mechanisms in the SVLC project.

SVLC leverages Podman for container management through both the command-line interface (CLI) and HTTP requests. 
This integration is encapsulated in two main classes: `PodmanCmd` and `PodmanHttp`, which implement the `Podman` interface. 
This design allows for flexible interaction with Podman, enabling operations such as creating, starting, stopping, 
and managing pods and containers, as well as pulling and deleting images.

Both implement the `Podman` interface, which defines the operations that can be performed with Podman. 
This interface is designed to be extensible, allowing for additional implementations to be added in the future.

```java
public interface Podman {
  String createPod() throws Exception;
  String startPod() throws Exception;
  String stopPod() throws Exception;
  String prunePods() throws Exception;
  String getPodStatistics() throws Exception;
  String createContainer() throws Exception;
  String pullImage() throws Exception;
  String deleteImage() throws Exception;
}
```

## Using Podman CLI

### PodmanCmd Class

The **[PodmanCmd](../src/main/java/pt/isel/leic/svlc/pod/cmd/PodmanCmd.java)** class is responsible for executing Podman commands via the CLI. 
It utilizes the `CmdExec` utility class to run these commands in a process, capturing the output or errors. 
This class supports a variety of operations:

- **Creating Pods and Containers:** Commands like `podman pod create` and `podman create` are constructed and executed.
- **Managing Pods:** Starting, stopping, and pruning pods are handled through respective Podman commands.
- **Image Management:** Pulling new images or deleting existing ones is performed via commands like `podman pull` and `podman image rm`.

This class works in any environment, Linux or Windows, as long as Podman is installed on the machine. Error handling is a 
critical aspect, with the class returning a `Result` object that encapsulates either a success message or an error detail, 
facilitating robust error management in the application.

### Commands

The PodmanCmd class uses the Commands class to define the commands that are executed. 
The `Commands` class contains static methods that return the commands as arrays of strings.

`Examples:`
```java
public static String createPodCMD(String podName, String ports) {
  return "podman pod create --name " + podName + " -p " + ports;
}

public static String deployInPodCMD(String podName, String image) {
  return "podman run -d --pod " + podName + " " + image;
}
```

## Using Podman REST API

### PodmanHttp Class

The [PodmanHttp](../src/main/java/pt/isel/leic/svlc/pod/http/PodmanHttp.java) class interacts with Podman's REST API to manage containers and pods. 
It uses the `HttpExec` utility class to send HTTP requests to the Podman server. 
This class supports similar operations as `PodmanCmd` but through HTTP requests:

- **Creating and Managing Pods:** HTTP POST requests are sent to endpoints like `/pods/create` and `/pods/{podName}/start`.
- **Image Management:** Pulling images is done by sending a POST request to `/images/pull`, with authentication handled via headers if necessary.

Note that the `PodmanHttp` class only works in a Linux environment. Additionally, it requires Podman to be running as a service, 
which can be enabled using the command `podman system service tcp:{ip}:{port}`. By default, this service will run for 5 seconds 
if the `--time` flag is not specified, and for an endless duration if set to 0. The class constructs JSON payloads for 
requests that require them and handles responses, converting them into `Result` objects. This approach allows for handling 
operations asynchronously and integrating with web-based services more naturally.

### Endpoints

The PodmanHttp class uses the Requests class to define the endpoints that are called.
The `Requests` class contains static methods that return the endpoints as strings.
    
`Examples:`
```java 
 public final static String IP_PORT = "localhost:8080";

private final static String BASE_URL = "http://"+ IP_PORT +"/v5.0.0/libpod";

public static String createPodHTTP() {
    return BASE_URL + "/pods/create";
}

public static String startPodHTTP(String podName) {
    return BASE_URL + "/pods/" + podName + "/start";
}
```

### Authentication

To be able to interact with image registries, is necessary include the authentication headers in the HTTP requests.
For that, the `PodmanHttp` class uses the function createAuthHeader of class `Auth` to create the authentication header.
This function gets the username and password/token from environment variables and encodes them in Base64.

```java
public static String createAuthHeader() {
  String format = "{\"username\":\"%s\",\"token\":\"%s\"}";
  return encode(format);
}

private static String encode(String format){
  //get username and access token to environment variables
  String username = System.getenv("USERNAME_ENV_VAR");
  String token = System.getenv("TOKEN_ENV_VAR");
  return Base64.getEncoder().encodeToString(String.format(format, username, token).getBytes());
}
```

## Result Handling

Both `PodmanCmd` and `PodmanHttp` classes utilize the `Result` class for operation outcomes, 
which is a generic container for either a ***success*** or an ***error*** result.

For more detailed information about results handling, please refer to the [Result Handling](./results.md) documentation.

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

