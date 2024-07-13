# Kubernetes Mechanism in SVLC

## Table of Contents
* [Introduction](#introduction)
* [Kubernetes](#kubernetes)
  * [Kubernetes Configuration](#kubernetes-configuration)
  * [Charts](#charts)
  * [Values](#values)
  * [Templates](#templates)
* [Implementation Details](#implementation-details)
  * [Yaml Conversion](#yaml-conversion)
  * [Yaml File Generation](#yaml-file-generation)
  * [Kube Resource](#kube-resource)
* [Authentication](#authentication)
* [Dependencies](#dependencies)
* [Limitations](#limitations)

## Introduction

This document provides a detailed overview of the Kubernetes mechanism in the SVLC project.
Kubernetes is an open-source container orchestration platform that automates the deployment, scaling, 
and management of containerized applications. It is a crucial component of the SVLC project,
enabling the deployment and management of microservices in a distributed environment.

## Kubernetes

### Kubernetes Configuration

```java 
public class Kubernetes {

    private final KubernetesClient client;  // The Kubernetes client to interact with the Kubernetes cluster.
    private final String namespace;          // The namespace to use in the Kubernetes cluster.
  
    public Kubernetes() {
        this.client = new KubernetesClientBuilder().build();
        this.namespace = "default";
    }
    
    public Kubernetes(String masterUrl, String namespace) throws Exception {
        this.namespace = checkAndSetNamespace(namespace);
        String token = Authentications.getKubernetesToken(this.namespace);
        Config config = new ConfigBuilder()
                .withMasterUrl(masterUrl)
                .withNamespace(this.namespace)
                .withOauthToken(token)
                .build();
        this.client = new KubernetesClientBuilder().withConfig(config).build();
    }
}    
```

### Charts
The [`Charts`](../src/main/java/pt/isel/leic/svlc/yaml/Charts.java) class is responsible for creating yaml charts for Kubernetes.
It provides methods to create charts, add values, and generate the yaml file.

```java
public class Charts extends YamlConverter {

    private final String apiVersion = "v2"; // The API version of the Helm chart.
    private String name;    // The name of the Helm chart.
    private String description; // A short description of the chart.
    private String version; // The version of the chart.
    private String appVersion;  // The version of the app this chart installs.
    private List<String> keywords;  // A list of keywords associated with the chart.
    private Map<String, String> sources;    // The source URLs of the chart.
    private List<String> maintainers;   // The maintainers of the chart.
    private String home; // optional
    private String icon; // optional
    private List<String> dependencies; // optional
    private String type; // optional
    private Map<String, String> annotations; // optional
  
    public Charts() {}
    
    public Charts(
            String name,
            String description,
            String version,
            String appVersion,
            List<String> keywords,
            Map<String, String> sources,
            List<String> maintainers
    ) {
        this.name = name;
        this.description = description;
        this.version = version;
        this.appVersion = appVersion;
        this.keywords = keywords;
        this.sources = sources;
        this.maintainers = maintainers;
    }
    
    // Methods...
}    
```

### Values
The [`Values`](../src/main/java/pt/isel/leic/svlc/yaml/Values.java) class is responsible for creating yaml values for Kubernetes.
It provides methods to add values and generate the yaml file.

```java
public class Values extends YamlConverter {
    
    private final Map<String, Object> values;   // The values of the chart.
    
    public Values() {
        this.values = new HashMap<>();
    }
    
    public Values(Map<String, Object> values) {
        this.values = values;
    }
    
    // Methods...
}
```

### Templates
The [`Templates`](../src/main/java/pt/isel/leic/svlc/yaml/Templates.java) class is responsible for creating yaml templates for Kubernetes.
It provides methods to create templates, add values, and generate the yaml file.

You can create some templates like:

* Pod
* Deployment
* Service
* ConfigMap
* Secret
* Persistent Volume
* Persistent Volume Claim

## Implementation Details

### Yaml Conversion
The `YamlConverter` class is responsible for converting Java objects into YAML format.
This conversion is crucial for generating Kubernetes manifests from Java applications.

```java
public class YamlConverter {
  private static Result<Failure, Success<String>> toYaml(Map<String, Object> data) {
    try {
      Yaml yaml = new Yaml();
      return Result.Right(new Success<>(yaml.dump(data)));
    } catch (YAMLException e) {
      return Result.Left(new Failure(e.getMessage()));
    }
  }

  public static String generateYaml(Map<String, Object> data) {
    Result<Failure, Success<String>> yaml = toYaml(data);
    try {
      return execIfElse(
              yaml.success(),
              () -> yaml.right().value(),
              () -> yaml.left().message()
      );
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
```

### Yaml File Generation
The `YamlCreateFile` class handles the generation of YAML files from Helm templates and values. These files are then used
by Helm to install or upgrade applications on Kubernetes clusters.

```java
public class YamlCreateFile {
    
  private static boolean isInvalidFilename(String filename) {
    return filename == null || filename.isEmpty();
  }
  
  private static String ensureYamlExtension(String filename) {
    return filename.endsWith(".yaml") ? filename : filename + ".yaml";
  }
  
  private static boolean isPathNotSet(String path) {
    return path == null || path.isEmpty();
  }
  
  public static Result<Failure, Success<String>> createYamlFile(String filename, String content, String path) {

    try {
      execIf(
        isInvalidFilename(filename),
        () -> Left(new Failure("Filename cannot be null or empty"))
      );

      filename = ensureYamlExtension(filename);

      execIf(
        isPathNotSet(path),
        () -> Left(new Failure("HELM_PATH environment variable is not set"))
      );

      Path filePath = Paths.get(path, filename);
      Files.createDirectories(filePath.getParent());
      Files.write(filePath, content.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
      return Result.Right(new Success<>("File created successfully: " + filePath));
    } catch (Exception e) {
      return Left(new Failure("Failed to create or write to file: " + e.getMessage()));
    }
  }
}
```

### Kube Resource
The `KubeResource` interface is responsible for executing Kubernetes resources.
It provides a method to execute a Kubernetes resource using a Kubernetes client, namespace, and resource.

```java
@FunctionalInterface
public interface KubeResource {
     HasMetadata execute(KubernetesClient client, String namespace, HasMetadata resource);
}
```

## Authentication
The `Auth` class is responsible for handling authentication in the Kubernetes mechanism.
It provides methods to authenticate with the Kubernetes cluster using a token.

```java
public static String getKubernetesToken(String namespace) throws Exception {
  String secretName = executeCommand(secretNameCMD(namespace), "", true).getOutput();
  String encodedToken = executeCommand(tokenCMD(secretName, namespace), "", true).getOutput();

  byte[] decodedBytes = Base64.getDecoder().decode(encodedToken);
  return new String(decodedBytes);
}
```

## Dependencies
* [kubernetes-client](https://github.com/fabric8io/kubernetes-client): A Java client for Kubernetes.
* [SnakeYAML](https://bitbucket.org/asomov/snakeyaml): A YAML parser and emitter for Java.

## Limitations

> **Limitation**: The Kubernetes mechanism in the SVLC project is limited to the deployment and management of Kubernetes resources.
> It does not support advanced Kubernetes features such as custom resource definitions (CRDs), operators, or Helm charts.