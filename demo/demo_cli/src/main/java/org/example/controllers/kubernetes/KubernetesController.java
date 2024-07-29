package org.example.controllers.kubernetes;

import pt.isel.leic.svlc.kubernetes.Kubernetes;
import pt.isel.leic.svlc.kubernetes.resources.Pod;
import pt.isel.leic.svlc.kubernetes.resources.Secret;
import pt.isel.leic.svlc.kubernetes.resources.Service;
import pt.isel.leic.svlc.util.executers.HttpExec;
import pt.isel.leic.svlc.util.kubernetes.configurations.CreateConfig;
import pt.isel.leic.svlc.util.kubernetes.configurations.InfoConfig;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static pt.isel.leic.svlc.util.auth.Auth.registryKey;
import static pt.isel.leic.svlc.util.executers.CommonExec.exec;

public class KubernetesController {
    public static void handleDeployPod(String[] args) {
        String secretName = "my-secret";
        Map<String, byte[]> data = new HashMap<>();
        byte[] registryKey = registryKey();
        data.put(".dockerconfigjson", registryKey);
        Secret secret = new Secret(secretName, "kubernetes.io/dockerconfigjson", data);
        // Create the pod, assuming imagePullSecrets is required
        List<String> imagePullSecrets = List.of(secretName);
        Integer[] ports = Arrays.stream(args[6].split(":")).map(Integer::parseInt).toArray(Integer[]::new);
        String imagePullPolicy = "IfNotPresent";
       // String imageName = args[4].split("/")[2];
        String externalIP = args[5];
        Pod pod = new Pod(
            args[3],
            args[4],
            Arrays.copyOfRange(ports, 1, ports.length),
            imagePullPolicy,
            imagePullSecrets
        );
        //create service for the pod exposing the ports
        // Example call with ClusterIP, adjust as necessary for your use case
        Service service = getService(args[2], externalIP ,ports);
        try {
            Kubernetes kubernetes = new Kubernetes(null, args[2]);
            exec( () -> kubernetes.createSecret(secret, new CreateConfig())).print().complete();
            exec( () -> kubernetes.createPod(pod, new CreateConfig())).print().complete();
            exec( () -> kubernetes.createService(service, new CreateConfig())).print().complete();
            Thread.sleep(2000);
            Map<String,Object> res = HttpExec.executeRequest("GET", "http://" + externalIP + ":" + ports[0] + "/goodbye", "", null, null);
            System.out.println(res);
            //Publisher.publish(imageName);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static Service getService(String podName, String ip ,Integer[] ports) {
        Map<String, Object> portConfig = new HashMap<>();
        portConfig.put("name", "http");
        portConfig.put("protocol", "TCP");
        portConfig.put("port", ports[0]);
        portConfig.put("targetPort", ports[1]);

        return new Service(
                podName,
                "loadBalancer",
                List.of(portConfig),
                List.of(ip)
        );
    }

    public static void handlePodLogs(String[] args) {
        try {
            Kubernetes kubernetes = new Kubernetes(null, args[2]);
            exec(()-> kubernetes.getPodLogs(new InfoConfig(args[3]))).print().complete();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
