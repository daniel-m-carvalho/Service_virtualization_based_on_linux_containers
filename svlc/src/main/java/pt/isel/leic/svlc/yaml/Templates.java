package pt.isel.leic.svlc.yaml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A utility class for generating Kubernetes deployment templates.
 * The templates are generated as YAML strings.
 */
public class Templates {

    /**
     * Creates a pod template with specified parameters.
     *
     * @param podName         The name of the pod.
     * @param containersName   A list of container names.
     * @param images          A list of Docker images for the containers.
     * @param imagePullPolicy The image pull policy.
     * @param imagePullSecrets A list of image pull secrets.
     * @return A map representing the pod template.
     */
    public static Map<String, Object> createPodTemplate(
            String podName,
            List<String> containersName,
            List<String> images,
            String imagePullPolicy,
            List<Map<String, String>> imagePullSecrets
    ) {
        Map<String, Object> pod = new HashMap<>();
        pod.put("apiVersion", "v1");
        pod.put("kind", "Pod");

        Map<String, String> metadata = new HashMap<>();
        metadata.put("name", podName);
        pod.put("metadata", metadata);

        List<Map<String, Object>> containers = createContainers(containersName, images, imagePullPolicy);

        Map<String, Object> spec = new HashMap<>();
        spec.put("containers", containers);
        spec.put("imagePullSecrets", imagePullSecrets);

        pod.put("spec", spec);

        return pod;
    }

    private static List<Map<String, Object>> createContainers(List<String> containersName, List<String> images, String imagePullPolicy) {
        return containersName.stream().map(containerName -> {
            Map<String, Object> container = new HashMap<>();
            container.put("name", containerName);
            container.put("image", images.get(containersName.indexOf(containerName)));
            container.put("imagePullPolicy", imagePullPolicy);
            return container;
        }).toList();
    }

    /**
     * Creates a deployment template with specified parameters.
     *
     * @param name           The name of the deployment.
     * @param image          The Docker image to use for the deployment.
     * @param replicas       The number of replicas.
     * @param port           The port number the container exposes.
     * @param envVariables   Environment variables for the container.
     * @param readinessProbe Configuration for the readiness probe.
     * @param livenessProbe  Configuration for the liveness probe.
     * @param resources      Resource requirements and limits.
     * @return A map representing the deployment template.
     */
    public static Map<String, Object> createDeploymentTemplate(
        String name,
        String image,
        int replicas,
        int port,
        Map<String, String> envVariables,
        Map<String, Object> readinessProbe,
        Map<String, Object> livenessProbe,
        Map<String, Object> resources
    ) {

        Map<String, Object> deployment = new HashMap<>();
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("name", name + "-deployment");

        Map<String, Object> selectorMatchLabels = new HashMap<>();
        selectorMatchLabels.put("app", name);

        Map<String, Object> selector = new HashMap<>();
        selector.put("matchLabels", selectorMatchLabels);

        Map<String, Object> templateMetadata = new HashMap<>();
        templateMetadata.put("labels", selectorMatchLabels);

        Map<String, Object> templateSpec = createTemplateSpec(name, image, port, envVariables, readinessProbe, livenessProbe, resources);

        Map<String, Object> template = new HashMap<>();
        template.put("metadata", templateMetadata);
        template.put("spec", templateSpec);

        Map<String, Object> spec = new HashMap<>();
        spec.put("replicas", replicas);
        spec.put("selector", selector);
        spec.put("template", template);

        deployment.put("apiVersion", "apps/v1");
        deployment.put("kind", "Deployment");
        deployment.put("metadata", metadata);
        deployment.put("spec", spec);

        return deployment;
    }

