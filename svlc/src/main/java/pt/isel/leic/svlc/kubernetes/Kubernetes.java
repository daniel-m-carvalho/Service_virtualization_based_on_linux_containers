package pt.isel.leic.svlc.kubernetes;

import io.fabric8.kubernetes.api.model.*;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentList;
import io.fabric8.kubernetes.api.model.autoscaling.v1.HorizontalPodAutoscaler;
import io.fabric8.kubernetes.api.model.autoscaling.v1.HorizontalPodAutoscalerBuilder;
import io.fabric8.kubernetes.client.*;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.utils.Serialization;
import pt.isel.leic.svlc.util.auth.Auth;
import pt.isel.leic.svlc.util.kubernetes.KubeResource;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents a class that handles the Kubernetes API.
 */
public class Kubernetes {

    private final KubernetesClient client;  // The Kubernetes client to interact with the Kubernetes cluster.
    private final String namespace;          // The namespace to use in the Kubernetes cluster.

    /**
     * Constructs a Kubernetes instance with the default master URL and namespace.
     */
    public Kubernetes() {
        this.client = new KubernetesClientBuilder().build();
        this.namespace = "default";
    }

    /**
     * Constructs a Kubernetes instance with the specified master URL and namespace.
     *
     * @param masterUrl The URL of the Kubernetes cluster.
     * @param namespace The namespace to use in the Kubernetes cluster.
     * @throws Exception if the Kubernetes token cannot be retrieved.
     */
    public Kubernetes(String masterUrl, String namespace) throws Exception {
        this.namespace = checkAndSetNamespace(namespace);
        String token = Auth.getKubernetesToken(this.namespace);
        Config config = new ConfigBuilder()
                .withMasterUrl(masterUrl)
                .withNamespace(this.namespace)
                .withOauthToken(token)
                .build();
        this.client = new KubernetesClientBuilder().withConfig(config).build();
    }

    /**
     *  Represents a map that maps a Kubernetes resource type to a KubeResource object.
     *  The KubeResource object is used to create the Kubernetes resource.
     */
    private static final Map<String, KubeResource> resourceMap = Map.of(
            "Pod", (client, namespace, resource) -> client.pods().inNamespace(namespace).resource((Pod) resource).create(),
            "Deployment", (client, namespace, resource) -> client.apps().deployments().inNamespace(namespace).resource((Deployment) resource).create(),
            "Service", (client, namespace, resource) -> client.services().inNamespace(namespace).resource((Service) resource).create(),
            "PersistentVolume", (client, namespace, resource) -> client.persistentVolumes().resource((PersistentVolume) resource).create(),
            "PersistentVolumeClaim", (client, namespace, resource) -> client.persistentVolumeClaims().inNamespace(namespace).resource((PersistentVolumeClaim) resource).create(),
            "ConfigMap", (client, namespace, resource) -> client.configMaps().inNamespace(namespace).resource((ConfigMap) resource).create(),
            "Secret", (client, namespace, resource) -> client.secrets().inNamespace(namespace).resource((Secret) resource).create()
    );

    /**
     * Checks if the namespace is null and sets it to "default" if it is.
     * @param namespace The namespace to check.
     * @return The namespace to use.
     */
    private String checkAndSetNamespace(String namespace) {
        return namespace != null ? namespace : "default";
    }

    /**
     * Lists all the pods in the Kubernetes cluster.
     *
     * @return A list of all the pods in the Kubernetes cluster.
     */
    public List<Pod> listPods() {
        PodList podList = client.pods().inNamespace(namespace).list();
        return podList.getItems();
    }

    /**
     * Lists all the deployments in the Kubernetes cluster.
     *
     * @return A list of all the deployments in the Kubernetes cluster.
     */
    public DeploymentList listDeployments() {
        return client.apps().deployments().inNamespace(namespace).list();
    }

    /**
     * Lists all the services in the Kubernetes cluster.
     *
     * @return A list of all the services in the Kubernetes cluster.
     */
    public ServiceList listServices() {
        return client.services().inNamespace(namespace).list();
    }

    /**
     * Scales a deployment to the specified number of replicas.
     *
     * @param deploymentName The name of the deployment to scale.
     * @param replicas The number of replicas to scale to.
     */
    public void scaleDeployment(String deploymentName, int replicas) {
        client.apps().deployments().inNamespace(namespace).withName(deploymentName).scale(replicas);
    }

    /**
     * Gets the logs of a pod with the specified name.
     *
     * @param podName The name of the pod to get logs from.
     * @return The logs of the pod.
     */
    public String getPodLogs(String podName) {
        return client.pods().inNamespace(namespace).withName(podName).getLog();
    }

    /**
     * Enables autoscaling for a deployment with the specified parameters.
     *
     * @param deploymentName The name of the deployment to enable autoscaling for.
     * @param minPods The minimum number of pods to scale to.
     * @param maxPods The maximum number of pods to scale to.
     * @param targetCPUUtilizationPercentage The target CPU utilization percentage.
     */
    public void enableAutoscaling(String deploymentName, int minPods, int maxPods, int targetCPUUtilizationPercentage) {
        HorizontalPodAutoscaler hpa = new HorizontalPodAutoscalerBuilder()
                .withNewMetadata().withName(deploymentName + "-hpa").withNamespace(namespace).endMetadata()
                .withNewSpec()
                .withMinReplicas(minPods)
                .withMaxReplicas(maxPods)
                .withTargetCPUUtilizationPercentage(targetCPUUtilizationPercentage)
                .withNewScaleTargetRef()
                .withApiVersion("apps/v1")
                .withKind("Deployment")
                .withName(deploymentName)
                .endScaleTargetRef()
                .endSpec()
                .build();

        client.autoscaling().v1().horizontalPodAutoscalers().inNamespace(namespace).resource(hpa).create();
    }

    /**
     * Gets the status of all the nodes in the Kubernetes cluster.
     *
     * @return A map containing the status of all the nodes in the Kubernetes cluster.
     */
    public Map<String, Map<String, String>> getNodeStates() {
        return client.nodes().list().getItems().stream()
                .collect(Collectors.toMap(
                        node -> node.getMetadata().getName(),
                        node -> node.getStatus().getConditions().stream()
                                .collect(Collectors.toMap(NodeCondition::getType, NodeCondition::getStatus))
                ));
    }

    /**
     * Gets the status of one node in the Kubernetes cluster.
     *
     * @return A map containing the status of the specified node in the Kubernetes cluster
     * or null if the node is not found.
     */
    public Map<String, String> getNodeState(String nodeName) {
        return client.nodes().list().getItems().stream()
                .filter(node -> node.getMetadata().getName().equals(nodeName)).findFirst()
                .map(node -> node.getStatus().getConditions().stream()
                        .collect(Collectors.toMap(NodeCondition::getType, NodeCondition::getStatus)))
                .orElse(null);
    }

    /**
     * Deploys a Kubernetes resource from a YAML string.
     *
     * @param yaml The YAML string to deploy.
     * @return The Kubernetes resource that was deployed.
     */
    public HasMetadata deployFromYaml(String yaml) {
        HasMetadata resource = Serialization.unmarshal(yaml, HasMetadata.class);
        String resourceType = resource.getKind();
        return  resourceMap.get(resourceType).execute(client, namespace, resource);
    }

}
