package org.example.controllers.podman;

import pt.isel.leic.svlc.podman.http.PodmanHttp;
import pt.isel.leic.svlc.podman.cmd.PodmanCmd;
import pt.isel.leic.svlc.util.FromXml;
import pt.isel.leic.svlc.util.resources.Pod;
import pt.isel.leic.svlc.util.results.Failure;
import pt.isel.leic.svlc.util.results.Result;
import pt.isel.leic.svlc.util.results.Success;

import java.util.List;
import java.util.Map;

import static pt.isel.leic.svlc.util.executers.CommonExec.exec;

public class HttpController {
    public static void handleDeployHttp(String[] args) {
        Result<Failure, Success<Thread>> service = null;
        try {
            Pod pod = FromXml.podFromXml("ConfigFiles/" + args[3]);
            String ports = pod.getContainerList().get(0).getPortConfigList().get(0).getHostPort() + ":" + pod.getContainerList().get(0).getPortConfigList().get(0).getTargetPort();
            PodmanHttp podmanHttp = new PodmanHttp(pod.getName(), pod.getContainerList().get(0).getImage(), ports);
            service = exec(() -> PodmanCmd.startPodmanService(podmanHttp.getServiceIpPort())).print();
            podmanHttp.setPayload(
                Map.of(
                    "name", pod.getName(),
                    "portmappings", List.of(
                        podmanHttp.getPortsConf()
                    )
                )
            );
            exec(podmanHttp::createPod).print().then(() -> {
                podmanHttp.setQueryString(
                   Map.of("reference", podmanHttp.getImage())
                );
               return exec(podmanHttp::pullImage);
            }).print()
            .then(() -> exec(
                () -> {
                    podmanHttp.setPayload(Map.of(
                        "image", podmanHttp.getImage(),
                        "pod", podmanHttp.getPodName()
                    ));
                    return podmanHttp.createContainer();
                }
            )).print().then(() -> {
                podmanHttp.reset();
                return exec(podmanHttp::startPod);
            }).print().complete();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }finally {
            if (service != null) {
                service.right().value().interrupt();
            }
        }
    }

    public static void handleInspectHttp(String[] args) {
        PodmanHttp podmanHttp = new PodmanHttp();
        podmanHttp.setPodName(args[3]);
        Result<Failure, Success<Thread>> service = null;
        try {
            service = exec(() -> PodmanCmd.startPodmanService(podmanHttp.getServiceIpPort())).print();
            exec(podmanHttp::inspectPod).print().complete();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }finally {
            if (service != null) service.right().value().interrupt();
        }
    }
}
