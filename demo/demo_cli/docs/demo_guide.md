### 1 - start ISoS_v4 and see the changes on ISoS_zooKeeper_Browser:

```sh
.\isos\start.cmd
```

### 2 - deploy pod in podman CLI and see the changes on ISystem0_Browser:

```sh
java -jar .\target\demo_cli-1.0-SNAPSHOT-jar-with-dependencies.jar pod deploy hello-config.xml
```

### 3 - start Kubernetes cluster

```sh
minikube start --driver=podman -p goodbye
```

### 4 - show connection with server on browser:

```http request
http://localhost:1234/hello
```

### 5 - deploy pod in Kubernetes cluster and see the changes on ISystem0_Browser:

```sh
java -jar .\target\demo_cli-1.0-SNAPSHOT-jar-with-dependencies.jar kube deploy goodbye-config.xml
```

### 6 - show connection with server on browser

```http request
http://localhost:4321/goodbye
```

### 7 - stop Kubernetes cluster and clean up

```sh
minikube delete -p goodbye --purge && .\isos\clean.cmd
```

### 7 - deploy pod in podman http on wsl2

```bash
java -jar target/demo_cli-1.0-SNAPSHOT-jar-with-dependencies.jar pod deploy http hello-config.xml
```

### 8 - show connection with server on terminal

```bash
curl http://localhost:1234/hello
```