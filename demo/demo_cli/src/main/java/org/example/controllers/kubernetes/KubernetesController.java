package org.example.controllers.kubernetes;

import pt.isel.leic.svlc.isos.Publisher;
import pt.isel.leic.svlc.kubernetes.Kubernetes;
import pt.isel.leic.svlc.util.FromXml;
import pt.isel.leic.svlc.util.resources.*;
import pt.isel.leic.svlc.util.kubernetes.configurations.CreateConfig;
import pt.isel.leic.svlc.util.kubernetes.configurations.InfoConfig;

import java.util.Map;

import static java.lang.Thread.sleep;
import static pt.isel.leic.svlc.util.auth.Auth.registryKey;
import static pt.isel.leic.svlc.util.executers.CmdExec.executeCommand;
import static pt.isel.leic.svlc.util.executers.CommonExec.exec;

public class KubernetesController {
    public static void handleDeployPod(String[] args) {
        try {
            Cluster cluster = FromXml.clusterFromXml(".\\ConfigFiles\\" + args[2]);
            Secret secret = cluster.getSecret();
            secret.setData(Map.of(".dockerconfigjson", registryKey()));
            Pod pod = cluster.getPod();
            String imageName = pod.getContainerList().get(0).getImage().split("/")[2];
            Integer targetPort = pod.getContainerList().get(0).getPortConfigList().get(0).getTargetPort();
            Service service = cluster.getService();
            Kubernetes kubernetes = new Kubernetes(cluster.getNamespace(), cluster.getName());
            exec( () -> kubernetes.createSecret(secret.toV1Secret(), new CreateConfig())).print().complete();
            exec( () -> kubernetes.createPod(pod.toV1Pod(), new CreateConfig())).print().complete();
            exec(() -> kubernetes.createService(service.toV1Service(), new CreateConfig())).print().complete();
            exec(() -> Publisher.publish(".\\isos\\" + imageName)).print().complete();
            sleep(2000);
            exec(() -> executeCommand(
                new String[]{"kubectl", "port-forward", "pods/" + pod.getName(), cluster.getPortForward() + ":" + targetPort},
                "",
                true
            ));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
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
