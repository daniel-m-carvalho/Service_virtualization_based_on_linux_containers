package pt.isel.leic.svlc.kubernetes;

import io.kubernetes.client.common.KubernetesObject;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.models.*;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.util.Config;
import io.kubernetes.client.util.Yaml;
import org.jetbrains.annotations.TestOnly;
import pt.isel.leic.svlc.util.auth.Auth;
import pt.isel.leic.svlc.util.kubernetes.KubeResource;
import pt.isel.leic.svlc.util.kubernetes.configurations.CreateConfig;
import pt.isel.leic.svlc.util.kubernetes.configurations.ListConfig;
import pt.isel.leic.svlc.util.kubernetes.configurations.InfoConfig;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static pt.isel.leic.svlc.util.executers.CmdExec.executeCommand;

/**
 * Represents a class that handles the Kubernetes API.
 */
public class Kubernetes {

    private final String namespace;   // The namespace to use in the Kubernetes cluster.

    /**
     * Constructs a Kubernetes instance with the default master URL and namespace.
     * @param namespace The namespace to use in the Kubernetes cluster.
     * @param clusterName The name of the cluster to use.
     * @throws Exception if the Kubernetes client cannot be created.
     */
    public Kubernetes(String namespace, String clusterName) throws Exception {
        ApiClient client = setClient(clusterName, false);
        Configuration.setDefaultApiClient(client);
        this.namespace = namespace;
    }

    /**
     * Constructs a Kubernetes instance with the default master URL and namespace.
     * @throws Exception if the Kubernetes client cannot be created.
     */
    @TestOnly
    public Kubernetes() throws Exception {
        ApiClient client = setClient("test", true);
        Configuration.setDefaultApiClient(client);
        this.namespace = "default";
    }

    /**
     * Constructs a Kubernetes instance with the specified master URL and namespace.
     * @param clusterName The name of the cluster to use.
     * @param debug Indicates if the client should be in debug mode.
     * @throws Exception if the Kubernetes client cannot be created.
     */
    private ApiClient setClient(String clusterName, boolean debug) throws Exception {
        String path = System.getProperty("user.home") + "/.kube/config";
        // The Kubernetes client to interact with the Kubernetes cluster.
        ApiClient client = Config.fromConfig(path).setVerifyingSsl(false);
        if (debug) client.setDebugging(true);
        client.setApiKey(Auth.getKubernetesToken(clusterName));
        Configuration.setDefaultApiClient(client);
        return client;
    }

    /**
     * A map containing the kinds of Kubernetes resources and their respective creation methods.
     */
    private final Map<String, KubeResource> KINDS = Map.of(
        "Pod", (pod, config) -> createPod((V1Pod) pod, config),
        "Service", (service, config) -> createService((V1Service) service, config),
        "Deployment", (deployment, config) -> createDeployment((V1Deployment) deployment, config),
        "PersistentVolume", (persistentVolume, config) -> createPersistentVolume((V1PersistentVolume) persistentVolume, config),
        "PersistentVolumeClaim", (persistentVolumeClaim, config) -> createPersistentVolumeClaim((V1PersistentVolumeClaim) persistentVolumeClaim, config),
        "ConfigMap", (configMap, config) -> createConfigMap((V1ConfigMap) configMap, config),
        "Secret", (secret, config) -> createSecret((V1Secret) secret, config)
    );

    /**
     * Lists all the pods in the Kubernetes cluster.
     * @param config The configuration to list the pods.
     * @return A list of all the pods in the Kubernetes cluster.
     */
    public List<V1Pod> listPods(ListConfig config) throws ApiException {
        CoreV1Api api = new CoreV1Api();
        return api.listNamespacedPod(
            namespace, config.getPretty(), config.getAllowWatchBookmarks(),
            config.getContinue(), config.getFieldSelector(), config.getLabelSelector(),
            config.getLimit(), config.getResourceVersion(), config.getResourceVersionMatch(),
            config.getSendInitialEvents(), config.getTimeoutSeconds(), config.getWatch()
        ).getItems();
    }

