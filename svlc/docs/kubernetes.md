# Kubernetes Mechanism in SVLC

## Table of Contents
* [Introduction](#introduction)
* [Kubernetes](#kubernetes)
  * [Kubernetes Configuration](#kubernetes-configuration)
  * [Requests Configurations](#requests-configurations)
    * [Create](#create)
    * [Info](#info)
    * [List](#list)
  * [Resource Types](#resource-types) 
    * [Deployment](#deployment)
    * [Service](#service)
    * [Pod](#pod)
    * [Secret](#secret)
    * [ConfigMap](#configmap)
    * [Persistent Volume](#persistent-volume)
    * [Persistent Volume Claim](#persistent-volume-claim)
  * [Result Handling](#result-handling)
* [Configuration Files](#configuration-files)
* [Authentication](#authentication)
* [Dependencies](#dependencies)
* [Limitations](#limitations)

## Introduction

This document provides a detailed overview of the Kubernetes mechanism in the SVLC project.
Kubernetes is an open-source container orchestration platform that automates the deployment, scaling, 
and management of containerized applications. It is a crucial component of the SVLC project,
enabling the deployment and management of microservices in a distributed environment.

## Kubernetes
The Kubernetes mechanism in the SVLC project is responsible for managing Kubernetes resources such as deployments, services, pods, and more.
The `Kubernetes` class encapsulates the Kubernetes client and provides methods to interact with the Kubernetes cluster.

### Kubernetes Configuration

```java 
public class Kubernetes {

  private final String namespace;   // The namespace to use in the Kubernetes cluster.
  
  public Kubernetes(String namespace, String clusterName) throws Exception {
      String path = System.getProperty("user.home") + "/.kube/config";
      // The Kubernetes client to interact with the Kubernetes cluster.
      ApiClient client = Config.fromConfig(path).setVerifyingSsl(false).setDebugging(true);
      client.setApiKey(Auth.getKubernetesToken(clusterName));
      Configuration.setDefaultApiClient(client);
      this.namespace = checkAndSetNamespace(namespace);
  }
  
  private String checkAndSetNamespace(String namespace) {
      return namespace != null ? namespace : "default";
  }
  
  // Methods to interact with the Kubernetes cluster...
}  
```

### Requests Configurations

#### Create
The `CreateConfig` class is responsible for creating Kubernetes resources configurations.
It provides the necessary properties to configure the resource creation process.

```java
public class CreateConfig {

  private String name;  // The name of the resource.
  private int replicas; // The number of replicas to scale to.
  private String pretty;  // If 'true', then the output is pretty printed.
  private String dryRun;  // If 'true', then the server will simulate the request.
  private String fieldManager;    // A unique value that identifies this request.
  private String fieldValidation; // If 'true', then the input is validated before submitting the request.

  public CreateConfig() {}

  public CreateConfig(String name, int replicas) {
      this.name = name;
      this.replicas = replicas;
  }

  // Getters and Setters for the class attributes...
}
```

#### Info
The `InfoConfig` class is responsible for retrieving information about Kubernetes resources.
It provides the necessary properties to configure the resource information retrieval process.

```java
public class InfoConfig {
    
  private final String name;  // The name of the resource to get logs from.
  private String container;   // The container name.
  private Boolean follow = false;  // If true, then the logs are streamed.
  private Boolean insecureSkipTlsVerify = false;   // If true, then the server's certificate will not be checked for validity.
  private Integer limitBytes;  // If set, then the logs are returned for this many bytes.
  private String pretty;  // If 'true', then the output is pretty printed.
  private Boolean previous = false;    // If 'true', then the previous container is used.
  private Integer sinceSeconds;    // If set, then the logs are returned since this many seconds ago.
  private Integer tailLines;   // If set, then the logs are returned for this many lines.
  private Boolean timestamps = false;  // If 'true', then the timestamps are included in the logs.

  public InfoConfig(String name) {
    this.name = name;
  }

  // Getters and Setters for the class attributes...
}
```
#### List
The `ListConfig` class is responsible for listing Kubernetes resources.
It provides the necessary properties to configure the resource listing process.

```java
public class ListConfig {

  private String pretty;  // If 'true', then the output is pretty printed.
  private Boolean allowWatchBookmarks;    // Allow watch bookmarks to be sent.
  private String _continue;   // The continue token for the list operation.
  private String fieldSelector;   // A selector to restrict the list of returned objects by their fields.
  private String labelSelector;   // A selector to restrict the list of returned objects by their labels.
  private Integer limit;  // The maximum number of items to return.
  private String resourceVersion; // The resource version from which to continue.
  private String resourceVersionMatch;    // The resource version match label to filter on.
  private Boolean sendInitialEvents;  // Send initial events.
  private Integer timeoutSeconds; // Timeout for the list/watch call.
  private Boolean watch;  // If true, then watch for changes to the returned objects.

  public ListConfig() {}

  // Getters and Setters for the class attributes...
}
```
### Resource Types
The Kubernetes mechanism in the SVLC project uses a set of resource classes to create Kubernetes resources.
These classes provide constructors to create the resources with the necessary configurations. All these classes
have a method to create the respective Kubernetes resource classes from the official Kubernetes Java client library.
The supported Kubernetes resource types are the following:

#### Deployment
The `Deployment` class is responsible for managing Kubernetes deployments.
```java
@XmlRootElement(name = "deployment")
@XmlAccessorType(XmlAccessType.FIELD)
public class Deployment {

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "replicas")
    private Integer replicas;

    @XmlElement(name = "containerList")
    private List<Container> containerList;

    @XmlElement(name = "imagePullPolicy")
    private String imagePullPolicy;

    @XmlElement(name = "imagePullSecret")
    private String imagePullSecret;

    public Deployment() {
        super();
    }

    public Deployment(String name, Integer replicas, List<Container> containerList, String imagePullPolicy, String imagePullSecret) {
        super();
        this.setName(name);
        this.setReplicas(replicas);
        this.setContainerList(containerList);
        this.setImagePullPolicy(imagePullPolicy);
        this.setImagePullSecret(imagePullSecret);
    }
    
    // Getters and Setters for the class attributes...
}
``` 

#### Service
The `Service` class is responsible for managing Kubernetes services.
```java
@XmlRootElement(name = "Service")
@XmlAccessorType(XmlAccessType.FIELD)
public class Service {

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "type")
    private String type;

    @XmlElement(name = "portConfigList")
    private List<PortConfig> portConfigList;

    @XmlElement(name = "externalIP")
    private String externalIP;

    public Service() {
        super();
    }

    public Service(String name, String type, List<PortConfig> portConfigList, String externalIP) {
        super();
        this.setName(name);
        this.setType(type);
        this.setPortConfigList(portConfigList);
        this.setExternalIP(externalIP);
    }
    
    // Getters and Setters for the class attributes...
}
```

#### Pod
The `Pod` class is responsible for managing Kubernetes pods.
```java
@XmlRootElement(name = "pod")
@XmlAccessorType(XmlAccessType.FIELD)
public class Pod {

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "containerList")
    private List<Container> containerList;

    @XmlElement(name = "imagePullPolicy")
    private String imagePullPolicy;

    @XmlElement(name = "imagePullSecret")
    private String imagePullSecret;

    public Pod() {
        super();
    }

    public Pod(String name, List<Container> containerList, String imagePullPolicy, String imagePullSecret) {
        super();
        this.setName(name);
        this.setContainerList(containerList);
        this.setImagePullPolicy(imagePullPolicy);
        this.setImagePullSecret(imagePullSecret);
    }
    
    // Getters and Setters for the class attributes...
}
```

#### Secret
The `Secret` class is responsible for managing Kubernetes secrets.
```java
@XmlRootElement(name = "secret")
@XmlAccessorType(XmlAccessType.FIELD)
public class Secret {

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "type")
    private String type;

    @XmlElement(name = "labels")
    private Map<String, String> labels;

    private Map<String, byte []> data;

    public Secret() {
        super();
    }

    public Secret(String name, String type, Map<String, String> labels) {
        super();
        this.setName(name);
        this.setType(type);
        this.setLabels(labels);
    }
    
    // Getters and Setters for the class attributes...
}
```

#### ConfigMap
The `ConfigMap` class is responsible for managing Kubernetes config maps.
```java
@XmlRootElement(name = "configMap")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConfigMap {

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "data")
    private Map<String, String> data;

    public ConfigMap() {
        super();
    }

    public ConfigMap(String name, Map<String, String> data) {
        super();
        this.setName(name);
        this.setData(data);
    }
    
    // Getters and Setters for the class attributes...
}
```

#### Persistent Volume
The `PersistentVolume` class is responsible for managing Kubernetes persistent volumes.
```java
@XmlRootElement(name = "persistentVolume")
@XmlAccessorType(XmlAccessType.FIELD)
public class PersistentVolume {

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "capacity")
    private String capacity;

    @XmlElement(name = "storageClass")
    private String storageClass;

    @XmlElement(name = "accessModes")
    private String accessModes;

    @XmlElement(name = "hostPath")
    private String hostPath;

    public PersistentVolume() {
        super();
    }

    public PersistentVolume(String name, String capacity, String storageClass, String accessModes, String hostPath) {
        super();
        this.setName(name);
        this.setCapacity(capacity);
        this.setStorageClass(storageClass);
        this.setAccessModes(accessModes);
        this.setHostPath(hostPath);
    }
    
    // Getters and Setters for the class attributes...
}
```

#### Persistent Volume Claim
The `PersistentVolumeClaim` class is responsible for managing Kubernetes persistent volume claims.
```java
@XmlRootElement(name = "PersistentVolumeClaim")
@XmlAccessorType(XmlAccessType.FIELD)
public class PersistentVolumeClaim {

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "storageClass")
    private String storageClass;

    @XmlElement(name = "accessModes")
    private String accessModes;

    @XmlElement(name = "storage")
    private String storage;

    public PersistentVolumeClaim() {
        super();
    }

    public PersistentVolumeClaim(String name, String storageClass, String accessModes, String storage) {
        super();
        this.setName(name);
        this.setStorageClass(storageClass);
        this.setAccessModes(accessModes);
        this.setStorage(storage);
    }
}
```

### Result Handling
`Kubernetes` class utilize indirectly the [`Result`](../src/main/java/pt/isel/leic/svlc/util/results/Result.java) class for operation outcomes,
which is a generic container for either a ***success*** or an ***error*** result.

## Configuration Files
The `Kubernetes` class uses the [`KubernetesParser`](../src/main/java/pt/isel/leic/svlc/kubernetes/KubernetesParser.java) class to
serialize .xml files into a "Cluster" object. This class uses the `JAXB` library to parse the XML files and create the corresponding objects.

See [`example`](../../demo/demo_cli/ConfigFiles/goodbye-config.xml) .

## Authentication
The `Auth` class is responsible for handling authentication in the Kubernetes mechanism.
It provides methods to authenticate with the Kubernetes cluster using a client key.
You can also create the registry secret for the deployment.

`Auth::getKubernetesToken`
```java
public static String getKubernetesToken(String cluster) throws Exception {
  String keyPath = System.getProperty("user.home") + "\\.minikube\\profiles\\" + cluster + "\\client.key";
  byte[] keyBytes = Files.readAllBytes(Paths.get(keyPath));
  // Remove all newline and carriage return characters
  return new String(keyBytes).replace("\n", "").replace("\r", "").trim();
}
```
    
`Auth::registryKey`
```java
public static String registryKey() {
    String format = "%s:%s";
    return encode(format);
}

private static String encode(String format){
  //get username and access token to environment variables
  String username = System.getenv("USERNAME_ENV_VAR");
  String token = System.getenv("TOKEN_ENV_VAR");
  return Base64.getEncoder().encodeToString(String.format(format, username, token).getBytes());
}
```

## Dependencies
* [kubernetes-java-client](https://github.com/kubernetes-client/java/tree/master?tab=readme-ov-file): Official Java client library for kubernetes

## Limitations

> **Limitation**: The Kubernetes mechanism in the SVLC project is limited to the deployment and management of Kubernetes resources.
> It does not support advanced Kubernetes features such as custom resource definitions (CRDs), operators, or Helm charts.