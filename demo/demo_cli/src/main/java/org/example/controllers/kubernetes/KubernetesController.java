package org.example.controllers.kubernetes;

import pt.isel.leic.svlc.kubernetes.Kubernetes;
import pt.isel.leic.svlc.util.auth.Auth;
import pt.isel.leic.svlc.yaml.Templates;
import pt.isel.leic.svlc.yaml.YamlConverter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static pt.isel.leic.svlc.util.executers.CommonExec.exec;

public class KubernetesController {
    public static void handleDeployPod(String[] args) {
        Kubernetes kubernetes = new Kubernetes();
        // Create the secret
        String secretName = "my-secret";
        Map<String, String> data = new HashMap<>();
        data.put(".dockerconfigjson", Auth.registryKey());
        Map<String, Object> secret = Templates.createSecretTemplate(
                secretName,
                data,
                new HashMap<>(), // Assuming no labels for simplicity
                "kubernetes.io/dockerconfigjson"
        );

        // Create the pod, assuming imagePullSecrets is required
        List<Map<String, String>> imagePullSecrets = List.of(Map.of("name", secretName));
        String imagePullPolicy = "on-failure";
        Map<String, Object> pod = Templates.createPodTemplate(
                args[2],
                List.of(args[2] + "-container"),
                List.of(args[3]),
                imagePullPolicy,
                imagePullSecrets
        );

        //create service for the pod exposing the ports
        String[] ports = args[4].split(":");
        String type = "NodePort";
        Map<String, Object> portConfig = new HashMap<>();
        Map<String,String> portsMap = Map.of(
                "protocol", "TCP",
                "port", ports[0],
                "targetPort", ports[1],
                "nodePort", ports[2]
        );
        portConfig.put("ports", portsMap);
        Map<String, Object> service = Templates.createServiceTemplate(
                args[2],
                type,
                List.of(portConfig),
                Map.of(),
                Map.of()
        );

        try {
            String yaml = YamlConverter.serializeToYaml(List.of(secret, pod, service));
            exec( () -> kubernetes.deployFromYaml(yaml)).print().complete();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static void handlePodLogs(String[] args) {
        Kubernetes kubernetes = new Kubernetes();
        try {
            exec(()-> kubernetes.getPodLogs(args[2])).print().complete();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