    /**
     * Lists all the deployments in the Kubernetes cluster.
     * @param config The configuration to list the deployments.
     * @return A list of all the deployments in the Kubernetes cluster.
     */
    public List<V1Deployment> listDeployments(ListConfig config) throws ApiException {
        AppsV1Api api = new AppsV1Api();
        return api.listNamespacedDeployment(
            namespace, config.getPretty(), config.getAllowWatchBookmarks(),
            config.getContinue(), config.getFieldSelector(), config.getLabelSelector(),
            config.getLimit(), config.getResourceVersion(), config.getResourceVersionMatch(),
            config.getSendInitialEvents(), config.getTimeoutSeconds(), config.getWatch()
        ).getItems();
    }

    /**
     * Lists all the services in the Kubernetes cluster.
     * @param config The configuration to list the services.
     * @return A list of all the services in the Kubernetes cluster.
     */
    public List<V1Service> listServices(ListConfig config) throws ApiException {
        CoreV1Api api = new CoreV1Api();
        return api.listNamespacedService(
            namespace, config.getPretty(), config.getAllowWatchBookmarks(),
            config.getContinue(), config.getFieldSelector(), config.getLabelSelector(),
            config.getLimit(), config.getResourceVersion(), config.getResourceVersionMatch(),
            config.getSendInitialEvents(), config.getTimeoutSeconds(), config.getWatch()
        ).getItems();
    }

    /**
     * Scales a deployment to the specified number of replicas.
     * @param config The configuration to scale the deployment.
     */
    public V1ReplicaSet scale(CreateConfig config) throws ApiException {
        AppsV1Api api = new AppsV1Api();
        V1ReplicaSet scale = new V1ReplicaSet();
        scale.setSpec(new V1ReplicaSetSpec().replicas(config.getReplicas()));
        return api.replaceNamespacedReplicaSet(
            config.getName(), namespace, scale, config.getPretty(),
            config.getDryRun(), config.getFieldManager(), config.getFieldValidation()
        );
    }

    /**
     * Gets the logs of a pod with the specified name.
     * @param config The configuration to get the logs.
     * @return The logs of the pod.
     */
    public String getPodLogs(InfoConfig config) throws ApiException {
        CoreV1Api api = new CoreV1Api();
        return api.readNamespacedPodLog(
            config.getName(), namespace, config.getContainer(), config.getFollow(),
            config.getInsecureSkipTlsVerify(), config.getLimitBytes(), config.getPretty(),
            config.getPrevious(), config.getSinceSeconds(), config.getTailLines(), config.getTimestamps()
        );
    }

    /**
     * Gets the status of all the nodes in the Kubernetes cluster.
     * @param config The configuration to list the nodes.
     * @return A map containing the status of all the nodes in the Kubernetes cluster.
     */
    private Map<String, Map<String, String>> getNodesStatus(ListConfig config) throws ApiException {
        CoreV1Api api = new CoreV1Api();
        V1NodeList nodeList = api.listNode(
            config.getPretty(), config.getAllowWatchBookmarks(), config.getContinue(),
            config.getFieldSelector(), config.getLabelSelector(), config.getLimit(),
            config.getResourceVersion(), config.getResourceVersionMatch(), config.getSendInitialEvents(),
            config.getTimeoutSeconds(), config.getWatch()
        );
        return nodeList.getItems().stream().collect(Collectors.toMap(
            node -> Objects.requireNonNull(node.getMetadata()).getName(),
            node -> Objects.requireNonNull(Objects.requireNonNull(node.getStatus()).getConditions())
                .stream().collect(Collectors.toMap(
                    V1NodeCondition::getType,
                    V1NodeCondition::getStatus
                ))
        ));
    }

    /**
     * Gets the status of one node in the Kubernetes cluster.
     * @param nodeName The name of the node to get the status from.
     * @param config The configuration to list the nodes.
     * @return A map containing the status of the specified node in the Kubernetes cluster
     * or null if the node is not found.
     */
    public Map<String, String> getNodeStatus(String nodeName, ListConfig config) throws ApiException {
        return getNodesStatus(config).get(nodeName);
    }

    /**
     * Deploys a Kubernetes resource from a YAML file.
     * @param yaml The YAML file to deploy.
     * @param config The configuration to deploy the resource.
     * @return A message indicating the resource was deployed successfully.
     * @throws ApiException if the resource cannot be deployed.
     * @throws IOException if the YAML file cannot be read.
     */
    public String deployFromYaml(String yaml, CreateConfig config) throws ApiException, IOException {
        KubernetesObject kubeObj = (KubernetesObject) Yaml.load(yaml);
        String kind = kubeObj.getKind();
        KINDS.get(kind).execute(kubeObj, config);
        return "Deployed " + kind + " successfully";
    }

