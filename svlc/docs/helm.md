# Helm utilization in SVLC

## Table of Contents
* [Introduction](#introduction)
* [Helm](#helm)
  * [Helm Manager](#helm-manager)
    * [Methods](#methods)
    * [Helm Configurations](#helm-configurations)
    * [Util](#util)
  * [Helm Chart](#helm-chart)
  * [Helm Values](#helm-values)
  * [Helm Template](#helm-template)
* [Implementation Details](#implementation-details)
  * [Yaml Conversion](#yaml-conversion)
  * [Yaml File Generation](#yaml-file-generation)
  * [Helm Execution](#helm-execution)
* [Environment Variables](#environment-variables)
* [Dependencies](#dependencies)
* [Limitation](#limitation)

## Introduction
This document provides a detailed overview of Helm utilization in the SVLC project. Helm, as a package manager for Kubernetes, 
facilitates the deployment and management of applications, making it an essential tool for our Kubernetes mechanism.

## Helm

### Helm Manager
The [`HelmManager`](../src/main/java/pt/isel/leic/svlc/helm/HelmManager.java) class is responsible for managing Helm 
operations such as installing, upgrading, uninstalling charts, and more. 
It utilizes the Helm CLI commands through Java, providing an interface to interact with Helm programmatically.

```java
public class HelmManager {

    private String chartName; // Name of the chart
    private String path;    // Path to the Helm chart or directory
    private final Helm helm; // Helm object
    
    public HelmManager() {
        this.helm =  new Helm(createPath(null));
    }
  
    public HelmManager(String chartName) {
        this.helm = new Helm(createPath(chartName));
    }

    public HelmManager(Helm helm) {
        this.helm = helm;
    }

    public String getChartName() {
        return chartName;
    }

    public String getPath() {
        return path;
    }
    
    private Path createPath(String chartName) {
        this.chartName = chartName;
        String helmPath = System.getenv("HELM_PATH");
        path = chartName != null ? helmPath + "/" + chartName : helmPath;
        return Path.of(path);
    }
    
    // Methods...
}    
```

#### Methods
The `HelmManager` class provides several methods to interact with Helm, including:

- `install`: Installs a Helm chart.
- `upgrade`: Upgrades a Helm chart.
- `uninstall`: Uninstalls a Helm chart.
- `list`: Lists Helm releases.
- `lint`: Lints a Helm chart.
- `test`: Tests a Helm chart.
- `version`: Shows the Helm version.
- `dependency`: Manages Helm chart dependencies.
  - `update`: Updates the dependencies of a Helm chart.
  - `build`: Builds the dependencies of a Helm chart.
  - `list`: Lists the dependencies of a Helm chart.
- `package`: Packages a Helm chart.

#### Helm Configurations
Each Helm operation requires specific configurations to be set. So each method (except `version`) in the `HelmManager` 
class has a corresponding configuration class that is used to set the configurations for that operation. 

To know more about the configurations for each operation, check the **[Helm Configurations](configurations.md)** document.

#### Util
The [`HelmManagerUtil`](../src/main/java/pt/isel/leic/svlc/util/helm/HelmManagerUtil.java) class provides utility methods
for the `HelmManager` class, to configure the helm commands before being executed.

### Helm Chart
The [`Charts`](../src/main/java/pt/isel/leic/svlc/helm/yaml/Charts.java) class focuses on managing Helm charts, including 
their creation, modification, and packaging. Charts are the core components in 
Helm that represent a set of pre-configured Kubernetes resources.

### Helm Values
The [`Values`](../src/main/java/pt/isel/leic/svlc/helm/yaml/Values.java) class deals with the customization of charts 
through values. These values override the default settings in a chart's `values.yaml` file, allowing for 
flexible configuration of Kubernetes resources.

### Helm Template
The [`Templates`](../src/main/java/pt/isel/leic/svlc/helm/yaml/Templates.java) class is designed to manage Helm templates. 
Templates are used to generate Kubernetes manifests from Helm charts, enabling the deployment of applications on Kubernetes
clusters. 

## Implementation Details

### Yaml Conversion
The `YamlConverter` class is responsible for converting Java objects into YAML format. 
This conversion is crucial for generating Kubernetes manifests from Java applications.

```java
public class YamlConverter {
  public static Result<Failure, Success<String>> toYaml(Map<String, Object> data) {
    try {
      Yaml yaml = new Yaml();
      return Result.Right(new Success<>(yaml.dump(data)));
    } catch (YAMLException e) {
      return Result.Left(new Failure(e.getMessage()));
    }
  }
}
```

### Yaml File Generation
The `YamlCreateFile` class handles the generation of YAML files from Helm templates and values. These files are then used 
by Helm to install or upgrade applications on Kubernetes clusters.

```java
public class YamlCreateFile {
  public static Result<Failure, Success<String>> createYamlFile(String filename, String content) {
    if (!isValidFilename(filename)) {
      return Result.Left(new Failure("Filename cannot be null or empty"));
    }
    filename = ensureYamlExtension(filename);

    String path = System.getenv("HELM_PATH");
    if (!isHelmPathSet(path)) {
      return Result.Left(new Failure("HELM_PATH environment variable is not set"));
    }

    Path filePath = Paths.get(path, filename);
    try {
      Files.createDirectories(filePath.getParent());
      Files.write(filePath, content.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
      return Result.Right(new Success<>("File created successfully: " + filePath));
    } catch (IOException e) {
      return Result.Left(new Failure("Failed to create or write to file: " + e.getMessage()));
    }
  }
}
```

### Helm Execution
The `helmProc` interface defines a generic way to execute Helm operations, allowing for flexible implementation of various 
Helm commands. The `HelmManager` class utilizes this interface to perform Helm operations.

```java
@FunctionalInterface
public interface helmProc<T> {
    T run();
}

```

## Environment Variables

* `HELM_PATH` - Path to the directory where Helm files are stored or where you want to store them.

Windows

```shell
set HELM_PATH=<path_to_helm_files>
```

Linux/MacOs

```bash
export HELM_PATH=<path_to_helm_files>
```

## Dependencies
- [Helm-Java](https://github.com/manusa/helm-java): A Java library for interacting with Helm CLI commands.
- [SnakeYAML](https://bitbucket.org/asomov/snakeyaml): A YAML parser and emitter for Java.

## Limitation

> **Limitation**: This implementation is limited to Helm operations that can be executed through the Helm CLI and the ones
> that are supported by the Helm-Java library.