    /**
     * Creates a service template with specified parameters.
     *
     * @param name                The name of the service.
     * @param type                The type of service (e.g., ClusterIP, NodePort).
     * @param portConfigurations  List of port configurations.
     * @param serviceLabels       Labels to apply to the service.
     * @param selectorLabels      Selector labels for selecting the pods.
     * @return A map representing the service template.
     */
    public static Map<String, Object> createServiceTemplate(
            String name, String type,
            List<Map<String, Object>> portConfigurations,
            Map<String, String> serviceLabels,
            Map<String, String> selectorLabels
    ) {

        Map<String, Object> service = createTemplateHeader(name + "-service", "Service", serviceLabels, null);

        Map<String, Object> selector = new HashMap<>();
        if (selectorLabels != null && !selectorLabels.isEmpty()) {
            selector.putAll(selectorLabels);
        } else {
            selector.put("app", name);
        }

        List<Map<String, Object>> ports = new ArrayList<>(portConfigurations);

        Map<String, Object> spec = new HashMap<>();
        spec.put("selector", selector);
        spec.put("ports", ports);
        spec.put("type", type);

        service.put("spec", spec);

        return service;
    }

    /**
     * Creates a ConfigMap template with specified parameters.
     *
     * @param name   The name of the ConfigMap.
     * @param data   The data to include in the ConfigMap.
     * @param labels Labels to apply to the ConfigMap.
     * @return A map representing the ConfigMap template.
     */
    public static Map<String, Object> createConfigMapTemplate(
            String name,
            Map<String, String> data,
            Map<String, String> labels
    ) {
        Map<String, Object> configMap = createTemplateHeader(name + "-configMap", "ConfigMap", labels, null);

        configMap.put("data", data);

        return configMap;
    }

    /**
     * Creates a Secret template with specified parameters.
     *
     * @param name   The name of the Secret.
     * @param data   The data to include in the Secret, encoded in base64.
     * @param labels Labels to apply to the Secret.
     * @param type   The type of the Secret (e.g., Opaque).
     * @return A map representing the Secret template.
     */
    public static Map<String, Object> createSecretTemplate(
            String name,
            Map<String, String> data,
            Map<String, String> labels,
            String type
    ) {
        Map<String, Object> secret = createTemplateHeader(name + "-secret", "Secret", labels, null);

        secret.put("data", data);
        secret.put("type", type);

        return secret;
    }

    /**
     * Creates a PersistentVolumeClaim (PVC) template with specified parameters.
     *
     * @param name         The name of the PVC.
     * @param storageSize  The size of the storage to request.
     * @param accessModes  The access modes (e.g., ReadWriteOnce).
     * @param labels       Labels to apply to the PVC.
     * @return A map representing the PVC template.
     */
    public static Map<String, Object> createPersistentVolumeClaimTemplate(
            String name,
            Integer storageSize,
            List<String> accessModes,
            Map<String, String> labels
    ) {
        Map<String, Object> pvc = createTemplateHeader(name + "-pcv", "PersistentVolumeClaim", labels, null);

        Map<String, Object> spec = new HashMap<>();
        spec.put("accessModes", accessModes);
        Map<String, Object> resources = new HashMap<>();
        Map<String, Object> requests = new HashMap<>();
        requests.put("storage", storageSize);
        resources.put("requests", requests);
        spec.put("resources", resources);

        pvc.put("spec", spec);

        return pvc;
    }

    /**
     * Creates a PersistentVolume template with specified parameters.
     *
     * @param name            The name of the PersistentVolume.
     * @param storageClassName The storage class name.
     * @param accessMode      The access mode (e.g., ReadWriteOnce).
     * @param capacity        The capacity of the volume.
     * @param hostPath        The host path for the volume.
     * @return A map representing the PersistentVolume template.
     */
    public static Map<String, Object> createPersistentVolumeTemplate(
            String name,
            String storageClassName,
            String accessMode,
            String capacity,
            String hostPath
    ) {
        Map<String, Object> pv = createTemplateHeader(name + "-pv", "PersistentVolume", null, null);
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("name", name);
        pv.put("metadata", metadata);

        Map<String, Object> spec = new HashMap<>();
        spec.put("storageClassName", storageClassName);
        spec.put("accessModes", List.of(accessMode));
        spec.put("capacity", Map.of("storage", capacity));
        spec.put("hostPath", Map.of("path", hostPath));
        pv.put("spec", spec);

        return pv;
    }