    /**
     * Creates a pod in the Kubernetes cluster.
     * @param pod The pod to create.
     * @param config The configuration to create the pod.
     * @return The pod created.
     * @throws ApiException if the pod cannot be created.
     */
    public V1Pod createPod(V1Pod pod, CreateConfig config) throws ApiException {
        CoreV1Api api = new CoreV1Api();
        return api.createNamespacedPod(namespace, pod,config.getPretty(), config.getDryRun(), config.getFieldManager(), config.getFieldValidation());
    }

    /**
     * Creates a service in the Kubernetes cluster.
     * @param service The service to create.
     * @param config The configuration to create the service.
     * @return The service created.
     * @throws ApiException if the service cannot be created.
     */
    public V1Service createService(V1Service service, CreateConfig config) throws ApiException {
        CoreV1Api api = new CoreV1Api();
        return api.createNamespacedService(namespace, service, config.getPretty(), config.getDryRun(), config.getFieldManager(), config.getFieldValidation());
    }

    /**
     * Creates a deployment in the Kubernetes cluster.
     * @param deployment The deployment to create.
     * @param config The configuration to create the deployment.
     * @return The deployment created.
     * @throws ApiException if the deployment cannot be created.
     */
    public V1Deployment createDeployment(V1Deployment deployment, CreateConfig config) throws ApiException {
        AppsV1Api api = new AppsV1Api();
        return api.createNamespacedDeployment(namespace, deployment, config.getPretty(), config.getDryRun(), config.getFieldManager(), config.getFieldValidation());
    }

    /**
     * Creates a persistent volume in the Kubernetes cluster.
     * @param persistentVolume The persistent volume to create.
     * @param config The configuration to create the persistent volume.
     * @return The persistent volume created.
     * @throws ApiException if the persistent volume cannot be created.
     */
    public V1PersistentVolume createPersistentVolume(V1PersistentVolume persistentVolume, CreateConfig config) throws ApiException {
        CoreV1Api api = new CoreV1Api();
        return api.createPersistentVolume(persistentVolume, config.getPretty(), config.getDryRun(), config.getFieldManager(), config.getFieldValidation());
    }

    /**
     * Creates a persistent volume claim in the Kubernetes cluster.
     * @param persistentVolumeClaim The persistent volume claim to create.
     * @param config The configuration to create the persistent volume claim.
     * @return The persistent volume claim created.
     * @throws ApiException if the persistent volume claim cannot be created.
     */
    public V1PersistentVolumeClaim createPersistentVolumeClaim(V1PersistentVolumeClaim persistentVolumeClaim, CreateConfig config) throws ApiException {
        CoreV1Api api = new CoreV1Api();
        return api.createNamespacedPersistentVolumeClaim(namespace, persistentVolumeClaim, config.getPretty(), config.getDryRun(), config.getFieldManager(), config.getFieldValidation());
    }

    /**
     * Creates a config map in the Kubernetes cluster.
     * @param configMap The config map to create.
     * @param config The configuration to create the config map.
     * @return The config map created.
     * @throws ApiException if the config map cannot be created.
     */
    public V1ConfigMap createConfigMap(V1ConfigMap configMap, CreateConfig config) throws ApiException {
        CoreV1Api api = new CoreV1Api();
        return api.createNamespacedConfigMap(namespace, configMap, config.getPretty(), config.getDryRun(), config.getFieldManager(), config.getFieldValidation());
    }

    /**
     * Creates a secret in the Kubernetes cluster.
     * @param secret The secret to create.
     * @param config The configuration to create the secret.
     * @return The secret created.
     * @throws ApiException if the secret cannot be created.
     */
    public V1Secret createSecret(V1Secret secret, CreateConfig config) throws ApiException {
        CoreV1Api api = new CoreV1Api();
        return api.createNamespacedSecret(namespace, secret, config.getPretty(), config.getDryRun(), config.getFieldManager(), config.getFieldValidation());
    }
}
