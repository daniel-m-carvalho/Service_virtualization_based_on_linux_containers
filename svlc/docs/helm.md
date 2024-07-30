# Helm utilization in SVLC

> [!NOTE] Even though all the code created of helms created in this library, its usage is not recommended. 
> This because there's no need to use Helm in the project, since the project is already using Kubernetes.
> This document is just for documentation purposes.

## Table of Contents
* [Introduction](#introduction)
* [Helm](#helm)
  * [Helm Manager](#helm-manager)
    * [Methods](#methods)
    * [Helm Configurations](#helm-configurations)
    * [Util](#util)
  * [Helm Chart](#helm-chart)
  * [Helm Values](#helm-values)
  * [Helm Templates](#helm-templates)
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
The [`Charts`](../src/main/java/pt/isel/leic/svlc/yaml/Charts.java) class focuses on managing Helm charts, including 
their creation, modification, and packaging. Charts are the core components in 
Helm that represent a set of pre-configured Kubernetes resources.

```java
public class Charts {

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

### Helm Values
The [`Values`](../src/main/java/pt/isel/leic/svlc/yaml/Values.java) class deals with the customization of charts 
through values. These values override the default settings in a chart's `values.yaml` file, allowing for 
flexible configuration of Kubernetes resources.

```java
public class Values {
    
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

### Helm Templates
The [`Templates`](../src/main/java/pt/isel/leic/svlc/yaml/Templates.java) class is designed to manage Helm templates. 
Templates are used to generate Kubernetes manifests from Helm charts, enabling the deployment of applications on Kubernetes
clusters. 

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
  public static String toYaml(Map<String, Object> data) {
    return Yaml.dump(data);
  }

  public static String serializeToYaml(List<Map<String, Object>> resources) {
    StringBuilder yaml = new StringBuilder();
    resources.forEach(resource -> {
      yaml.append(toYaml(resource));
      yaml.append("\n---\n");
    });
    return yaml.toString();
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