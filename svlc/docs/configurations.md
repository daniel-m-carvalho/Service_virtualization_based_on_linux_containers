# Helm Configurations

## Table of Contents
* [Introduction](#introduction)
* [Configurations](#configurations)
  * [Install Configuration](#install-configuration)
  * [Upgrade Configuration](#upgrade-configuration)
  * [Uninstall Configuration](#uninstall-configuration)
  * [List Configuration](#list-configuration)
  * [Lint Configuration](#lint-configuration)
  * [Test Configuration](#test-configuration)
  * [Dependency Configuration](#dependency-configuration)
  * [Package Configuration](#package-configuration)

## Introduction
This document describes the configurations that can be used with the Helm commands to manage the Helm charts.
The configurations are used to provide the necessary information to the Helm commands to perform the required operations.
All fields are optional and can be set as needed.

## Configurations
The following are the configurations that can be used with the Helm commands:

### Install Configuration
The following are the configurations that can be used with the `helm install` command:

```java
public class HelmInstallConfig {

  private String chartReference;  // The reference to the chart
  private String name;    // The name of the release
  private String nameTemplate;    // The template to use for the name
  private String namespace;   // The namespace scope for this request
  private String description; // The description of the release
  private boolean devel = false;  // If true, use development versions, too (alpha, beta, and release candidate releases)
  private boolean dependencyUpdate = false;   // If true, run helm dependency update before installing the chart
  private boolean disableOpenApiValidation = false;   // If true, disable openapi validation when installing the chart
  private boolean dryRun = false; // If true, simulate an installation
  private DryRun dryRunOption;    // The dry run option
  private boolean waitReady = false;  // If true, wait until everything is in a ready state before marking the release as successful
  private Map<String, Object> values;    // The values to be used in the installation
  private Path kubeConfig;    // The path to the kube config file
  private Path certFile;  // The path to the certificate file for the TLS connection to the server
  private Path keyFile;   // The path to the key file for the TLS connection to the server
  private Path caFile;    // The path to the CA file for the TLS connection to the server
  private boolean insecureSkipTlsVerify = false;  // If true, skip the TLS verification
  private boolean plainHttp = false;  // If true, use plain HTTP requests
  private Path keyring;   // The path to the keyring containing public keys for verification
  private boolean debug = false;  // If true, enable verbose output


  public HelmInstallConfig() {}

  public HelmInstallConfig(String chartReference) {
      this.chartReference = chartReference;
  }
  
  // Getters and Setters...
}
```

### Upgrade Configuration
The following are the configurations that can be used with the `helm upgrade` command:

```java
public class HelmUpgradeConfig {

  private String chartReference;  // The reference to the chart
  private String releaseName; // The name of the release
  private String namespace;   // The namespace scope for this request
  private boolean install = false;    // If true, install the chart if it doesn't exist
  private boolean force = false;  // If true, force resource updates through a replacement strategy
  private boolean resetValues = false;    // If true, reset the values to the ones built into the chart
  private boolean reuseValues = false;    // If true, reuse the last release's values and merge in any new values
  private boolean resetThenReuseValues = false;   // If true, reset the values and then reuse the last release's values
  private boolean atomic = false; // If true, upgrade process rolls back changes made in case of failed upgrade
  private boolean cleanupOnFail = false;  // If true, allow deletion of new resources created in this upgrade when upgrade fails
  private String description; // The description of the release
  private boolean devel = false;  // If true, use development versions, too (alpha, beta, and release candidate releases)
  private boolean dependencyUpdate = false;   // If true, run helm dependency update before installing the chart
  private boolean disableOpenApiValidation = false;   // If true, disable openapi validation when installing the chart
  private boolean dryRun = false; // If true, simulate an upgrade
  private DryRun dryRunOption;    // The dry run option
  private boolean waitReady = false; // If true, wait until everything is in a ready state before marking the release as successful
  private Map<String, Object> values; // The values to be used in the upgrade
  private Path kubeConfig;    // The path to the kube config file
  private Path certFile;  // The path to the certificate file for the TLS connection to the server
  private Path keyFile;   // The path to the key file for the TLS connection to the server
  private Path caFile;    // The path to the CA file for the TLS connection to the server
  private boolean insecureSkipTlsVerify = false;  // If true, skip the TLS verification
  private boolean plainHttp = false;  // If true, use plain HTTP requests
  private Path keyring;   // The path to the keyring containing public keys for verification
  private boolean debug = false;  // If true, enable verbose output

  public HelmUpgradeConfig() {}

  public HelmUpgradeConfig(String chartReference) {
      this.chartReference = chartReference;
  }
  
  // Getters and Setters...
}    
```

### Uninstall Configuration
The following are the configurations that can be used with the `helm uninstall` command:

```java
public class HelmUninstallConfig {

  private String chartReference;  // The reference to the chart
  private boolean dryRun = false; // If true, simulate an uninstallation
  private boolean noHooks = false;    // If true, prevent hooks from running during uninstall
  private boolean ignoreNotFound = false; // If true, ignore the not found error for the release
  private boolean keepHistory = false;// If true, remove all associated resources and mark the release as deleted
  private Cascade cascade; // Determines whether resources should be deleted in the same order they were created
  private String namespace;   // The namespace scope for this request
  private Path kubeConfig;    // The path to the kube config file
  private boolean debug = false;  // If true, enable verbose output

  public HelmUninstallConfig(String chartReference) {
      this.chartReference = chartReference;
  }
  
  // Getters and Setters...
}    
```

### List Configuration
The following are the configurations that can be used with the `helm list` command:

```java
public class HelmListConfig {

  private String namespace; // Namespace
  private Path kubeConfig; // Path to the kube config file
  private boolean all = false; // If true, List all releases
  private boolean allNamespaces = false; // If true, List releases in all namespaces
  private boolean deployed = false; // If true, List deployed releases
  private boolean failed = false; // If true, List failed releases
  private boolean pending = false; // If true, List pending releases
  private boolean superseded = false; // If true, List superseded releases
  private boolean uninstalled = false; // If true, List uninstalled releases
  private boolean uninstalling = false; // If true, List uninstalling releases

  public HelmListConfig() {}

  // Getters and Setters...
}
```

### Lint Configuration
The following are the configurations that can be used with the `helm lint` command:

```java
public class HelmLintConfig {

  private boolean strict = false; // If true, treats warnings as errors
  private boolean quiet = false; // If true, suppresses warnings

  public HelmLintConfig() {}
  
  // Getters and Setters...
}
```

### Test Configuration
The following are the configurations that can be used with the `helm test` command:

```java
public class HelmTestConfig {

  private String chartReference;  // The reference to the chart.
  private int timeout = 0;    // The timeout for the test.
  private String namespace;   // The namespace to use for the test.
  private Path kubeConfig;    // The path to the kube config file.
  private boolean debug = false;  // If true, the debug mode is enabled.

  /**
   * Constructs a HelmTestConfig object with the given chart reference.
   * @param chartReference The reference to the chart.
   */
  public HelmTestConfig(String chartReference) {
      this.chartReference = chartReference;
  }
  
  // Getters and Setters...
}
```

### Dependency Configuration
The following are the configurations that can be used with the `helm dependency` command:

```java
public class HelmDependencyConfig {

    private Path keyring;   // Path to the keyring file
    private boolean skipRefresh = false;    // If true, do not refresh the local repository cache
    private boolean verify = false; // If true, verify the package against its signature
    private boolean debug = false;  // If true, enable verbose output

    public HelmDependencyConfig() {}
  
    // Getters and Setters...
}
```

### Package Configuration
The following are the configurations that can be used with the `helm package` command:

```java
public class HelmPackageConfig {

    private Path destination;   // The destination path for the generated package
    private boolean sign = false;   // If true, the package will be signed
    private String keyUID;  // The key UID to use for signing (required if sign is true)
    private Path keyring;   // The keyring to use for signing (required if sign is true)
    private Path passphraseFile;    // The file containing the passphrase for the key

    /**
     * Constructs a HelmPackageConfig object with no parameters.
     */
    public HelmPackageConfig() {}
  
    // Getters and Setters...
}
```
