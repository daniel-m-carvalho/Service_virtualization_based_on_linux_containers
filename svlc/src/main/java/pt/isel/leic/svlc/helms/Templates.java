package pt.isel.leic.svlc.helms;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a class that handles the Helm API.
 */
public class Templates {

    public String createDeploymentTemplate() {
        Map<String, Object> deployment = new HashMap<>();
        // fill in the structure of the deployment...
        return Yaml.toYaml(deployment);
    }

    public String createServiceTemplate() {
        Map<String, Object> service = new HashMap<>();
        // fill in the structure of the service...
        return Yaml.toYaml(service);
    }

    public String createConfigMapTemplate() {
        Map<String, Object> configMap = new HashMap<>();
        // fill in the structure of the ConfigMap...
        return Yaml.toYaml(configMap);
    }

    public String createSecretTemplate() {
        Map<String, Object> secret = new HashMap<>();
        // fill in the structure of the Secret...
        return Yaml.toYaml(secret);
    }

    public String createPersistentVolumeClaimTemplate() {
        Map<String, Object> pvc = new HashMap<>();
        // fill in the structure of the PersistentVolumeClaim...
        return Yaml.toYaml(pvc);
    }

    public String createIngressTemplate() {
        Map<String, Object> ingress = new HashMap<>();
        // fill in the structure of the Ingress...
        return Yaml.toYaml(ingress);
    }
}