    /**
     * Generates a list of environment variable maps from a given map of environment variables.
     * Each environment variable is represented as a map with "name" and "value" keys.
     *
     * @param envVariables A map of environment variable names to their values.
     * @return A list of maps, each representing an environment variable.
     */
    private static List<Map<String, Object>> createEnvVariables(Map<String, String> envVariables) {
        List<Map<String, Object>> env = new ArrayList<>();
        envVariables.forEach((key, value) -> env.add(Map.of("name", key, "value", value)));
        return env;
    }

    /**
     * Creates a list containing a single port configuration map.
     * The map contains a single key "containerPort" with the provided port number as its value.
     *
     * @param port The port number to be included in the port configuration.
     * @return A list containing a single port configuration map.
     */
    private static List<Map<String, Object>> createPorts(int port) {
        return List.of(Map.of("containerPort", port));
    }

    /**
     * Creates a template specification for a Kubernetes container.
     * This includes the container name, image, ports, environment variables, and optionally,
     * readiness and liveness probes, and resource requirements.
     *
     * @param name           The name of the container.
     * @param image          The Docker image for the container.
     * @param port           The port number the container exposes.
     * @param envVariables   A map of environment variable names to their values.
     * @param readinessProbe A map representing the readiness probe configuration.
     * @param livenessProbe  A map representing the liveness probe configuration.
     * @param resources      A map representing the resource requirements and limits.
     * @return A map representing the template specification of a container.
     */
    private static Map<String, Object> createTemplateSpec(String name, String image, int port,
                                                          Map<String, String> envVariables, Map<String, Object> readinessProbe,
                                                          Map<String, Object> livenessProbe, Map<String, Object> resources) {
        List<Map<String, Object>> env = createEnvVariables(envVariables);
        List<Map<String, Object>> ports = createPorts(port);

        Map<String, Object> container = new HashMap<>();
        container.put("name", name);
        container.put("image", image);
        container.put("ports", ports);
        container.put("env", env);
        if (readinessProbe != null) container.put("readinessProbe", readinessProbe);
        if (livenessProbe != null) container.put("livenessProbe", livenessProbe);
        if (resources != null) container.put("resources", resources);

        return Map.of("containers", List.of(container));
    }

    /**
     * Creates metadata for a Kubernetes resource.
     * This includes the resource name, and optionally, labels and annotations.
     *
     * @param name        The name of the resource.
     * @param labels      A map of labels to be applied to the resource.
     * @param annotations A map of annotations to be applied to the resource.
     * @return A map representing the metadata of a Kubernetes resource.
     */
    private static Map<String, Object> createMetadata(String name, Map<String, String> labels, Map<String, String> annotations) {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("name", name);
        if (annotations != null && !annotations.isEmpty()) {
            metadata.put("annotations", annotations);
        }
        if (labels != null && !labels.isEmpty()) {
            metadata.put("labels", labels);
        }
        return metadata;
    }

    /**
     * Creates a template header for a Kubernetes resource.
     * This includes the resource name, kind, and optionally, labels and annotations.
     *
     * @param name        The name of the resource.
     * @param kind        The kind of the resource (e.g., Deployment, Service).
     * @param labels      A map of labels to be applied to the resource.
     * @param annotations A map of annotations to be applied to the resource.
     * @return A map representing the header of a Kubernetes resource template.
     */
    private static Map<String, Object> createTemplateHeader(String name, String kind, Map<String, String> labels, Map<String, String> annotations) {
        Map<String, Object> metadata = createMetadata(name, labels, annotations);
        Map<String, Object> template = new HashMap<>();
        template.put("apiVersion", "v1");
        template.put("kind", kind);
        template.put("metadata", metadata);
        return template;
    }
}